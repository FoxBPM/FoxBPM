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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.web.common.constant.FoxbpmActionNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmServiceNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmViewNameDefinition;
import org.foxbpm.web.common.constant.FoxbpmWebContextAttributeNameDefinition;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.util.Pagination;
import org.foxbpm.web.service.interfaces.IFlowManageService;
import org.foxbpm.web.service.interfaces.IWorkFlowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * mvc 控制器 流程管理中心
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
@Controller
public class FlowManageController extends AbstWebController {

	// 工作流服务
	@Resource(name = FoxbpmServiceNameDefinition.WORKFLOW_SERVICENAME)
	private IWorkFlowService workFlowService;
	// 流程管理服务
	@Resource(name = FoxbpmServiceNameDefinition.FLOWMANAGE_SERVICENAME)
	private IFlowManageService flowManageService;

	/**
	 * 流程定义 action请求
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.PROCESSDEF_ACTION)
	public ModelAndView processDef(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = getRequestParams(request);
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
		List<Map<String, Object>> resultData = flowManageService.queryProcessDef(pageInfor, requestParams);
		// 封装参数给页面使用
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
		// 获取分页条件参数
		resultMap.put("dataList", resultData);
		// 封装参数

		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_RESULT, resultMap);
		request.setAttribute(FoxbpmWebContextAttributeNameDefinition.ATTRIBUTE_NAME_PAGEINFOR, pageInfor);

		ModelAndView modelAndView = createModelAndView(FoxbpmViewNameDefinition.PROCESSDEF_VIEWNAME);
		return modelAndView;
	}

	/**
	 * 流程定义 action请求
	 * 
	 * @param request
	 *            http请求参数
	 * @return 返回响应视图
	 */
	@RequestMapping(FoxbpmActionNameDefinition.DEPLOY_ACTION)
	public ModelAndView deploy(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = getRequestParams(request);
		flowManageService.deployByZip(requestParams);
		return processDef(request);

	}

	@RequestMapping(FoxbpmActionNameDefinition.DELETEDEPLOY_ACTION)
	public ModelAndView deleteDeploy(HttpServletRequest request, HttpServletResponse response) {
		// 请求参数
		Map<String, Object> requestParams = getRequestParams(request);
		flowManageService.deleteDeploy(requestParams);
		return null;
	}

	@RequestMapping(FoxbpmActionNameDefinition.UPDATECACHE_ACTION)
	public ModelAndView updateCache(HttpServletRequest request, HttpServletResponse response) {
		try {
			CacheUtil.clearCache();
			response.getWriter().write("update success!");
			return null;
		} catch (IOException e) {
			throw new FoxbpmWebException(e);
		}
	}

	@Override
	protected String getPrefix() {
		return "manageCenter/";
	}

}
