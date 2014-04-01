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
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.exception.FixFlowException;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.TaskInstanceEntity;
import org.foxbpm.engine.task.TaskInstance;

public class SaveTaskCmd implements Command<Void>{

	protected TaskInstanceEntity taskInstanceEntity;
	
	public SaveTaskCmd(TaskInstance taskInstanceEntity){
		this.taskInstanceEntity=(TaskInstanceEntity)taskInstanceEntity;
	}
	
	
	
	public Void execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		
		if(taskInstanceEntity==null||taskInstanceEntity.getId()==null||taskInstanceEntity.getId().equals("")){
			throw new FixFlowException("taskId不能为空!");
		}
		
		commandContext.getTaskManager().saveTaskInstanceEntity(taskInstanceEntity);
		
		
		return null;
	}

}
