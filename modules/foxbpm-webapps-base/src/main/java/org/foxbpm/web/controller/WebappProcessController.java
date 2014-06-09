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
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.web.common.constant.FoxbpmActionNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmServiceNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IWebappProcessService;
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
public class WebappProcessController {
	@Resource(name = FoxbpmServiceNameDefinition.PROCESS_SERVICENAME)
	private IWebappProcessService webappProcessService;

	/**
	 * 对应到前端请求的action
	 * 
	 * @param parameter
	 *            形参名称必须和请求参数名称一致
	 * @return ModelAndView
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_PROCESSDEFINITION_ACTION)
	public ModelAndView queryProcessDefinition(String parameter) {
		try {

			List<ProcessDefinition> processDefinitionList = webappProcessService.queryProcessDefinition();
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView("error");
		}
		ModelAndView modelAndView = new ModelAndView(FoxbpmViewNameDefinition.START_PROCESS_VIEWNAME);
		return modelAndView;
	}

	@RequestMapping(FoxbpmActionNameDefinition.QUERY_QUERYALLPROCESSDEF_ACTION)
	public ModelAndView queryAllProcessDef(HttpServletRequest request) {
		try {
			// 请求参数
			Map<String, Object> requestParams = getRequestParams(request);
			// 查询结果
			Map<String, List<Map<String, Object>>> resultMap = webappProcessService.queryAllProcessDef(null, requestParams);
			// 封装参数
			request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
			request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGEINFOR, null);
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView(FoxbpmViewNameDefinition.ERROR_VIEWNAME);
		}
		ModelAndView modelAndView = new ModelAndView(FoxbpmViewNameDefinition.QUERY_QUERYALLPROCESSDEF_VIEWNAME);
		return modelAndView;
	}

	/**
	 * http request 请求参数获取
	 * 
	 * @param request
	 *            http 请求
	 * @return 返回获取的http请求参数
	 */
	private Map<String, Object> getRequestParams(HttpServletRequest request) {

		// 请求参数
		Map<String, Object> requestParams = new HashMap<String, Object>();

		requestParams.putAll(request.getParameterMap());
		Enumeration<String> enumeration = request.getParameterNames();
		if (null != enumeration) {
			String key = null;
			while (enumeration.hasMoreElements()) {
				key = enumeration.nextElement();
				requestParams.put(key, request.getParameter(key));
			}
		}
		enumeration = request.getAttributeNames();
		if (null != enumeration) {
			String key = null;
			while (enumeration.hasMoreElements()) {
				key = enumeration.nextElement();
				requestParams.put(key, request.getAttribute(key));
			}
		}
		return requestParams;
	}
}