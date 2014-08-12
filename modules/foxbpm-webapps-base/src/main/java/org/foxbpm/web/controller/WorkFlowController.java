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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.web.common.constant.WebActionName;
import org.foxbpm.web.common.constant.WebContextAttributeName;
import org.foxbpm.web.common.constant.WebViewName;
import org.foxbpm.web.common.util.Pagination;
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

	/**
	 * 处理 action请求
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(WebActionName.QUERY_STARTPROCESS_ACTION)
	public ModelAndView queryStartProcess(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = getRequestParams(request);
		// 查询结果
		List<Map<String, Object>> resultList = workFlowService.queryStartProcess(requestParams);

		// 封装参数
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_RESULT, resultList);

		ModelAndView modelAndView = createModelAndView(WebViewName.QUERY_QUERYALLPROCESSDEF_VIEWNAME);
		return modelAndView;
	}

	/**
	 * 处理 action请求
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(WebActionName.QUERY_QUERYALLPROCESSINST_ACTION)
	public ModelAndView queryProcessInst(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = getRequestParams(request);
		// 获取分页条件参数
		String pageI = StringUtil.getString(requestParams.get(WebContextAttributeName.ATTRIBUTE_NAME_PAGEINDEX));
		String pageS = StringUtil.getString(requestParams.get(WebContextAttributeName.ATTRIBUTE_NAME_PAGESIZE));

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
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_RESULT, resultMap);
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);
		ModelAndView modelAndView = createModelAndView(WebViewName.QUERY_QUERYALLPROCESSINST_VIEWNAME);
		return modelAndView;
	}

	/**
	 * 查询任务详细信息
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(WebActionName.QUERY_TASKDETAILINFOR_ACTION)
	public ModelAndView queryTaskDetailInfor(HttpServletRequest request) {

		Map<String, Object> requestParams = getRequestParams(request);
		// 根据流程实例id查询结果
		Map<String, Object> resultMap = workFlowService.queryTaskDetailInfor(requestParams);
		// 如果没有查询结果,说明是启动流程时 点击“流程状态”查询操作
		if (resultMap.isEmpty()) {
			String processDefinitionId = StringUtil.getString(requestParams.get("processDefinitionId"));
			if (StringUtil.isNotEmpty(processDefinitionId)) {
				resultMap.put("processDefinitionId", processDefinitionId);
			}
			String processDefinitionKey = StringUtil.getString(requestParams.get("processDefinitionKey"));
			if (StringUtil.isNotEmpty(processDefinitionKey)) {
				resultMap.put("processDefinitionKey", processDefinitionKey);
			}
		}

		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_RESULT, resultMap);
		return createModelAndView(WebViewName.QUERY_TASKDETAILINFOR_ACTION);
	}

	/**
	 * 查询代办任务
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(WebActionName.QUERY_TODOTASK_ACTION)
	public ModelAndView queryToDoTask(HttpServletRequest request) {

		Map<String, Object> requestParams = getRequestParams(request);
		// 获取分页条件参数
		String pageI = StringUtil.getString(requestParams.get(WebContextAttributeName.ATTRIBUTE_NAME_PAGEINDEX));
		String pageS = StringUtil.getString(requestParams.get(WebContextAttributeName.ATTRIBUTE_NAME_PAGESIZE));

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
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_RESULT, resultMap);
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);
		return createModelAndView(WebViewName.QUERY_QUERYTODOTASK_VIEWNAME);
	}

	/**
	 * 获取流程图
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return 返回响应视图
	 */
	@RequestMapping(WebActionName.GETFLOWGRAPH_ACTION)
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

	@RequestMapping(WebActionName.SELECT_USER)
	public ModelAndView selectUserList(HttpServletRequest request) {
		Map<String, Object> requestParams = getRequestParams(request);
		// 获取分页条件参数
		String pageI = StringUtil.getString(requestParams.get(WebContextAttributeName.ATTRIBUTE_NAME_PAGEINDEX));
		String pageS = StringUtil.getString(requestParams.get(WebContextAttributeName.ATTRIBUTE_NAME_PAGESIZE));

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
		List<UserEntity> users = workFlowService.queryUsers(pageInfor, requestParams);
		// 查询用户数
		long count = workFlowService.queryUsersCount(requestParams);
		pageInfor.setTotal(StringUtil.getInt(count));
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		if (null != users && !users.isEmpty()) {
			for (UserEntity user : users) {
				userList.add(user.getPersistentState());
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataList", userList);
		resultMap.put("pageInfo", pageInfor);
		// 将参数封装给页面使用
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_RESULT, resultMap);
		request.setAttribute(WebContextAttributeName.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);
		return createModelAndView(WebViewName.SELECT_USER);

	}

	@Override
	protected String getPrefix() {
		return "taskCenter/";
	}
}
