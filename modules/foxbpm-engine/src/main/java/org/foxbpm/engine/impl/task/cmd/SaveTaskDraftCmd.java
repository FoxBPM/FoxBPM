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
 */
package org.foxbpm.engine.impl.task.cmd;

import java.util.List;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.task.command.SaveTaskDraftCommand;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class SaveTaskDraftCmd extends AbstractExpandTaskCmd<SaveTaskDraftCommand,Void>  {

	private static final long serialVersionUID = 1L;

	public SaveTaskDraftCmd(SaveTaskDraftCommand saveTaskDraftCommand) {
		super(saveTaskDraftCommand);
	}
	
	 
	public Void execute(CommandContext commandContext) {
		TaskEntity task = Context.getCommandContext().getTaskManager().findTaskById(taskId);
		if (task != null && task.isSuspended()) {
			throw ExceptionUtil.getException("10503002");
		}
		return execute(commandContext, task);
	}

	 
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		if(task!=null){
			/** 流程已经启动有任务的时候的处理 */
			// 获取任务命令
			TaskCommand taskCommand = getTaskCommand(task);
			// 获取流程内容执行器
			FlowNodeExecutionContext executionContext = getExecutionContext(task);
			// 任务命令的执行表达式变量
			taskCommand.getExpressionValue(executionContext);
			//设置任务处理者
			task.setAssignee(Authentication.getAuthenticatedUserId());
			//设置为草稿
			task.setDraft(true);
			// 处理意见
			task.setTaskComment(taskComment);
			
			if(StringUtil.isEmpty(task.getBizKey())){
				task.setBizKey(businessKey);
			}
			TaskDefinition taskDefinition=task.getTaskDefinition();
			if (taskDefinition != null && taskDefinition.getTaskSubject() != null) {
				Object result = null;
				try{
					result = taskDefinition.getTaskSubject().getValue(executionContext);
				}catch(Exception ex){
					throw ExceptionUtil.getException("10604003",ex);
				}
				
				if (result != null) {
					task.setDescription(result.toString());
				} else {
					task.setDescription(task.getToken().getFlowNode().getName());
				}
			} else {
				if (task.getProcessDefinition().getSubject() != null) {
					Object result = null;
					try{
						result = taskDefinition.getTaskSubject().getValue(executionContext);
					}catch(Exception ex){
						throw ExceptionUtil.getException("10604004",ex);
					}
					if (result != null) {
						task.setDescription(result.toString());
					}
				} else {
					task.setDescription(task.getToken().getFlowNode().getName());
				}
			}
		}else{
			/** 还没有发起流程时候的处理 */
			ProcessEngine processEngine =ProcessEngineManagement.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			ProcessInstanceEntity processInstance = (ProcessInstanceEntity)runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,transientVariables,persistenceVariables);
			List<TaskEntity> tasks = processInstance.getTasks();
			if(tasks!= null && tasks.size() >0){
				TaskEntity tmpTask = tasks.get(0);
				tmpTask.setDraft(true);
				String userId = Authentication.getAuthenticatedUserId();
				tmpTask.setAssignee(userId);
			}
		}
		return null;
	}
}
