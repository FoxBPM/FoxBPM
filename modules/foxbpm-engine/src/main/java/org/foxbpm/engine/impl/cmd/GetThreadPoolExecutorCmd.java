/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.engine.impl.cmd;

import java.util.concurrent.ThreadPoolExecutor;

import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.threadpool.FixThreadPoolExecutor;

public class GetThreadPoolExecutorCmd implements Command<ThreadPoolExecutor>{

	protected String threadPoolKey;
	
	public GetThreadPoolExecutorCmd(String threadPoolKey){
		this.threadPoolKey=threadPoolKey;
	}
	
	public ThreadPoolExecutor execute(CommandContext commandContext) {
		if(threadPoolKey==null||threadPoolKey.equals("")){
			threadPoolKey="default";
		}
		ProcessEngineConfigurationImpl processEngineConfigurationImpl=commandContext.getProcessEngineConfigurationImpl();
		FixThreadPoolExecutor fixThreadPoolExecutor= processEngineConfigurationImpl.getThreadPoolMap().get(threadPoolKey);
		return fixThreadPoolExecutor;
	}

}
