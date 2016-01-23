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

package org.foxbpm.engine.repository;

import org.foxbpm.engine.impl.model.ProcessDefinitionQueryImpl;
import org.foxbpm.engine.query.Query;

/**
 * 流程定义查询
 * @author ych
 *
 */
public interface ProcessDefinitionQuery extends Query<ProcessDefinitionQuery, ProcessDefinition> {
  
	 /** 根据流程定义ID查询   */
	ProcessDefinitionQuery processDefinitionId(String processDefinitionId);

	/** 根据流程分类查询   */
	ProcessDefinitionQuery processDefinitionCategory(String processDefinitionCategory);
	
	/** 根据流程分类模糊查询   */
	ProcessDefinitionQuery processDefinitionCategoryLike(String processDefinitionCategoryLike);

	/** 根据流程名称查询   */
	ProcessDefinitionQuery processDefinitionName(String processDefinitionName);

	/** 根据流程名称模糊查询   */
	ProcessDefinitionQuery processDefinitionNameLike(String processDefinitionNameLike);

	/** 根据发布号查询   */
	ProcessDefinitionQuery deploymentId(String deploymentId);

	/** 根据流程Key查询   */
	ProcessDefinitionQuery processDefinitionKey(String processDefinitionKey);

	/** 根据流程Key模糊查询   */
	ProcessDefinitionQuery processDefinitionKeyLike(String processDefinitionKeyLike);

	/** 根据流程版本号查询 */
	ProcessDefinitionQuery processDefinitionVersion(Integer processDefinitionVersion);

	/** 只查询最新版本 */
	ProcessDefinitionQueryImpl latestVersion();
	
	// ordering ////////////////////////////////////////////////////////////
	/** 根据分类排序  */
	ProcessDefinitionQuery orderByProcessDefinitionCategory();

	/** 根据Key排序  */
	ProcessDefinitionQuery orderByProcessDefinitionKey();

	/** 根据定义ID排序  */
	ProcessDefinitionQuery orderByProcessDefinitionId();

	ProcessDefinitionQuery orderByProcessDefinitionVersion();

	/** 根据定义名称排序  */
	ProcessDefinitionQuery orderByProcessDefinitionName();

	/** 根据发布号排序  */
	ProcessDefinitionQuery orderByDeploymentId();
  
}
