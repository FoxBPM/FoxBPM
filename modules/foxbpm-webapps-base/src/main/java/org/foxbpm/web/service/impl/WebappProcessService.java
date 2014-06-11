package org.foxbpm.web.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.DateUtil;
import org.foxbpm.web.common.util.JSONUtil;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.service.interfaces.IWebappProcessService;
import org.springframework.stereotype.Service;

/**
 * 流程服务类
 * 
 * @author MEL
 * @date 2014-06-04
 */
@Service("webappProcessService")
public class WebappProcessService extends AbstractWebappService implements IWebappProcessService {

	/**
	 * spring事物控制方法、所以在方法的开头开启数据库连接、 采用Spring DataSourceUtils类获取连接
	 * 这样数据库的连接就交给事物进行处理，事物处理完毕后会关闭数据库连接
	 * 
	 */
	public ProcessDefinition createProcessDefinition(String parameter) throws FoxbpmWebException {
		Connection connection = null;
		try {
			// connection = dbfactory.createConnection();
			// 开启数据库连接
			// 开启事物
			// 设置工作流引擎的数据库连接
			// 工作流引擎的API调用
			// 本地业务数据DAO调用
			// 提交或回滚事物
			// 事物处理完毕后会自动关闭数据库连接
			// BizInfo bizInfo = new BizInfo();
			// bizInfo.setId("id001");
			// bizInfo.setName("name001");
			// bizInfo.setComment("comment001");
			// bizDB.insertBizInfo(bizInfo, connection);
			return null;
		} catch (FoxbpmWebException e) {
			throw e;
		}

	}

	@Override
	public List<ProcessDefinition> queryProcessDefinition() {
		Connection connection = null;
		try {
			connection = dbfactory.createConnection();
			// 开启数据库连接
			// 开启事物
			// 设置工作流引擎的数据库连接
			// 工作流引擎的API调用
			// 本地业务数据DAO调用
			// 提交或回滚事物
			// 事物处理完毕后会自动关闭数据库连接
			// BizInfo bizInfo = new BizInfo();
			// bizInfo.setId("id001");
			// bizInfo.setName("name001");
			// bizInfo.setComment("comment001");
			// bizDB.insertBizInfo(bizInfo, connection);
			List<ProcessDefinition> processDefinitionList = modelService.createProcessDefinitionQuery().list();
			System.out.println(processDefinitionList.size());
			return processDefinitionList;
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		// return modelService.createProcessDefinitionQuery().list();
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryProcessDef(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
		try {
			// 创建流程定义查询
			ProcessDefinitionQuery pdq = modelService.createProcessDefinitionQuery();

			List<ProcessDefinition> pdList = null;
			if (null == pageInfor) {
				pdList = pdq.list();
			} else {
				pdList = pdq.listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
				pageInfor.setTotal(StringUtil.getInt(pdq.count()));
			}
			Map<String, Object> instances = null;
			for (int i = 0, size = (null == pdList) ? 0 : pdList.size(); i < size; i++) {
				instances = new HashMap<String, Object>();
				instances.putAll(pdList.get(i).getPersistentState());
				// String formUrl = (String) instances.get("startFormKey");
				instances.put("formUrl", "startTask.action");
				String category = StringUtil.getString(instances.get("category"));
				if (StringUtil.isEmpty(category)) {
					category = "默认分类";
				}
				List<Map<String, Object>> tlist = resultMap.get(category);
				if (tlist == null) {
					tlist = new ArrayList<Map<String, Object>>();
					resultMap.put(category, tlist);
				}
				tlist.add(instances);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> queryProcessInst(Pagination<String> pageInfor, Map<String, Object> params) throws FoxbpmWebException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
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
				piq.subjectLike(title);
			}
			if (StringUtil.isNotEmpty(bizKey)) {
				piq.processInstanceBusinessKeyLike(bizKey);
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
				piq.processDefinitionNameLike(processDefinitionName);
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

			List<Map<String, Object>> instanceMaps = new ArrayList<Map<String, Object>>();
			Map<String, Object> instances = null;
			ProcessInstance pi = null;
			for (int i = 0, size = (null == piList) ? 0 : piList.size(); i < size; i++) {
				pi = piList.get(i);
				instances = new HashMap<String, Object>();
				instances.putAll(pi.getPersistentState());
				instanceMaps.add(instances);
				instances.put("processDefinitionName", modelService.getProcessDefinition(pi.getProcessDefinitionId()).getName());
			}
			resultMap.put("dataList", instanceMaps);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> queryTaskDetailInfor(Map<String, Object> params) {
		// 返回对象
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String processInstanceId = StringUtil.getString(params.get("processInstanceId"));
			ProcessInstanceQuery piq = runtimeService.createProcessInstanceQuery();
			if (StringUtil.isNotEmpty(processInstanceId)) {
				ProcessInstance processInstance = piq.processInstanceId(processInstanceId).list().get(0);
				String processName = modelService.getProcessDefinition(processInstance.getProcessDefinitionId()).getName();
				TaskQuery tq = taskService.createTaskQuery();
				tq.processInstanceId(processInstanceId);
				tq.taskIsEnd().orderByEndTime().asc();
				List<Task> instances = tq.list();
				List<Map<String, Object>> instanceMaps = new ArrayList<Map<String, Object>>();
				for (Task tmp : instances) {
					Map<String, Object> instanceMap = tmp.getPersistentState();
					instanceMaps.add(instanceMap);
				}
				tq.taskNotEnd().orderByTaskCreateTime().asc();
				List<Task> instancesNotEnd = tq.list();
				List<Map<String, Object>> notEndInstanceMaps = new ArrayList<Map<String, Object>>();
				for (Task tmp : instancesNotEnd) {
					Map<String, Object> instanceMap = tmp.getPersistentState();
					notEndInstanceMaps.add(instanceMap);
				}
				Map<String, Map<String, Object>> postionMap = modelService.GetFlowGraphicsElementPosition(processInstance.getProcessDefinitionId());
				resultMap.put("notEnddataList", notEndInstanceMaps);
				resultMap.put("dataList", instanceMaps);
				resultMap.put("positionInfo", JSONUtil.parseObject2JSON(postionMap));
				resultMap.put("taskEndedJson", JSONUtil.parseObject2JSON(instanceMaps));
				resultMap.put("taskNotEndJson", JSONUtil.parseObject2JSON(instancesNotEnd));
				resultMap.put("processName", processName);
			}
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	public void setBizDB(BizDBInterface bizDB) {
		this.bizDB = bizDB;
	}

	public void setDbfactory(FoxbpmDBConnectionFactory dbfactory) {
		this.dbfactory = dbfactory;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

}
