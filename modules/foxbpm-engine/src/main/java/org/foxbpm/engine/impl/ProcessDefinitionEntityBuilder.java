package org.foxbpm.engine.impl;

import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;

public class ProcessDefinitionEntityBuilder extends ProcessDefinitionBuilder {

	@Override
	protected KernelProcessDefinitionImpl createProcessDefinition(String processDefinitionId) {
		return new ProcessDefinitionEntity(processDefinitionId);
	}

}
