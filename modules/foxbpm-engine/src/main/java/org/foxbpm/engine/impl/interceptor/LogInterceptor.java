package org.foxbpm.engine.impl.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInterceptor extends CommandInterceptor {
	private static Logger log = LoggerFactory.getLogger(LogInterceptor.class);

	public <T> T execute(CommandConfig config, Command<T> command) {
		if (!log.isDebugEnabled()) {
			// do nothing here if we cannot log
			return next.execute(config, command);
		}
		log.debug("                                                                                                    ");
		log.debug("--- starting {} --------------------------------------------------------", command.getClass().getSimpleName());
		try {

			return next.execute(config, command);

		} finally {
			log.debug("--- {} finished --------------------------------------------------------", command.getClass().getSimpleName());
			log.debug("                                                                                                    ");
		}
	}

}
