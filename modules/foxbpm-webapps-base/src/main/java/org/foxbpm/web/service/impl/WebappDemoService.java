/** 
 * Project Name:foxbpm-webapps-base 
 * File Name:WebappDemoService.java 
 * Package Name:org.foxbpm.web.service.impl 
 * Date:2014年6月10日下午4:27:03 
 * Copyright (c) 2014, yangguangftlp@163.com All Rights Reserved. 
 * 
 */
package org.foxbpm.web.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.db.SqlCommand;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.model.TDemo;
import org.foxbpm.web.service.interfaces.IWebappDemoService;

/**
 * date: 2014年6月10日 下午4:27:03
 * 
 * @author yangguangftlp
 */
public class WebappDemoService extends AbstractWebappService implements IWebappDemoService {

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
			resultMap.put("viewName", FoxbpmViewNameDefinition.START_TASK_VIEWNAME);
			// 业务处理
			String bizKey = StringUtil.getString(params.get("businessKey"));
			if (StringUtil.isNotEmpty(bizKey)) {
				List<Object> sqlParams = new ArrayList<Object>();
				sqlParams.add(bizKey);
				List<TDemo> resultList = idemoDao.queryDemoData(sqlParams);
				if (null != resultList && !resultList.isEmpty()) {
					resultMap.put("demoObject", resultList.get(0).getPersistentState());
				}
				resultMap.put("viewName", FoxbpmViewNameDefinition.DO_TASK_VIEWNAME);
			}

		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return resultMap;
	}

	@Override
	public ProcessInstance executeTask(Map<String, Object> params) {
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
			TDemo tDemo = new TDemo();
			tDemo.setPak(StringUtil.getString(params.get("businessKey")));
			tDemo.setPak(StringUtil.getString(params.get("infor")));
			idemoDao.saveDemoData(tDemo);
			processInstance = taskService.expandTaskComplete(expandTaskCommand, null);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
		return processInstance;
	}
}
