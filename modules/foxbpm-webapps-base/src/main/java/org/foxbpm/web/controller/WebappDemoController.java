/** 
 * Project Name:foxbpm-webapps-base 
 * File Name:WebappDemoController.java 
 * Package Name:org.foxbpm.web.controller 
 * Date:2014年6月10日下午4:22:30 
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved. 
 * 
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
import org.foxbpm.web.service.interfaces.IWebappDemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * date: 2014年6月10日 下午4:22:30
 * 
 * @author yangguangftlp
 */
@Controller
public class WebappDemoController extends AbstractWebappController {

	@Resource(name = FoxbpmServiceNameDefinition.DEMO_SERVICENAME)
	private IWebappDemoService webappDemoService;

	@RequestMapping(FoxbpmActionNameDefinition.START_TASK_ACTION)
	public ModelAndView startTask(HttpServletRequest request) {
		String viewName = FoxbpmViewNameDefinition.START_TASK_VIEWNAME;
		try {
			Map<String, Object> requestParams = getRequestParams(request);
			Map<String, Object> resultMap = webappDemoService.startTask(requestParams);
			request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
			// 获取视图处理
			viewName = StringUtil.getString(resultMap.remove("viewName"));
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView(FoxbpmViewNameDefinition.ERROR_VIEWNAME);
		}
		return new ModelAndView(viewName);
	}

	@RequestMapping(FoxbpmActionNameDefinition.COMPLETE_TASK_ACTION)
	public ModelAndView executeTask(HttpServletRequest request) {
		try {
			Map<String, Object> requestParams = getRequestParams(request);
			if (StringUtil.isEmpty(StringUtil.getString(requestParams.get("businessKey")))) {
				throw new FoxbpmWebException("", "businessKey不能为空!");
			}
			webappDemoService.executeTask(requestParams);
		} catch (FoxbpmWebException foxbpmException) {
			return new ModelAndView(FoxbpmViewNameDefinition.ERROR_VIEWNAME);
		}
		return new ModelAndView(FoxbpmViewNameDefinition.COMPLETETASK_VIEWNAME);
	}
}
