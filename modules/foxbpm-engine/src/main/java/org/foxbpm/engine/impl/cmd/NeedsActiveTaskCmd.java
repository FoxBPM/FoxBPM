package org.foxbpm.engine.impl.cmd;

import java.io.Serializable;

import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public abstract class NeedsActiveTaskCmd<T> implements Command<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String taskId;
	public NeedsActiveTaskCmd(String taskId) {
		this.taskId = taskId;
	}

	public T execute(CommandContext commandContext) {
		if (taskId == null) {
			throw new FoxBPMIllegalArgumentException("taskId is null");
		}
		TaskEntity task = Context.getCommandContext().getTaskManager().findTaskById(taskId);
		if (task == null) {
			throw new FoxBPMObjectNotFoundException("Cannot find task with id " + taskId);
		}
		if(task.hasEnded()){
			throw new FoxBPMBizException("task is ended");
		}
		if (task.isSuspended()) {
			throw new FoxBPMBizException("task is suspended");
		}

		return execute(commandContext, task);
	}

	/** 子类需要实现这个方法 */
	protected abstract T execute(CommandContext commandContext, TaskEntity task);

}
