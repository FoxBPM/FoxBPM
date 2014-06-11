package org.foxbpm.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.DateUtil;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.service.interfaces.IWebappTaskService;
import org.springframework.stereotype.Service;

/**
 * 任务服务类
 * 
 * @author MEL
 * @date 2014-06-04
 */
@Service("webappTaskService")
public class WebappTaskService extends AbstractWebappService implements IWebappTaskService {

	@Override
	public void completeTask(String taskId) {
		// 开启数据库连接
		// 启动事务，事务由spring控制
		// 处理业务逻辑数据
		taskService.complete(taskId);
	}

	@Override
	public List<Task> queryTask() {
		return taskService.createTaskQuery().list();
	}

	public void setBizDB(BizDBInterface bizDB) {
		this.bizDB = bizDB;
	}

	public void setDbfactory(FoxbpmDBConnectionFactory dbfactory) {
		this.dbfactory = dbfactory;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public Map<String, Object> queryToDoTask(Pagination<String> pageInfor, Map<String, Object> params) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			TaskQuery taskQuery = taskService.createTaskQuery();
			// 获取基本查询条件参数
			String userId = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_USERID);
			String initiator = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_INITIATOR);
			String processDefinitionName = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PROCESSDEFINITIONNAME);
			String businessKey = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_BUSINESSKEY);
			String title = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_TITLE);
			String dss = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_ARRIVALTIMES);
			String dse = (String) params.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_ARRIVALTIMEE);

			userId = "admin";
			// 处理查询参数
			if (StringUtil.isNotEmpty(userId)) {
				taskQuery.taskAssignee(userId);
				taskQuery.taskCandidateUser(userId);
			}
			if (StringUtil.isNotEmpty(initiator)) {
				taskQuery.initiator(initiator);
			}
			if (StringUtil.isNotEmpty(processDefinitionName)) {
				taskQuery.processDefinitionNameLike(processDefinitionName);
			}
			if (StringUtil.isNotEmpty(businessKey)) {
				taskQuery.businessKeyLike(businessKey);
			}
			if (StringUtil.isNotEmpty(title)) {
				taskQuery.taskDescriptionLike(title);
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
				taskQuery.taskCreatedAfter(datee);
			}
			if (datee != null) {
				taskQuery.taskCreatedBefore(dates);
			}

			// 查询代办任务
			List<Task> taskList = null;

			if (null == pageInfor) {
				taskList = taskQuery.list();
			} else {
				taskList = taskQuery.orderByTaskCreateTime().desc().listPagination(pageInfor.getPageIndex(), pageInfor.getPageSize());
				pageInfor.setTotal(StringUtil.getInt(taskQuery.count()));
			}
			// 设置分页信息
			List<Map<String, Object>> instanceMaps = new ArrayList<Map<String, Object>>();
			Map<String, Object> instances = null;
			for (int i = 0, size = (null == taskList) ? 0 : taskList.size(); i < size; i++) {
				instances = new HashMap<String, Object>();
				instances.putAll(taskList.get(i).getPersistentState());
				instanceMaps.add(instances);
			}
			resultMap.put("dataList", instanceMaps);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> startTask(Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> tmpres = new ArrayList<Map<String, Object>>();
		try {
			String taskId = StringUtil.getString(params.get("taskId"));
			String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
			List<TaskCommand> list = null;
			if (StringUtil.isNotEmpty(taskId)) {
				list = taskService.getTaskCommandByTaskId(taskId);
			} else {
				list = taskService.getSubTaskCommandByKey(processDefinitionKey);
			}
			for (TaskCommand tmp : list) {
				tmpres.add(tmp.getPersistentState());
			}
			resultMap.put("commandList", tmpres);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	@Override
	public ProcessInstance completeTask(Map<String, Object> params) {

		Authentication.setAuthenticatedUserId("admin");
		ProcessInstance processInstance = null;
		String taskId = StringUtil.getString(params.get("taskId"));
		String commandType = StringUtil.getString(params.get("commandType"));
		String commandId = StringUtil.getString(params.get("commandId"));
		String processDefinitionKey = StringUtil.getString(params.get("processDefinitionKey"));
		String businessKey = StringUtil.getString(params.get("businessKey"));
		String userId = StringUtil.getString(params.get("userId"));
		String taskComment = StringUtil.getString(params.get("_taskComment"));

		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		userId = "admin";
		// 命令类型，可以从流程引擎配置中查询 启动并提交为startandsubmit
		expandTaskCommand.setCommandType(commandType);
		// 设置提交人
		expandTaskCommand.setInitiator(userId);
		// 设置命令的id,需和节点上配置的按钮编号对应，会执行按钮中的脚本。
		expandTaskCommand.setTaskCommandId(commandId);
		expandTaskCommand.setTaskComment(taskComment);
		if (StringUtil.isNotEmpty(taskId)) {
			expandTaskCommand.setTaskId(taskId);
		} else {
			String processInstanceId = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey).getId();
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskNotEnd().singleResult();
			expandTaskCommand.setTaskId(task.getId());
		}
		try {
			processInstance = taskService.expandTaskComplete(expandTaskCommand, null);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return processInstance;
	}
}
