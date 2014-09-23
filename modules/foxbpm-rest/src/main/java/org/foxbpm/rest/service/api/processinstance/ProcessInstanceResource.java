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
package org.foxbpm.rest.service.api.processinstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.IdentityLinkEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

public class ProcessInstanceResource extends AbstractRestResource {

	@Get
	public Map<String,Object> getProcessInstance(){
		// 获取参数
		String processInstanceId = getAttribute(RestConstants.PROCESSINSTANCE_ID);
		Map<String,Object> result = null;
		ProcessEngine engine = FoxBpmUtil.getProcessEngine();
		RuntimeService runtimeService = engine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		result = processInstance.getPersistentState();
		Map<String,Object> nowStep = getNowStepInfo(processInstanceId);
		result.put("nowStep", nowStep);
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
