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
package org.foxbpm.engine.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.founder.fix.bpmn2extensions.coreconfig.Priority;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.factory.ProcessObjectFactory;
import org.foxbpm.engine.impl.bpmn.behavior.TaskCommandInst;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.cmd.AddCommentCmd;
import org.foxbpm.engine.impl.cmd.CompleteGeneralTaskSimulationRunCmd;
import org.foxbpm.engine.impl.cmd.DeleteIdentityLinkCmd;
import org.foxbpm.engine.impl.cmd.DeleteTaskCmd;
import org.foxbpm.engine.impl.cmd.ExpandTaskComplete;
import org.foxbpm.engine.impl.cmd.GetAgentToUsersAndCountCmd;
import org.foxbpm.engine.impl.cmd.GetAgentToUsersCmd;
import org.foxbpm.engine.impl.cmd.GetAgentUsersAndCountCmd;
import org.foxbpm.engine.impl.cmd.GetAgentUsersCmd;
import org.foxbpm.engine.impl.cmd.GetNextTaskCmd;
import org.foxbpm.engine.impl.cmd.GetPreviousStepTaskByTaskIdCmd;
import org.foxbpm.engine.impl.cmd.GetPriorityCmd;
import org.foxbpm.engine.impl.cmd.GetProcessInstanceCommentsCmd;
import org.foxbpm.engine.impl.cmd.GetRecoverTaskCmd;
import org.foxbpm.engine.impl.cmd.GetRollBackNodeCmd;
import org.foxbpm.engine.impl.cmd.GetRollBackScreeningTaskCmd;
import org.foxbpm.engine.impl.cmd.GetRollBackTaskCmd;
import org.foxbpm.engine.impl.cmd.GetSubTaskUserCommandCmd;
import org.foxbpm.engine.impl.cmd.GetSystemTaskCommandCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommandByTaskIdCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommandByTaskInstanceCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommentsCmd;
import org.foxbpm.engine.impl.cmd.GetTaskStatusByByProcessInstanceIdCmd;
import org.foxbpm.engine.impl.cmd.GetTaskUsersByTaskIdCmd;
import org.foxbpm.engine.impl.cmd.GetUserCommandCmd;
import org.foxbpm.engine.impl.cmd.GetUserEndTaskNodesInProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.QueryVariablesCmd;
import org.foxbpm.engine.impl.cmd.SaveIdentityLinkCmd;
import org.foxbpm.engine.impl.cmd.SaveTaskCmd;
import org.foxbpm.engine.impl.cmd.SaveTaskDraftCmd;
import org.foxbpm.engine.impl.cmd.SaveVariablesCmd;
import org.foxbpm.engine.impl.cmd.TransferTaskCmd;
import org.foxbpm.engine.impl.command.AbstractCustomExpandTaskCommand;
import org.foxbpm.engine.impl.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.command.GeneralTaskCommand;
import org.foxbpm.engine.impl.command.QueryVariablesCommand;
import org.foxbpm.engine.impl.command.SaveTaskDraftCommand;
import org.foxbpm.engine.impl.command.SaveVariablesCommand;
import org.foxbpm.engine.impl.command.TransferTaskCommand;
import org.foxbpm.engine.impl.identity.UserTo;
import org.foxbpm.engine.impl.task.IdentityLinkEntity;
import org.foxbpm.engine.impl.task.TaskInstanceEntity;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.runtime.IdentityLinkQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.CommentQueryTo;
import org.foxbpm.engine.task.IdentityLink;
import org.foxbpm.engine.task.TaskInstance;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.task.UserCommandQueryTo;

public class TaskServiceImpl extends ServiceImpl implements TaskService {
	
	
	/* ****************************************    任务处理接口  begin  ************************************************************ */

	

