package org.foxbpm.rest.service.api.model;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProcessDefinitionResouce extends ServerResource{

	@Get
	public ProcessDefinitionResponse getProcessDefinition(){
		String processDefinitionId = getAttribute("processDefinitionId");
		if(processDefinitionId == null){
			return null;
		}
		ProcessEngine engine = ProcessEngineManagement.getDefaultProcessEngine();
		ModelService modelService =engine.getModelService();
		ProcessDefinition processEntity = modelService.getProcessDefinitionById(processDefinitionId);
		if(processEntity == null){
			return null;
		}
		ProcessDefinitionResponse processDefintionResponse = new ProcessDefinitionResponse();
		processDefintionResponse.setId(processEntity.getId());
		processDefintionResponse.setCatory(processEntity.getCategory());
		processDefintionResponse.setDeploymentId(processEntity.getDeploymentId());
		processDefintionResponse.setName(processEntity.getName());
		return processDefintionResponse;
	}
}
