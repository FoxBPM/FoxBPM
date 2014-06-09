package org.foxbpm.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.util.DateUtil;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.db.factory.FoxbpmDBConnectionFactory;
import org.foxbpm.web.db.interfaces.BizDBInterface;
import org.foxbpm.web.service.interfaces.IWebappTaskService;

/**
 * 任务服务类
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class WebappTaskService implements IWebappTaskService {
	private BizDBInterface bizDB;
	private FoxbpmDBConnectionFactory dbfactory;
	private TaskService taskService;

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
	public Map<String, Object> queryToDoTask(Pagination<String> pageInfor,
			Map<String, Object> params) {
		TaskQuery taskQuery = taskService.createTaskQuery();
		// 获取基本查询条件参数
		String userId = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_USERID);
		String initiator = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_INITIATOR);
		String processDefinitionName = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PROCESSDEFINITIONNAME);
		String businessKey = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_BUSINESSKEY);
		String title = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_TITLE);
		String dss = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_ARRIVALTIMES);
		String dse = (String) params
				.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_ARRIVALTIMEE);

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
			taskList = taskQuery
					.orderByTaskCreateTime()
					.desc()
					.listPagination(pageInfor.getPageIndex(),
							pageInfor.getPageSize());
			pageInfor.setTotal(StringUtil.getInt(taskQuery.count()));
		}
		// 设置分页信息
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> instanceMaps = new ArrayList<Map<String, Object>>();
		Map<String, Object> instances = null;
		for (int i = 0, size = (null == taskList) ? 0 : taskList.size(); i < size; i++) {
			instances = new HashMap<String, Object>();
			instances.putAll(taskList.get(i).getPersistentState());
			instanceMaps.add(instances);
		}
		resultMap.put("dataList", instanceMaps);
		return resultMap;
	}

}
