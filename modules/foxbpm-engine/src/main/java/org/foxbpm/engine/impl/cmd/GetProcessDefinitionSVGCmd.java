package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.svg.factory.AbstractProcessDefinitionSVGFactory;
import org.foxbpm.engine.impl.svg.factory.ProcessDefinitionSVGFactory;

public class GetProcessDefinitionSVGCmd implements Command<String> {
	private String processDefinitionId;

	public GetProcessDefinitionSVGCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public String execute(CommandContext commandContext) {
		ProcessDefinitionEntity deployedProcessDefinition = commandContext
				.getProcessEngineConfigurationImpl().getDeploymentManager()
				.findDeployedProcessDefinitionById(processDefinitionId);
		AbstractProcessDefinitionSVGFactory svgFactory = new ProcessDefinitionSVGFactory();
		return svgFactory
				.createProcessDefinitionSVGString(deployedProcessDefinition);
	}
}
