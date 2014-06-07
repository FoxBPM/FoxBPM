package org.foxbpm.web.common.log;

import org.apache.log4j.Logger;

/**
 * 封装日志记录
 * @author MEL
 * @date 2014-06-06
 */
public class FoxbpmLogger {
	private Logger logger;
	public FoxbpmLogger(String name) {
		this.logger = Logger.getLogger(name);
	}

	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}

	public void debug(Object message) {
		logger.debug(message);
	}

	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	public void error(Object message) {
		logger.error(message);
	}

	public void info(Object message, Throwable t) {
		logger.info(message, t);
	}

	public void info(Object message) {
		logger.info(message);
	}

	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}

	public void warn(Object message) {
		logger.warn(message);
	}

}
