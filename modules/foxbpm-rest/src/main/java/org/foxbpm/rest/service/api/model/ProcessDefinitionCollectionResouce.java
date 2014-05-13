package org.foxbpm.rest.service.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.rest.common.api.DataResponse;
import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProcessDefinitionCollectionResouce extends ServerResource{

	@Get
	public DataResponse getDefinitions(){
		ProcessEngine engine = ProcessEngineManagement.getDefaultProcessEngine();
		
		ProcessDefinitionQuery processDefinitionQuery = engine.getModelService().createProcessDefinitionQuery();
	    Form query = getQuery();
	    Set<String> names = query.getNames();
	    
	    // Populate filter-parameters
	    if(names.contains("category")) {
	      processDefinitionQuery.processDefinitionCategory(getQueryParameter("category", query));
	    }
	    if(names.contains("categoryLike")) {
	      processDefinitionQuery.processDefinitionCategoryLike(getQueryParameter("categoryLike", query));
	    }
	    if(names.contains("key")) {
	      processDefinitionQuery.processDefinitionKey(getQueryParameter("key", query));
	    }
	    if(names.contains("keyLike")) {
	      processDefinitionQuery.processDefinitionKeyLike(getQueryParameter("keyLike", query));
	    }
	    if(names.contains("name")) {
	      processDefinitionQuery.processDefinitionName(getQueryParameter("name", query));
	    }
	    if(names.contains("nameLike")) {
	      processDefinitionQuery.processDefinitionNameLike(getQueryParameter("nameLike", query));
	    }
	    
	    List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();
	    List<ProcessDefinitionResponse> results = new ArrayList<ProcessDefinitionResponse>();
	    for(ProcessDefinition process : processDefinitions){
	    	ProcessDefinitionResponse response = new ProcessDefinitionResponse();
	    	response.setId(process.getId());
	    	response.setName(process.getName());
	    	response.setProcessKey(process.getKey());
	    	results.add(response);
	    }
	    DataResponse result = new DataResponse();
	    result.setData(results);
	    result.setStart(0);
	    result.setTotal(processDefinitions.size());
	    
	    return result;
	}
	
	protected String getQueryParameter(String name, Form query) {
	    return query.getFirstValue(name);
	}
}
