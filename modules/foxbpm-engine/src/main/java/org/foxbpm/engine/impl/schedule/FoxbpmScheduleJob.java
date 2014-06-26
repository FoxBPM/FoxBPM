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
import org.quartz.Job;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

/**
 * FOXBPM 工作
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public abstract class FoxbpmScheduleJob implements Job {
	protected String name;
	protected String groupName;
	protected Trigger trigger;

	public FoxbpmScheduleJob(){}
	public FoxbpmScheduleJob(String name, String groupName, Trigger trigger) {
		this.name = name;
		this.groupName = groupName;
		this.trigger = trigger;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
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
