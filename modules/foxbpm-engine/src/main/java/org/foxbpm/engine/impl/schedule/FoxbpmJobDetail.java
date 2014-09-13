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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.QuartzUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;

/**
 * FOXBPM 工作详细
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public class FoxbpmJobDetail<T extends Job> extends JobDetailImpl {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1555130032039717646L;
	protected JobDetail jobDetail;
	protected T foxbpmJob;
	protected List<Trigger> triggerList = new ArrayList<Trigger>();
	
	public FoxbpmJobDetail(T foxbpmJob) {
		this.foxbpmJob = foxbpmJob;
		if (foxbpmJob instanceof FoxbpmScheduleJob) {
			this.jobDetail = JobBuilder.newJob(foxbpmJob.getClass()).withIdentity(((FoxbpmScheduleJob) foxbpmJob).getName(), ((FoxbpmScheduleJob) foxbpmJob).getGroupName()).build();
		} else {
			throw new FoxBPMException("非 FoxbpmScheduleJob ，无法创建FoxbpmJobDetail！");
		}
		
	}
	
	/**
	 * 创建两种类型TRIGGER
	 * 
	 * @param startDate
	 * @param cronExpression
	 * @param triggerName
	 * @param groupName
	 * @return trigger
	 */
	public void createTrigger(Object startDate, String cronExpression, String durationExpression,
	    String triggerName, String groupName) {
		Trigger trigger = null;
		TriggerBuilder<Trigger> withIdentity = newTrigger().withIdentity(triggerName, groupName);
		if (startDate == null && isBlank(cronExpression) && isBlank(durationExpression)) {
			throw new FoxBPMException("自动启动流程实例，启动时间表达式为空！");
		} else if (startDate != null) {
			// Date 启动
			if (startDate instanceof Date) {
				Date date = (Date) startDate;
				trigger = withIdentity.startAt(date).build();
			} else if (startDate instanceof String) {
				Date date = ClockUtil.parseStringToDate((String) startDate);
				trigger = withIdentity.startAt(date).build();
			} else {
				throw new FoxBPMException("自动启动流程实例，启动时间表达式有错误！");
			}
		} else if (isNotBlank(cronExpression)) {
			// CRON表达式启动
			trigger = withIdentity.withSchedule(cronSchedule(cronExpression)).build();
		} else if (isNotBlank(durationExpression)) {
			// TODO DURATION Expression暂时未实现
		}
		
		triggerList.add(trigger);
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void createCompatibleTriggerList(Expression timeExpression,
	    ListenerExecutionContext executionContext, String groupName) {
		if (timeExpression == null || StringUtil.isBlank(timeExpression.getExpressionText())) {
			return;
		}
		List<Trigger> triggersList = new ArrayList<Trigger>();
		Object triggerObj = ExpressionMgmt.execute(timeExpression.getExpressionText(), executionContext);
		if (triggerObj == null) {
			throw new FoxBPMException("FoxbpmJobDetail创建TRIGGER LIST时候，TIMER 表达式执行结果为NULL");
		}
		if (triggerObj instanceof List) {
			try {
				triggersList = (List<Trigger>) triggerObj;
			} catch (Exception e) {
				throw new FoxBPMException("FoxbpmJobDetail创建的触发器集合必须为List<Trigger>");
			}
		} else if (triggerObj instanceof Trigger) {
			Trigger tempTrigger = null;
			try {
				tempTrigger = (Trigger) triggerObj;
			} catch (Exception e) {
				throw new FoxBPMException("FoxbpmJobDetail创建的触发器集合必须为Trigger", e);
			}
			triggersList.add(tempTrigger);
		} else if (triggerObj instanceof Date) {
			triggersList.add(QuartzUtil.createTriggerByDateTime(triggerObj, groupName));
		} else if (triggerObj instanceof String) {
			triggersList.add(QuartzUtil.createCronTrigger(groupName, triggerObj.toString()));
		} else {
			throw new FoxBPMException("创建TRIGGER  LIST时候，TIMER 表达式执行错误");
		}
		
		this.triggerList = triggersList;
	}
	@SuppressWarnings("unchecked")
	public void createDateTimeTriggerList(Expression timeExpression,
	    ListenerExecutionContext executionContext, String groupName) {
		if (timeExpression == null || StringUtil.isBlank(timeExpression.getExpressionText())) {
			return;
		}
		List<Trigger> triggersList = new ArrayList<Trigger>();
		Object triggerObj = ExpressionMgmt.execute(timeExpression.getExpressionText(), executionContext);
		if (triggerObj == null) {
			throw new FoxBPMException("FoxbpmJobDetail创建TRIGGER LIST时候，TIMER 表达式执行结果为NULL");
		}
		if (triggerObj instanceof List) {
			try {
				triggersList = (List<Trigger>) triggerObj;
			} catch (Exception e) {
				throw new FoxBPMException("FoxbpmJobDetail创建的触发器集合必须为List<Trigger>");
			}
		} else if (triggerObj instanceof Trigger) {
			Trigger tempTrigger = null;
			try {
				tempTrigger = (Trigger) triggerObj;
			} catch (Exception e) {
				throw new FoxBPMException("FoxbpmJobDetail创建的触发器集合必须为Trigger", e);
			}
			triggersList.add(tempTrigger);
		} else if (triggerObj instanceof Date) {
			triggersList.add(QuartzUtil.createTriggerByDateTime(triggerObj, groupName));
		} else if (triggerObj instanceof String) {
			triggersList.add(QuartzUtil.createTriggerByDateTimeStr(triggerObj, groupName));
		} else {
			throw new FoxBPMException("创建TRIGGER  LIST时候，TIMER 表达式执行错误");
		}
		
		this.triggerList = triggersList;
	}
	@SuppressWarnings("unchecked")
	public void createTriggerListByDuration(Expression timeExpression,
	    ListenerExecutionContext executionContext, String groupName) {
		if (timeExpression == null || StringUtil.isBlank(timeExpression.getExpressionText())) {
			return;
		}
		List<Trigger> triggersList = new ArrayList<Trigger>();
		Object triggerObj = ExpressionMgmt.execute(timeExpression.getExpressionText(), executionContext);
		if (triggerObj == null) {
			throw new FoxBPMException("FoxbpmJobDetail创建TRIGGER LIST时候，TIMER 表达式执行结果为NULL");
		}
		if (triggerObj instanceof List) {
			try {
				triggersList = (List<Trigger>) triggerObj;
			} catch (Exception e) {
				throw new FoxBPMException("FoxbpmJobDetail创建的触发器集合必须为List<Trigger>");
			}
		} else if (triggerObj instanceof Trigger) {
			Trigger tempTrigger = null;
			try {
				tempTrigger = (Trigger) triggerObj;
			} catch (Exception e) {
				throw new FoxBPMException("FoxbpmJobDetail创建的触发器集合必须为Trigger", e);
			}
			triggersList.add(tempTrigger);
		} else if (triggerObj instanceof Date) {
			triggersList.add(QuartzUtil.createTriggerByDateTime(triggerObj, groupName));
		} else if (triggerObj instanceof String) {
			triggersList.add(QuartzUtil.createCronTrigger(groupName, triggerObj.toString()));
		} else {
			throw new FoxBPMException("创建TRIGGER  LIST时候，TIMER 表达式执行错误");
		}
		
		this.triggerList = triggersList;
	}
	
	public void putContextAttribute(String attributeName, Object attribute) {
		this.jobDetail.getJobDataMap().put(attributeName, attribute);
	}
	
	public JobDetail getJobDetail() {
		return jobDetail;
	}
	
	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
	
	public T getFoxbpmJob() {
		return foxbpmJob;
	}
	
	public void setFoxbpmJob(T foxbpmJob) {
		this.foxbpmJob = foxbpmJob;
	}
	
	public List<Trigger> getTriggerList() {
		return triggerList;
	}
	
	public void setTriggerList(List<Trigger> triggerList) {
		this.triggerList = triggerList;
	}
	
}
