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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 * 日志拦截器
 * 
 * @author yangguangftlp
 * @date 2014年7月14日
 */
public class FoxbpmLogInterceptor implements AfterReturningAdvice, MethodBeforeAdvice, ThrowsAdvice {

	 
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		Logger logger = LoggerFactory.getLogger(target.getClass());
		logger.debug("结束方法,方法名:" + method.getName() + ",执行结果" + returnValue);
	}

	 
	public void before(Method method, Object[] args, Object target) throws Throwable {
		Logger logger = LoggerFactory.getLogger(target.getClass());
		logger.debug("进入方法,方法名:" + method.getName());
	}

	/**
	 * 方法异常日志打印
	 * 
	 * @param method
	 *            方法名
	 * @param args
	 *            参数
	 * @param implObj
	 *            实现类
	 * @param ex
	 *            异常
	 */
	public void afterThrowing(Method method, Object[] args, Object implObj, Exception ex) {
		Logger logger = LoggerFactory.getLogger(implObj.getClass());
		logger.error("service执行出错：方法：" + method.getName() + "，参数：" + args, ex);
	}
}
