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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.web.common.constant.FoxbpmActionNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmServiceNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IWorkFlowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * mvc控制器 主要处理工作流
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
@Controller
public class WorkFlowController extends AbstWebController {
	// 工作流服务
	@Resource(name = FoxbpmServiceNameDefinition.WORKFLOW_SERVICENAME)
	private IWorkFlowService workFlowService;

	/**
	 * 处理 action请求
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_STARTPROCESS_ACTION)
	public ModelAndView queryStartProcess(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = getRequestParams(request);
		requestParams.put("userId", "admin");
		// 查询结果
		List<Map<String, Object>> resultList = workFlowService.queryStartProcess(requestParams);

		// 封装参数
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultList);

		ModelAndView modelAndView = createModelAndView(FoxbpmViewNameDefinition.QUERY_QUERYALLPROCESSDEF_VIEWNAME);
		return modelAndView;
	}

	/**
	 * 处理 action请求
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_QUERYALLPROCESSINST_ACTION)
	public ModelAndView queryProcessInst(HttpServletRequest request) {
		// 请求参数
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
		List<Map<String, Object>> resultData = workFlowService.queryProcessInst(pageInfor, requestParams);
		// 封装参数给页面使用
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
		// 获取分页条件参数
		resultMap.put("dataList", resultData);
		// 将参数封装给页面使用
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);
		ModelAndView modelAndView = createModelAndView(FoxbpmViewNameDefinition.QUERY_QUERYALLPROCESSINST_VIEWNAME);
		return modelAndView;
	}

	/**
	 * 查询任务详细信息
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_TASKDETAILINFOR_ACTION)
	public ModelAndView queryTaskDetailInfor(HttpServletRequest request) {

		Map<String, Object> requestParams = getRequestParams(request);
		// 查询结果
		Map<String, Object> resultMap = workFlowService.queryTaskDetailInfor(requestParams);
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
		return createModelAndView(FoxbpmViewNameDefinition.QUERY_TASKDETAILINFOR_ACTION);
	}

	/**
	 * 查询代办任务
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.QUERY_TODOTASK_ACTION)
	public ModelAndView queryToDoTask(HttpServletRequest request) {

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
		List<Map<String, Object>> resultData = workFlowService.queryToDoTask(pageInfor, requestParams);
		// 封装参数给页面使用
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
		// 获取分页条件参数
		resultMap.put("dataList", resultData);
		// 将参数封装给页面使用
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);
		return createModelAndView(FoxbpmViewNameDefinition.QUERY_QUERYTODOTASK_VIEWNAME);
	}

	@RequestMapping(FoxbpmActionNameDefinition.GETFLOWGRAPH_ACTION)
	public ModelAndView getFlowGraph(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> requestParams = getRequestParams(request);
		String flag = StringUtil.getString(requestParams.get("flag"));
		InputStream in = null;
		if ("svg".equals(flag)) {
			String svgContent = workFlowService.getFlowSvgGraph(requestParams);
			if (StringUtil.isNotEmpty(svgContent)) {
				in = new ByteArrayInputStream(svgContent.getBytes());
			}
		} else {
			in = workFlowService.getFlowImagGraph(requestParams);
		}
		if (null != in) {
			doResponse(response, in);
		}
		return null;

	}

	@Override
	protected String getPrefix() {
		return "taskCenter/";
	}
}
