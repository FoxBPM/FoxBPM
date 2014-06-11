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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.foxbpm.engine.impl.util.StringUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * 抽象控制器
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
public abstract class AbstWebController {

	/**
	 * 根据视图名称创建 modelAndView 视图对象
	 * 
	 * @param viewName
	 *            视图名称
	 * @return 返回视图对象
	 */
	public ModelAndView createModelAndView(String viewName) {
		return new ModelAndView(getPrefix() + viewName);
	}

	/**
	 * 获取控制对象视图前缀 例如:taskCenter/xxxx 子类需要根据业务情况重写该类
	 * 
	 * @return 返回视图前缀
	 */
	protected String getPrefix() {
		return StringUtil.EMPTY;
	}

	/**
	 * http request 请求参数获取
	 * 
	 * @param request
	 *            http 请求
	 * @return 返回获取的http请求参数
	 */
	protected Map<String, Object> getRequestParams(HttpServletRequest request) {
		// 请求参数
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.putAll(request.getParameterMap());
		// 获取parmeter中参数
		Enumeration<String> enumeration = request.getParameterNames();
		if (null != enumeration) {
			String key = null;
			while (enumeration.hasMoreElements()) {
				key = enumeration.nextElement();
				requestParams.put(key, request.getParameter(key));
			}
		}
		// 获取attribute中参数
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
