package org.foxbpm.web.service.aop;

import java.lang.reflect.Method;

import org.foxbpm.web.common.log.FoxbpmLogger;
import org.foxbpm.web.common.log.FoxbpmLoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

/**
 * 方法执行之后进行面向切面的操作,如：记录日志，记录方法返回值
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class FoxbpmMethodAfterInvokeLogger implements AfterReturningAdvice {
	@Override
	public void afterReturning(Object returnObj, Method method,
			Object[] params, Object implObj) throws Throwable {
		FoxbpmLogger logger = FoxbpmLoggerFactory.createLogger(implObj);
		logger.info(method.getName() + "将被执行::执行参数是[" + params);
	}
}
