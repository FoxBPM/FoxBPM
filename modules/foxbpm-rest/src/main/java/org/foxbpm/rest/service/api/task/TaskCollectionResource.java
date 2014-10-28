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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.task.TaskQueryProperty;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.query.QueryProperty;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.resource.Get;

/**
 * 任务查询接口
 * 
 * @author ych
 * 
 */
public class TaskCollectionResource extends AbstractRestResource {
	
	private static HashMap<String, QueryProperty> properties = new HashMap<String, QueryProperty>();
	
	static {
		properties.put("id", TaskQueryProperty.TASK_ID);
		properties.put("name", TaskQueryProperty.NAME);
		properties.put("description", TaskQueryProperty.DESCRIPTION);
		properties.put("dueDate", TaskQueryProperty.DUE_DATE);
		properties.put("createTime", TaskQueryProperty.CREATE_TIME);
		properties.put("priority", TaskQueryProperty.PRIORITY);
		properties.put("processInstanceId", TaskQueryProperty.PROCESS_INSTANCE_ID);
		properties.put("endTime", TaskQueryProperty.END_TIME);
	}
	
	@SuppressWarnings("unchecked")
	@Get
	public DataResult getTasks() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmssSSS");
		
		Form queryForm = getQuery();
		Set<String> queryNames = queryForm.getNames();
		if (!validateUser())
			return null;
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		if (queryNames.contains("assignee")) {
			String assignee = getQueryParameter("assignee", queryForm);
			taskQuery.taskAssignee(assignee);
		}
		if (queryNames.contains("candidateUser")) {
			String candidateUser = getQueryParameter("candidateUser", queryForm);
			taskQuery.taskCandidateUser(candidateUser);
		}
		
		if (queryNames.contains("nameLike")) {
			taskQuery.taskNameLike(parseLikeValue(getQueryParameter("nameLike", queryForm)));
		}
		
		if (queryNames.contains("bizKeyLike")) {
			taskQuery.businessKeyLike(parseLikeValue(getQueryParameter("bizKeyLike", queryForm)));
		}
		
		if (queryNames.contains("tokenId")) {
			taskQuery.tokenId(getQueryParameter("tokenId", queryForm));
		}
		
		if (queryNames.contains("processInstanceId")) {
			taskQuery.processInstanceId(getQueryParameter("processInstanceId", queryForm));
		}
		
		if (queryNames.contains("processDefinitionKey")) {
			taskQuery.processDefinitionKey(getQueryParameter("processDefinitionKey", queryForm));
		}
		
		if (queryNames.contains("processDefinitionNameLike")) {
			taskQuery.processDefinitionNameLike(parseLikeValue(getQueryParameter("processDefinitionNameLike", queryForm)));
		}
		
		if (queryNames.contains("nodeId")) {
			taskQuery.nodeId(getQueryParameter("nodeId", queryForm));
		}
		
		if (queryNames.contains("processDefinitionId")) {
			taskQuery.processDefinitionId(getQueryParameter("processDefinitionId", queryForm));
		}
		
		if (queryNames.contains("assigneed")) {
			String assigneedFlag = getQueryParameter("assigneed", queryForm);
			if ("0".equals(assigneedFlag)) {
				taskQuery.taskUnnassigned();
			} else if ("1".equals(assigneedFlag)) {
				//领取
				taskQuery.taskAssigned();
			} else {
				// 全部
				taskQuery.ignorTaskAssigned();
			}
			
		}
		
		if (queryNames.contains("subjectLike")) {
			taskQuery.taskSubjectLike(parseLikeValue(getQueryParameter("subjectLike", queryForm)));
		}
		
		if (queryNames.contains("initiator")) {
			taskQuery.initiator(getQueryParameter("initiator", queryForm));
		}
		
		if (queryNames.contains("isSuspended")) {
			taskQuery.isSuspended(StringUtil.getBoolean(getQueryParameter("isSuspended", queryForm)));
		}
		
		if (queryNames.contains("taskId")) {
			taskQuery.taskId(getQueryParameter("taskId", queryForm));
		}
		
