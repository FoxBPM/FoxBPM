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
package org.foxbpm.engine.impl.util;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.Properties;

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 
 * 
 * QuartzUtil QUARTZ框架的工具辅助类
 * 
 * MAENLIANG 2014年6月30日 下午4:41:20
 * 
 * @version 1.0.0
 * 
 */
public final class QuartzUtil {
	/**
	 * 创建定时任务工厂
	 * 
	 * @return
	 */
	public final static SchedulerFactory createSchedulerFactory() {
		SchedulerFactory sf = new StdSchedulerFactory();
		return sf;
	}
	
	/**
	 * 根据传入的属性文件创建定时任务工厂
	 * 
	 * @param props
	 * @return
	 */
	public final static SchedulerFactory createSchedulerFactory(Properties props) {
		SchedulerFactory sf = null;
		try {
			sf = new StdSchedulerFactory(props);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sf;
	}
	
	/**
	 * 根据任务工厂拿到定时任务
	 * 
	 * @param schedulerFactory
	 *            任务工厂
	 * @return
	 */
	public final static Scheduler getScheduler(SchedulerFactory schedulerFactory) {
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scheduler;
	}
	
	/**
	 * 创建作业
	 * 
	 * @param jobClass
	 *            实现Job接口的类
	 * @param jobName
	 *            作业名称
	 * @param groupName
	 *            组名称
	 * @return
	 */
	public final static JobDetail createJobDetail(Class<? extends Job> jobClass, String jobName,
	    String groupName) {
		JobDetail job = newJob(jobClass).withIdentity(jobName, groupName).build();
		return job;
	}
	
	/**
	 * 创建简单触发器
	 * 
	 * @param jobName
	 *            作业名
	 * @param groupName
	 *            组名
	 * @param DataTime
	 *            启动时间
	 * @return
	 */
	public final static Trigger createSimpleTrigger(String jobName, String groupName, Date DataTime) {
		Trigger trigger = newTrigger().withIdentity(jobName, groupName).startAt(DataTime).build();
		return trigger;
	}
	
	/**
	 * 创建复杂触发器
	 * 
	 * @param jobName
	 *            作业名
	 * @param groupName
	 *            组名
	 * @param cronExpression
	 *            cron表达式
	 * @return
	 */
	public final static Trigger createCronTrigger(String jobName, String groupName,
	    String cronExpression) {
		CronTrigger trigger = newTrigger().withIdentity(jobName, groupName).withSchedule(cronSchedule(cronExpression)).build();
		return trigger;
	}
	
	/**
	 * 创建简单触发器
	 * 
	 * @param jobName
	 *            作业名
	 * @param groupName
	 *            组名
	 * @param DataTime
	 *            启动时间
	 * @return
	 */
	public final static Trigger createSimpleTrigger(ListenerExecutionContext executionContext,
	    Date DataTime) {
		Trigger trigger = newTrigger().withIdentity(GuidUtil.CreateGuid(), executionContext.getProcessInstanceId()).startAt(DataTime).build();
		return trigger;
	}
	
	/**
	 * 创建复杂触发器
	 * 
	 * @param jobName
	 *            作业名
	 * @param groupName
	 *            组名
	 * @param cronExpression
	 *            cron表达式
	 * @return
	 */
	public final static Trigger createCronTrigger(ListenerExecutionContext executionContext,
	    String cronExpression) {
		CronTrigger trigger = newTrigger().withIdentity(GuidUtil.CreateGuid(), executionContext.getProcessInstanceId()).withSchedule(cronSchedule(cronExpression)).build();
		return trigger;
	}
	
	/**
	 * 创建复杂触发器
	 * 
	 * @param jobName
	 *            作业名
	 * @param groupName
	 *            组名
	 * @param cronExpression
	 *            cron表达式
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public final static Trigger createCronTrigger(ListenerExecutionContext executionContext,
	    String cronExpression, Date startTime, Date endTime) {
		
		TriggerBuilder<CronTrigger> triggerBuilder = newTrigger().withIdentity(GuidUtil.CreateGuid(), executionContext.getProcessInstanceId()).withSchedule(cronSchedule(cronExpression));
		
		if (startTime != null) {
			triggerBuilder.startAt(startTime);
		}
		if (endTime != null) {
			triggerBuilder.endAt(endTime);
		}
		
		CronTrigger trigger = triggerBuilder.build();
		return trigger;
	}
	
	/**
	 * 根据定时任务和作业名称得到作业
	 * 
	 * @param scheduler
	 *            定时任务
	 * @param jobKey
	 *            作业名称
	 * @return
	 */
	public final static JobDetail getJobDetail(Scheduler scheduler, String jobKey) {
		JobDetail jobDetail = null;
		try {
			jobDetail = scheduler.getJobDetail(new JobKey(jobKey));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobDetail;
	}
	
	/**
	 * 根据定时任务和触发器名称得到触发器
	 * 
	 * @param scheduler
	 *            定时任务
	 * @param triggerKey
	 *            触发器名称
	 * @return
	 */
	public final static Trigger getTrigger(Scheduler scheduler, String triggerKey) {
		Trigger trigger = null;
		try {
			trigger = scheduler.getTrigger(new TriggerKey(triggerKey));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trigger;
	}
	
	/**
	 * 
	 * 根据日期对应的字符串创建
	 * 
	 * @param dateTime
	 * @param groupName
	 * @return Trigger
	 * @exception
	 * @since 1.0.0
	 */
	public final static Trigger createTriggerByDateTimeStr(Object dateTime, String groupName) {
		Date startDateTime = ClockUtil.parseStringToDate((String) dateTime);
		return newTrigger().withIdentity(GuidUtil.CreateGuid(), groupName).startAt(startDateTime).build();
		
	}
	
	/**
	 * 
	 * 根据日期类型创建TRIGGER
	 * 
	 * @param dateTimeObj
	 * @param groupName
	 * @return Trigger
	 * @exception
	 * @since 1.0.0
	 */
	public final static Trigger createTriggerByDateTime(Object dateTimeObj, String groupName) {
		Date dateTime = (Date) dateTimeObj;
		return newTrigger().withIdentity(GuidUtil.CreateGuid(), groupName).startAt(dateTime).build();
		
	}
	
	/**
	 * 调度，先清空后调度
	 * 
	 * @param jobKey
	 * @param jobDetail
	 */
	public final static void scheduleFoxbpmJob(FoxbpmJobDetail<?> jobDetail) {
		try {
			FoxbpmScheduler foxbpmScheduler = ProcessEngineManagement.getDefaultProcessEngine().getProcessEngineConfiguration().getFoxbpmScheduler();
			if (foxbpmScheduler == null) {
				throw new FoxBPMException("FOXBPM 自动启动类型的部署时候，调度器没有初始化！");
			}
			
			// 先清空，后调度
			foxbpmScheduler.deleteJob(jobDetail);
			foxbpmScheduler.scheduleFoxbpmJob(jobDetail);
		} catch (SchedulerException e) {
			throw new FoxBPMException("调度  《流程自动启动JOB》时候出现问题！");
		}
	}
}
