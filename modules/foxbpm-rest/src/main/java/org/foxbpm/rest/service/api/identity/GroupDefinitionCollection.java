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

import java.util.List;

import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 获取所有的组定义
 * @author ych
 *
 */
public class GroupDefinitionCollection extends AbstractRestResource {

	@Get
	public DataResult getAllGroupDefinitions(){
		List<GroupDefinition> groupDefinitions = FoxBpmUtil.getProcessEngine().getIdentityService().getAllGroupDefinitions();
		//数据响应体构造
		DataResult result = new DataResult();
		result.setData(groupDefinitions);
		result.setTotal(groupDefinitions.size());
		return result;
	}
}
