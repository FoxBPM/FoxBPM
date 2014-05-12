package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.repository.ProcessDefinition;

public class GetProcessDefinitionById  implements Command<ProcessDefinition>{

	String processDefinitionId = null;
	public GetProcessDefinitionById(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	public ProcessDefinition execute(CommandContext commandContext) {
		if(processDefinitionId == null){
			throw new FoxBPMIllegalArgumentException("查询的processDefinitionId不能为null");
		}
		return commandContext.getProcessDefinitionManager().selectById(ProcessDefinitionEntity.class, processDefinitionId);
	}
}
