package org.foxbpm.web.common.log;

import java.lang.reflect.Method;

/**
 * 创建FoxbpmLogger
 * 
 * @author MEL
 * @date 2014-06-06
 */
public final class FoxbpmLoggerFactory {
	public static FoxbpmLogger createLogger(Class cls) {
		return new FoxbpmLogger(cls.getName());
	}

	public static FoxbpmLogger createLogger(Method method) {
		return new FoxbpmLogger(method.getDeclaringClass().getName());
	}

	public static FoxbpmLogger createLogger(Object obj) {
		return new FoxbpmLogger(obj.getClass().getName());
	}

}
