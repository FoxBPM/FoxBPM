package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.filter.AbstractCommandFilter;
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
		if (taskInstance != null) {

			for (TaskCommand taskCommand : taskInstance.getTaskDefinition().getTaskCommands()) {
				AbstractCommandFilter abstractCommandFilter = Context.getProcessEngineConfiguration().getAbstractCommandFilterMap()
						.get(taskCommand.getTaskCommandType());
				if (abstractCommandFilter != null) {
					abstractCommandFilter.setProcessTracking(isProcessTracking);
					abstractCommandFilter.setTaskCommandInst(taskCommand);
					if (abstractCommandFilter.accept(taskInstance)) {
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
}
