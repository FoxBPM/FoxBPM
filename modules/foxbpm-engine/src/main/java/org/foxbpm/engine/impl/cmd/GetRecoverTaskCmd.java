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
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FixFlowException;
import org.foxbpm.engine.factory.ProcessObjectFactory;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TaskCommandInst;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.persistence.TaskManager;
import org.foxbpm.engine.impl.runtime.ProcessInstanceEntity;
import org.foxbpm.engine.impl.runtime.TokenEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ExecutionContext;
import org.foxbpm.engine.task.TaskInstance;
import org.foxbpm.engine.task.TaskInstanceType;
import org.foxbpm.engine.task.TaskQuery;


public class GetRecoverTaskCmd implements Command<List<TaskInstance>>{
	
	protected String taskId;
	
	protected String taskCommandId;
	
	public GetRecoverTaskCmd(String taskId,String taskCommandId)
	{
		this.taskId=taskId;
		this.taskCommandId=taskCommandId;
	}
	
	public List<TaskInstance> execute(CommandContext commandContext) {
		
		
		TaskManager taskManager = commandContext.getTaskManager();

		TaskInstance taskInstanceQuery = taskManager.findTaskById(taskId);
		
		if(taskId==null||taskId.equals("")){
			throw new FixFlowException("taskId不能为空");
		}

		
		if(taskInstanceQuery==null){
			throw new FixFlowException("没有查询到相关任务");
		}
		


		

		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
		List<TaskInstance> taskInstancesEnd = taskQuery.processInstanceId(taskInstanceQuery.getProcessInstanceId())
				.taskAssignee(Authentication.getAuthenticatedUserId()).addTaskType(TaskInstanceType.FIXFLOWTASK).addTaskType(TaskInstanceType.FIXNOTICETASK).taskIsEnd().list();

		String tokenId = taskInstanceQuery.getTokenId();
		String nodeId = taskInstanceQuery.getNodeId();
		String processDefinitionId = taskInstanceQuery.getProcessDefinitionId();
		ProcessInstanceManager processInstanceManager = Context.getCommandContext().getProcessInstanceManager();

		String processInstanceId = taskInstanceQuery.getProcessInstanceId();

		ProcessDefinitionManager processDefinitionManager = Context.getCommandContext().getProcessDefinitionManager();

		ProcessDefinitionBehavior processDefinition = processDefinitionManager.findLatestProcessDefinitionById(processDefinitionId);

		ProcessInstanceEntity processInstanceImpl = processInstanceManager.findProcessInstanceById(processInstanceId, processDefinition);

		TokenEntity token = processInstanceImpl.getTokenMap().get(tokenId);

		ExecutionContext executionContext = ProcessObjectFactory.FACTORYINSTANCE.createExecutionContext(token);
		
		
		UserTaskBehavior userTask = (UserTaskBehavior) processDefinition.getDefinitions().getElement(nodeId);
		
		TaskCommandInst taskCommandInst =userTask.getTaskCommandsMap().get(taskCommandId);
		List<TaskInstance> taskInstances=new ArrayList<TaskInstance>();
		
		

		Object returnValueObject = null;
		if (taskCommandInst != null && taskCommandInst.getExpression() != null) {
			try {

				returnValueObject = ExpressionMgmt.execute(taskCommandInst.getExpression(), executionContext);
			} catch (Exception e) {
				throw new FixFlowException("用户命令表达式执行异常!", e);
			}
		}
		if (returnValueObject == null || returnValueObject.equals("")) {
			return taskInstancesEnd;
		} else {
			String nodeIdString = StringUtil.getString(returnValueObject);
			String[] nodeIdSZ = nodeIdString.split(",");

			for (TaskInstance taskInstanceTemp : taskInstancesEnd) {
				if (isExist(nodeIdSZ, taskInstanceTemp.getNodeId())) {
					taskInstances.add(taskInstanceTemp);
				}
			}
			return taskInstances;
		}
		
		
		
	}
	
	private boolean isExist(String[] contentStrings, String content) {
		for (String string : contentStrings) {
			if (string.equals(content)) {
				return true;
			}
		}
		return false;
	}

	


}
