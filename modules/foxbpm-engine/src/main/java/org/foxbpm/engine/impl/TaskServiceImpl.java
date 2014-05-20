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
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.cmd.CompleteTaskCmd;
import org.foxbpm.engine.impl.cmd.FindTaskCmd;
import org.foxbpm.engine.impl.query.NativeTaskQueryImpl;
import org.foxbpm.engine.impl.task.TaskQueryImpl;
import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;

public class TaskServiceImpl extends ServiceImpl implements TaskService {

	public Task newTask() {
		// TODO Auto-generated method stub
		return null;
	}

	public Task newTask(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task findTask(String taskId) {
		return commandExecutor.execute(new FindTaskCmd(taskId));
	}

	public void saveTask(Task task) {
		// TODO Auto-generated method stub

	}

	public void deleteTask(String taskId) {
		// TODO Auto-generated method stub

	}

	public void deleteTasks(Collection<String> taskIds) {
		// TODO Auto-generated method stub

	}

	public void deleteTask(String taskId, boolean cascade) {
		// TODO Auto-generated method stub

	}

	public void deleteTasks(Collection<String> taskIds, boolean cascade) {
		// TODO Auto-generated method stub

	}

	public void deleteTask(String taskId, String deleteReason) {
		// TODO Auto-generated method stub

	}

	public void deleteTasks(Collection<String> taskIds, String deleteReason) {
		// TODO Auto-generated method stub

	}

	public void claim(String taskId, String userId) {
		// TODO Auto-generated method stub

	}

	public void unclaim(String taskId) {
		// TODO Auto-generated method stub

	}

	public void complete(String taskId) {
		commandExecutor.execute(new CompleteTaskCmd(taskId, null));
	}
	
	public void complete(String taskId, Map<String, Object> variables) {
		commandExecutor.execute(new CompleteTaskCmd(taskId, variables));
	}


	public void delegateTask(String taskId, String userId) {
		// TODO Auto-generated method stub

	}

	public void resolveTask(String taskId) {
		// TODO Auto-generated method stub

	}

	public void resolveTask(String taskId, Map<String, Object> variables) {
		// TODO Auto-generated method stub

	}


	public NativeTaskQuery createNativeTaskQuery() {
		return new NativeTaskQueryImpl(commandExecutor);
	}
	
	public TaskQuery createTaskQuery() {
		return new TaskQueryImpl(commandExecutor);
	}
}
