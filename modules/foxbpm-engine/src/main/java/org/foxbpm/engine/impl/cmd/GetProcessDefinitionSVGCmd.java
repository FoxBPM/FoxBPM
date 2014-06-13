package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.diagramview.factory.FoxbpmProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.diagramview.factory.ConcreteProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

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
		// SVG上一层接口，独立于SVG，后期支持动态切换到微软SVG实现
		FoxbpmProcessDefinitionVOFactory svgFactory = new ConcreteProcessDefinitionVOFactory();
		return svgFactory
				.createProcessDefinitionVOString(deployedProcessDefinition);
	}
}
