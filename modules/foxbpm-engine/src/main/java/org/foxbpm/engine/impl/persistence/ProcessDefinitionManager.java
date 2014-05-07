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

import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;

/**
 * 流程定义管理器
 * @author Kenshin
 */
public class ProcessDefinitionManager extends AbstractManager {

	public void test(){
		System.out.println("manager执行了");
		TaskEntity task = new TaskEntity();
		task.setId("200");
		getSqlSession().insert(task);
	}

	/**
	 * 根据流程定义编号返回流程定时实体，此
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessDefinitionEntity findProcessDefinitionById(String processDefinitionId) {
		// TODO Auto-generated method stub
		Cache cache = Context.getProcessEngineConfiguration().getDeploymentManager().getProcessDefinitionCache();
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity)cache.get(processDefinitionId);
		if(processEntity != null){
			return processEntity;
		}
		ProcessDefinitionEntity processEntityNew = getSqlSession().selectById(ProcessDefinitionEntity.class, processDefinitionId);
		if(processEntityNew != null){
			ResourceEntity resource = Context.getCommandContext().getResourceManager().selectResourceByDeployIdAndName(processEntityNew.getDeploymentId(), processEntityNew.getResourceName());
			if(resource == null){
				throw new FoxBPMException("数据库中不存在流程定义：" + processEntityNew.getName() + "的流程定义！");
			}
			//转换
			
			
		}
		
		return processEntityNew;
	}

	public ProcessDefinitionEntity findLatestProcessDefinitionByKey(String processDefinitionKey) {
		ProcessDefinitionEntity processEntity = (ProcessDefinitionEntity)getSqlSession().selectOne("selectLatestProcessDefinitionByKey", processDefinitionKey);
		if(processEntity != null){
			return findProcessDefinitionById(processEntity.getId());
		}
		return null;
	}

	public ProcessDefinitionEntity findProcessDefinitionByKeyAndVersion(String processDefinitionKey, Integer processDefinitionVersion) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessDefinitionEntity findProcessDefinitionByDeploymentAndKey(String deploymentId, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
