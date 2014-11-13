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
 * @author kenshin
 */
package org.foxbpm.engine.impl.interceptor;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.kernel.KernelException;
import org.foxbpm.kernel.KernelListenerException;
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
		}catch(KernelListenerException ex){
			throw ExceptionUtil.getException("10700001",ex,ex.getTokenId(),ex.getNodeId(),ex.getListenerId(),ex.getEventName());
		}catch(KernelException ex){
			throw ExceptionUtil.getException("10300000",ex,ex.getMessage());
		}catch(FoxBPMException ex){
			throw ex;
		}catch(RuntimeException ex){
			throw ExceptionUtil.getException("10300001",ex);
		}finally {
			log.debug("--- {} finished --------------------------------------------------------", command.getClass().getSimpleName());
			log.debug("                                                                                                    ");
		}
	}

}
