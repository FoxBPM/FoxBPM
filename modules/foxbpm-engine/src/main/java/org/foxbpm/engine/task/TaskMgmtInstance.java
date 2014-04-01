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
package org.foxbpm.engine.task;

import java.util.List;

import org.foxbpm.engine.impl.runtime.ProcessInstanceEntity;
import org.foxbpm.engine.impl.task.TaskInstanceEntity;
import org.foxbpm.engine.runtime.ExecutionContext;
import org.foxbpm.engine.runtime.Token;

public interface TaskMgmtInstance {

	TaskInstanceEntity createTaskInstanceEntity(TaskDefinition taskDefinition, ExecutionContext executionContext);
	
	void addTaskInstanceEntity(TaskInstanceEntity taskInstance);
	
	public TaskInstanceEntity createTaskInstanceEntity(TaskDefinition taskDefinition, ExecutionContext executionContext,String taskGroup);

	void setProcessInstance(ProcessInstanceEntity processInstance);

	void performAssignment(TaskDefinition taskDefinition, Assignable assignable, ExecutionContext executionContext);

	/**
	 * 暂停这个令牌下的所有任务实例
	 */
	void suspend(Token token);

	/**
	 * 恢复这个令牌下的所有任务实例
	 */
	void resume(Token token);

	List<TaskInstanceEntity> getTaskInstanceEntitys(Token token);
	
	TaskInstanceEntity getTaskInstanceEntitys(String taskId);
	
	List<TaskInstanceEntity> getTaskInstanceEntitys();
	
	List<TaskInstanceEntity> getTaskInstancesNoDB();
}
