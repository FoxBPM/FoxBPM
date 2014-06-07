package org.foxbpm.web.service.aop;

import java.lang.reflect.Method;

import org.foxbpm.web.common.exception.FoxbpmWebException;
import org.foxbpm.web.common.log.FoxbpmLogger;
import org.foxbpm.web.common.log.FoxbpmLoggerFactory;
import org.springframework.aop.ThrowsAdvice;

/**
 * 方法执行过程中发生异常，进行面向切面的操作,如：记录日志，记录异常信息，记录方法信息
 * 
 * @author MEL
 * @date 2014-06-04
 */
public class FoxbpmMethodInvokeExceptionLogger implements ThrowsAdvice {

	public void afterThrowing(Method method, Object[] args, Object implObj,
			FoxbpmWebException ex) {
		FoxbpmLogger logger = FoxbpmLoggerFactory.createLogger(implObj);
		logger.info("异常信息");
		logger.info("抛出的异常：" + ex.toString());
		logger.info("调用方法的名称：" + method.getName());
		logger.info("方法的参数个数：" + args.length);
		logger.info("调用的目标对象：" + implObj);
	}

}
