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
package org.foxbpm.engine.impl.persistence;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.model.ProcessDefinitionQueryImpl;
import org.foxbpm.engine.repository.ProcessDefinition;

/**
 * 定义发布持久化管理器
 * @author kenshin
 *
 */
public class DeploymentEntityManager extends AbstractManager {

	public DeploymentEntity findDeploymentById(String deploymentId) {
		DeploymentEntity deployment = (DeploymentEntity)getSqlSession().selectOne("selectDeploymentById", deploymentId);
		return deployment;
	}
	
	public void insertDeployment(DeploymentEntity deployment){
		getSqlSession().insert(deployment);
		Map<String,ResourceEntity> resources = deployment.getResources();
		for(ResourceEntity resource : resources.values()){
			resource.setDeploymentId(deployment.getId());
			Context.getCommandContext().getResourceManager().insert(resource);
		}
	}
	  

	public void deleteDeployment(String deploymentId, boolean cascade) {
		List<ProcessDefinition> processDefinitions = new ProcessDefinitionQueryImpl().deploymentId(deploymentId).list();
		List<ResourceEntity> resources = Context.getCommandContext().getResourceManager().findResourcesByDeploymentId(deploymentId);
		for(ResourceEntity resource : resources){
			getSqlSession().delete(resource);
		}
		if (cascade) {
			for (ProcessDefinition processDefinition : processDefinitions) {
				Context.getCommandContext().getProcessDefinitionManager().deleteProcessDefinition(processDefinition.getId(), cascade);
				String processDefinitionId = processDefinition.getId();
				getProcessInstanceManager().deleteProcessInstancesByProcessDefinition(processDefinitionId,cascade);
			}
		}
	}
}
