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
 * @author yangguangftlp
 */
package org.foxbpm.rest.service.api.task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月12日
 */
public class TaskInforResource extends AbstractRestResource {
	
	@Get
	public DataResult getTaskInfor() {
		Form query = getQuery();
		String type = getQueryParameter("type", query);
		String processInstanceId = getQueryParameter("processInstanceId", query);
		if (StringUtil.isEmpty(type)) {
			type = "all";
		}
		
		if (StringUtil.isEmpty(processInstanceId)) {
			throw new FoxBPMBizException("processInstanceId is null!");
		}
		
		ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ModelService modelService = processEngine.getModelService();
		TaskService taskService = processEngine.getTaskService();
		
		// 判断是否存在流程实例Id
		ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
		ProcessInstance processInstance = processInstanceQuery.processInstanceId(processInstanceId).singleResult();
		if (null == processInstance) {
			throw new FoxBPMBizException("processInstanceId=" + processInstanceId + " is Invalid parameter value!");
		}
		
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		// 获取流程定义名称
		String processName = modelService.getProcessDefinition(processInstance.getProcessDefinitionId()).getName();
		// 创建任务查询器
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processInstanceId(processInstanceId);
		taskQuery.taskIsEnd().orderByEndTime().asc();
		// 获取任务结束信息
		List<Task> endTaskInstances = taskQuery.list();
		List<Map<String, Object>> endTasks = new ArrayList<Map<String, Object>>();
		Map<String, Object> taskInstanceMap = null;
		
		Date date = null;
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
		for (Task task : endTaskInstances) {
			taskInstanceMap = task.getPersistentState();
			taskInstanceMap.put("userName", getUserName(task.getAssignee()));
			date = (Date) taskInstanceMap.get("createTime");
			if (null != date) {
				taskInstanceMap.put("createTime", formatter.format(date));
			}
			date = (Date) taskInstanceMap.get("endTime");
			if (null != date) {
				taskInstanceMap.put("endTime", formatter.format(date));
			}
			endTasks.add(taskInstanceMap);
		}
		
		// 获取任务未结束信息
		taskQuery.taskNotEnd().orderByTaskCreateTime().asc();
		List<Task> openTaskInstances = taskQuery.list();
		List<Map<String, Object>> openTasks = new ArrayList<Map<String, Object>>();
		for (Task task : openTaskInstances) {
			taskInstanceMap = task.getPersistentState();
			taskInstanceMap.put("userName", getUserName(task.getAssignee()));
			date = (Date) taskInstanceMap.get("createTime");
			if (null != date) {
				taskInstanceMap.put("createTime", formatter.format(date));
			}
			date = (Date) taskInstanceMap.get("endTime");
			if (null != date) {
				taskInstanceMap.put("endTime", formatter.format(date));
			}
			openTasks.add(taskInstanceMap);
		}
		Map<String, Map<String, Object>> positionInfor = modelService.getFlowGraphicsElementPositionById(processInstance.getProcessDefinitionId());
		
		
		resultData.put("endTasks", endTasks);
		resultData.put("openTasks", openTasks);
		resultData.put("positionInfor", positionInfor);
		resultData.put("processName", processName);
		resultData.put("processInstanceId", processInstance.getId());
		resultData.put("processDefinitionId", processInstance.getProcessDefinitionId());
		DataResult result = new DataResult();
		result.setData(resultData);
		return result;
	}
	/**
	 * 根据用户Id获取用户名称
	 * 
	 * @param userId
	 *            用户Id
	 * @return 返回用户名
	 */
	protected String getUserName(String userId) {
		if (StringUtil.isNotEmpty(userId)) {
			UserEntity tmpUser = FoxBpmUtil.getProcessEngine().getIdentityService().getUser(userId);
			if (tmpUser != null) {
				return tmpUser.getUserName();
			}
		}
		return "未知用户:" + userId;
	}
}
