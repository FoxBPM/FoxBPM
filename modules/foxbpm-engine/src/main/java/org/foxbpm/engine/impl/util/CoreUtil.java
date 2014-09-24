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
package org.foxbpm.engine.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.task.filter.AbstractCommandFilter;
import org.foxbpm.engine.task.TaskCommand;

public class CoreUtil {

	public static List<TaskCommand> getTaskCommand(TaskEntity task, boolean isProcessTracking) {
		List<TaskCommand> taskCommands = new ArrayList<TaskCommand>();
		if (task != null) {
			Map<String, AbstractCommandFilter> abstractCommandFilterMap = Context
					.getProcessEngineConfiguration().getCommandFilterMap();
			for (TaskCommand taskCommand : task.getTaskDefinition().getTaskCommands()) {
				AbstractCommandFilter abstractCommandFilter = abstractCommandFilterMap
						.get(taskCommand.getTaskCommandType());
				if (abstractCommandFilter != null) {
					abstractCommandFilter.setProcessTracking(isProcessTracking);
					abstractCommandFilter.setTaskCommandInst(taskCommand);
					if (abstractCommandFilter.accept(task)) {
						taskCommands.add(taskCommand);
					}
				} else {
					if (!isProcessTracking) {
						taskCommands.add(taskCommand);
					}

				}
			}
		}

		return taskCommands;
	}

	public static List<TaskCommand> getSubmitNodeTaskCommand(TaskDefinition taskDefinition) {

		List<TaskCommand> taskCommandInsts = taskDefinition.getTaskCommands();
		List<TaskCommand> taskCommandInstsNew = new ArrayList<TaskCommand>();
		Map<String, AbstractCommandFilter> abstractCommandFilterMap = Context
				.getProcessEngineConfiguration().getCommandFilterMap();
		for (TaskCommand taskCommandInst : taskCommandInsts) {
			AbstractCommandFilter abstractCommandFilter = abstractCommandFilterMap.get(taskCommandInst.getTaskCommandType());
			if (abstractCommandFilter != null) {
				abstractCommandFilter.setProcessTracking(false);
				abstractCommandFilter.setTaskCommandInst(taskCommandInst);
				if (abstractCommandFilter.accept(null)) {
					taskCommandInstsNew.add(taskCommandInst);
				}
			} else {
				taskCommandInstsNew.add(taskCommandInst);
			}
		}

		// for (TaskCommandInst taskCommandInstObj : taskCommandInstsNew) {
		// taskCommandInstObj.clearParamMap();
		// taskCommandInstObj.execExpressionParam(null,null);
		// }

		// taskCommandInstsNew.get(0).getParamMap();

		return taskCommandInstsNew;
	}
}
