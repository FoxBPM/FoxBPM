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

import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMExpressionException;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.engine.impl.schedule.FoxbpmSchedulerGroupnameGernerater;
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
				// 处理跳过策略表达式
				if (StringUtil.isNotEmpty(skipStrategy.getSkipExpression())) {
					Expression expression = new ExpressionImpl(skipStrategy.getSkipExpression());
					try {
						LOG.debug("节点: {}({}) 跳过策略开始直接,跳过策略表达式内容为:\n {}", this.getName(), this.getId(), expression.getExpressionText());
						// 执行验证表达式
						valueObj = StringUtil.getBoolean(expression.getValue(executionContext));
						LOG.debug("节点: {}({}) 跳过策略直接结束,结果为 '{}'.", this.getName(), this.getId(), valueObj);
					} catch (Exception e) {
						LOG.error("节点: " + this.getName() + "(" + this.getId() + ") 跳过策略执行出错,错误信息: 【" + e.getMessage() + "】.", e);
						throw new FoxBPMExpressionException(ExceptionCode.EXPRESSION_EXCEPTION_SKIPSTRATEGY, this.getId(), this.getName(),
								expression.getExpressionText(), e);
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
		MultiInstanceLoopCharacteristics milc = (MultiInstanceLoopCharacteristics) loopCharacteristics;
		// 并行多实例处理
		LOG.debug("节点: {}({}) 含有并行多实例,将进入多实例'进入'阶段处理,令牌号: {}({}).", getName(), getId(), token.getName(), token.getId());
		// 输出数据集
		String loopDataOutputCollectionExpressionValue = milc.getLoopDataOutputCollection();
		// 多实例输入数据集
		String loopDataInputCollectionExpressionValue =milc.getLoopDataInputCollection();
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
		LOG.debug("\n【输出数据集】: \n{}", outputDataItemExpressionValue);
		LOG.debug("\n【完成条件】: \n{}", completionConditionExpressionValue);
		// 解决多实例处理退回BUG
		// 在进入多实例的第一次先清空多实例输出集合,以防历史数据影响。
		if (loopDataOutputCollectionExpressionValue != null && !loopDataOutputCollectionExpressionValue.equals("")) {
			Object valueObj = new ExpressionImpl(loopDataOutputCollectionExpressionValue).getValue(executionContext);
			if (valueObj != null) {
				if (valueObj instanceof Collection) {
					LOG.debug("清空多实例输出集合");
					// 如果计算结果为集合时清空数据
					((Collection<?>) valueObj).clear();
				} else {
					LOG.error("多实例输出集合不是一个集合");
					throw new FoxBPMExpressionException(ExceptionCode.EXPRESSION_EXCEPTION_LOOPDATAOUTPUTCOLLECTION_COLLECTIONCHECK,
							this.getId(), this.getName(), loopDataOutputCollectionExpressionValue);
				}
			}
		}
		// 开始触发多实例循环
		if (StringUtil.isEmpty(loopDataInputCollectionExpressionValue)) {
			LOG.error("多实例输入集合值为空");
			throw new FoxBPMExpressionException("多实例输入集合值为空");
		}

		// 生成一个唯一组号,用户多实例任务组的标识
		String groupID = GuidUtil.CreateGuid();
		// 给流程上下文设置唯一组号,好在多实例每次进入节点的时候将唯一组号传递给每个任务创建方法。
		token.setGroupID(groupID);
		LOG.debug("为多实例生成唯一组号: '{}'", groupID);
		// 执行多实例 输入数据集 解释
		Collection<?> valueObjCollection =getloopDataInputCollection(executionContext,loopDataInputCollectionExpressionValue);

		if (valueObjCollection == null) {
			LOG.error("多实例输出集合不是一个集合");
			throw new FoxBPMExpressionException(ExceptionCode.EXPRESSION_EXCEPTION_LOOPDATAOUTPUTCOLLECTION_COLLECTIONCHECK, this.getId(),
					this.getName(), loopDataOutputCollectionExpressionValue);
		}
		if (valueObjCollection.size() == 0) {

			LOG.error("多实例输入集合的个数为 0,请重新检查多实例输入集合配置.");
			throw new FoxBPMExpressionException(ExceptionCode.EXPRESSION_EXCEPTION_LOOPDATAINPUTCOLLECTIONEMPTY, this.getId(),
					this.getName(), loopDataInputCollectionExpressionValue);
		}

		// 判断事都是并行多实例
		if (milc.isSequential()) {
			// 串行多实例
			sequentialMultiInstanceExecute(executionContext, valueObjCollection, inputDataItemExpressionValue);

		} else {
			// 并行多实例
			parallelMultiInstanceExecute(executionContext, valueObjCollection, inputDataItemExpressionValue);
		}
	}
	
	protected Collection<?>  getloopDataInputCollection(FlowNodeExecutionContext executionContext,String loopDataInputCollectionExpressionValue){
		
		Object valueObj = null;
		try {
			valueObj = new ExpressionImpl(loopDataInputCollectionExpressionValue).getValue(executionContext);
		} catch (Exception e) {
			LOG.error("多实例输入数据集解释出错,错误信息: " + e.getMessage(), e);
			throw new FoxBPMExpressionException(ExceptionCode.EXPRESSION_EXCEPTION_LOOPDATAOUTPUTCOLLECTION_COLLECTIONCHECK, this.getId(),
					this.getName(), loopDataInputCollectionExpressionValue, e);
		}
		if (valueObj == null) {
			LOG.error("多实例输入集合值为空");
			throw new FoxBPMExpressionException("多实例输入集合值为空");
		}

		Collection<?> valueObjCollection = null;

		if (valueObj instanceof Collection) {
			valueObjCollection = (Collection<?>) valueObj;
		}

		if (valueObj instanceof String[]) {
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
		if (valueObj instanceof String && valueObj != null && !valueObj.equals("")) {
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
		return valueObjCollection;
	}

	/** 串行多实例处理 */
	protected void sequentialMultiInstanceExecute(FlowNodeExecutionContext executionContext, Collection<?> valueObjCollection,
			String inputDataItemExpressionValue) {
		TokenEntity token=(TokenEntity)executionContext;
		int loopCount=token.getLoopCount();
		
		Object[] object = valueObjCollection.toArray();

		LOG.debug("串行多实例循环第 '{}' 次开始执行,循环值为: '{}'", loopCount, StringUtil.getString(object[loopCount]));
		try {
			// 将循环的每个变量赋值给输入数据项
			ExpressionMgmt.setVariable(inputDataItemExpressionValue, object, executionContext);
		} catch (Exception e) {
			LOG.error("串行多实例循环第 '" + loopCount + "' 次执行出错,错误信息: " + e.getMessage(), e);
			throw new FoxBPMExpressionException(ExceptionCode.EXPRESSIONEXCEPTION_COLLECTIONININPUTDATAITEM, this.getId(), this.getName(),
					inputDataItemExpressionValue, e);
		}
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
	protected void parallelMultiInstanceExecute(FlowNodeExecutionContext executionContext, Collection<?> valueObjCollection,
			String inputDataItemExpressionValue) {

		int i = 1;
		for (Object object : valueObjCollection) {
			LOG.debug("多实例循环第 '{}' 次开始执行,循环值为: '{}'", i, StringUtil.getString(object));
			try {
				// 将循环的每个变量赋值给输入数据项
				ExpressionMgmt.setVariable(inputDataItemExpressionValue, object, executionContext);
			} catch (Exception e) {
				LOG.error("多实例循环第 '" + i + "' 次执行出错,错误信息: " + e.getMessage(), e);
				throw new FoxBPMExpressionException(ExceptionCode.EXPRESSIONEXCEPTION_COLLECTIONININPUTDATAITEM, this.getId(),
						this.getName(), inputDataItemExpressionValue, e);
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
		Expression completionCondition = new ExpressionImpl(milc.getCompletionCondition());
		String completionConditionExpressionValue = completionCondition != null ? completionCondition.getExpressionText() : null;
		// 打印日志信息
		LOG.debug("\n多实例配置信息: \n 【输入数据集】: \n{}", loopDataInputCollectionExpressionValue);
		LOG.debug("\n【输入项编号】: \n{}", inputDataItemExpressionValue);
		LOG.debug("\n【输出项编号】: \n{}", outputDataItemExpressionValue);
		LOG.debug("\n【输出数据集】: \n{}", outputDataItemExpressionValue);
		LOG.debug("\n【完成条件】: \n{}", completionConditionExpressionValue);

		if (loopDataOutputCollectionExpressionValue != null && !loopDataOutputCollectionExpressionValue.equals("")) {
			Object valueObj = ExpressionMgmt.execute(loopDataOutputCollectionExpressionValue, executionContext);
			if (valueObj != null) {
				if (valueObj instanceof Collection) {
					Collection collection = (Collection) valueObj;
					collection.add(new ExpressionImpl(outputDataItemExpressionValue).getValue(executionContext));
				} else {
					throw new FoxBPMException("输出集合类型必须是Collection");
				}
			} else {
				throw new FoxBPMException("输出集合为null:" + loopDataOutputCollectionExpressionValue);
			}
		}

		if (completionConditionExpressionValue == null || completionConditionExpressionValue.equals("")) {
			LOG.error("节点: " + getName() + "(" + getId() + ") 多实例完成条件为空.");
			throw new FoxBPMExpressionException(ExceptionCode.EXPRESSIONEXCEPTION_CONDITIONEXPRESSIONEMPTY, this.getId(), this.getName(),
					"");
		}
		
		boolean isCompletion = false;
		try {
			isCompletion = StringUtil.getBoolean(ExpressionMgmt.execute(completionConditionExpressionValue, executionContext));
		} catch (Exception e) {
			LOG.error("节点: " + getName() + "(" + getId() + ") 多实例完成条件计算出错.", e);
			throw new FoxBPMExpressionException(ExceptionCode.EXPRESSIONEXCEPTION_CONDITIONEXPRESSIONERROR, this.getId(), this.getName(),
					"");
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
			String inputDataItemExpressionValue = milc.getInputDataItem();
			TokenEntity token=(TokenEntity)executionContext;
			token.setLoopCount(1);
			if(token.getLoopCount()==valueObjCollection.size()){
				throw new FoxBPMException("串行多实例集合全部处理完毕,但完成条件依然不满足!");
			}
			sequentialMultiInstanceExecute(executionContext, valueObjCollection, inputDataItemExpressionValue);
			
		}
	}

	protected void parallelMultiInstanceLeave(FlowNodeExecutionContext executionContext,boolean isCompletion) {
		if (isCompletion) {
			super.leave(executionContext);
		}
	}

	 
	public void cleanData(FlowNodeExecutionContext executionContext) {
		try {
			FoxbpmScheduler foxbpmScheduler =Context.getProcessEngineConfiguration().getFoxbpmScheduler();
			if (foxbpmScheduler == null) {
				LOG.debug("自动调度器未启动，导致任务节点离开时清空相关数据失败！");
				return;
			}
			String groupName = null;
			groupName = new FoxbpmSchedulerGroupnameGernerater(executionContext).gernerateDefinitionGroupName();
			foxbpmScheduler.deleteJobsByGroupName(groupName);
		} catch (Exception e) {
			throw new FoxBPMException("Activity 离开时清空 节点调度器报错", e);
		}
		TokenEntity token = (TokenEntity) executionContext;
		token.setGroupID(null);
		token.setLoopCount(0);
		super.cleanData(executionContext);
	}
}
