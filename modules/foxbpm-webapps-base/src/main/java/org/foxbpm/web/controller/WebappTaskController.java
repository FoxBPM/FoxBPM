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
 * @author MEL
 */
package org.foxbpm.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.Task;
import org.foxbpm.web.common.constant.FoxbpmActionNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmServiceNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IWebappTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * MVC 控制器
 * 
 * @author MEL
 * @date 2014-06-04
 */
@Controller
public class WebappTaskController extends AbstractWebappController {
	@Resource(name = FoxbpmServiceNameDefinition.TAST_SERVICENAME)
	private IWebappTaskService taskService;

	/**
	 * 对应到前端请求的action
	 * 
	 * @param parameter
	 *            形参名称必须和请求参数名称一致
	 * @return ModelAndView
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_TASK_ACTION)
	public ModelAndView startProcess(String parameter) {
		try {

			List<Task> tasks = taskService.queryTask();
			System.out.println(tasks.size());
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView("error");
		}
		ModelAndView modelAndView = new ModelAndView(FoxbpmViewNameDefinition.START_PROCESS_VIEWNAME);
		return modelAndView;
	}

	/**
	 * 查询代办任务
	 * 
	 * @param request
	 *            请求
	 * @return 返回对应展现视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_TODOTASK_ACTION)
	public ModelAndView queryToDoTask(HttpServletRequest request) {

		try {
			Map<String, Object> requestParams = getRequestParams(request);
			// 获取分页条件参数
			String pageI = StringUtil.getString(requestParams.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGEINDEX));
			String pageS = StringUtil.getString(requestParams.get(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGESIZE));

			// 处理分页
			int pageIndex = Pagination.PAGE_INDEX;
			int pageSize = Pagination.PAGE_SIZE;
			if (StringUtil.isNotEmpty(pageI)) {
				pageIndex = StringUtil.getInt(pageI);
			}
			if (StringUtil.isNotEmpty(pageS)) {
				pageSize = StringUtil.getInt(pageS);
			}
			// 分页信息
			Pagination<String> pageInfor = new Pagination<String>(pageIndex, pageSize);
			// 查询结果
			Map<String, Object> resultMap = taskService.queryToDoTask(pageInfor, requestParams);
			// 封装请求参数
			resultMap.putAll(requestParams);
			// 封装参数
			request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
			request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView(FoxbpmViewNameDefinition.ERROR_VIEWNAME);
		}
		return new ModelAndView(FoxbpmViewNameDefinition.QUERY_QUERYTODOTASK_VIEWNAME);
	}
}