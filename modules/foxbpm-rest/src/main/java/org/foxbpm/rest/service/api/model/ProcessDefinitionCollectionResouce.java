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
		if (names.contains("category")) {
			processDefinitionQuery.processDefinitionCategory(getQueryParameter("category", query));
		}
		if (names.contains("categoryLike")) {
			processDefinitionQuery.processDefinitionCategoryLike(getQueryParameter("categoryLike", query));
		}
		if (names.contains("key")) {
			processDefinitionQuery.processDefinitionKey(getQueryParameter("key", query));
		}
		if (names.contains("keyLike")) {
			processDefinitionQuery.processDefinitionKeyLike(getQueryParameter("keyLike", query));
		}
		if (names.contains("name")) {
			processDefinitionQuery.processDefinitionName(getQueryParameter("name", query));
		}
		if (names.contains("nameLike")) {
			processDefinitionQuery.processDefinitionNameLike(getQueryParameter("nameLike", query));
		}

		List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for (ProcessDefinition process : processDefinitions) {
			results.add(process.getPersistentState());
		}
		DataResult result = new DataResult();
		result.setData(results);
		result.setStart(0);
		result.setTotal(processDefinitions.size());
		return result;
	}
}