	public void complete(String taskId, String taskComment, String taskCommandId, Map<String, Object> transientVariables) {
		ExpandTaskCommand expandTaskCommandClaim=new ExpandTaskCommand();
		expandTaskCommandClaim.setCommandType("general");
		expandTaskCommandClaim.setTaskId(taskId);
		expandTaskCommandClaim.setTaskComment(taskComment);

		expandTaskCommandClaim.setTransientVariables(transientVariables);
		this.expandTaskComplete(expandTaskCommandClaim, null);
	}
	
	public void claim(String taskId) {
		ExpandTaskCommand expandTaskCommandClaim=new ExpandTaskCommand();
		expandTaskCommandClaim.setCommandType("claim");
		expandTaskCommandClaim.setTaskId(taskId);
		this.expandTaskComplete(expandTaskCommandClaim, null);
	}

	
	public void claim(String taskId, String claimUserId) {
		ExpandTaskCommand expandTaskCommandClaim=new ExpandTaskCommand();
		expandTaskCommandClaim.setCommandType("claim");
		expandTaskCommandClaim.setTaskId(taskId);
		this.expandTaskComplete(expandTaskCommandClaim, null);		
	}

	
	

	public void release(String taskId){
		ExpandTaskCommand expandTaskCommandClaim=new ExpandTaskCommand();
		expandTaskCommandClaim.setCommandType("releaseTask");
		expandTaskCommandClaim.setTaskId(taskId);
		this.expandTaskComplete(expandTaskCommandClaim, null);
	}
	
	
	


	
	public void transfer(String taskId, String transferUserId, String taskComment, String taskCommandId, Map<String, Object> transientVariables) {
		// TODO Auto-generated method stub
		
	}

	public void rollBack(String taskId, String rollBackNodeId, String taskComment, String taskCommandId, Map<String, Object> transientVariables) {
		// TODO Auto-generated method stub
		
	}
	
	
	public <T> T expandTaskComplete(ExpandTaskCommand expandTaskCommand, T classReturn) {

		return (T) commandExecutor.execute(new ExpandTaskComplete<AbstractCustomExpandTaskCommand, T>(expandTaskCommand));

	}
	
	
	/* ****************************************    任务处理接口  end  ************************************************************ */
	

	public TaskInstance newTask() {

		String guidString=GuidUtil.CreateGuid();
		return newTask(guidString);

	}

	public TaskInstance newTask(String taskId) {

		TaskInstance newTask = TaskInstanceEntity.create();
		newTask.setId(taskId);
		return newTask;
	}

	public void saveTask(TaskInstance task) {
		commandExecutor.execute(new SaveTaskCmd(task));
	}

	public void deleteTask(String taskId) {
		commandExecutor.execute(new DeleteTaskCmd(taskId, false));

	}

	public void deleteTasks(Collection<String> taskIds) {
		commandExecutor.execute(new DeleteTaskCmd(taskIds, false));
	}

	public void deleteTask(String taskId, boolean cascade) {
		commandExecutor.execute(new DeleteTaskCmd(taskId, cascade));
	}

	public void deleteTasks(Collection<String> taskIds, boolean cascade) {
		commandExecutor.execute(new DeleteTaskCmd(taskIds, cascade));
	}
	
	public void resumeTask(String taskId) {

		TaskInstance taskInstance=createTaskQuery().taskId(taskId).singleResult();
		taskInstance.resume();
		saveTask(taskInstance);
	}

	public void suspendTask(String taskId) {

		TaskInstance taskInstance=createTaskQuery().taskId(taskId).singleResult();
		taskInstance.suspend();
		saveTask(taskInstance);
	}

	public TaskQuery createTaskQuery() {
		return ProcessObjectFactory.FACTORYINSTANCE.createTaskQuery(commandExecutor);
	}

	public IdentityLinkQuery createIdentityLinkQuery() {

		return ProcessObjectFactory.FACTORYINSTANCE.createIdentityLinkQuery(commandExecutor);
	}

	public List<UserCommandQueryTo> getSubTaskUserCommandByKey(String processDefinitionKey) {
		return commandExecutor.execute(new GetSubTaskUserCommandCmd<UserCommandQueryTo>(processDefinitionKey));
	}

