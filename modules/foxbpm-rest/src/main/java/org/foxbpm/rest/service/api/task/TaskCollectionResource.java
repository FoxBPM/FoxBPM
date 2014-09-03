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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
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
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HHmmssSSS" );
		
		Form queryForm = getQuery();
		Set<String> queryNames = queryForm.getNames();
		
		if(!validationUser())
			return null;
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		
		taskQuery.taskAssignee(userId);
		taskQuery.taskCandidateUser(userId);
		
		if(queryNames.contains("nameLike")) {
			taskQuery.taskNameLike(parseLikeValue(getQueryParameter("nameLike", queryForm)));
	    }
		
		if(queryNames.contains("bizKeyLike")) {
			taskQuery.businessKeyLike(parseLikeValue(getQueryParameter("bizKeyLike", queryForm)));
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
			taskQuery.processDefinitionNameLike(parseLikeValue(getQueryParameter("processDefinitionNameLike", queryForm)));
	    }
		
		if(queryNames.contains("nodeId")) {
			taskQuery.nodeId(getQueryParameter("nodeId", queryForm));
	    }
		
		if(queryNames.contains("processDefinitionId")) {
			taskQuery.processDefinitionId(getQueryParameter("processDefinitionId", queryForm));
	    }
		
		if(queryNames.contains("assigneed")) {
			String unassigneed = getQueryParameter("processDefinitionId", queryForm);
			if("0".equals(unassigneed)){
				taskQuery.taskUnnassigned();
			}else if("1".equals(unassigneed)){
				//已领取
			}else{
				//全部
			}
			
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
			taskQuery.taskDescriptionLike(getQueryParameter("descriptionLike", queryForm));
	    }
		
		if(queryNames.contains("createTimeB")) {
			String dateB = getQueryParameter("createTimeB", queryForm);
			Date createTimeB;
			try {
				createTimeB = sdf.parse(dateB+" 0000000");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式："+dateB);
			}
			taskQuery.taskCreatedAfter(createTimeB);
	    }
		
		if(queryNames.contains("createTimeE")) {
			String dateE = getQueryParameter("createTimeE", queryForm);
			Date createTimeE;
			try {
				createTimeE = sdf.parse(dateE + " 2359999");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式："+dateE);
			}
			taskQuery.taskCreatedBefore(createTimeE);
	    }
		
		if(queryNames.contains("dueDateB")) {
			String dateB = getQueryParameter("dueDateB", queryForm);
			Date dueDateB;
			try {
				dueDateB = sdf.parse(dateB+" 0000000");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("期望时间格式转换错误，需要yyyy-MM-dd格式,实际格式："+dateB);
			}
			taskQuery.taskDueDateAfter(dueDateB);
	    }
		
		if(queryNames.contains("dueDateE")) {
			String dateE = getQueryParameter("dueDateE", queryForm);
			dateE = " 235959999";
			Date dueDateE;
			try {
				dueDateE = sdf.parse(dateE + " 2359999");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式："+dateE);
			}
			taskQuery.taskCreatedBefore(dueDateE);
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
