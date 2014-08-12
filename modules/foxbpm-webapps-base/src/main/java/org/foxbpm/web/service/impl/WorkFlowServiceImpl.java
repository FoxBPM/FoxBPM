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
package org.foxbpm.web.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.db.Page;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.runningtrack.RunningTrack;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.web.common.constant.FoxbpmExceptionCode;
import org.foxbpm.web.common.constant.WebContextAttributeName;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.DateUtil;
import org.foxbpm.web.common.util.FlowUtil;
import org.foxbpm.web.common.util.JSONUtil;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IWorkFlowService;
import org.springframework.stereotype.Service;

/**
 * 工作流服务实现类
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
@Service("workFlowServiceImpl")
public class WorkFlowServiceImpl extends AbstWorkFlowService implements IWorkFlowService {
	
	@Override
	public List<Map<String, Object>> queryStartProcess(Map<String, Object> params)
	    throws FoxbpmWebException {
		// 创建流程定义查询
		String userId = StringUtil.getString(params.get(WebContextAttributeName.USERID));
		// 参数校验
		if (StringUtil.isEmpty(userId)) {
			throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_USERID, "userId is null !");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (ProcessDefinition pd : modelService.getStartProcessByUserId(userId)) {
			result.add(pd.getPersistentState());
		}
		return result;
	}
	
	public List<RunningTrack> queryRunningTrack(String processInstanceID) {
		return runtimeService.createRunningTrackQuery().processInstanceID(processInstanceID).list();
	}
	
	@Override
	public List<Map<String, Object>> queryProcessInst(Pagination<String> pageInfor,
	    Map<String, Object> params) {
		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
		// 获取查询条件参数
		String userId = StringUtil.getString(params.get(WebContextAttributeName.USERID));
		String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
		String processInstanceId = StringUtil.getString(params.get("processInstanceId"));
		String processDefinitionName = StringUtil.getString(params.get("processDefinitionName"));
		String title = StringUtil.getString(params.get("title"));
		String bizKey = StringUtil.getString(params.get("bizKey"));
		String initor = StringUtil.getString(params.get("initor"));
		String status = StringUtil.getString(params.get("status"));
		String processType = StringUtil.getString(params.get("processType"));
		
		String dss = StringUtil.getString(params.get("startTimeS"));
		String dse = StringUtil.getString(params.get("startTimeE"));
		if (StringUtil.isNotEmpty(processDefinitionKey)) {
			piq.processDefinitionKey(processDefinitionKey);
		}
		if (StringUtil.isNotEmpty(processInstanceId)) {
			piq.processInstanceId(processInstanceId);
		}
		if (StringUtil.isNotEmpty(title)) {
			piq.subjectLike(assembleLikeParam(title));
		}
		if (StringUtil.isNotEmpty(bizKey)) {
			piq.processInstanceBusinessKeyLike(assembleLikeParam(bizKey));
		}
		if (StringUtil.isNotEmpty(status)) {
			piq.processInstanceStatus(status);
		}
		
		if (StringUtil.isNotEmpty(initor)) {
			piq.initiator(initor);
		}
		
		if (StringUtil.isNotEmpty(processType)) {
			if (processType.equals("initor")) {
				piq.initiator(userId);
			} else {
				piq.taskParticipants(userId);
			}
			
		}
		if (StringUtil.isNotEmpty(processDefinitionName)) {
			piq.processDefinitionNameLike(assembleLikeParam(processDefinitionName));
		}
		Date dates = null;
		Date datee = null;
		
		if (StringUtil.isNotEmpty(dss)) {
			dates = DateUtil.stringToDate(dss, "yyyy-MM-dd");
		}
		if (StringUtil.isNotEmpty(dse)) {
			String endTime = "235959999";
			dse += endTime;
			datee = DateUtil.stringToDate(dse, "yyyy-MM-ddHHmmssSSS");
		}
		if (null != dates) {
			piq.startTimeBefore(dates);
		}
		if (null != datee) {
			piq.startTimeAfter(datee);
		}
		
		List<ProcessInstance> piList = null;
		piq.orderByUpdateTime().desc();
		if (null == pageInfor) {
			piList = piq.list();
		} else {
			// 执行分页查询
			piList = piq.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
			// 设置分页信息
			pageInfor.setTotal(StringUtil.getInt(piq.count()));
		}
		// 流程实例属性集
		Map<String, Object> attrMap = null;
		ProcessInstance pi = null;
		for (int i = 0, size = (null == piList) ? 0 : piList.size(); i < size; i++) {
			pi = piList.get(i);
			attrMap = pi.getPersistentState();
			attrMap.put("processDefinitionName", modelService.getProcessDefinition(pi.getProcessDefinitionId()).getName());
			attrMap.put("initiatorName", getUserName(StringUtil.getString(attrMap.get("initiator"))));
			String nowNodeInfo = FlowUtil.getShareTaskNowNodeInfo(pi.getId());
			attrMap.put("nowNodeInfo",nowNodeInfo);
			resultData.add(attrMap);
		}
		
		return resultData;
	}
	
	@Override
	public Map<String, Object> queryTaskDetailInfor(Map<String, Object> params) {
		// 返回结果
		Map<String, Object> resultData = new HashMap<String, Object>();
		String processInstanceId = StringUtil.getString(params.get("processInstanceId"));
		if (StringUtil.isNotEmpty(processInstanceId)) {
			ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
			List<ProcessInstance> pinstanceList = piq.processInstanceId(processInstanceId).list();
			if (null == pinstanceList || pinstanceList.isEmpty()) {
				throw new IllegalArgumentException("processInstanceId=" + processInstanceId
				        + " is Invalid parameter value!");
			}
			ProcessInstance processInstance = pinstanceList.get(0);
			String processName = modelService.getProcessDefinition(processInstance.getProcessDefinitionId()).getName();
			
			TaskQuery tq = taskService.createTaskQuery();
			tq.processInstanceId(processInstanceId);
			tq.taskIsEnd().orderByEndTime().asc();
			List<Task> instances = tq.list();
			List<Map<String, Object>> instanceMaps = new ArrayList<Map<String, Object>>();
			// 获取任务详细信息
			for (Task tmp : instances) {
				Map<String, Object> instanceMap = tmp.getPersistentState();
				String assigneeUserId = tmp.getAssignee();
				instanceMap.put("assgneeUserName", getUserName(assigneeUserId));
				instanceMaps.add(instanceMap);
			}
			
			// 获取任务结束信息
			tq.taskNotEnd().orderByTaskCreateTime().asc();
			List<Task> instancesNotEnd = tq.list();
			List<Map<String, Object>> notEndInstanceMaps = new ArrayList<Map<String, Object>>();
			for (Task tmp : instancesNotEnd) {
				Map<String, Object> instanceMap = tmp.getPersistentState();
				String assigneeUserId = tmp.getAssignee();
				instanceMap.put("assgneeUserName", getUserName(assigneeUserId));
				notEndInstanceMaps.add(instanceMap);
			}
			Map<String, Map<String, Object>> postionMap = modelService.getFlowGraphicsElementPositionById(processInstance.getProcessDefinitionId());
			
			// 获取运行轨迹信息
			List<RunningTrack> runningTrackInfo = this.queryRunningTrack(processInstanceId);
			if (runningTrackInfo != null) {
				resultData.put("runningTrackInfo", JSONUtil.parseObject2JSON(runningTrackInfo));
				resultData.put("runningTrackInfoList", runningTrackInfo);
			}
			
			resultData.put("dataList", instanceMaps);
			resultData.put("notEnddataList", notEndInstanceMaps);
			resultData.put("positionInfo", JSONUtil.parseObject2JSON(postionMap));
			resultData.put("taskEndedJson", JSONUtil.parseObject2JSON(instanceMaps));
			resultData.put("taskNotEndJson", JSONUtil.parseObject2JSON(instancesNotEnd));
			resultData.put("processName", processName);
			resultData.put("processInstanceId", processInstance.getId());
			resultData.put("processDefinitionId", processInstance.getProcessDefinitionId());
		}
		return resultData;
	}
	
	@Override
	public List<Map<String, Object>> queryToDoTask(Pagination<String> pageInfor,
	    Map<String, Object> params) {
		
		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		TaskQuery taskQuery = taskService.createTaskQuery();
		// 获取基本查询条件参数
		String userId = (String) params.get(WebContextAttributeName.USERID);
		String initiator = (String) params.get(WebContextAttributeName.ATTRIBUTE_NAME_INITIATOR);
		String processDefinitionName = (String) params.get(WebContextAttributeName.ATTRIBUTE_NAME_PROCESSDEFINITIONNAME);
		String businessKey = (String) params.get(WebContextAttributeName.ATTRIBUTE_NAME_BUSINESSKEY);
		String title = (String) params.get(WebContextAttributeName.ATTRIBUTE_NAME_TITLE);
		String dss = (String) params.get(WebContextAttributeName.ATTRIBUTE_NAME_ARRIVALTIMES);
		String dse = (String) params.get(WebContextAttributeName.ATTRIBUTE_NAME_ARRIVALTIMEE);
		
		// 处理查询参数
		if (StringUtil.isNotEmpty(userId)) {
			taskQuery.taskAssignee(userId);
			taskQuery.taskCandidateUser(userId);
		}
		if (StringUtil.isNotEmpty(initiator)) {
			taskQuery.initiator(initiator);
		}
		if (StringUtil.isNotEmpty(processDefinitionName)) {
			taskQuery.processDefinitionNameLike(assembleLikeParam(processDefinitionName));
		}
		if (StringUtil.isNotEmpty(businessKey)) {
			taskQuery.businessKeyLike(assembleLikeParam(businessKey));
		}
		if (StringUtil.isNotEmpty(title)) {
			taskQuery.taskSubjectLike(assembleLikeParam(title));
		}
		// 时间处理
		Date dates = null;
		Date datee = null;
		
		if (StringUtil.isNotEmpty(dss)) {
			dates = DateUtil.stringToDate(dss, "yyyy-MM-dd");
		}
		if (StringUtil.isNotEmpty(dse)) {
			String endTime = "235959999";
			dse += endTime;
			datee = DateUtil.stringToDate(dse, "yyyy-MM-ddHHmmssSSS");
		}
		if (dates != null) {
			taskQuery.taskCreatedBefore(dates);
		}
		if (datee != null) {
			taskQuery.taskCreatedAfter(datee);
		}
		taskQuery.orderByTaskCreateTime().desc();
		taskQuery.taskNotEnd();
		// 查询代办任务
		List<Task> taskList = null;
		if (null == pageInfor) {
			taskList = taskQuery.list();
		} else {
			taskList = taskQuery.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
			pageInfor.setTotal(StringUtil.getInt(taskQuery.count()));
		}
		Map<String, Object> attrMap = null;
		Task task = null;
		for (int i = 0, size = (null == taskList) ? 0 : taskList.size(); i < size; i++) {
			task = taskList.get(i);
			attrMap = task.getPersistentState();
			if (StringUtil.isEmpty(StringUtil.getString(attrMap.get("formUri")))) {
				attrMap.put("formUri", "startTask.action");
			}
			resultData.add(attrMap);
		}
		return resultData;
	}
	
	@Override
	public Map<String, Object> startTask(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> tmpres = new ArrayList<Map<String, Object>>();
		String taskId = StringUtil.getString(params.get("taskId"));
		String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
		List<TaskCommand> list = null;
		
		if (StringUtil.isEmpty(processDefinitionKey)) {
			throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_PROCESSDEFKEY, "processDefinitionKey is null !");
		}
		if (StringUtil.isNotEmpty(taskId)) {
			list = taskService.getTaskCommandByTaskId(taskId);
		} else {
			list = taskService.getSubTaskCommandByKey(processDefinitionKey);
		}
		for (TaskCommand tmp : list) {
			tmpres.add(tmp.getPersistentState());
		}
		resultMap.put("commandList", tmpres);
		
		return resultMap;
	}
	
	@Override
	public ProcessInstance completeTask(String flowInfo) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode params = null;
		try {
			params = objectMapper.readTree(flowInfo);
		} catch (Exception e) {
			throw new FoxbpmWebException("任务命令参数格式不正确","",e);
		}
		JsonNode taskIdNode = params.get("taskId");
		JsonNode commandIdNode = params.get("commandId");
		JsonNode processDefinitionKeyNode = params.get("processDefinitionKey");
		JsonNode businessKeyNode = params.get("bizKey");
		JsonNode taskCommentNode = params.get("taskComment");
		// 参数校验
		
		// 命令类型
		JsonNode commandTypeNode = params.get("commandType");
		if (commandTypeNode == null) {
			throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_COMMANDTYPE, "commandType is null !");
		}
		// 命令Id
		if (commandIdNode == null) {
			throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_COMMANDID, "commandId is null !");
		}
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType(commandTypeNode.getTextValue());
		// 设置命令的id,需和节点上配置的按钮编号对应，会执行按钮中的脚本。
		expandTaskCommand.setTaskCommandId(commandIdNode.getTextValue());
		if(taskCommentNode != null){
			expandTaskCommand.setTaskComment(taskCommentNode.getTextValue());
		}
		
		ProcessInstance processInstance = null;
		if (taskIdNode != null && StringUtil.isNotEmpty(taskIdNode.getTextValue())) {
			expandTaskCommand.setTaskId(taskIdNode.getTextValue());
		} else {
			String userId = Authentication.getAuthenticatedUserId();
			expandTaskCommand.setInitiator(userId);
			if(businessKeyNode == null){
				throw new FoxbpmWebException("启动流程时关联键不能为null","");
			}
			if(processDefinitionKeyNode == null){
				throw new FoxbpmWebException("启动流程时流程Key不能为null","");
			}
			expandTaskCommand.setBusinessKey(businessKeyNode.getTextValue());
			expandTaskCommand.setProcessDefinitionKey(processDefinitionKeyNode.getTextValue());
		}
		processInstance = taskService.expandTaskComplete(expandTaskCommand, null);
		return processInstance;
	}
	
	@Override
	public String getFlowSvgGraph(Map<String, Object> params) {
		String processDefinitionId = StringUtil.getString(params.get("processDefinitionId"));
		// 流程定义Id
		if (StringUtil.isEmpty(processDefinitionId)) {
			String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
			// 流程定义Key
			if (StringUtil.isEmpty(processDefinitionKey)) {
				throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_PROCESSDEFKEY, "processDefinitionKey is null !");
			}
			ProcessDefinition processDefinition = modelService.getLatestProcessDefinition(processDefinitionKey);
			if (null == processDefinition) {
				throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_ERROR, "无效的流程定义key:"
				        + processDefinitionKey);
			}
			processDefinitionId = processDefinition.getId();
		}
		return modelService.getProcessDefinitionSVG(processDefinitionId);
	}
	
	@Override
	public InputStream getFlowImagGraph(Map<String, Object> params) {
		String processDefinitionId = StringUtil.getString(params.get("processDefinitionId"));
		// 流程定义Id
		if (StringUtil.isEmpty(processDefinitionId)) {
			String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
			// 流程定义Key
			if (StringUtil.isEmpty(processDefinitionKey)) {
				throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_PROCESSDEFKEY, "processDefinitionKey is null !");
			}
			ProcessDefinition processDefinition = modelService.getLatestProcessDefinition(processDefinitionKey);
			if (null == processDefinition) {
				throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_ERROR, "无效的流程定义key:"
				        + processDefinitionKey);
			}
			processDefinitionId = processDefinition.getId();
		}
		return modelService.GetFlowGraphicsImgStreamByDefId(processDefinitionId);
	}
	
	@Override
	public Map<String, Object> queryUserDelegationInfo(Map<String, Object> params) {
		String agentUser = Authentication.getAuthenticatedUserId();
		Map<String, Object> resultData = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(agentUser)) {
			AgentEntity agentEntity = identityService.queryAgent(agentUser);
			if (null != agentEntity) {
				resultData.putAll(agentEntity.getPersistentState());
				resultData.put("detailInfoList", agentEntity.getAgentDetails());
			} else {
				resultData.put("agentFrom", agentUser);
			}
		}
		return resultData;
	}
	
	@Override
	public List<UserEntity> queryUsers(Pagination<String> pageInfor, Map<String, Object> params) {
		
		String id = StringUtil.getString(params.get("id"));
		String name = StringUtil.getString(params.get("name"));
		String idLike = null;
		String nameLike = null;
		if (StringUtil.isNotEmpty(id)) {
			idLike = assembleLikeParam(id);
		}
		if (StringUtil.isNotEmpty(name)) {
			nameLike = assembleLikeParam(name);
		}
		int pageNum = pageInfor.getPageIndex();
		int rowNum = pageInfor.getPageSize();
		
		Page page = new Page(pageNum * rowNum - rowNum, rowNum);
		return identityService.getUsers(idLike, nameLike, page);
	}
	
	@Override
	public Long queryUsersCount(Map<String, Object> params) {
		String idLike = StringUtil.getString(params.get("idLike"));
		String nameLike = StringUtil.getString(params.get("nameLike"));
		return (Long) identityService.getUsersCount(idLike, nameLike);
	}
}
