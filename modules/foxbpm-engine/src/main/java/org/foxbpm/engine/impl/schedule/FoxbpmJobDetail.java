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

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
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
