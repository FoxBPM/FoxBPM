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
package org.foxbpm.web.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.service.impl.AbstWorkFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * 进行面向切面的操作,如：记录日志，记录异常信息，记录方法信息
 * 
 * @author yangguangftlp
 * @date 2014年6月11日
 */
public class FoxbpmMethodInvokeLogger implements AfterReturningAdvice, MethodBeforeAdvice, MethodInterceptor, ThrowsAdvice {
	@Override
	public void afterReturning(Object returnObj, Method method, Object[] params, Object implObj) throws Throwable {
		Logger logger = LoggerFactory.getLogger(implObj.getClass());
		logger.debug(method.getName() + "执行完成，执行结果：" + returnObj);
	}

	@Override
	public void before(Method method, Object[] params, Object implObj) throws Throwable {
		Logger logger = LoggerFactory.getLogger(implObj.getClass());
		logger.debug("方法名:{}",method.getName());
		if(implObj instanceof AbstWorkFlowService){
			Authentication.setAuthenticatedUserId("admin");
		}
//		StringBuilder paramsBuilder = new StringBuilder();
//		for(Object param : params){
//			paramsBuilder.append(param);
//			paramsBuilder.append("(");
//			paramsBuilder.append(param.getClass());
//			paramsBuilder.append(")");
//			paramsBuilder.append(", ");
//		}
//		logger.debug("参数：{}" , paramsBuilder.toString().substring(0, paramsBuilder.toString().length()-1));
	}

	public void afterThrowing(Method method, Object[] args, Object implObj, Exception ex) {
		Logger logger = LoggerFactory.getLogger(implObj.getClass());
		logger.error("service执行出错：方法："+method.getName()+"，参数："+args , ex);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return invocation.proceed();
	}
}
