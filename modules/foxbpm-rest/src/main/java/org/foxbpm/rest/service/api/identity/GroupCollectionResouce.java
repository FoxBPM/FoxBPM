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
 * @author ych
 */
package org.foxbpm.rest.service.api.identity;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 获取所有的组实体
 * @author ych
 *
 */
public class GroupCollectionResouce extends AbstractRestResource{
	
	@Get
	public DataResult getAllGroups(){
		List<GroupEntity> groupEntitys = new ArrayList<GroupEntity>();
		List<GroupDefinition> groupDefinitions = FoxBpmUtil.getProcessEngine().getProcessEngineConfiguration().getGroupDefinitions();
		IdentityService identityService = FoxBpmUtil.getProcessEngine().getIdentityService();
		if(groupDefinitions != null && ! groupDefinitions.isEmpty()){
			for(GroupDefinition tmpDefintion : groupDefinitions){
				List<GroupEntity> groups = identityService.getAllGroup(tmpDefintion.getType());
				groupEntitys.addAll(groups);
			}
		}
		//数据响应体构造
		DataResult result = new DataResult();
		result.setData(groupEntitys);
		result.setStart(0);
		result.setTotal(groupEntitys.size());
		return result;
	}

}