	public List<UserCommandQueryTo> getUserCommandById(String processDefinitionId, String nodeId) {
		return commandExecutor.execute(new GetUserCommandCmd<UserCommandQueryTo>(processDefinitionId, nodeId));
	}
	
	
	public List<TaskCommandInst> getTaskCommandById(String processDefinitionId, String nodeId) {
		return commandExecutor.execute(new GetUserCommandCmd<TaskCommandInst>(processDefinitionId, nodeId));	
	}

	public List<TaskCommandInst> getSubTaskTaskCommandByKey(String processDefinitionKey) {
		return commandExecutor.execute(new GetSubTaskUserCommandCmd<TaskCommandInst>(processDefinitionKey));
	}

	


	public void transferTask(TransferTaskCommand transferTaskCommand) {

		commandExecutor.execute(new TransferTaskCmd(transferTaskCommand));
	}



	

	public void setAssignee(String taskId, String userId) {
		// TODO Auto-generated method stub

	}

	public void setOwner(String taskId, String userId) {
		// TODO Auto-generated method stub

	}

	public void addComment(String taskId, String processInstanceId, String message, String fullMessage) {
		commandExecutor.execute(new AddCommentCmd(taskId, processInstanceId, message, fullMessage));

	}

	public List<CommentQueryTo> getTaskComments(String taskId) {
		return commandExecutor.execute(new GetTaskCommentsCmd(taskId));
	}

	public List<CommentQueryTo> getProcessInstanceComments(String processInstanceId) {
		return commandExecutor.execute(new GetProcessInstanceCommentsCmd(processInstanceId));
	}

	public List<TaskInstance> getRollBackTask(String taskId) {
		return commandExecutor.execute(new GetRollBackTaskCmd(taskId));
	}

	public List<UserTaskBehavior> getRollBackNode(String taskId) {
		return commandExecutor.execute(new GetRollBackNodeCmd(taskId));
	}

	public void saveTaskDraft(SaveTaskDraftCommand saveTaskDraftCommand) {
		commandExecutor.execute(new SaveTaskDraftCmd(saveTaskDraftCommand));
	}


	/**
	 * 模拟提交任务
	 * 
	 * @param generalTaskCommand
	 */
	public ProcessInstance commandCompleteSimulationRun(GeneralTaskCommand generalTaskCommand) {
		return commandExecutor.execute(new CompleteGeneralTaskSimulationRunCmd(generalTaskCommand));
	}

	/**
	 * 根据流程实例编号获取任务状态
	 */
	public List<Map<String, Object>> getTaskStatusByByProcessInstanceId(List<String> processInstanceIdList) {
		return commandExecutor.execute(new GetTaskStatusByByProcessInstanceIdCmd(processInstanceIdList));
	}

	public List<TaskInstance> getPreviousStepTaskByTaskId(String taskId) {
		return commandExecutor.execute(new GetPreviousStepTaskByTaskIdCmd(taskId));
	}

	public List<TaskInstance> getRollBackTaskByTaskId(String taskId) {
		return commandExecutor.execute(new GetRollBackTaskCmd(taskId));
	}

	public void setVariable(String taskId, String variableName, Object value) {

		SaveVariablesCommand saveVariablesCommand = new SaveVariablesCommand();
		saveVariablesCommand.setTaskInstanceId(taskId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(variableName, value);
		saveVariablesCommand.setVariables(map);
		commandExecutor.execute(new SaveVariablesCmd(saveVariablesCommand));

	}

	public void setVariables(String taskId, Map<String, ? extends Object> variables) {
		SaveVariablesCommand saveVariablesCommand = new SaveVariablesCommand();
		saveVariablesCommand.setTaskInstanceId(taskId);
		saveVariablesCommand.setVariables(variables);
		commandExecutor.execute(new SaveVariablesCmd(saveVariablesCommand));
	}

	public Object getVariable(String taskId, String variableName) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setTaskInstanceId(taskId);
		List<String> variableNames = new ArrayList<String>();
		variableNames.add(variableName);
		queryVariablesCommand.setVariableNames(variableNames);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map.get(variableName);
	}

