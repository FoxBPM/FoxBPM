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
package org.foxbpm.engine.impl;

import java.util.List;

import org.foxbpm.engine.SchedulerService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * 调度器实现类
 * 
 * @author MAENLIANG
 * @date 2014-06-24
 * 
 */
public class SchedulerServiceImpl implements SchedulerService {
	private FoxbpmScheduler foxbpmScheduler;

	public void setFoxbpmScheduler(FoxbpmScheduler foxbpmScheduler) {
		this.foxbpmScheduler = foxbpmScheduler;
	}

	public void queryJobList() {
	}

	public void suspendJob(String name, String group) {
		try {
			foxbpmScheduler.pauseJob(new JobKey(name, group));
		} catch (SchedulerException e) {
			throw new FoxBPMException("", e);
		}
	}

	@Override
	public void continueJob(String name, String group) {
		try {
			foxbpmScheduler.resumeJob(new JobKey(name, group));
		} catch (SchedulerException e) {
			throw new FoxBPMException("", e);
		}
	}

	@Override
	public List<Trigger> getTriggerList(String jobName, String jobGroup) {
		// TODO Auto-generated method stub
		return null;
	}
}
