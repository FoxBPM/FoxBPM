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
package org.foxbpm.rest.service.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 流程定义集合资源
 * Get：processDefinitionQuery
 * @author ych
 *
 */
public class ProcessDefinitionCollectionResouce extends AbstractRestResource {

	@Get
	public DataResult getDefinitions() {
		ProcessEngine engine = ProcessEngineManagement.getDefaultProcessEngine();
		ProcessDefinitionQuery processDefinitionQuery = engine.getModelService().createProcessDefinitionQuery();
		Form query = getQuery();
		Set<String> names = query.getNames();
		if (names.contains(RestConstants.CATEGORY)) {
			processDefinitionQuery.processDefinitionCategory(getQueryParameter(RestConstants.CATEGORY, query));
		}
		if (names.contains(RestConstants.PROCESS_KEY)) {
			processDefinitionQuery.processDefinitionKey(getQueryParameter(RestConstants.PROCESS_KEY, query));
		}
		if (names.contains(RestConstants.NAME)) {
			processDefinitionQuery.processDefinitionName(getQueryParameter(RestConstants.NAME, query));
		}
		if (names.contains(RestConstants.NAME_LIKE)) {
			processDefinitionQuery.processDefinitionNameLike(getQueryParameter(RestConstants.NAME_LIKE, query));
		}

		List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for (ProcessDefinition process : processDefinitions) {
			results.add(process.getPersistentState());
		}
		DataResult result = new DataResult();
		result.setData(results);
		result.setTotal(processDefinitions.size());
		return result;
	}
}
