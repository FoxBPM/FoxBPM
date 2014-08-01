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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.model.ProcessDefinitionQueryImpl;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.repository.ProcessDefinition;
/**
 * 流程定义管理器
 * 
 * @author Kenshin
 */
public class ProcessDefinitionManager extends AbstractManager {
	
	/**
	 * 根据流程定义编号返回流程定时实体
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessDefinitionEntity findProcessDefinitionById(String processDefinitionId) {
		ProcessDefinitionEntity processEntityNew = getSqlSession().selectById(ProcessDefinitionEntity.class, processDefinitionId);
		return processEntityNew;
	}
	
	public ProcessDefinitionEntity findLatestProcessDefinitionByKey(String processDefinitionKey) {
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity) getSqlSession().selectOne("selectLatestProcessDefinitionByKey", processDefinitionKey);
		return processEntity;
	}
	
	public ProcessDefinitionEntity findProcessDefinitionByKeyAndVersion(
	    String processDefinitionKey, Integer processDefinitionVersion) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("key", processDefinitionKey);
		paramMap.put("version", processDefinitionVersion);
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity) getSqlSession().selectOne("selectProcessDefinitionByKeyAndVersion", paramMap);
		return processEntity;
	}
	
	public ProcessDefinitionEntity findProcessDefinitionByDeploymentAndKey(String deploymentId,
	    String key) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deploymentId", deploymentId);
		paramMap.put("key", key);
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity) getSqlSession().selectOne("selectProcessDefinitionByDeployIdAndKey", paramMap);
		return processEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessDefinitionEntity> findProcessDefinitionGroupByKey() {
		return (List<ProcessDefinitionEntity>) getSqlSession().selectList("selectProcessDefinitionGroupByKey");
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessDefinition> findProcessDefinitionsByQueryCriteria(
	    ProcessDefinitionQueryImpl processDefinitionQuery) {
		List<ProcessDefinitionEntity> processDefinitions = (List<ProcessDefinitionEntity>) getSqlSession().selectList("selectProcessDefinitionsByQueryCriteria", processDefinitionQuery);
		List<ProcessDefinition> result = new ArrayList<ProcessDefinition>();
		DeploymentManager deploymentManager = Context.getProcessEngineConfiguration().getDeploymentManager();
		if (processDefinitions != null) {
			Iterator<ProcessDefinitionEntity> iterator = processDefinitions.iterator();
			while (iterator.hasNext()) {
				ProcessDefinitionEntity processDefinitionEntity = iterator.next();
				ProcessDefinitionEntity newProcessDefinitionEntity = deploymentManager.resolveProcessDefinition(processDefinitionEntity);
				result.add(newProcessDefinitionEntity);
			}
		}
		return result;
	}
	
	public long findProcessDefinitionCountByQueryCriteria(
	    ProcessDefinitionQueryImpl processDefinitionQuery) {
		return (Long) getSqlSession().selectOne("selectProcessDefinitionCountByQueryCriteria", processDefinitionQuery);
	}
	
	public void deleteProcessDefinition(String processDefinitionId, boolean cascade) {
		// 删除流程实例
		if (cascade) {
			getProcessInstanceManager().deleteProcessInstancesByProcessDefinition(processDefinitionId, cascade);
		}
		// 删除流程定义
		getSqlSession().delete("deleteProcessDefinitionById", processDefinitionId);
	}
	
}
