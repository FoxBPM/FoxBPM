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
package org.foxbpm.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.web.common.constant.WebContextAttributeName;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * mvc拦截器
 * 
 * @author yangguangftlp
 * @date 2014年7月8日
 */
public class FoxbpmMVCInterceptor implements HandlerInterceptor {

	 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 过滤登陆action
		if (request.getRequestURI().equals(request.getContextPath() + "/login.action")) {
			return true;
		}
		// 其他页面统一进行鉴权
		String userId = StringUtil.getString(request.getSession().getAttribute(WebContextAttributeName.USERID));
		if (StringUtil.isEmpty(userId)) {
			String context = request.getContextPath();
			response.sendRedirect(context + "/login.jsp");
			return false;
		}
		return true;
	}

	 
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
