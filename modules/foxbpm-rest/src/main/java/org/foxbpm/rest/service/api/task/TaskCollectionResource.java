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
		
		if(!validationUser())
			return null;
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		Form query = getQuery();
		Set<String> names = query.getNames();
		
		taskQuery.taskAssignee(userId);
		taskQuery.taskCandidateUser(userId);
		
		boolean ended = false;
		if(names.contains(RestConstants.IS_END)){
			ended = StringUtil.getBoolean(getQueryParameter(RestConstants.IS_END, query));
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
