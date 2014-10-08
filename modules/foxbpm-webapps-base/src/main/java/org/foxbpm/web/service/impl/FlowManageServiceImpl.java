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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipInputStream;

import org.apache.commons.fileupload.FileItem;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.web.common.constant.FoxbpmExceptionCode;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.DateUtil;
import org.foxbpm.web.common.util.JSONUtil;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IFlowManageService;
import org.springframework.stereotype.Service;

/**
 * @author yangguangftlp
 * @date 2014年6月12日
 */
@Service("flowManageServiceImpl")
@SuppressWarnings("unchecked")
public class FlowManageServiceImpl extends AbstWorkFlowService implements IFlowManageService {

	 
	public void deployByZip(Map<String, Object> params) {
		ZipInputStream zipInputStream = null;
		try {
			FileItem file = (FileItem) params.get("processFile");
			if (null != file) {
				zipInputStream = new ZipInputStream(file.getInputStream());
				String deploymentId = StringUtil.getString(params.get("deploymentId"));
				DeploymentBuilder deploymentBuilder = modelService.createDeployment();
				deploymentBuilder.addZipInputStream(zipInputStream);
				// 有deploymentID则为更新，否则为新增
				if (StringUtil.isNotEmpty(deploymentId)) {
					deploymentBuilder.updateDeploymentId(deploymentId);
				}
				deploymentBuilder.deploy();
			}
		} catch (IOException e) {
			throw new FoxbpmWebException(e);
		} finally {
			if (null != zipInputStream) {
				try {
					zipInputStream.close();
				} catch (IOException e) {
					throw new FoxbpmWebException(e);
				}
			}
		}
	}

	 
	public void deleteDeploy(Map<String, Object> params) {
		String deploymentId = StringUtil.getString(params.get("deploymentId"));
		try {
			if (StringUtil.isEmpty(deploymentId)) {
				throw new FoxbpmWebException(FoxbpmExceptionCode.FOXBPMEX_DEPLOYMENTID, "deploymentId is null !");
			}
			String[] deploymentIds = deploymentId.split(",");
			for (int i = 0; i < deploymentIds.length; i++) {
				modelService.deleteDeployment(deploymentIds[i]);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e);
		}
	}

	 
	public List<Map<String, Object>> queryProcessDef(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		// 创建流程定义查询
		ProcessDefinitionQuery pdq = modelService.createProcessDefinitionQuery();
		String processName = StringUtil.getString(params.get("queryProcessName"));
		if (StringUtil.isNotEmpty(processName)) {
			pdq.processDefinitionNameLike(assembleLikeParam(processName));
		}
		String processId = StringUtil.getString(params.get("queryProcessId"));
		if (StringUtil.isNotEmpty(processId)) {
			pdq.processDefinitionId(processId);
		}
		String processCategory = StringUtil.getString(params.get("queryType"));
		if (StringUtil.isNotEmpty(processCategory)) {
			pdq.processDefinitionCategoryLike(assembleLikeParam(processCategory));
		}
		String queryProcessKey = StringUtil.getString(params.get("queryProcessKey"));
		if (StringUtil.isNotEmpty(queryProcessKey)) {
			pdq.processDefinitionKeyLike(assembleLikeParam(queryProcessKey));
		}

		pdq.orderByDeploymentId().desc();
		List<ProcessDefinition> pdList = null;
		if (null == pageInfor) {
			pdList = pdq.list();
		} else {
			pdList = pdq.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
			pageInfor.setTotal(StringUtil.getInt(pdq.count()));
		}
		Map<String, Object> attrMap = null;
		for (int i = 0, size = (null == pdList) ? 0 : pdList.size(); i < size; i++) {
			attrMap = pdList.get(i).getPersistentState();
			resultData.add(attrMap);
		}
		return resultData;
	}

	/**
	 * 查询所有流程实例信息
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件参数
	 * @return 返回查询结果
	 * @throws FoxbpmWebException
	 */
	 
