package org.foxbpm.web.service.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.foxbpm.web.common.log.FoxbpmLogger;
import org.foxbpm.web.common.log.FoxbpmLoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 方法执行时进行面向切面的操作,通过代理控制对原始方法的访问
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class FoxbpmMethodInvokeInterceptor implements MethodInterceptor {
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String parameter = "test";// invocation.getArguments()[0].toString();
		if (!StringUtils.isEmpty(parameter)) {
			return invocation.proceed();
		} else {
			FoxbpmLogger logger = FoxbpmLoggerFactory.createLogger(invocation
					.getMethod().getDeclaringClass());
			logger.info(invocation.getMethod().getName() + ":参数是null");
		}
		return null;
	}

}
