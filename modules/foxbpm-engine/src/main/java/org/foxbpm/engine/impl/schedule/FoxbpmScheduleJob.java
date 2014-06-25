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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.schedule;

import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.quartz.JobExecutionException;

/**
 * FOXBPM 工作
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public abstract class FoxbpmScheduleJob {

	/**
	 * 执行任务的命令
	 */
	protected CommandExecutor commandExecutor;

	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	/**
	 * 执行任务的方法
	 * 
	 * @param foxpmJobExecutionContext
	 * @throws JobExecutionException
	 */
	public abstract void executeJob(
			FoxbpmJobExecutionContext foxpmJobExecutionContext)
			throws JobExecutionException;
}
