package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class CompleteTaskCmd extends NeedsActiveTaskCmd<Void> {

	private static final long serialVersionUID = 1L;

	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;

	public CompleteTaskCmd(String taskId, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		super(taskId);
		this.transientVariables = transientVariables;
		this.persistenceVariables = persistenceVariables;
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		if (transientVariables != null) {
			task.setProcessInstanceVariables(transientVariables);
		}

		task.complete();
		return null;
	}

}
