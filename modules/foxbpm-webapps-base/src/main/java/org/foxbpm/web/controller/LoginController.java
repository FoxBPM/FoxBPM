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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.web.common.constant.WebContextAttributeName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登陆控制器
 * 
 * @author yangguangftlp
 * @date 2014年7月8日
 */
@Controller
public class LoginController extends AbstWebController {

	/**
	 * 登陆处理
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return 返回
	 */
	@RequestMapping(value = "login", method = { RequestMethod.GET, RequestMethod.POST })
	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 从登录的口获取到用户名和密码
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			// 该接口同时也是登出的口，当发现有特殊参数时则做登出操作。
			String logout = request.getParameter("doLogOut");
			String contextPath = request.getContextPath();
			if (StringUtil.isNotEmpty(logout)) {
				request.getSession().invalidate();
				response.sendRedirect(contextPath + "/login.jsp");
			} else {
				UserEntity userEntity = (UserEntity) Authentication.selectUserByUserId(userName);
				if (null != userEntity && StringUtil.equals(password, userEntity.getPassword())) {
					// 这里约定了一个参数，流程引擎在运行时会默认从session里按照这两个key来获取参数，如果替换了登录的方式，请保证这两个key依然可以获取到正确的数据
					request.getSession().setAttribute(WebContextAttributeName.USERID, userEntity.getUserId());
					request.getSession().setAttribute(WebContextAttributeName.USERNAME, userName);
					// 登录时根据登录的目标切换跳转目标
					String loginType = request.getParameter("loginType");
					if ("on".equalsIgnoreCase(loginType)) {
						response.sendRedirect(contextPath + "/processDef.action");
					} else {
						response.sendRedirect(contextPath + "/queryToDoTask.action");
					}
				} else {
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().print("<script>alert('用户名或密码错误！');window.location.href='" + contextPath + "/login.jsp';</script>");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getPrefix() {
		return null;
	}

}