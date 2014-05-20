package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class CompleteTaskCmd extends NeedsActiveTaskCmd<Void> {

	private static final long serialVersionUID = 1L;

	 protected Map<String, Object> variables;
	
	public CompleteTaskCmd(String taskId, Map<String, Object> variables) {
		super(taskId);
	    this.variables = variables;
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		  if (variables!=null) {
		      task.setExecutionVariables(variables);
		    }
		    
		    task.complete();
		    return null;
	}

}