	public List<Map<String, Object>> queryProcessInst(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		// 返回结果
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
		// 获取查询条件参数
		String userId = StringUtil.getString(params.get("userId"));
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
			resultData.add(attrMap);
		}

		return resultData;
	}

	 
	public void saveUserDelegationInfo(Map<String, Object> params) {
		String addInfo = StringUtil.getString(params.get("add"));
		String updateInfo = StringUtil.getString(params.get("update"));
		String deleteInfo = StringUtil.getString(params.get("delete"));

		String agentUser = StringUtil.getString(params.get("agentUser"));
		String agentId = StringUtil.getString(params.get("agentId"));
		String startTime = StringUtil.getString(params.get("startTime"));
		String endTime = StringUtil.getString(params.get("endTime"));
		String status = StringUtil.getString(params.get("status"));

		if (StringUtil.isNotEmpty(agentUser)) {

			AgentEntity agentEntity = new AgentEntity();
			agentEntity.setAgentFrom(agentUser);
			agentEntity.setStatus(status);
			agentEntity.setId(agentId);
			if (StringUtil.isNotEmpty(startTime)) {
				agentEntity.setStartTime(DateUtil.stringToyyyyMmDdDate(startTime));
			}
			if (StringUtil.isNotEmpty(endTime)) {
				agentEntity.setEndTime(DateUtil.stringToyyyyMmDdDate(endTime));
			}

			// 代理id为空说明是新增
			if (StringUtil.isEmpty(agentId)) {
				agentEntity.setId(GuidUtil.CreateGuid());
				identityService.addAgent(agentEntity);
			} else {
				identityService.updateAgentEntity(agentEntity);
			}

			// 新增
			if (StringUtil.isNotEmpty(addInfo)) {
				Map<String, Object> agentInfo = JSONUtil.parseJSON2Map(addInfo);
				Iterator<String> iterator = agentInfo.keySet().iterator();
				String key = null;
				AgentDetailsEntity agentDetailsEntity = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					agentDetailsEntity = new AgentDetailsEntity();
					agentDetailsEntity.setId(GuidUtil.CreateGuid());
					agentDetailsEntity.setAgentId(agentEntity.getId());
					agentDetailsEntity.setAgentTo(StringUtil.getString(agentInfo.get(key)));
					agentDetailsEntity.setProcessKey(key);
					identityService.addAgentDetails(agentDetailsEntity);
				}
			}
			// 更新
			if (StringUtil.isNotEmpty(updateInfo)) {
				Map<String, Object> agentInfo = JSONUtil.parseJSON2Map(updateInfo);
				Iterator<String> iterator = agentInfo.keySet().iterator();
				String key = null;
				Map<String, String> value = null;
				AgentDetailsEntity agentDetailsEntity = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					value = (Map<String, String>) agentInfo.get(key);
					agentDetailsEntity = new AgentDetailsEntity();
					agentDetailsEntity.setId(key);
					agentDetailsEntity.setAgentId(agentEntity.getId());
					agentDetailsEntity.setAgentTo(value.get("user"));
					agentDetailsEntity.setProcessKey(value.get("key"));
					identityService.updateAgentDetailsEntity(agentDetailsEntity);
				}
			}
			// 删除
			if (StringUtil.isNotEmpty(deleteInfo)) {
				StringTokenizer st = new StringTokenizer(deleteInfo, ",");
				while (st.hasMoreTokens()) {
					identityService.deleteAgentDetails(st.nextToken());
				}
			}
		}

	}

	 
	public void deleUserDelegationInfo(Map<String, Object> params) {
		String agentInfoJson = StringUtil.getString(params.get("deleteIndex"));
		String agentId = StringUtil.getString(params.get("agentId"));
		if (StringUtil.isNotEmpty(agentId)) {
			if (StringUtil.isNotEmpty(agentInfoJson)) {
				StringTokenizer st = new StringTokenizer(agentInfoJson, ",");
				while (st.hasMoreTokens()) {
					identityService.deleteAgentDetails(st.nextToken());
				}
			}
		}
	}

}
