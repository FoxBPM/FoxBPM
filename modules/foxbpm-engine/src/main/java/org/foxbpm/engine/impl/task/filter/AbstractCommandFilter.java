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
 */
package org.foxbpm.engine.impl.task.filter;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;


public abstract class AbstractCommandFilter {

	
	protected boolean isProcessTracking=false;
	
	protected TaskCommand taskCommand;
	

	public boolean isProcessTracking() {
		return isProcessTracking;
	}


	public void setProcessTracking(boolean isProcessTracking) {
		this.isProcessTracking = isProcessTracking;
	}


	public TaskCommand getTaskCommand() {
		return taskCommand;
	}


	public void setTaskCommandInst(TaskCommand taskCommand) {
		this.taskCommand = taskCommand;
	}


	/**
	 * 获取任务的处理方式
	 * @return
	 */
	public static String  getCommandType() {

		return null;
	}
	
	/**
	 * 判断是否是自动领取
	 * @return
	 */
	public static boolean isAutoClaim(Task task){
		
		TaskEntity taskEntity=(TaskEntity)(task);
		TaskDefinition taskDefinition=taskEntity.getTaskDefinition();
		return taskDefinition.isAutoClaim();

	}
	
	
	public abstract boolean accept(Task task);
	

}