	public Map<String, Object> getVariables(String taskId) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setTaskInstanceId(taskId);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map;
	}

	public Map<String, Object> getVariables(String taskId, List<String> variableNames) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setTaskInstanceId(taskId);
		queryVariablesCommand.setVariableNames(variableNames);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map;
	}

	public IdentityLink newIdentityLink() {
		String guidString=GuidUtil.CreateGuid();
		return newIdentityLink(guidString);
		
	}

	public IdentityLink newIdentityLink(String linkId) {
		
		IdentityLink identityLink = new IdentityLinkEntity(linkId);	
		return identityLink;
		
	}

	public void saveIdentityLink(IdentityLink identityLink) {
		commandExecutor.execute(new SaveIdentityLinkCmd(identityLink));
	}

	public void deleteIdentityLink(String linkId) {
		commandExecutor.execute(new DeleteIdentityLinkCmd(linkId));
	}

	public void deleteIdentityLink(Collection<String> linkIds) {
		for (String linkId : linkIds) {
			commandExecutor.execute(new DeleteIdentityLinkCmd(linkId));
		}
	}



	public List<UserTo> getAgentUsers(String userId) {

		return commandExecutor.execute(new GetAgentUsersCmd(userId));
	}

	public List<Map<String, Object>> getAgentUsersAndCount(String userId) {

		return commandExecutor.execute(new GetAgentUsersAndCountCmd(userId));
	}
	
	public List<UserTo> getAgentToUsers(String userId) {

		return commandExecutor.execute(new GetAgentToUsersCmd(userId));
	}

	public List<Map<String, Object>> getAgentToUsersAndCount(String userId) {

		return commandExecutor.execute(new GetAgentToUsersAndCountCmd(userId));
	}
	
	public List<TaskCommandInst> getSystemTaskCommand(String commandType) {
		
		
		return commandExecutor.execute(new GetSystemTaskCommandCmd(commandType));
		

	}

	public List<UserTaskBehavior> getUserEndTaskNodesInProcessInstance(
			String processInstanceId) {

		return commandExecutor.execute(new GetUserEndTaskNodesInProcessInstanceCmd(processInstanceId));
	}

	public List<TaskCommandInst> GetTaskCommandByTaskId(String taskId,boolean isProcessTracking) {
		// TODO 自动生成的方法存根
		return commandExecutor.execute(new GetTaskCommandByTaskIdCmd(taskId,isProcessTracking));
	}

	
	public List<TaskCommandInst> GetTaskCommandByTaskInstance(
			TaskInstance taskInstance,boolean isProcessTracking) {
		return commandExecutor.execute(new GetTaskCommandByTaskInstanceCmd(taskInstance,isProcessTracking));
	}

	public List<UserTo> getTaskUsersByTaskId(String taskId) {
		// TODO 自动生成的方法存根
		
		return commandExecutor.execute(new GetTaskUsersByTaskIdCmd(taskId));
		
	}

	public Priority getPriority(int priorityValue) {
		
		return commandExecutor.execute(new GetPriorityCmd(priorityValue));
	}

	public List<TaskInstance> getNextTask(String taskId,String processInstanceId) {


		return commandExecutor.execute(new GetNextTaskCmd(taskId,processInstanceId));
	}

	public List<TaskInstance> getRollBackScreeningTask(String taskId) {

		return commandExecutor.execute(new GetRollBackScreeningTaskCmd(taskId));
	}

	public List<TaskInstance> GetRecoverTask(String taskId, String taskCommandId) {
		return commandExecutor.execute(new GetRecoverTaskCmd(taskId,taskCommandId));
	}

	

	

	

	



}
