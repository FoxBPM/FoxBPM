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
package org.foxbpm.engine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;

public interface TaskService {

	Task newTask();

	Task newTask(String taskId);

	Task findTask(String taskId);

	void saveTask(Task task);

	void deleteTask(String taskId);

	void deleteTasks(Collection<String> taskIds);

	void deleteTask(String taskId, boolean cascade);

	void deleteTasks(Collection<String> taskIds, boolean cascade);

	void deleteTask(String taskId, String deleteReason);

	void deleteTasks(Collection<String> taskIds, String deleteReason);

	void claim(String taskId, String userId);

	void unclaim(String taskId);

	void complete(String taskId);
	
	void complete(String taskId,Map<String, Object> transientVariables,Map<String, Object> persistenceVariables);
	
	/**
	 * 自定义扩展方式完成任务的处理命令调用的方法
	 * 
	 * @param expandTaskCommand
	 * @param classReturn
	 * @return
	 */
	<T> T expandTaskComplete(ExpandTaskCommand expandTaskCommand, T classReturn);
	

	void delegateTask(String taskId, String userId);

	void resolveTask(String taskId);

	void resolveTask(String taskId, Map<String, Object> variables);
	
	TaskQuery createTaskQuery();

	public NativeTaskQuery createNativeTaskQuery();
	
	
	/**
	 * 获取任务节点上的toolbar
	 * @param taskId
	 * @return
	 */
	List<TaskCommand> getTaskCommandByTaskId(String taskId);
	
	/**
	 * 获取提交节点上的toolbar
	 * 用于流程开始界面上，此时实例未生成，取定义上的toolbar配置
	 * @param key 流程定义key，会根据key去当前流程定义的最新版本key,因为启动流程总是启动最新版本的流程
	 * @return
	 */
	List<TaskCommand> getSubTaskCommandByKey(String Key);

}
