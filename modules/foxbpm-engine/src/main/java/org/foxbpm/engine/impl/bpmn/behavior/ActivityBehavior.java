/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.schedule.FoxbpmSchedulerGroupnameGernerater;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.model.Activity;
import org.foxbpm.model.LoopCharacteristics;
import org.foxbpm.model.MultiInstanceLoopCharacteristics;
import org.foxbpm.model.SkipStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityBehavior extends FlowNodeBehavior {

	private static final long serialVersionUID = 1L;

	private static Logger LOG = LoggerFactory.getLogger(ActivityBehavior.class);
	
	private List<BoundaryEventBehavior> boundaryEventBehaviors = new ArrayList<BoundaryEventBehavior>();

	public void enter(FlowNodeExecutionContext executionContext) {

		Activity activity = (Activity)baseElement;
		SkipStrategy skipStrategy = activity.getSkipStrategy();
		/** 事件顺序如下,先判断跳过策略,再判断边界事件,再执行多实例,最后执行节点进入事件 */
		/** 判断跳过策略 */
		if (skipStrategy != null) {
			boolean isEnable = skipStrategy.isEnable();
			// 判断是否启用
			if (isEnable) {
				// 启用跳过策略处理的情况
				LOG.debug("节点: {}({}) 启用跳过策略.", this.getName(), this.getId());
				boolean valueObj = false;
				String skipExpression = skipStrategy.getSkipExpression();
				// 处理跳过策略表达式
				if (StringUtil.isNotEmpty(skipExpression)) {
					try {
						LOG.debug("节点: {}({}) 跳过策略开始直接,跳过策略表达式内容为:\n {}", this.getName(), this.getId(), skipExpression);
						// 执行验证表达式
						valueObj = StringUtil.getBoolean(ExpressionMgmt.execute(skipExpression, executionContext));
						LOG.debug("节点: {}({}) 跳过策略直接结束,结果为 '{}'.", this.getName(), this.getId(), valueObj);
					} catch (Exception e) {
						throw ExceptionUtil.getException("10404012", e, this.getFlowNode().getId());
					}
				}
				if (valueObj) {
					// 直接跳过
					boolean isCreateSkipProcess = skipStrategy.isCreateSkipTaskRecord();
					if (isCreateSkipProcess) {
						skipTaskRecord(executionContext, skipStrategy);
					}
					LOG.debug("跳过策略生效,跳过节点: {}({}).", this.getName(), this.getId());
					// 节点离开
					leave(executionContext);
					return;
				} else {
					LOG.debug("节点: {}({}),跳过策略未生效.", this.getName(), this.getId());
				}
			}
		}
		eventExecute(executionContext);
	}

	protected void eventExecute(FlowNodeExecutionContext executionContext) {

		List<BoundaryEventBehavior> boundaryEventBehaviors = getBoundaryEventBehaviors();
		// 验证是否含有边界事件
		if (boundaryEventBehaviors.size() > 0) {
			/*
			 * 边界事件的处理逻辑是这样的, 如果有边界事件,主令牌创建一个儿子,进入到当前节点, 主令牌自己去执行边界事件的执行方法,
			 * 当定时到的时候如果是中断边界,则直接杀掉主令牌所有的儿子,将主令牌推下去
			 * 如果是非中断,则主令牌再生成个儿子给边界事件继续向下走,最后需要并行合并网关将他们收回。
			 */
			TokenEntity tokenEntity = (TokenEntity) executionContext;

			LOG.debug("节点: {}({}) 含有 {} 个边界事件,令牌号: {}({}).", this.getName(), this.getId(), boundaryEventBehaviors.size(), tokenEntity.getName(),
					tokenEntity.getId());
			LOG.debug("主令牌: {}({}),进入节点.", tokenEntity.getName(), tokenEntity.getId());
			// 将子令牌放入节点
			forkedTokenEnter(tokenEntity);
			if (tokenEntity.getFlowNode().getId().equals(this.getId())) {
				// 遍历边界事件
				for (BoundaryEventBehavior boundaryEvent : getBoundaryEventBehaviors()) {
					LOG.debug("主令牌: {}({}),触发边界事件: {} 执行.", tokenEntity.getName(), tokenEntity.getId(), boundaryEvent.getId());
					// 主令牌执行边界事件里的事件定义
					boundaryEvent.execute(executionContext);
				}
			}
		} else {
			// 没有边界事件,则执行多实例执行
			loopExecute(executionContext);
		}

	}
	
	public List<BoundaryEventBehavior> getBoundaryEventBehaviors(){
		return boundaryEventBehaviors;
	}
	
	public void setBoundaryEventBehaviors(List<BoundaryEventBehavior> boundaryEventBehaviors) {
		this.boundaryEventBehaviors = boundaryEventBehaviors;
	}

	private void forkedTokenEnter(FlowNodeExecutionContext executionContext) {

		executionContext.ensureEnterInitialized(getFlowNode());

		loopExecute(executionContext);

	}

	protected void loopExecute(FlowNodeExecutionContext executionContext) {
		Activity activity = (Activity)baseElement;
		
		// 获取 Activity 的多实例信息
		LoopCharacteristics loopCharacteristics = activity.getLoopCharacteristics();
		// 非多实例执行
		if (loopCharacteristics == null || !(loopCharacteristics instanceof MultiInstanceLoopCharacteristics)) {
			// 执行令牌进入节点方法
			executionContext.execute();
			return;
		}

		// 多实例执行
		TokenEntity token = (TokenEntity) executionContext;
		
		// 生成一个唯一组号,用户多实例任务组的标识
		String groupID = GuidUtil.CreateGuid();
		// 给流程上下文设置唯一组号,好在多实例每次进入节点的时候将唯一组号传递给每个任务创建方法。
		token.setGroupID(groupID);
		LOG.debug("为多实例生成唯一组号: '{}'", groupID);
		
		MultiInstanceLoopCharacteristics milc = (MultiInstanceLoopCharacteristics) loopCharacteristics;
		
		// 解决多实例处理退回BUG
		// 在进入多实例的第一次先清空多实例输出集合,以防历史数据影响。
		// 输出数据集
		String loopDataOutputCollectionExpressionValue = milc.getLoopDataOutputCollection();
		if (loopDataOutputCollectionExpressionValue != null && !loopDataOutputCollectionExpressionValue.equals("") && token.getLoopCount() == 0) {
			Object valueObj = null;
			try {
				valueObj = ExpressionMgmt.execute(loopDataOutputCollectionExpressionValue, executionContext);
			} catch (Exception e) {
				throw ExceptionUtil.getException("10404001",this.flowNode.getId());
			}
			if (valueObj != null) {
				if (valueObj instanceof Collection) {
					LOG.debug("清空多实例输出集合");
					// 如果计算结果为集合时清空数据
					((Collection<?>) valueObj).clear();
				} else {
					throw ExceptionUtil.getException("10404003",this.flowNode.getId());
				}
			}
		}
				
		// 判断事都是并行多实例
		if (milc.isSequential()) {
			// 串行多实例
			sequentialMultiInstanceExecute(executionContext, milc);

		} else {
			// 并行多实例
			parallelMultiInstanceExecute(executionContext, milc);
		}
	}
	
	protected Collection<?>  getloopDataInputCollection(FlowNodeExecutionContext executionContext,String loopDataInputCollectionExpressionValue){
		
		Object valueObj = null;
		try {
			valueObj = ExpressionMgmt.execute(loopDataInputCollectionExpressionValue, executionContext);
		} catch (Exception e) {
			throw ExceptionUtil.getException("01404009",e,this.flowNode.getId());
		}

		Collection<?> valueObjCollection = null;

		if (valueObj instanceof Collection) {
			valueObjCollection = (Collection<?>) valueObj;
		}

		else if (valueObj instanceof String[]) {
			// 如果是个字符串数组
			String[] valueObjString = (String[]) valueObj;
			// 循环执行 将令牌多次放入节点
			List<String> valueList = new ArrayList<String>();
			for (int i = 0; i < valueObjString.length; i++) {
				valueList.add(valueObjString[i]);
			}
			valueObjCollection = (Collection<?>) valueList;
		}
		// 如果是一个逗号分割的字符串
		else if (valueObj instanceof String && valueObj != null && !valueObj.equals("")) {
			// 将字符串转换为字符串数组
			String[] valueObjString = valueObj.toString().split(",");
			// 如果大于0再处理
			if (valueObjString.length > 0) {
				// 循环执行 将令牌多次放入节点
				List<String> valueList = new ArrayList<String>();
				for (int i = 0; i < valueObjString.length; i++) {
					valueList.add(valueObjString[i]);
				}
				valueObjCollection = (Collection<?>) valueList;
			}
		}
		else{
			throw ExceptionUtil.getException("01404013");
		}
		return valueObjCollection;
	}


	/** 串行多实例处理 */
	protected void sequentialMultiInstanceExecute(FlowNodeExecutionContext executionContext,MultiInstanceLoopCharacteristics milc) {
		TokenEntity token=(TokenEntity)executionContext;
		int loopCount=token.getLoopCount();
		// 多实例输入数据集
		String loopDataInputCollectionExpressionValue =milc.getLoopDataInputCollection();
		// 多实例输入项
		String inputDataItemExpressionValue = milc.getInputDataItem();
		Collection<?> valueObjCollection =getloopDataInputCollection(executionContext,loopDataInputCollectionExpressionValue);
		Object[] object = valueObjCollection.toArray();
		if(object.length <= loopCount){
			throw ExceptionUtil.getException("串行多实例循环错误，循环次数超过了输入数据集的个数，当前循环次数："+loopCount+"，输入数据集总长度"+object.length);
		}
		LOG.debug("串行多实例循环第 '{}' 次开始执行,循环值为: '{}'", loopCount, StringUtil.getString(object[loopCount]));
		try {
			// 将循环的每个变量赋值给输入数据项
			ExpressionMgmt.setVariable(inputDataItemExpressionValue, object[loopCount], executionContext);
		} catch (Exception e) {
			throw ExceptionUtil.getException("01404008",e,this.flowNode.getId());
		}
		token.setLoopCount(token.getLoopCount()+1);
		// 执行令牌进入节点方法
		executionContext.execute();

	}

	/**
	 * 并行多实例执行逻辑
	 * 
	 * @param executionContext
	 *            令牌
	 * @param valueObjCollection
	 *            入参集合
	 * @param inputDataItemExpressionValue
	 *            输入参数变量
	 */
	protected void parallelMultiInstanceExecute(FlowNodeExecutionContext executionContext,MultiInstanceLoopCharacteristics milc) {
		
		TokenEntity token = (TokenEntity)executionContext;
		// 并行多实例处理
		LOG.debug("节点: {}({}) 含有并行多实例,将进入多实例'进入'阶段处理,令牌号: {}({}).", getName(), getId(), token.getName(), token.getId());
		// 多实例输入数据集
		String loopDataInputCollectionExpressionValue =milc.getLoopDataInputCollection();
		// 多实例输入项
		String inputDataItemExpressionValue = milc.getInputDataItem();
		// 打印日志信息
		LOG.debug("\n多实例输入配置信息: \n 【输入数据集】: \n{}", loopDataInputCollectionExpressionValue);
		LOG.debug("\n【输入项编号】: \n{}", inputDataItemExpressionValue);
		// 开始触发多实例循环
		if (StringUtil.isEmpty(loopDataInputCollectionExpressionValue)) {
			throw ExceptionUtil.getException("10404009",this.flowNode.getId());
		}
		// 执行多实例 输入数据集 解释
		Collection<?> valueObjCollection =getloopDataInputCollection(executionContext,loopDataInputCollectionExpressionValue);

		if (valueObjCollection == null) {
			throw ExceptionUtil.getException("10404010",this.flowNode.getId());
		}
		if (valueObjCollection.size() == 0) {
			throw ExceptionUtil.getException("10404011",this.flowNode.getId());
		}
		
		int i = 1;
		for (Object object : valueObjCollection) {
			LOG.debug("多实例循环第 '{}' 次开始执行,循环值为: '{}'", i, StringUtil.getString(object));
			try {
				// 将循环的每个变量赋值给输入数据项
				ExpressionMgmt.setVariable(inputDataItemExpressionValue, object, executionContext);
			} catch (Exception e) {
				throw ExceptionUtil.getException("01404011",this.flowNode.getId());
			}
			// 执行令牌进入节点方法
			executionContext.execute();
			// 循环计数器自加1
			i = i + 1;
		}

	}

	/** 跳过策略记录 */
	protected void skipTaskRecord(FlowNodeExecutionContext executionContext, SkipStrategy skipStrategy) {
		/** 需要生成记录的节点重写这个方法 */

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	 
	public void leave(FlowNodeExecutionContext executionContext) {

		Activity activity = (Activity)baseElement;
		TokenEntity token = (TokenEntity) executionContext;
		// 获取 Activity 的多实例信息
		LoopCharacteristics loopCharacteristics = activity.getLoopCharacteristics();
		// 非多实例执行
		if (loopCharacteristics == null || !(loopCharacteristics instanceof MultiInstanceLoopCharacteristics)) {
			// 执行令牌进入节点方法
			super.leave(executionContext);
			return;
		}
		// 并行多实例处理
		MultiInstanceLoopCharacteristics milc = (MultiInstanceLoopCharacteristics) loopCharacteristics;
		LOG.debug("节点: {}({}) 含有并行多实例,将进入多实例'离开'阶段处理,令牌号: {}({}).", this.getName(), this.getId(), token.getName(), token.getId());
		// 输出数据集
		String loopDataOutputCollectionExpressionValue =milc.getLoopDataOutputCollection();
		// 多实例输入数据集
		String loopDataInputCollectionExpressionValue = milc.getLoopDataInputCollection();
		// 多实例输入项
		String inputDataItemExpressionValue = milc.getInputDataItem();
		// 多实例输出项
		String outputDataItemExpressionValue = milc.getOutputDataItem();
		// 完成条件
		String completionConditionExpressionValue = milc.getCompletionCondition();
		// 打印日志信息
		LOG.debug("\n多实例配置信息: \n 【输入数据集】: \n{}", loopDataInputCollectionExpressionValue);
		LOG.debug("\n【输入项编号】: \n{}", inputDataItemExpressionValue);
		LOG.debug("\n【输出项编号】: \n{}", outputDataItemExpressionValue);
		LOG.debug("\n【输出数据集】: \n{}", loopDataOutputCollectionExpressionValue);
		LOG.debug("\n【完成条件】: \n{}", completionConditionExpressionValue);

		if (loopDataOutputCollectionExpressionValue != null && !loopDataOutputCollectionExpressionValue.equals("")) {
			Object valueObj = null;
			try {
				valueObj = ExpressionMgmt.execute(loopDataOutputCollectionExpressionValue, executionContext);
			} catch (Exception e) {
				throw ExceptionUtil.getException("10404001",this.flowNode.getId());
			}
			if (valueObj != null) {
				if (valueObj instanceof Collection) {
					Collection collection = (Collection) valueObj;
					try {
						collection.add(ExpressionMgmt.execute(outputDataItemExpressionValue, executionContext));
					} catch (Exception e) {
						throw ExceptionUtil.getException("10404002",this.flowNode.getId());
					}
				} else {
					throw ExceptionUtil.getException("10404003",this.flowNode.getId());
				}
			} else {
				throw ExceptionUtil.getException("10404004",this.flowNode.getId());
			}
		}

		if (completionConditionExpressionValue == null || completionConditionExpressionValue.equals("")) {
			throw ExceptionUtil.getException("10404005",this.flowNode.getId());
		}
		
		boolean isCompletion = false;
		Object flag = null;
		try {
			flag = ExpressionMgmt.execute(completionConditionExpressionValue, executionContext);
		} catch (Exception e) {
			throw ExceptionUtil.getException("10404006",this.flowNode.getId());
		}
		
		try{
			isCompletion = StringUtil.getBoolean(flag);
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404007",this.flowNode.getId());
		}

		if (isCompletion) {
			LOG.debug("节点: {}({}) 多实例完成条件验证通过,令牌号: {}({}).", getName(), getId(), token.getName(), token.getId());
		} else {		
			LOG.debug("节点: {}({}) 多实例完成条件验证不通过,令牌将继续停留在当前节点,令牌号: {}({}).", getName(), getId(), token.getName(), token.getId());
		}
		
		if(milc.isSequential()){
			sequentialMultiInstanceLeave(executionContext,isCompletion);
		}else{
			parallelMultiInstanceLeave(executionContext,isCompletion);
		}
	}

	protected void sequentialMultiInstanceLeave(FlowNodeExecutionContext executionContext,boolean isCompletion) {
		if (isCompletion) {
			super.leave(executionContext);
		}else{
			Activity activity = (Activity)baseElement;
			LoopCharacteristics loopCharacteristics = activity.getLoopCharacteristics();
			MultiInstanceLoopCharacteristics milc = (MultiInstanceLoopCharacteristics) loopCharacteristics;
			Collection<?> valueObjCollection =getloopDataInputCollection(executionContext,milc.getLoopDataInputCollection());
			TokenEntity token=(TokenEntity)executionContext;
			if(token.getLoopCount() == valueObjCollection.size()){
				throw ExceptionUtil.getException("串行多实例循环完成，但是不满足完成条件，流程无法继续执行！");
			}
			sequentialMultiInstanceExecute(executionContext,milc);
		}
	}

	protected void parallelMultiInstanceLeave(FlowNodeExecutionContext executionContext,boolean isCompletion) {
		if (isCompletion) {
			super.leave(executionContext);
		}
		
		//
	}

	 
	public void cleanData(FlowNodeExecutionContext executionContext) {
		try {
			FoxbpmScheduler foxbpmScheduler =Context.getProcessEngineConfiguration().getFoxbpmScheduler();
			if (foxbpmScheduler == null || foxbpmScheduler.getScheduler() == null) {
				LOG.debug("自动调度器未启动，导致任务节点离开时清空相关数据失败！");
			}else{
				String groupName = null;
				groupName = new FoxbpmSchedulerGroupnameGernerater(executionContext).gernerateDefinitionGroupName();
				foxbpmScheduler.deleteJobsByGroupName(groupName);
			}
		} catch (Exception e) {
			throw ExceptionUtil.getException("10408001",e,this.getFlowNode().getId());
		}
		TokenEntity token = (TokenEntity) executionContext;
		token.setGroupID(null);
		token.setLoopCount(0);
		super.cleanData(executionContext);
	}
}
