package org.foxbpm.web.common.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 异常环境类
 * 
 * @author MEL
 * @date 2014-06-06
 */
public class ExceptionEnvironment extends Environment {

	private static ExceptionEnvironment exceptionEnv = null;

	private ExceptionEnvironment() {
		if (properties == null) {
			properties = new Properties();
		}
		if (exceptionEnv == null) {
			exceptionEnv = this;
		}
	}

	@Override
	public void init() {
		InputStream inputStream = null;
		try {
			inputStream = ExceptionEnvironment.class
					.getResourceAsStream(Environment.EXCEPTION_MESSAGE_FILE);
			properties.load(inputStream);
		} catch (IOException e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public String getValue(String key) {
		return this.properties.getProperty(key);
	}

	public static ExceptionEnvironment getInstance() {
		if (exceptionEnv == null) {
			exceptionEnv = new ExceptionEnvironment();
		}
		return exceptionEnv;
	}

	public String getMessageByCode(String errorCode) {
		return this.getValue(errorCode);
	}
}
