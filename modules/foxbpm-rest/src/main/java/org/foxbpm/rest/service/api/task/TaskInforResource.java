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
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
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
 * 获取任务详细信息
 * @author yangguangftlp
 * @date 2014年8月12日
 */
public class TaskInforResource extends AbstractRestResource {
	
	@Get
	public DataResult getTaskInfor() {
		Form query = getQuery();
		String processInstanceId = getQueryParameter("processInstanceId", query);
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
			String userId = task.getAssignee();
			if (StringUtil.isEmpty(userId)) {
				taskInstanceMap.put("userName", "未领取");
			} else {
				taskInstanceMap.put("userName", getUserName(userId));
			}
			
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
		resultData.put("nowStep", getNowStepInfo(processInstance.getId()));
		DataResult result = new DataResult();
		result.setData(resultData);
		return result;
	}
	
	/**
	 * @param processInstanceId
	 * @return
	 */
	public static Map<String,Object> getNowStepInfo(String processInstanceId) {
		
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			ProcessEngine engine = FoxBpmUtil.getProcessEngine();
			ProcessInstance processInstance = engine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			result.put("status", processInstance.getInstanceStatus());
			List<Task> tasks = new ArrayList<Task>();
			tasks =engine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).taskNotEnd().list();
			List<Map<String ,Object>> taskInfo = new ArrayList<Map<String,Object>>();
			Map<String,Object> tmpMap = null;
			TaskEntity taskEntity = null;
			for (Task task : tasks) {
				tmpMap = new HashMap<String, Object>();
				taskEntity = (TaskEntity)task;
				tmpMap.put("nodeId", taskEntity.getNodeId());
				tmpMap.put("nodeName", taskEntity.getNodeName());
				tmpMap.put("user",taskUserInfo(taskEntity));
				taskInfo.add(tmpMap);
			}
			result.put("taskInfo", taskInfo);
			return result;
		} catch (Exception e) {
			throw new FoxBPMException("任务状态获取失败！实例号："+processInstanceId,e);
		}
	}
	
	private static List<Map<String,Object>> taskUserInfo(Task task) throws Exception{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String assignee = task.getAssignee();
		Map<String,Object> map = null;
		//已经被领取
		if(StringUtil.isNotEmpty(assignee)){
			UserEntity user = Authentication.selectUserByUserId(assignee);
			map = new HashMap<String, Object>();
			map.put("type", "user");
			map.put("id", user.getUserId());
			map.put("name", user.getUserName());
			result.add(map);
			return result;
		}
		//未领取
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		List<IdentityLinkEntity> identityLinkList = taskService.getIdentityLinkByTaskId(task.getId());
		for (IdentityLinkEntity identityLink : identityLinkList) {
			String userId = identityLink.getUserId();
			if (userId == null) {
				String groupTypeId = identityLink.getGroupType();
				String groupId = identityLink.getGroupId();
				GroupEntity group = Authentication.findGroupById(groupId, groupTypeId);
				if (group == null) {
					continue;
				}
				map = new HashMap<String, Object>();
				map.put("type", groupTypeId);
				map.put("id", groupId);
				map.put("name", group.getGroupName());
				result.add(map);
			} else {
				UserEntity user=null;
				if (userId.equals("foxbpm_all_user")) {
					user=new UserEntity("foxbpm_all_user", "所有人");
				} else {
					user= Authentication.selectUserByUserId(userId);
				}
				if(user!=null){
					map = new HashMap<String, Object>();
					map.put("type", "user");
					map.put("id", user.getUserId());
					map.put("name", user.getUserName());
					result.add(map);
				}
			}
		}
		return result;
	}
}
