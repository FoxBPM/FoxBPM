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
package org.foxbpm.engine.impl.schedule.quartz;

import org.foxbpm.engine.impl.cmd.TimeExecuteTokenCmd;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 
 * TokenTimeoutAutoExecuteJob 超时任务自动执行JOB
 * 
 * MAENLIANG 2014年7月8日 下午1:59:37
 * 
 * @version 1.0.0
 * 
 */
public class TokenTimeoutAutoExecuteJob extends AbstractQuartzScheduleJob {

	/**
	 * quartz系统创建
	 */
	public TokenTimeoutAutoExecuteJob() {
	}

	/**
	 * 本地自动调度创建
	 * 
	 * @param name
	 * @param groupName
	 * @param trigger
	 */
	public TokenTimeoutAutoExecuteJob(String name, String groupName) {
		super(name, groupName);
	}

	@Override
	public void executeJob(FoxbpmJobExecutionContext foxpmJobExecutionContext)
			throws JobExecutionException {
		TimeExecuteTokenCmd timeExecuteTokenCmd = new TimeExecuteTokenCmd();
		timeExecuteTokenCmd.setNodeID(foxpmJobExecutionContext.getNodeId());
		timeExecuteTokenCmd.setTokenID(foxpmJobExecutionContext.getTokenId());
		timeExecuteTokenCmd.setProcessInstanceID(foxpmJobExecutionContext.getProcessInstanceId());
		commandExecutor.execute(timeExecuteTokenCmd);
	}

}
