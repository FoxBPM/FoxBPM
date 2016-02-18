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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.Constant;
import org.foxbpm.engine.calendar.WorkCalendar;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.connector.ConnectorListener;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.event.AbstractTaskEvent;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommandDefinition;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.model.SkipStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 人工任务处理器
 * 
 * @author kenshin
 * 
 */
public class UserTaskBehavior extends TaskBehavior {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(UserTaskBehavior.class);
	
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
			try{
				task.setSubject(StringUtil.getString(processDefinition.getSubject().getValue(executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404021",ex,this.getId());
			}
			
		}else{
			try{
				task.setSubject(StringUtil.getString(taskDefinition.getTaskSubject().getValue(executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404022",ex,this.getId());
			}
			
		}
		
		if(StringUtil.isEmpty(task.getSubject())){
			task.setSubject(getName());
		}
		task.setProcessInitiator(processInstance.getInitiator());
		task.setProcessStartTime(processInstance.getStartTime());
		try{
			task.setDescription(StringUtil.getString(taskDefinition.getTaskDescription().getValue(executionContext)));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404023",ex,this.getId());
		}
		
		try{
			task.setCompleteDescription(StringUtil.getString(taskDefinition.getCompleteTaskDescription().getValue(executionContext)));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404024",ex,this.getId());
		}
		
		task.setToken((TokenEntity) executionContext);
		task.setTaskType(taskDefinition.getTaskType());
		
		String formUri = getFormUri(executionContext);
		task.setFormUri(formUri);
		String formUriView = getFormUriView(executionContext);
		task.setFormUriView(formUriView);
		
		if(taskDefinition.getTaskPriority() !=null){
			try{
				task.setPriority(StringUtil.getInt(taskDefinition.getTaskPriority().getValue(executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404025",ex,this.getId());
			}
			
		}
//		List<FormParam> formParams = taskDefinition.getFormParams();
//		if(formParams!=null&&formParams.size()>0){
//			Map<String, Object> paramMap=new HashMap<String, Object>();
//			for (FormParam formParam : formParams) {
//				paramMap.put(formParam.getParamKey(), formParam.getExpression().getValue(executionContext));
//			}
//			task.setParamMap(paramMap);
//		}
		
		/** 判断是否有强制任务处理者指定 */
		if(StringUtil.isNotEmpty(token.getTaskAssignee())){
			/** 根据强制任务处理者设置任务 */
			task.setAssignee(token.getTaskAssignee());
		}else{
			/** 重新分配任务 */
			for (ConnectorListener connector : taskDefinition.getActorConnectors()) {
				try {
					connector.notify((ListenerExecutionContext) executionContext);
				} catch (Exception e) {
					throw ExceptionUtil.getException("10700002",e,this.getId(),connector.getConnector().getType());
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
		
		// ThinkGem 2015-10-26  如果没有下一步处理者，则抛出异常
		boolean isExistsNextAssignee = false;
		StringBuilder sb = new StringBuilder();
		sb.append("下一步处理人： nodeId: ").append(task.getNodeId());
		if (StringUtils.isNotBlank(task.getAssignee())){
			sb.append(" 独享任务： userId: ").append(task.getAssignee());
			if (Authentication.selectUserByUserId(task.getAssignee()) != null){
				isExistsNextAssignee = true;
			}
		}else{
			List<IdentityLinkEntity> identityLinks = task.getIdentityLinks();
			if (identityLinks != null){
				sb.append(" 共享任务：");
				for (IdentityLinkEntity e : identityLinks){
					if (StringUtils.isNotBlank(e.getUserId())){
						sb.append(" userId: ").append(e.getUserId());
						if (Constant.FOXBPM_ALL_USER.equals(e.getUserId())){
							isExistsNextAssignee = true;
							break;
						}else if (Authentication.selectUserByUserId(e.getUserId()) != null){
							isExistsNextAssignee = true;
							break;
						}
					}
					if (StringUtils.isNotBlank(e.getGroupId()) && StringUtils.isNotBlank(e.getGroupType())){
						sb.append(" groupId: ").append(e.getGroupId()).append("  groupType: ").append(e.getGroupType());
						List<String> userIds = Authentication.selectUserIdsByGroupIdAndType(e.getGroupId(), e.getGroupType());
						if (userIds != null && userIds.size() > 0){
							isExistsNextAssignee = true;
							break;
						}
					}
				}
			}
		}
		log.info(sb.toString());
		if (!isExistsNextAssignee){
			throw new FoxBPMException("没有下一步处理人");
		}
		// ThinkGem 2015-10-26 end
		
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

	@Override
	protected void skipTaskRecord(FlowNodeExecutionContext executionContext,
			SkipStrategy skipStrategy) {
		String skipAssignee = skipStrategy.getSkipAssignee();
		String skipComment = skipStrategy.getSkipComment();
 
		TokenEntity token = (TokenEntity)executionContext;

		TaskEntity newTask = TaskEntity.createAndInsert(executionContext);
		newTask.setCreateTime(new Date());
		newTask.setNodeId(this.getId());

		if (skipAssignee != null && !skipAssignee.equals("")) {
			try{
				newTask.setAssignee(StringUtil.getString(ExpressionMgmt.execute(skipAssignee, executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404026",ex,this.getId());
			}
			
		}

		newTask.setDraft(false);

		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MILLISECOND, 1);
		date = cal.getTime();

		newTask.setEndTime(date);
		newTask.setPriority(50);
		ProcessInstanceEntity processInstance = token.getProcessInstance();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)processInstance.getProcessDefinition();
		newTask.setProcessDefinitionId(processDefinition.getId());
		newTask.setProcessDefinitionKey(processDefinition.getKey());
		newTask.setProcessInitiator(processInstance.getInitiator());
		newTask.setName(this.getName());
		newTask.setNodeName(this.getName());
		newTask.setProcessInstanceId(token.getProcessInstance().getId());
		newTask.setTokenId(token.getId());
		newTask.setProcessDefinitionName(processDefinition.getName());
		newTask.setTaskType(Task.TYPE_FOXBPMTASK);
		String bizKey = token.getProcessInstance().getBizKey();
		newTask.setBizKey(bizKey);
		newTask.setCommandId("skipNode");
		newTask.setCommandType("skipNode");

		TaskCommandDefinition taskCommandDefinition = Context.getProcessEngineConfiguration().getTaskCommandDefinition("skipNode");

		if (taskCommandDefinition.getName() != null) {
			newTask.setCommandMessage(taskCommandDefinition.getName());
		}

		if (skipComment != null && !skipComment.equals("")) {
			try{
				newTask.setTaskComment(StringUtil.getString(ExpressionMgmt.execute(skipComment, executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404027",ex,this.getId());
			}
			
		}
		
		if(taskDefinition.getTaskSubject()==null||StringUtil.isEmpty(taskDefinition.getTaskSubject().getExpressionText())){
			try{
				newTask.setSubject(StringUtil.getString(processDefinition.getSubject().getValue(executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404021",ex,this.getId());
			}
			
		}else{
			try{
				newTask.setSubject(StringUtil.getString(taskDefinition.getTaskSubject().getValue(executionContext)));
			}catch(Exception ex){
				throw ExceptionUtil.getException("10404022",ex,this.getId());
			}
			
		}
		
		if(StringUtil.isEmpty(newTask.getSubject())){
			newTask.setSubject(getName());
		}

		Context.getCommandContext().getTaskManager().update(newTask);
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
		String formUri = null;
		try{
			formUri = StringUtil.getString(taskDefinition.getFormUri().getValue(executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404028");
		}
		
		
		if(StringUtil.isNotEmpty(formUri)){
			return formUri;
		}
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)executionContext.getProcessDefinition();
		try{
			formUri = StringUtil.getString(processDefinitionEntity.getFormUri().getValue(executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404029");
		}
		
		return formUri;
	}
	
	/**
	 * 获取浏览表单
	 * 顺序逻辑：节点浏览表单->全局浏览表单->节点操作表单->全局操作表单
	 * @param executionContext
	 * @return
	 */
	private String getFormUriView(FlowNodeExecutionContext executionContext){
		String formUriView = null;
		try{
			formUriView = StringUtil.getString(taskDefinition.getFormUriView().getValue(executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404030");
		}
		if(StringUtil.isNotEmpty(formUriView)){
			return formUriView;
		}
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)executionContext.getProcessDefinition();
		try{
			formUriView = StringUtil.getString(processDefinitionEntity.getFormUriView().getValue(executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404031");
		}
		
		
		if(StringUtil.isNotEmpty(formUriView)){
			return formUriView;
		}
		formUriView = getFormUri(executionContext);
		return formUriView;
	}
	
}
