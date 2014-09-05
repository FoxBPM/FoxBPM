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
package org.foxbpm.portal.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.foxbpm.engine.impl.identity.Authentication;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 对workflowService的实现方法进行拦截
 * 在执行前设置当前操作用户
 * 执行后清空当前操作用户
 * @author yangguangftlp
 * @author ych
 * @date 2014年6月11日
 */
public class FoxbpmUserInterceptor implements AfterReturningAdvice, MethodBeforeAdvice{
	
	@Autowired
    private HttpSession session;
	@Override
	public void afterReturning(Object returnObj, Method method, Object[] params, Object implObj) throws Throwable {
		Authentication.setAuthenticatedUserId(null);
	}

	@Override
	public void before(Method method, Object[] params, Object implObj) throws Throwable {
		Authentication.setAuthenticatedUserId("admin");
	}
}
