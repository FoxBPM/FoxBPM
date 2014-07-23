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

import org.foxbpm.engine.impl.datavariable.DataVariableDefinition;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;

public class VariableDefinitonResouces extends AbstractRestResource {

	public DataResult getProcessDefinitionVariable(){
		String processKey = getAttribute("processKey");
		int version = StringUtil.getInt(getAttribute("version"));
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)FoxBpmUtil.getProcessEngine().getModelService().getProcessDefinition(processKey, version); 
		if(processDefinition == null){
			return null;
		}
		List<DataVariableDefinition> dataVariableDefinitions = processDefinition.getDataVariableMgmtDefinition().getDataVariableDefinitions();
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for (DataVariableDefinition variableDefinition : dataVariableDefinitions) {
			results.add(variableDefinition.getPersistentState());
		}
		DataResult result = new DataResult();
		result.setData(results);
		result.setStart(0);
		result.setTotal(dataVariableDefinitions.size());
		return result;
	}
}
