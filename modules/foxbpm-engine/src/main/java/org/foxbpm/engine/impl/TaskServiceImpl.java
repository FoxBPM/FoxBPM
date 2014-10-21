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
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.cmd.ClaimCmd;
import org.foxbpm.engine.impl.cmd.CompleteTaskCmd;
import org.foxbpm.engine.impl.cmd.DeleteTasksCmd;
import org.foxbpm.engine.impl.cmd.FindFutureTaskCmd;
import org.foxbpm.engine.impl.cmd.FindTaskCmd;
import org.foxbpm.engine.impl.cmd.GetIdentityLinkByTaskIdCmd;
import org.foxbpm.engine.impl.cmd.GetRollbackNodeCmd;
import org.foxbpm.engine.impl.cmd.GetRollbackTasksCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommandByKeyCmd;
import org.foxbpm.engine.impl.cmd.GetTaskCommandByTaskIdCmd;
import org.foxbpm.engine.impl.cmd.GetTaskOperationCmd;
import org.foxbpm.engine.impl.cmd.NewTaskCmd;
import org.foxbpm.engine.impl.cmd.SaveTaskCmd;
import org.foxbpm.engine.impl.cmd.UnClaimCmd;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.ProcessOperatingEntity;
import org.foxbpm.engine.impl.query.NativeTaskQueryImpl;
import org.foxbpm.engine.impl.task.TaskQueryImpl;
import org.foxbpm.engine.impl.task.cmd.ExpandTaskCompleteCmd;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
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

	 
	public <T> T expandTaskComplete(ExpandTaskCommand expandTaskCommand, T classReturn) {
		return (T) commandExecutor.execute(new ExpandTaskCompleteCmd<T>(expandTaskCommand));
	}
	
	public NativeTaskQuery createNativeTaskQuery() {
		return new NativeTaskQueryImpl(commandExecutor);
	}
	
	public TaskQuery createTaskQuery() {
		return new TaskQueryImpl(commandExecutor);
	}
	
	 
	public List<TaskCommand> getSubTaskCommandByKey(String Key) {
		return commandExecutor.execute(new GetTaskCommandByKeyCmd(Key));
	}
	
	 
	public List<TaskCommand> getTaskCommandByTaskId(String taskId) {
		return commandExecutor.execute(new GetTaskCommandByTaskIdCmd(taskId,false));
	}
	
	 
	public List<TaskCommand> getTaskCommandByTaskId(String taskId,boolean isProcessTracking) {
		return commandExecutor.execute(new GetTaskCommandByTaskIdCmd(taskId,isProcessTracking));
	}
	
	 
	public List<KernelFlowNode> getRollbackFlowNode(String taskId) {
		return commandExecutor.execute(new GetRollbackNodeCmd(taskId));
	}
	
	 
	public List<Task> getRollbackTasks(String taskId) {
		return commandExecutor.execute(new GetRollbackTasksCmd(taskId));
	}
	
	 
	public List<ProcessOperatingEntity> getTaskOperations(String taskId) {
		return commandExecutor.execute(new GetTaskOperationCmd(taskId));
	}
	
	public List<IdentityLinkEntity> getIdentityLinkByTaskId(String taskId){
		return commandExecutor.execute(new GetIdentityLinkByTaskIdCmd(taskId));
	}
	
	 
	public Class<?> getInterfaceClass() {
		return TaskService.class;
	}

	public List<Task> getFutureTasks(String todoTaskId) {
		return commandExecutor.execute(new FindFutureTaskCmd(todoTaskId));
	}

}
