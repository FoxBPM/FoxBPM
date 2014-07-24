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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.foxbpm.engine.exception.FoxBPMException;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

/**
 * FOXBPM 工作任务调度器，目前是封装的QUARTZ API,实现Scheduler以获取其所有行为
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public class FoxbpmScheduler implements Scheduler {
	private Scheduler scheduler;
	
	public FoxbpmScheduler() {
	}
	
	public FoxbpmScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public void scheduleFoxbpmJob(FoxbpmJobDetail<?> jobDetail) throws SchedulerException {
		// 进行FOXBPM操作
		FoxbpmJobDetail<?> foxbpmJobDetail = (FoxbpmJobDetail<?>) jobDetail;
		List<Trigger> triggerList = foxbpmJobDetail.getTriggerList();
		if (triggerList != null && triggerList.size() != 0) {
			if (triggerList.size() == 1) {
				this.scheduler.scheduleJob(foxbpmJobDetail.getJobDetail(), triggerList.get(0));
			} else {
				Map<JobDetail, List<Trigger>> triggersAndJobs = new HashMap<JobDetail, List<Trigger>>();
				triggersAndJobs.put(foxbpmJobDetail.getJobDetail(), triggerList);
				this.scheduler.scheduleJobs(triggersAndJobs, true);
			}
		} else {
			throw new FoxBPMException("FoxbpmSheduler 执行scheduleFoxbpmJob时候 triggerList为空");
		}
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	@Override
	public String getSchedulerName() throws SchedulerException {
		return this.getClass().getName();
	}
	
	@Override
	public String getSchedulerInstanceId() throws SchedulerException {
		return scheduler.getSchedulerInstanceId();
	}
	
	@Override
	public SchedulerContext getContext() throws SchedulerException {
		return scheduler.getContext();
	}
	
	@Override
	public void start() throws SchedulerException {
		scheduler.start();
	}
	
	@Override
	public void startDelayed(int seconds) throws SchedulerException {
		scheduler.startDelayed(seconds);
	}
	
	@Override
	public boolean isStarted() throws SchedulerException {
		return scheduler.isStarted();
	}
	
	@Override
	public void standby() throws SchedulerException {
		scheduler.standby();
	}
	
	@Override
	public boolean isInStandbyMode() throws SchedulerException {
		return scheduler.isInStandbyMode();
		
	}
	
	@Override
	public void shutdown() throws SchedulerException {
		scheduler.shutdown();
	}
	
	@Override
	public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
		scheduler.shutdown(waitForJobsToComplete);
	}
	
	@Override
	public boolean isShutdown() throws SchedulerException {
		return scheduler.isShutdown();
	}
	
	@Override
	public SchedulerMetaData getMetaData() throws SchedulerException {
		return scheduler.getMetaData();
	}
	
	@Override
	public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {
		return scheduler.getCurrentlyExecutingJobs();
	}
	
	@Override
	public void setJobFactory(JobFactory factory) throws SchedulerException {
		scheduler.setJobFactory(factory);
	}
	
	@Override
	public ListenerManager getListenerManager() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		return this.scheduler.scheduleJob(jobDetail, trigger);
	}
	
	@Override
	public Date scheduleJob(Trigger trigger) throws SchedulerException {
		return scheduler.scheduleJob(trigger);
	}
	
	@Override
	public void scheduleJobs(Map<JobDetail, List<Trigger>> triggersAndJobs, boolean replace)
	    throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean deleteJob(JobKey jobKey) throws SchedulerException {
		return this.getScheduler().deleteJob(jobKey);
	}
	
	public boolean deleteJob(FoxbpmJobDetail<?> jobDetail) {
		FoxbpmScheduleJob foxbpmScheduleJob = (FoxbpmScheduleJob) jobDetail.getFoxbpmJob();
		try {
			return this.getScheduler().deleteJob(new JobKey(foxbpmScheduleJob.getName(), foxbpmScheduleJob.getGroupName()));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
		}
		return false;
	}
	
	@Override
	public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
		return this.scheduler.deleteJobs(jobKeys);
	}
	
	public boolean deleteJobsByGroupName(String nodeId) throws SchedulerException {
		Set<JobKey> jobKeys = new HashSet<JobKey>();
		jobKeys = this.getJobKeys(GroupMatcher.jobGroupContains(nodeId));
		List<JobKey> jobKeysList = null;
		if (jobKeys.size() > 0) {
			jobKeysList = new ArrayList<JobKey>();
			jobKeysList.addAll(jobKeys);
			return this.scheduler.deleteJobs(jobKeysList);
		}
		return false;
		
	}
	@Override
	public void triggerJob(JobKey jobKey) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void triggerJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pauseJob(JobKey jobKey) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pauseJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.pauseTrigger(triggerKey);
	}
	
	@Override
	public void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
		scheduler.pauseTriggers(matcher);
	}
	
	@Override
	public void resumeJob(JobKey jobKey) throws SchedulerException {
		scheduler.resumeJob(jobKey);
	}
	
	@Override
	public void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
		scheduler.resumeJobs(matcher);
	}
	
	@Override
	public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pauseAll() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resumeAll() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<String> getJobGroupNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException {
		return this.scheduler.getJobKeys(matcher);
	}
	
	@Override
	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getTriggerGroupNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher)
	    throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Set<String> getPausedTriggerGroups() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addCalendar(String calName, Calendar calendar, boolean replace,
	    boolean updateTriggers) throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean deleteCalendar(String calName) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Calendar getCalendar(String calName) throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getCalendarNames() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean checkExists(JobKey jobKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void clear() throws SchedulerException {
		// TODO Auto-generated method stub
		
	}
	
}
