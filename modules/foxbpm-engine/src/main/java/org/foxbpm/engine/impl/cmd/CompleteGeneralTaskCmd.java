package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.command.GeneralTaskCommand;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class CompleteGeneralTaskCmd extends AbstractExpandTaskCmd<GeneralTaskCommand, Void>{

	private static final long serialVersionUID = 1L;

	public CompleteGeneralTaskCmd(GeneralTaskCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		
		
		
		
		
		return null;
	}

}
