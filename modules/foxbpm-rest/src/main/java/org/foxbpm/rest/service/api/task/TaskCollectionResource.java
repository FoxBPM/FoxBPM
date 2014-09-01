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
package org.foxbpm.rest.service.api.task;

import java.util.Set;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 任务查询接口
 * @author ych
 *
 */
public class TaskCollectionResource extends AbstractRestResource {

	@Get
	public DataResult getTasks(){
		
		Form queryForm = getQuery();
		Set<String> queryNames = queryForm.getNames();
		
		if(!validationUser())
			return null;
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		
		taskQuery.taskAssignee(userId);
		taskQuery.taskCandidateUser(userId);
		
		if(queryNames.contains("nameLike")) {
			taskQuery.taskNameLike(getQueryParameter("nameLike", queryForm));
	    }
		
		if(queryNames.contains("bizKeyLike")) {
			taskQuery.businessKeyLike(getQueryParameter("bizKeyLike", queryForm));
	    }
		
		if(queryNames.contains("tokenId")) {
			taskQuery.tokenId(getQueryParameter("tokenId", queryForm));
	    }
		
		if(queryNames.contains("processInstanceId")) {
			taskQuery.processInstanceId(getQueryParameter("processInstanceId", queryForm));
	    }
		
		if(queryNames.contains("processDefinitionKey")) {
			taskQuery.processDefinitionKey(getQueryParameter("processDefinitionKey", queryForm));
	    }
		
		if(queryNames.contains("processDefinitionNameLike")) {
			taskQuery.processDefinitionNameLike(getQueryParameter("processDefinitionNameLike", queryForm));
	    }
		
		if(queryNames.contains("nodeId")) {
			taskQuery.nodeId(getQueryParameter("nodeId", queryForm));
	    }
		
		if(queryNames.contains("processDefinitionId")) {
			taskQuery.processDefinitionId(getQueryParameter("processDefinitionId", queryForm));
	    }
		
		if(queryNames.contains("unAssigneed")) {
			taskQuery.taskUnnassigned();
	    }
		
		if(queryNames.contains("subjectLike")) {
			taskQuery.taskSubjectLike(getQueryParameter("subjectLike", queryForm));
	    }
		
		if(queryNames.contains("initiator")) {
			taskQuery.initiator(getQueryParameter("initiator", queryForm));
	    }
		
		if(queryNames.contains("isSuspended")) {
			taskQuery.isSuspended(StringUtil.getBoolean(getQueryParameter("isSuspended", queryForm)));
	    }
		
		if(queryNames.contains("taskId")) {
			taskQuery.taskId(getQueryParameter("taskId", queryForm));
	    }
		
		if(queryNames.contains("descriptionLike")) {
			taskQuery.taskId(getQueryParameter("descriptionLike", queryForm));
	    }
		
		boolean ended = false;
		if(queryNames.contains(RestConstants.IS_END)){
			ended = StringUtil.getBoolean(getQueryParameter(RestConstants.IS_END, queryForm));
		}
		if(ended){
			taskQuery.taskIsEnd();
		}else{
			taskQuery.taskNotEnd();
		}
		DataResult result = paginateList(taskQuery);
		return result;
	}
	
}
