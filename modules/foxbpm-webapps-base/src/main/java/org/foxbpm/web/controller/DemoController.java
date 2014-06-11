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
package org.foxbpm.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.web.common.constant.FoxbpmActionNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmServiceNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.service.interfaces.IDemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * mvc 控制器主要处理业务
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
@Controller
public class DemoController extends AbstWebController {
	// 工作业务服务
	@Resource(name = FoxbpmServiceNameDefinition.DEMO_SERVICENAME)
	private IDemoService workDemoService;

	/**
	 * 开启任务
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.START_TASK_ACTION)
	public ModelAndView startTask(HttpServletRequest request) {
		String viewName = FoxbpmViewNameDefinition.START_TASK_VIEWNAME;
		try {
			Map<String, Object> requestParams = getRequestParams(request);
			Map<String, Object> resultMap = workDemoService.startTask(requestParams);
			request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
			// 获取视图处理
			viewName = StringUtil.getString(resultMap.remove("viewName"));
		} catch (FoxbpmWebException foxbpmException) {
			return createModelAndView(FoxbpmViewNameDefinition.ERROR_VIEWNAME);
		}
		return createModelAndView(viewName);
	}

	/**
	 * 完成任务
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.COMPLETE_TASK_ACTION)
	public ModelAndView completeTask(HttpServletRequest request) {
		try {
			Map<String, Object> requestParams = getRequestParams(request);
			if (StringUtil.isEmpty(StringUtil.getString(requestParams.get("businessKey")))) {
				throw new FoxbpmWebException("", "businessKey不能为空!");
			}
			workDemoService.completeTask(requestParams);
		} catch (FoxbpmWebException foxbpmException) {
			return createModelAndView(FoxbpmViewNameDefinition.ERROR_VIEWNAME);
		}
		return createModelAndView(FoxbpmViewNameDefinition.COMPLETETASK_VIEWNAME);
	}

	protected String getPrefix() {
		return "taskCenter/";
	}
}
