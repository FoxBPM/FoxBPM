package org.foxbpm.web.common.env;

import java.util.Properties;
/**
 * 资源环境类
 * 
 * @author MEL
 * @date 2014-06-06
 */
public abstract class Environment {
	public static final String EXCEPTION_MESSAGE_FILE = "exception.properties";
	protected Properties properties = null;

	// 初始化
	public abstract void init();

	public abstract String getValue(String key);
}
