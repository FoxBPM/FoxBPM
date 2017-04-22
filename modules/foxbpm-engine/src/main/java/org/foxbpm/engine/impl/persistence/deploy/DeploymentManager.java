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
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.model.ProcessDefinitionQueryImpl;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.QuartzUtil;
import org.foxbpm.engine.repository.ProcessDefinition;

/**
 * 流程发布管理器
 * 
 * @author Administrator
 * 
 */
public class DeploymentManager {
	
	protected Cache<ProcessDefinition> processDefinitionCache;
	protected List<Deployer> deployers;
	
	public void deploy(DeploymentEntity deployment) {
		// 先清理流程定义缓存
		CacheUtil.clearProcessDefinitionCache();
		CacheUtil.clearUserProcessDefinitionCache();
		// 然后再部署流程
		for (Deployer deployer : deployers) {
			deployer.deploy(deployment);
		}
	}
	
	public ProcessDefinitionEntity findDeployedProcessDefinitionById(String processDefinitionId) {
		if (processDefinitionId == null) {
			throw ExceptionUtil.getException("10101002");
		}
		ProcessDefinitionEntity processDefinition = null;
		processDefinition = (ProcessDefinitionEntity) processDefinitionCache.get(processDefinitionId);
		if(processDefinition != null){
			return processDefinition;
		}
		processDefinition = Context.getCommandContext().getProcessDefinitionManager().findProcessDefinitionById(processDefinitionId);
		if (processDefinition != null) {
			processDefinition = resolveProcessDefinition(processDefinition);
		}
		return processDefinition;
	}
	
	public List<ProcessDefinitionEntity> findProcessDefinitionGroupByKey() {
		List<ProcessDefinitionEntity> processDefinitions = Context.getCommandContext().getProcessDefinitionManager().findProcessDefinitionGroupByKey();
		for (int i = 0, size = processDefinitions.size(); i < size; i++) {
			processDefinitions.set(i, resolveProcessDefinition(processDefinitions.get(i)));
		}
		return processDefinitions;
	}
	
	public ProcessDefinitionEntity findDeployedLatestProcessDefinitionByKey(
	    String processDefinitionKey) {
		ProcessDefinitionEntity processDefinition = Context.getCommandContext().getProcessDefinitionManager().findLatestProcessDefinitionByKey(processDefinitionKey);
		if (processDefinition == null) {
			throw ExceptionUtil.getException("10102003",processDefinitionKey);
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;
	}
	
	public ProcessDefinitionEntity findDeployedProcessDefinitionByKeyAndVersion(
	    String processDefinitionKey, Integer processDefinitionVersion) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) Context.getCommandContext().getProcessDefinitionManager().findProcessDefinitionByKeyAndVersion(processDefinitionKey, processDefinitionVersion);
		if (processDefinition == null) {
			return null;
		}
		processDefinition = resolveProcessDefinition(processDefinition);
		return processDefinition;
	}
	
	public ProcessDefinitionEntity resolveProcessDefinition(
	    ProcessDefinitionEntity processDefinition) {
		String processDefinitionId = processDefinition.getId();
		String deploymentId = processDefinition.getDeploymentId();
		processDefinition = (ProcessDefinitionEntity) processDefinitionCache.get(processDefinitionId);
		if (processDefinition == null) {
			DeploymentEntity deployment = Context.getCommandContext().getDeploymentEntityManager().findDeploymentById(deploymentId);
			deployment.setNew(false);
			deploy(deployment);
			processDefinition = (ProcessDefinitionEntity) processDefinitionCache.get(processDefinitionId);
		}
		return processDefinition;
	}
	
	public void removeDeployment(String deploymentId, boolean cascade) {
		DeploymentEntityManager deploymentEntityManager = Context.getCommandContext().getDeploymentEntityManager();
		if (deploymentEntityManager.findDeploymentById(deploymentId) == null)
			throw ExceptionUtil.getException("10102003",deploymentId);
		
		List<ProcessDefinition> processDefinitions = new ProcessDefinitionQueryImpl(Context.getCommandContext()).deploymentId(deploymentId).list();
		for (ProcessDefinition processDefinition : processDefinitions) {
			processDefinitionCache.remove(processDefinition.getId());
			// 清空该流程定义所关联的调度器
			QuartzUtil.deleteJob(processDefinition.getKey());
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