		if (queryNames.contains("descriptionLike")) {
			taskQuery.taskDescriptionLike(parseLikeValue(getQueryParameter("descriptionLike", queryForm)));
		}
		
		if (queryNames.contains("createTimeB")) {
			String dateB = getQueryParameter("createTimeB", queryForm);
			Date createTimeB;
			try {
				createTimeB = sdf.parse(dateB + " 0000000");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式：" + dateB);
			}
			taskQuery.taskCreatedAfter(createTimeB);
		}
		
		if (queryNames.contains("createTimeE")) {
			String dateE = getQueryParameter("createTimeE", queryForm);
			Date createTimeE;
			try {
				createTimeE = sdf.parse(dateE + " 2359999");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式：" + dateE);
			}
			taskQuery.taskCreatedBefore(createTimeE);
		}
		
		if (queryNames.contains("dueDateB")) {
			String dateB = getQueryParameter("dueDateB", queryForm);
			Date dueDateB;
			try {
				dueDateB = sdf.parse(dateB + " 0000000");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("期望时间格式转换错误，需要yyyy-MM-dd格式,实际格式：" + dateB);
			}
			taskQuery.taskDueDateAfter(dueDateB);
		}
		
		if (queryNames.contains("dueDateE")) {
			String dateE = getQueryParameter("dueDateE", queryForm);
			Date dueDateE;
			try {
				dueDateE = sdf.parse(dateE + " 2359999");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式：" + dateE);
			}
			taskQuery.taskDueDateBefore(dueDateE);
		}
		
		if (queryNames.contains("endTimeB")) {
			String dateB = getQueryParameter("endTimeB", queryForm);
			Date endTimeB;
			try {
				endTimeB = sdf.parse(dateB + " 0000000");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("期望时间格式转换错误，需要yyyy-MM-dd格式,实际格式：" + dateB);
			}
			taskQuery.taskEndTimeAfter(endTimeB);
		}
		
		if (queryNames.contains("endTimeE")) {
			String dateE = getQueryParameter("endTimeE", queryForm);
			Date endTimeE;
			try {
				endTimeE = sdf.parse(dateE + " 2359999");
			} catch (ParseException e) {
				throw new FoxBPMIllegalArgumentException("创建时间格式转换错误，需要yyyy-MM-dd格式,实际格式：" + dateE);
			}
			taskQuery.taskEndTimeBefore(endTimeE);
		}
		
		String ended = null;
		if (queryNames.contains(RestConstants.IS_END)) {
			ended = StringUtil.getString(getQueryParameter(RestConstants.IS_END, queryForm));
		}
		if ("true".equals(ended)) {
			taskQuery.taskIsEnd();
		} else if ("false".equals(ended)) {
			taskQuery.taskNotEnd();
		}
		
		DataResult result = paginateList(taskQuery, properties, "createTime");
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) result.getData();
		if (mapList != null && mapList.size() > 0) {
			for (Map<String, Object> tmp : mapList) {
				String initator = StringUtil.getString(tmp.get("processInitiator"));
				String initatorName = getUserName(initator);
				tmp.put("initatorName", initatorName);
				String assignee = StringUtil.getString(tmp.get("assignee"));
				tmp.put("assigneeName", getUserName(assignee));
			}
		}
		return result;
	}
	
	protected boolean validateUser() {
		HttpServletRequest request = ServletUtils.getRequest(getRequest());
		String userId = (String) request.getSession().getAttribute("userId");
		Form queryForm = getQuery();
		String assignee = getQueryParameter("assignee", queryForm);
		String processInstanceId = getQueryParameter("processInstanceId", queryForm);
		String candidateUser = getQueryParameter("candidateUser", queryForm);
		if (userId == null || (assignee == null && candidateUser == null && processInstanceId == null)
		        || (assignee != null && !userId.equals(assignee))
		        || (candidateUser != null && !userId.equals(candidateUser))) {
			setStatus(Status.CLIENT_ERROR_UNAUTHORIZED, "对应的请求无权访问！");
			return false;
		}
		return true;
	}
}
