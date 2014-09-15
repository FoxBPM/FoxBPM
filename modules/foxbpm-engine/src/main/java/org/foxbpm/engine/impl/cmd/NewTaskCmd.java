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
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 创建新的任务
 * @author ych
 *
 */
public class NewTaskCmd implements Command<TaskEntity>{

	private String taskId;
	public NewTaskCmd(String taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public TaskEntity execute(CommandContext commandContext) {
		TaskEntity taskEntity = TaskEntity.create();
		if(taskId != null){
			taskEntity.setId(taskId);
		}
		return taskEntity;
	}
	
}
