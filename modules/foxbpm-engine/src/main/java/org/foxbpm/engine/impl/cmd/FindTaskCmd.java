package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class FindTaskCmd implements Command<TaskEntity> {

	protected String taskId;
	
	public FindTaskCmd(String taskId){
		this.taskId=taskId;
	}
	
	public TaskEntity execute(CommandContext commandContext) {
		TaskEntity taskEntity=commandContext.getTaskManager().findTaskById(taskId);
		return taskEntity;
	}

}
