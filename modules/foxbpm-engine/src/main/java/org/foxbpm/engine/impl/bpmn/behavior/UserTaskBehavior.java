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
 * @author ych
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.calendar.WorkCalendar;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.event.AbstractTaskEvent;
import org.foxbpm.engine.impl.task.FormParam;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * 人工任务处理器
 * 
 * @author kenshin
 * 
 */
public class UserTaskBehavior extends TaskBehavior {

	private static final long serialVersionUID = 1L;


	/** 任务信息定义 */
	private TaskDefinition taskDefinition;


	 
	public void execute(FlowNodeExecutionContext executionContext) {
		
		TokenEntity token=(TokenEntity)executionContext;
		
		if(StringUtil.isNotEmpty(token.getGroupID())){
			//会签任务创建
			//串行多实例、并行多实例 考虑
			//串行多实例 、 加签 、
			
		}else{
			//非会签任务创建
			
		}
		

		TaskEntity task = TaskEntity.createAndInsert(executionContext);
		
		if(StringUtil.isNotEmpty(token.getGroupID())){
			task.setTaskGroup(token.getGroupID());
			/** 如果为会签任务,则清空强制任务处理者 */
			token.setTaskAssignee(null);
		}
		task.setTaskDefinition(taskDefinition);
		task.setNodeId(getId());
		task.setNodeName(getName());
		((TokenEntity) executionContext).setAssignTask(task);
		ProcessInstanceEntity processInstance = (ProcessInstanceEntity) executionContext.getProcessInstance();
		task.setBizKey(processInstance.getBizKey());
		ProcessDefinitionEntity processDefinition=(ProcessDefinitionEntity)processInstance.getProcessDefinition();
		if(taskDefinition.getTaskSubject()==null||StringUtil.isEmpty(taskDefinition.getTaskSubject().getExpressionText())){
			task.setSubject(StringUtil.getString(processDefinition.getSubject().getValue(executionContext)));
		}else{
			task.setSubject(StringUtil.getString(taskDefinition.getTaskSubject().getValue(executionContext)));
		}
		
		if(StringUtil.isEmpty(task.getSubject())){
			task.setSubject(getName());
		}
		task.setProcessInitiator(processInstance.getInitiator());
		task.setProcessStartTime(processInstance.getStartTime());
		task.setDescription(StringUtil.getString(taskDefinition.getTaskDescription().getValue(executionContext)));
		task.setCompleteDescription(StringUtil.getString(taskDefinition.getCompleteTaskDescription().getValue(executionContext)));
		task.setToken((TokenEntity) executionContext);
		task.setTaskType(taskDefinition.getTaskType());
		
		String formUri = getFormUri(executionContext);
		task.setFormUri(formUri);
		String formUriView = getFormUriView(executionContext);
		task.setFormUriView(formUriView);
		
		if(taskDefinition.getTaskPriority() !=null){
			task.setPriority(StringUtil.getInt(taskDefinition.getTaskPriority().getValue(executionContext)));
		}
		List<FormParam> formParams = taskDefinition.getFormParams();
		if(formParams!=null&&formParams.size()>0){
			Map<String, Object> paramMap=new HashMap<String, Object>();
			for (FormParam formParam : formParams) {
				paramMap.put(formParam.getParamKey(), formParam.getExpression().getValue(executionContext));
			}
			task.setParamMap(paramMap);
		}
		
		/** 判断是否有强制任务处理者指定 */
		if(StringUtil.isNotEmpty(token.getTaskAssignee())){
			/** 根据强制任务处理者设置任务 */
			task.setAssignee(token.getTaskAssignee());
		}else{
			/** 重新分配任务 */
			for (Connector connector : taskDefinition.getActorConnectors()) {
				try {
					connector.notify((ListenerExecutionContext) executionContext);
				} catch (Exception e) {
					if(e instanceof FoxBPMException)
						throw (FoxBPMException)e;
					else{
						throw new FoxBPMException("执行选择人处理器失败！节点"+this.getId()+",处理器："+connector.getConnectorId() , e);
					}
				}
			}
		}
		
		WorkCalendar workCalendar = Context.getProcessEngineConfiguration().getWorkCalendar();
		double expectedTime = taskDefinition.getExpectExecuteTime();
		task.setExpectedExecutionTime(expectedTime);
		if(workCalendar != null && expectedTime >0){
			Map<String,Object> calendarParams = new HashMap<String, Object>();
			calendarParams.put("assignee", task.getAssignee());
			calendarParams.put("identityLinks",task.getIdentityLinks());
			calendarParams.put("processDefinitionKey",task.getProcessDefinitionKey());
			
			Date now = ClockUtil.getCurrentTime();
			Date dueDate = workCalendar.getDueTime(now, expectedTime, calendarParams);
			task.setDueDate(dueDate);
		}
		
		token.setAssignTask(task);
		/** 触发分配事件(后事件) */
		executionContext.fireEvent(AbstractTaskEvent.TASK_ASSIGN);
		token.setAssignTask(null);
		token.setTaskAssignee(null);
	}

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}

	/**
	 * 节点离开时清理事件
	 */
	 
	public void cleanData(FlowNodeExecutionContext executionContext) {
		removeTaskInstanceSynchronization(executionContext);
		super.cleanData(executionContext);
	}

	/**
	 * 令牌离开节点时，强制结束所有未完成任务
	 * @param executionContext
	 */
	private void removeTaskInstanceSynchronization(FlowNodeExecutionContext executionContext) {
		if(executionContext!=null){
			List<TaskEntity> tasks = Context.getCommandContext().getTaskManager().findTasksByTokenId(executionContext.getId());
			
			for (TaskEntity taskInstance : tasks) {
				if (!taskInstance.hasEnded()) {
					//强制结束任务.
					taskInstance.end(null, null);
				}
			}
		}
		
	}
	
	/**
	 * 获取浏览表单
	 * 顺序逻辑：节点操作表单->全局操作表单
	 * @param executionContext
	 * @return
	 */
	private String getFormUri(FlowNodeExecutionContext executionContext){
		String formUri = StringUtil.getString(taskDefinition.getFormUri().getValue(executionContext));
		if(StringUtil.isNotEmpty(formUri)){
			return formUri;
		}
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)executionContext.getProcessDefinition();
		formUri = StringUtil.getString(processDefinitionEntity.getFormUri().getValue(executionContext));
		return formUri;
	}
	
	/**
	 * 获取浏览表单
	 * 顺序逻辑：节点浏览表单->全局浏览表单->节点操作表单->全局操作表单
	 * @param executionContext
	 * @return
	 */
	private String getFormUriView(FlowNodeExecutionContext executionContext){
		String formUriView = StringUtil.getString(taskDefinition.getFormUriView().getValue(executionContext));
		if(StringUtil.isNotEmpty(formUriView)){
			return formUriView;
		}
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)executionContext.getProcessDefinition();
		formUriView = StringUtil.getString(processDefinitionEntity.getFormUriView().getValue(executionContext));
		
		if(StringUtil.isNotEmpty(formUriView)){
			return formUriView;
		}
		formUriView = getFormUri(executionContext);
		return formUriView;
	}

}
