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
 * @author kenshin
 */
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.CoreUtil;
import org.foxbpm.engine.task.TaskCommand;

public class GetTaskCommandByTaskIdCmd implements Command<List<TaskCommand>> {

	private String taskId;
	private boolean isProcessTracking;

	public GetTaskCommandByTaskIdCmd(String taskId, boolean isProcessTracking) {
		this.taskId = taskId;
		this.isProcessTracking = isProcessTracking;
	}

	@Override
	public List<TaskCommand> execute(CommandContext commandContext) {
		List<TaskCommand> taskCommands = new ArrayList<TaskCommand>();
		TaskEntity taskInstance = commandContext.getTaskManager().findTaskById(taskId);
		taskCommands=CoreUtil.getTaskCommand(taskInstance, isProcessTracking);
		return taskCommands;
	}
}
