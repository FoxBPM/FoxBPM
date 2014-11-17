/**
 * Copyright 1996-2014 FoxBPM ORG.
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
 * @author ych
 */
package org.foxbpm.engine.impl.task.cmd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.entity.ProcessOperatingEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.VariableInstanceEntity;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtDefinition;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.task.command.AbstractCustomExpandTaskCommand;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.DataVarUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.model.Activity;
import org.foxbpm.model.DataVariableDefinition;

public abstract class AbstractExpandTaskCmd<P extends AbstractCustomExpandTaskCommand, T> implements Command<T>,
    Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务编号
	 */
	protected String taskId;
	
	/**
	 * 任务命令类型
	 */
	protected String commandType;
	
	/**
	 * 任务命令编号
	 */
	protected String taskCommandId;
	
	/**
	 * 任务意见
	 */
	protected String taskComment;
	
	/**
	 * 瞬态流程实例变量Map
	 */
	protected Map<String, Object> transientVariables = null;
	
	/**
	 * 持久化流程实例变量Map
	 */
	protected Map<String, Object> persistenceVariables = null;
	
	protected ExpandTaskCommand expandTaskCommand = null;
	
	protected String businessKey;
	
	protected String initiator;
	
	protected String processDefinitionKey;
	
	protected String agent;
	
	protected String admin;
	
	public AbstractExpandTaskCmd(P abstractCustomExpandTaskCommand) {
		
		this.taskId = abstractCustomExpandTaskCommand.getExpandTaskCommand().getTaskId();
		this.commandType = abstractCustomExpandTaskCommand.getExpandTaskCommand().getCommandType();
		this.taskCommandId = abstractCustomExpandTaskCommand.getExpandTaskCommand().getTaskCommandId();
		this.taskComment = abstractCustomExpandTaskCommand.getExpandTaskCommand().getTaskComment();
		this.transientVariables = abstractCustomExpandTaskCommand.getExpandTaskCommand().getTransientVariables();
		this.persistenceVariables = abstractCustomExpandTaskCommand.getExpandTaskCommand().getPersistenceVariables();
		this.expandTaskCommand = abstractCustomExpandTaskCommand.getExpandTaskCommand();
		
		this.initiator = abstractCustomExpandTaskCommand.getExpandTaskCommand().getInitiator();
		this.processDefinitionKey = abstractCustomExpandTaskCommand.getExpandTaskCommand().getProcessDefinitionKey();
		this.businessKey = abstractCustomExpandTaskCommand.getExpandTaskCommand().getBusinessKey();
		this.agent = abstractCustomExpandTaskCommand.getExpandTaskCommand().getAgent();
		this.admin = abstractCustomExpandTaskCommand.getExpandTaskCommand().getAdmin();
	}
	
	public T execute(CommandContext commandContext) {
		
		if (StringUtil.isEmpty(taskId)) {
			throw ExceptionUtil.getException("10501001");
		}
		
		TaskEntity task = Context.getCommandContext().getTaskManager().findTaskById(taskId);
		
		if (task == null) {
			throw ExceptionUtil.getException("10502001", taskId);
		}
		
		if (task.hasEnded()) {
			throw ExceptionUtil.getException("10503001", taskId);
		}
		if (persistenceVariables != null && persistenceVariables.size() > 0) {
			task.setProcessInstanceVariables(persistenceVariables);
		}
		if (transientVariables != null && transientVariables.size() > 0) {
			task.setProcessInstanceTransientVariables(transientVariables);
		}
		// 增加流程更新时间功能，by ych
		task.getProcessInstance().setUpdateTime(ClockUtil.getCurrentTime());
		// 对所有操作插入操作
		// createProcessOperating(task, getTaskCommand(task), taskComment);
		// 处理流程实例数据库变量优化
		loadDatabaseVariables(task);
		return execute(commandContext, task);
	}
	
	protected void loadDatabaseVariables(TaskEntity task) {
		String bizName = null;
		String bizkeyField = null;
		Map<String, DataVariableDefinition> bizTypeDataVarMap = new HashMap<String, DataVariableDefinition>();
		DataVariableDefinition dataVariableDefinition = null;
		// 获取流程定义变量
		DataVariableMgmtDefinition dataVariableMgmtDefinition = task.getProcessDefinition().getDataVariableMgmtDefinition();
		if (null != dataVariableMgmtDefinition) {
			List<DataVariableDefinition> dataVariableDefinitions = dataVariableMgmtDefinition.getDataVariableDefinitions();
			if (null != dataVariableDefinitions) {
				for (Iterator<DataVariableDefinition> iterator = dataVariableDefinitions.iterator(); iterator.hasNext();) {
					dataVariableDefinition = iterator.next();
					if (Constant.DB_BIZTYPE.equals(dataVariableDefinition.getBizType())) {
						bizTypeDataVarMap.put(dataVariableDefinition.getId(), dataVariableDefinition);
					} else if (Constant.BIZTYPE_CUSTOMVARIABLE.equals(dataVariableDefinition.getBizType())) {
						if (Constant._BIZNAME.equals(dataVariableDefinition.getId())) {
							bizName = dataVariableDefinition.getExpression().replace("\"", "");
						} else if (Constant._BIZKEYFIELD.equals(dataVariableDefinition.getId())) {
							bizkeyField = dataVariableDefinition.getExpression().replace("\"", "");
						}
					}
				}
			}
		}
		// 获取流程实例变量
		DataVariableMgmtInstance dataVariableMgmtInstance = task.getProcessInstance().getDataVariableMgmtInstance();
		if (null != dataVariableMgmtInstance) {
			List<VariableInstanceEntity> variableInstanceEntitys = dataVariableMgmtInstance.getDataVariableEntities();
			if (null != variableInstanceEntitys) {
				VariableInstanceEntity variableInstanceEntity = null;
				for (Iterator<VariableInstanceEntity> iterator = variableInstanceEntitys.iterator(); iterator.hasNext();) {
					variableInstanceEntity = iterator.next();
					// 删除已经处理过的变量
					bizTypeDataVarMap.remove(variableInstanceEntity.getId());
				}
			}
		}
		// 查询业务数据
		Map<String, DataVariableDefinition> dataVals = new HashMap<String, DataVariableDefinition>();
		if (null != bizName && null != bizkeyField) {
			Map<String, Object> dataValues = DataVarUtil.getInstance().getDataValue(null, task.getBizKey(), bizName, bizkeyField);
			if (null != dataValues) {
				DataVariableDefinition dataVarDefin = null;
				for (Iterator<DataVariableDefinition> iterator = bizTypeDataVarMap.values().iterator(); iterator.hasNext();) {
					dataVarDefin = iterator.next();
					if (dataValues.containsKey(dataVarDefin.getFieldName())) {
						dataVarDefin.setExpression(StringUtil.getString(dataValues.get(dataVarDefin.getFieldName())));
						dataVals.put(dataVarDefin.getFieldName(), dataVarDefin);
					}
				}
			}
		}
		
		// 添加变量至流程变量管理和脚本变量中(即使暂无业务数据)
		for (Iterator<DataVariableDefinition> iterator = dataVals.values().iterator(); iterator.hasNext();) {
			dataVariableDefinition = iterator.next();
			dataVariableMgmtInstance.createDataVariableInstance(dataVariableDefinition);
			ExpressionMgmt.setVariable(dataVariableDefinition.getId(), dataVariableDefinition.getExpression());
		}
	}
	
	protected void createProcessOperating(TaskEntity task, TaskCommand taskCommand, String taskComment) {
		
		ProcessOperatingEntity processOperating = new ProcessOperatingEntity();
		processOperating.setId(GuidUtil.CreateGuid());
		processOperating.setOperatingTime(ClockUtil.getCurrentTime());
		processOperating.setTask(task);
		processOperating.setTaskCommand(taskCommand);
		processOperating.setOperatingComment(taskComment);
		
		/** 插入流程操作 */
		Context.getCommandContext().getProcessOperatingManager().insert(processOperating);
	}
	
	/** 子类需要实现这个方法 */
	protected abstract T execute(CommandContext commandContext, TaskEntity task);
	
	/** 获取任务命令 */
	protected TaskCommand getTaskCommand(TaskEntity task) {
		
		TaskDefinition taskDefinition = task.getTaskDefinition();
		
		TaskCommand taskCommand = taskDefinition.getTaskCommand(taskCommandId);
		
		return taskCommand;
		
	}
	
	protected FlowNodeExecutionContext getExecutionContext(TaskEntity task) {
		return (FlowNodeExecutionContext) task.getToken();
	}
	
	protected KernelProcessDefinition getProcessDefinition(TaskEntity task) {
		return task.getProcessDefinition();
	}
	
	protected CommandExecutor getCommandExecutor() {
		CommandExecutor commandExecutor = Context.getProcessEngineConfiguration().getCommandExecutor();
		return commandExecutor;
	}
	
	protected boolean isMutilInstance(KernelFlowNode kernelFlowNode) {
		KernelFlowNodeBehavior kernelBehavior = kernelFlowNode.getKernelFlowNodeBehavior();
		if (kernelBehavior instanceof ActivityBehavior) {
			ActivityBehavior actBehavior = (ActivityBehavior) kernelBehavior;
			Activity activity = (Activity) actBehavior.getBaseElement();
			if (activity.getLoopCharacteristics() != null) {
				return true;
			}
		}
		return false;
	}
	
}
