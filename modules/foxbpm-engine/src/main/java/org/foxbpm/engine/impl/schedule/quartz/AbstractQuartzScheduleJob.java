package org.foxbpm.engine.impl.schedule.quartz;

import org.foxbpm.engine.impl.interceptor.CommandExecutorImpl;
import org.foxbpm.engine.impl.interceptor.CommandInvoker;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduleJob;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractQuartzScheduleJob extends FoxbpmScheduleJob
		implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// 统一封装数据,统一设置执行命令
		FoxbpmJobExecutionContext foxpmJobExecutionContext = new FoxbpmJobExecutionContext(
				context);
		commandExecutor = new CommandExecutorImpl(new CommandInvoker());
		// 执行任务
		this.executeJob(foxpmJobExecutionContext);
	}

}
