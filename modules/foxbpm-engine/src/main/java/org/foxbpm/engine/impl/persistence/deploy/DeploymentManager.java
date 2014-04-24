package org.foxbpm.engine.impl.persistence.deploy;

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.repository.ProcessDefinition;


public class DeploymentManager {

	protected DeploymentCache<ProcessDefinitionEntity> processDefinitionCache;
	protected DeploymentCache<Object> knowledgeBaseCache; // Needs to be object
															// to avoid an
															// import to Drools
															// in this core
															// class
	protected List<Deployer> deployers;

	public void deploy(DeploymentEntity deployment) {
		for (Deployer deployer : deployers) {
			deployer.deploy(deployment);
		}
	}

	public ProcessDefinitionEntity findDeployedProcessDefinitionById(String processDefinitionId) {
		if (processDefinitionId == null) {
			throw new FoxBPMIllegalArgumentException("Invalid process definition id : null");
		}
		BpmnDeployer BpmnDeployer=(BpmnDeployer)deployers.get(0);
		return (ProcessDefinitionEntity)BpmnDeployer.getProcessModelParseHandler().createProcessDefinition("", "111");
		
		/*
		ProcessDefinitionEntity processDefinition = Context.getCommandContext().getProcessDefinitionManager()
				.findProcessDefinitionById(processDefinitionId);
		if (processDefinition == null) {
			//throw new FoxBPMObjectNotFoundException("no deployed process definition found with id '" + processDefinitionId + "'",
			//		ProcessDefinition.class);
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;*/
	}

	public ProcessDefinitionEntity findDeployedLatestProcessDefinitionByKey(String processDefinitionKey) {
		ProcessDefinitionEntity processDefinition = Context.getCommandContext().getProcessDefinitionManager()
				.findLatestProcessDefinitionByKey(processDefinitionKey);
		if (processDefinition == null) {
			//throw new ActivitiObjectNotFoundException("no processes deployed with key '" + processDefinitionKey + "'",
			//		ProcessDefinition.class);
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;
	}

	public ProcessDefinitionEntity findDeployedProcessDefinitionByKeyAndVersion(String processDefinitionKey,
			Integer processDefinitionVersion) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) Context.getCommandContext()
				.getProcessDefinitionManager().findProcessDefinitionByKeyAndVersion(processDefinitionKey, processDefinitionVersion);
		if (processDefinition == null) {
			//throw new ActivitiObjectNotFoundException("no processes deployed with key = '" + processDefinitionKey + "' and version = '"
			//		+ processDefinitionVersion + "'", ProcessDefinition.class);
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;
	}

	public ProcessDefinitionEntity resolveProcessDefinition(ProcessDefinitionEntity processDefinition) {
		String processDefinitionId = processDefinition.getId();
		String deploymentId = processDefinition.getDeploymentId();
		processDefinition = processDefinitionCache.get(processDefinitionId);
		if (processDefinition == null) {
			DeploymentEntity deployment = Context.getCommandContext().getDeploymentEntityManager().findDeploymentById(deploymentId);
			deployment.setNew(false);
			deploy(deployment);
			processDefinition = processDefinitionCache.get(processDefinitionId);

			if (processDefinition == null) {
				throw new FoxBPMException("deployment '" + deploymentId + "' didn't put process definition '" + processDefinitionId
						+ "' in the cache");
			}
		}
		return processDefinition;
	}

	public void removeDeployment(String deploymentId, boolean cascade) {
		DeploymentEntityManager deploymentEntityManager = Context.getCommandContext().getDeploymentEntityManager();
		//if (deploymentEntityManager.findDeploymentById(deploymentId) == null)
			//throw new ActivitiObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.", DeploymentEntity.class);

		// Remove any process definition from the cache
		List<ProcessDefinition> processDefinitions =null; //new ProcessDefinitionQueryImpl(Context.getCommandContext()).deploymentId(deploymentId)
				//.list();
		for (ProcessDefinition processDefinition : processDefinitions) {
			processDefinitionCache.remove(processDefinition.getId());
		}

		// Delete data
		deploymentEntityManager.deleteDeployment(deploymentId, cascade);
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	public List<Deployer> getDeployers() {
		return deployers;
	}

	public void setDeployers(List<Deployer> deployers) {
		this.deployers = deployers;
	}

	public DeploymentCache<ProcessDefinitionEntity> getProcessDefinitionCache() {
		return processDefinitionCache;
	}

	public void setProcessDefinitionCache(DeploymentCache<ProcessDefinitionEntity> processDefinitionCache) {
		this.processDefinitionCache = processDefinitionCache;
	}

	public DeploymentCache<Object> getKnowledgeBaseCache() {
		return knowledgeBaseCache;
	}

	public void setKnowledgeBaseCache(DeploymentCache<Object> knowledgeBaseCache) {
		this.knowledgeBaseCache = knowledgeBaseCache;
	}

}
