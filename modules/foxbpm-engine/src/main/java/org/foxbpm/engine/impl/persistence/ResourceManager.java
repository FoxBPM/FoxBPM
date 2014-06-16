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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.entity.ResourceEntity;

/**
 * 资源文件管理器
 * @author kenshin
 *
 */
public class ResourceManager extends AbstractManager {
	
	@SuppressWarnings("unchecked")
	public List<ResourceEntity> findResourcesByDeploymentId(String id) {
		List<ResourceEntity> resources = (List<ResourceEntity>) getSqlSession().selectListWithRawParameter("selectResourceByDeploymentId", id);
		return resources;
	}
	
	public ResourceEntity selectResourceByDeployIdAndName(String deploymentId,String resourceName){
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("deployId", deploymentId);
		paramsMap.put("name", resourceName);
		return (ResourceEntity)getSqlSession().selectOne("selectResourceByDeployIdAndName", paramsMap);
	}
	
	public void deleteResourceByDeploymentId(String deploymentId){
		getSqlSession().delete("deleteResourceByDeploymentId", deploymentId);
	}
	
}
