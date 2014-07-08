/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.persistence.deploy;

import java.util.List;

import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.model.ProcessDefinitionQueryImpl;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.repository.ProcessDefinition;

/**
 * 流程发布管理器
 * @author Administrator
 *
 */
public class DeploymentManager {

	protected Cache<ProcessDefinition> processDefinitionCache;
	protected List<Deployer> deployers;

	public void deploy(DeploymentEntity deployment) {
		for (Deployer deployer : deployers) {
			deployer.deploy(deployment);
		}
	}

	public ProcessDefinitionEntity findDeployedProcessDefinitionById(String processDefinitionId) {
		if (processDefinitionId == null) {
			throw new FoxBPMIllegalArgumentException("processDefinitionId不能为 : null");
		}
		ProcessDefinitionEntity processDefinition = Context.getCommandContext().getProcessDefinitionManager().findProcessDefinitionById(processDefinitionId);
		if (processDefinition != null) {
			processDefinition = resolveProcessDefinition(processDefinition);
		}
		return processDefinition;
	}
	
	public List<ProcessDefinitionEntity> findProcessDefinitionGroupByKey(){
		List<ProcessDefinitionEntity> processDefinitions = Context.getCommandContext().getProcessDefinitionManager().findProcessDefinitionGroupByKey();
		for(ProcessDefinitionEntity process : processDefinitions){
			resolveProcessDefinition(process);
		}
		return processDefinitions;
	}

	public ProcessDefinitionEntity findDeployedLatestProcessDefinitionByKey(String processDefinitionKey) {
		ProcessDefinitionEntity processDefinition = Context.getCommandContext().getProcessDefinitionManager()
				.findLatestProcessDefinitionByKey(processDefinitionKey);
		if (processDefinition == null) {
			throw new FoxBPMObjectNotFoundException("数据库中未找到流程key = '" + processDefinitionKey + "'的流程定义！");
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;
	}

	public ProcessDefinitionEntity findDeployedProcessDefinitionByKeyAndVersion(String processDefinitionKey,
			Integer processDefinitionVersion) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) Context.getCommandContext()
				.getProcessDefinitionManager().findProcessDefinitionByKeyAndVersion(processDefinitionKey, processDefinitionVersion);
		if (processDefinition == null) {
			return null;
//			throw new FoxBPMObjectNotFoundException("数据库中未找到key = '" + processDefinitionKey + "' ， version 为='"
//					+ processDefinitionVersion + "'的流程定义");
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;
	}

	public ProcessDefinitionEntity resolveProcessDefinition(ProcessDefinitionEntity processDefinition) {
		String processDefinitionId = processDefinition.getId();
		String deploymentId = processDefinition.getDeploymentId();
		processDefinition = (ProcessDefinitionEntity) processDefinitionCache.get(processDefinitionId);
		if (processDefinition == null) {
			DeploymentEntity deployment = Context.getCommandContext().getDeploymentEntityManager().findDeploymentById(deploymentId);
			deployment.setNew(false);
			deploy(deployment);
			processDefinition = (ProcessDefinitionEntity) processDefinitionCache.get(processDefinitionId);

			if (processDefinition == null) {
				throw new FoxBPMException("deploymentId = '" + deploymentId + "' 的发布中不存在  processDefinitionId = '" + processDefinitionId
						+ "' 的流程定义或未放入缓存");
			}
		}
		return processDefinition;
	}

	public void removeDeployment(String deploymentId, boolean cascade) {
		DeploymentEntityManager deploymentEntityManager = Context.getCommandContext().getDeploymentEntityManager();
		if (deploymentEntityManager.findDeploymentById(deploymentId) == null)
			throw new FoxBPMObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.");

		List<ProcessDefinition> processDefinitions = new ProcessDefinitionQueryImpl(Context.getCommandContext()).deploymentId(deploymentId).list();
		for (ProcessDefinition processDefinition : processDefinitions) {
			processDefinitionCache.remove(processDefinition.getId());
		}
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

	public Cache<ProcessDefinition> getProcessDefinitionCache() {
		return processDefinitionCache;
	}

	public void setProcessDefinitionCache(Cache<ProcessDefinition> processDefinitionCache) {
		this.processDefinitionCache = processDefinitionCache;
	}
}
