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

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.task.Task;

/**
 * 保存任务
 * @author ych
 *
 */
public class SaveTaskCmd implements Command<Void>{

	private TaskEntity taskEntity;
	public SaveTaskCmd(Task task) {
		this.taskEntity = (TaskEntity)task;
	}
	
	@Override
	public Void execute(CommandContext commandContext) {
		if(taskEntity == null){
			throw new FoxBPMIllegalArgumentException("需要保存的task为null");
		}
		if (taskEntity.getRevision()==0){
			taskEntity.insert(null);
		}else{
			taskEntity.update();
		}
		return null;
	}
	
	
}
