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
package org.foxbpm.engine.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.cmd.ClaimCmd;
import org.foxbpm.engine.impl.cmd.CompleteTaskCmd;
import org.foxbpm.engine.impl.cmd.DeleteTasksCmd;
import org.foxbpm.engine.impl.cmd.FindTaskCmd;
import org.foxbpm.engine.impl.cmd.GetRollbackNodeCmd;
import org.foxbpm.engine.impl.cmd.GetRollbackTasksCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommandByKeyCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommandByTaskIdCmd;
import org.foxbpm.engine.impl.cmd.NewTaskCmd;
import org.foxbpm.engine.impl.cmd.SaveTaskCmd;
import org.foxbpm.engine.impl.cmd.UnClaimCmd;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.query.NativeTaskQueryImpl;
import org.foxbpm.engine.impl.task.TaskQueryImpl;
import org.foxbpm.engine.impl.task.cmd.ExpandTaskCompleteCmd;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.kernel.process.KernelFlowNode;

public class TaskServiceImpl extends ServiceImpl implements TaskService {

	public Task newTask() {
		return newTask(null);
	}

	public Task newTask(String taskId) {
		return commandExecutor.execute(new NewTaskCmd(taskId));
	}

	public Task findTask(String taskId) {
		return commandExecutor.execute(new FindTaskCmd(taskId));
	}

	public void saveTask(Task task) {
		commandExecutor.execute(new SaveTaskCmd(task));
	}

	public void deleteTask(String taskId) {
		deleteTask(taskId,true);
	}

	public void deleteTasks(Collection<String> taskIds) {
		deleteTasks(taskIds,true);
	}

	public void deleteTask(String taskId, boolean cascade) {
		commandExecutor.execute( new DeleteTasksCmd(taskId,cascade));
	}

	public void deleteTasks(Collection<String> taskIds, boolean cascade) {
		commandExecutor.execute( new DeleteTasksCmd(taskIds,cascade));
	}

	public void claim(String taskId, String userId) {
		commandExecutor.execute(new ClaimCmd(taskId, userId));
	}

	public void unclaim(String taskId) {
		commandExecutor.execute(new UnClaimCmd(taskId));
	}

	public void complete(String taskId) {
		commandExecutor.execute(new CompleteTaskCmd(taskId, null,null));
	}
	
	public void complete(String taskId,Map<String, Object> transientVariables,Map<String, Object> persistenceVariables) {
		commandExecutor.execute(new CompleteTaskCmd(taskId, transientVariables,persistenceVariables));
	}

	@Override
	public <T> T expandTaskComplete(ExpandTaskCommand expandTaskCommand, T classReturn) {
		return (T) commandExecutor.execute(new ExpandTaskCompleteCmd<T>(expandTaskCommand));
	}
	
	@Override
	public void expandTaskComplete(String taskCommandJson) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode params = null;
		try {
			params = objectMapper.readTree(taskCommandJson);
		} catch (Exception e) {
			throw new FoxBPMException("任务命令参数格式不正确",e);
		}
		JsonNode taskIdNode = params.get("taskId");
		JsonNode commandIdNode = params.get("commandId");
		JsonNode processDefinitionKeyNode = params.get("processDefinitionKey");
		JsonNode businessKeyNode = params.get("bizKey");
		JsonNode taskCommentNode = params.get("taskComment");
		// 参数校验
		
		// 命令类型
		JsonNode commandTypeNode = params.get("commandType");
		JsonNode commandParamsNode = params.get("commandParams");
		
		if (commandTypeNode == null) {
			throw new FoxBPMException("commandType is null !");
		}
		// 命令Id
		if (commandIdNode == null) {
			throw new FoxBPMException("commandId is null !");
		}
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType(commandTypeNode.getTextValue());
		// 设置命令的id,需和节点上配置的按钮编号对应，会执行按钮中的脚本。
		expandTaskCommand.setTaskCommandId(commandIdNode.getTextValue());
		if(taskCommentNode != null){
			expandTaskCommand.setTaskComment(taskCommentNode.getTextValue());
		}
		//设置任务命令参数
		Map<String,Object> taskParams = new HashMap<String, Object>();
		if(commandParamsNode != null){
			Iterator<String> it = commandParamsNode.getFieldNames();
			while(it.hasNext()){
				String tmp = it.next();
				taskParams.put(tmp, commandParamsNode.get(tmp).getTextValue());
			}
		}
		expandTaskCommand.setParamMap(taskParams);
		if (taskIdNode != null && StringUtil.isNotEmpty(taskIdNode.getTextValue())) {
			expandTaskCommand.setTaskId(taskIdNode.getTextValue());
		} else {
			String userId = Authentication.getAuthenticatedUserId();
			expandTaskCommand.setInitiator(userId);
			if(businessKeyNode == null){
				throw new FoxBPMException("启动流程时关联键不能为null","");
			}
			if(processDefinitionKeyNode == null){
				throw new FoxBPMException("启动流程时流程Key不能为null","");
			}
			expandTaskCommand.setBusinessKey(businessKeyNode.getTextValue());
			expandTaskCommand.setProcessDefinitionKey(processDefinitionKeyNode.getTextValue());
		}
		expandTaskComplete(expandTaskCommand, null);
	}

	public NativeTaskQuery createNativeTaskQuery() {
		return new NativeTaskQueryImpl(commandExecutor);
	}
	
	public TaskQuery createTaskQuery() {
		return new TaskQueryImpl(commandExecutor);
	}
	
	@Override
	public List<TaskCommand> getSubTaskCommandByKey(String Key) {
		return commandExecutor.execute(new GetTaskCommandByKeyCmd(Key));
	}
	
	@Override
	public List<TaskCommand> getTaskCommandByTaskId(String taskId) {
		return commandExecutor.execute(new GetTaskCommandByTaskIdCmd(taskId,false));
	}
	
	@Override
	public List<TaskCommand> getTaskCommandByTaskId(String taskId,boolean isProcessTracking) {
		return commandExecutor.execute(new GetTaskCommandByTaskIdCmd(taskId,isProcessTracking));
	}
	
	@Override
	public List<KernelFlowNode> getRollbackFlowNode(String taskId) {
		return commandExecutor.execute(new GetRollbackNodeCmd(taskId));
	}
	
	@Override
	public List<Task> getRollbackTasks(String taskId) {
		return commandExecutor.execute(new GetRollbackTasksCmd(taskId));
	}
	
	@Override
	public Class<?> getInterfaceClass() {
		return TaskService.class;
	}


}
