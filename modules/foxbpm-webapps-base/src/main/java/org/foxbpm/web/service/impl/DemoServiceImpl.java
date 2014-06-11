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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.db.interfaces.IDemoDao;
import org.foxbpm.web.model.TDemo;
import org.foxbpm.web.service.interfaces.IDemoService;
import org.foxbpm.web.service.interfaces.IWorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工作流业务处理类
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
@Service("demoServiceImpl")
public class DemoServiceImpl implements IDemoService {

	// 业务 Dao
	@Autowired
	private IDemoDao idemoDao;
	// 工作流服务
	@Autowired
	private IWorkFlowService workFlowService;

	@Override
	public Map<String, Object> startTask(Map<String, Object> params) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("viewName", FoxbpmViewNameDefinition.START_TASK_VIEWNAME);
		// 调用工作流程服务开启一个任务,并存放获取信息
		resultMap.putAll(workFlowService.startTask(params));
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
		return resultMap;
	}

	@Override
	public ProcessInstance completeTask(Map<String, Object> params) {
		String infor = StringUtil.getString(params.get("infor"));
		String taskStauts = StringUtil.getString(params.get("taskStauts"));
		String businessKey = StringUtil.getString(params.get("businessKey"));
		try {
			// 如果当前流程是首次启动
			if ("0".equalsIgnoreCase(taskStauts)) {
				// 将业务数据存放到数据库中
				TDemo tDemo = new TDemo();
				tDemo.setPak(businessKey);
				tDemo.setInfor(infor);
				idemoDao.saveDemoData(tDemo);
			}
			return workFlowService.completeTask(params);
		} catch (Exception e) {
			throw new FoxbpmWebException(e.getMessage(), "", e);
		}
	}
}
