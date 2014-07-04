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
		ResourceManager resourceManager = Context.getCommandContext().getResourceManager();
		for(ResourceEntity resource : resources.values()){
			resource.setDeploymentId(deployment.getId());
			resourceManager.insert(resource);
		}
	}
	  

	public void deleteDeployment(String deploymentId, boolean cascade) {
		//删除大字段表
		getResourceManager().deleteResourceByDeploymentId(deploymentId);
		//删除所有的流程定义
		if (cascade) {
			List<ProcessDefinition> processDefinitions = new ProcessDefinitionQueryImpl().deploymentId(deploymentId).list();
			ProcessDefinitionManager processDefinitionManager = Context.getCommandContext()
					.getProcessDefinitionManager();
			for (ProcessDefinition processDefinition : processDefinitions) {
				processDefinitionManager.deleteProcessDefinition(processDefinition.getId(), cascade);
			}
		}
		//删除deployment发布记录
		getSqlSession().delete("deleteDeploymentById", deploymentId);
	}
}
