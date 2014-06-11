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
import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.log.FoxbpmLogger;
import org.foxbpm.web.common.log.FoxbpmLoggerFactory;
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
		FoxbpmLogger logger = FoxbpmLoggerFactory.createLogger(implObj);
		logger.info(method.getName() + "将被执行::执行参数是[" + params);
	}

	@Override
	public void before(Method method, Object[] params, Object implObj) throws Throwable {
		FoxbpmLogger logger = FoxbpmLoggerFactory.createLogger(implObj);
		logger.info(method.getName() + "将被执行::执行参数是" + params);
	}

	public void afterThrowing(Method method, Object[] args, Object implObj, FoxbpmWebException ex) {
		FoxbpmLogger logger = FoxbpmLoggerFactory.createLogger(implObj);
		logger.info("异常信息");
		logger.info("抛出的异常：" + ex.toString());
		logger.info("调用方法的名称：" + method.getName());
		logger.info("方法的参数个数：" + args.length);
		logger.info("调用的目标对象：" + implObj);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return invocation.proceed();
	}
}
