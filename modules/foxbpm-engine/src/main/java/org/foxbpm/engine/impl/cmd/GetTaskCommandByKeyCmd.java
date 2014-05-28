package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.task.TaskCommand;

public class GetTaskCommandByKeyCmd implements Command<List<TaskCommand>> {

	private String processKey;
	public GetTaskCommandByKeyCmd(String processKey) {
		this.processKey = processKey;
	}
	
	@Override
	public List<TaskCommand> execute(CommandContext commandContext) {
		DeploymentManager deploymentManager = Context.getProcessEngineConfiguration().getDeploymentManager();
		ProcessDefinition processDefinition = deploymentManager.findDeployedLatestProcessDefinitionByKey(processKey);
		if(processDefinition == null){
			throw new FoxBPMIllegalArgumentException("为找到key为："+processKey+" 的流程定义");
		}
		return null;
		
	}
}
