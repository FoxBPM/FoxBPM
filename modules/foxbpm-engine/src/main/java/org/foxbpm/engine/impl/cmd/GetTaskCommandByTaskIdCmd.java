package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.task.TaskCommand;

public class GetTaskCommandByTaskIdCmd implements Command<List<TaskCommand>> {

	private String taskId;
	public GetTaskCommandByTaskIdCmd(String taskId,boolean isProcessTracking) {
		this.taskId = taskId;
	}
	
	@Override
	public List<TaskCommand> execute(CommandContext commandContext) {
		List<TaskCommand> taskCommandInsts=new ArrayList<TaskCommand>();
		TaskEntity taskInstance=commandContext.getTaskManager().findTaskById(taskId);
		
		//非流程追踪查询
		if(taskInstance!=null){
			taskInstance.getTaskDefinition().getTaskCommands();
		}
		return taskCommandInsts;
	}
}
