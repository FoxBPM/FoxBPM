package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class DeleteProcessInstanceByIdCmd implements Command<Void> {

	private String processInstanceId;
	public DeleteProcessInstanceByIdCmd(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	 
	public Void execute(CommandContext commandContext) {
		commandContext.getProcessInstanceManager().deleteProcessInstanceById(processInstanceId);
		return null;
	}
}
