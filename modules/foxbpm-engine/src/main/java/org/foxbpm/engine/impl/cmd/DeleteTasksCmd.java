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

import java.util.Collection;
import java.util.Iterator;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 删除任务，可选择是否级联删除任务候选人
 * @author ych
 */
public class DeleteTasksCmd implements Command<Void> {

	private String taskId;
	private Collection<String> taskIds;
	private boolean cascade = true;
	public DeleteTasksCmd(String taskId,boolean cascade) {
		this.taskId = taskId;
		this.cascade = cascade;
	}
	
	public DeleteTasksCmd(Collection<String> taskIds,boolean cascade){
		this.taskIds = taskIds;
		this.cascade = cascade;
	}
	
	@Override
	public Void execute(CommandContext commandContext) {
		if(taskId != null){
			commandContext.getTaskManager().deleteTaskById(taskId);
			//级联删除，则删除对应的taskIdentityLink候选人
			if(cascade){
				commandContext.getIdentityLinkManager().deleteIdentityLinkByTaskId(taskId);
			}
		}else if(taskIds != null && !taskIds.isEmpty()){
			Iterator<String> iterator = taskIds.iterator();
			while(iterator.hasNext()){
				String tmpId = iterator.next();
				commandContext.getTaskManager().deleteTaskById(tmpId);
				//级联删除，则删除对应的taskIdentityLink候选人
				if(cascade){
					commandContext.getIdentityLinkManager().deleteIdentityLinkByTaskId(tmpId);
				}
			}
		}
		return null;
	}
}
