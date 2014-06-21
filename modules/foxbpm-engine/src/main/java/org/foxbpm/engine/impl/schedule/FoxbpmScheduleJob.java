package org.foxbpm.engine.impl.schedule;

import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.quartz.JobExecutionException;

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
