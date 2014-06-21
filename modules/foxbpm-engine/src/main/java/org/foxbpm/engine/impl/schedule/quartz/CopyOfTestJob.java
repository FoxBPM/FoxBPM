package org.foxbpm.engine.impl.schedule.quartz;

import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.quartz.JobExecutionException;

public class CopyOfTestJob extends AbstractQuartzScheduleJob {

	@Override
	public void executeJob(FoxbpmJobExecutionContext foxpmJobExecutionContext)
			throws JobExecutionException {
		System.out.println("=========================你在跑job");
		// this.commandExecutor.execute(null);
	}
}
