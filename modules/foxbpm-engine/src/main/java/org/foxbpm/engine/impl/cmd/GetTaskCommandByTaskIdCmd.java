package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.task.TaskCommand;

public class GetTaskCommandByTaskIdCmd implements Command<List<TaskCommand>> {

	private String taskId;
	private boolean isProcessTracking;
	public GetTaskCommandByTaskIdCmd(String taskId,boolean isProcessTracking) {
		this.taskId = taskId;
		this.isProcessTracking = isProcessTracking;
	}
	@Override
	public List<TaskCommand> execute(CommandContext commandContext) {
		List<TaskCommand> taskCommandInsts=new ArrayList<TaskCommand>();
		TaskEntity taskInstance=commandContext.getTaskManager().findTaskById(taskId);
		//非流程追踪查询
		if(taskInstance!=null){
//			taskCommandInsts= CoreUtil.getTaskCommandInst(taskInstance,this.isProcessTracking);
		}
		return taskCommandInsts;
	}
}
