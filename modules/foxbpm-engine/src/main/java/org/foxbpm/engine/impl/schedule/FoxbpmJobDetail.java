package org.foxbpm.engine.impl.schedule;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;

public class FoxbpmJobDetail<T extends Job> extends JobDetailImpl {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1555130032039717646L;
	private JobDetail jobDetail;
	private String foxpmnJobName;
	private String foxpmnJobGroup;
	private T foxbpmJob;
	private Trigger trigger;

	public FoxbpmJobDetail(T foxbpmJob, String foxpmnJobName,
			String foxpmnJobGroup, Trigger trigger) {
		this.foxpmnJobGroup = foxpmnJobGroup;
		this.foxpmnJobName = foxpmnJobName;
		this.trigger = trigger;
		this.jobDetail = JobBuilder.newJob(foxbpmJob.getClass())
				.withIdentity(foxpmnJobName, foxpmnJobGroup).build();
	}

	public FoxbpmJobDetail(T foxbpmJob) {
		this.foxbpmJob = foxbpmJob;
		this.jobDetail = JobBuilder.newJob(foxbpmJob.getClass()).build();
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public String getFoxpmnJobName() {
		return foxpmnJobName;
	}

	public void setFoxpmnJobName(String foxpmnJobName) {
		this.foxpmnJobName = foxpmnJobName;
	}

	public String getFoxpmnJobGroup() {
		return foxpmnJobGroup;
	}

	public void setFoxpmnJobGroup(String foxpmnJobGroup) {
		this.foxpmnJobGroup = foxpmnJobGroup;
	}

	public T getFoxbpmJob() {
		return foxbpmJob;
	}

	public void setFoxbpmJob(T foxbpmJob) {
		this.foxbpmJob = foxbpmJob;
	}

}
