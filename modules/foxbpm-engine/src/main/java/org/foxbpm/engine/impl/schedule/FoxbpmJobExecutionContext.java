package org.foxbpm.engine.impl.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class FoxbpmJobExecutionContext {

	public Job getScheduleJob() {
		return scheduleJob;
	}

	public void setScheduleJob(Job scheduleJob) {
		this.scheduleJob = scheduleJob;
	}

	private String tokenId;
	private String processInstanceId;
	private String nodeId;
	private String processKey;
	private String processId;
	private String processName;
	private String bizKey;
	private String jobType;
	private String connectorId;
	private String connectorInstanceId;
	private String connectorInstanceName;
	private String eventType;
	private String taskId;
	private Job scheduleJob;

	public FoxbpmJobExecutionContext(JobExecutionContext jobExecutionContext) {
		scheduleJob = jobExecutionContext.getJobInstance();
		this.tokenId = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("tokenId");
		this.processInstanceId = jobExecutionContext.getJobDetail()
				.getJobDataMap().getString("processInstanceId");
		this.nodeId = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("nodeId");
		this.processKey = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("processKey");
		this.processId = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("processId");
		this.processName = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("processName");
		this.bizKey = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("bizKey");
		this.jobType = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("jobType");
		this.connectorId = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("connectorId");
		this.connectorInstanceId = jobExecutionContext.getJobDetail()
				.getJobDataMap().getString("connectorInstanceId");
		this.connectorInstanceName = jobExecutionContext.getJobDetail()
				.getJobDataMap().getString("connectorInstanceName");
		this.eventType = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("eventType");
		this.taskId = jobExecutionContext.getJobDetail().getJobDataMap()
				.getString("taskId");
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

	public String getConnectorInstanceId() {
		return connectorInstanceId;
	}

	public void setConnectorInstanceId(String connectorInstanceId) {
		this.connectorInstanceId = connectorInstanceId;
	}

	public String getConnectorInstanceName() {
		return connectorInstanceName;
	}

	public void setConnectorInstanceName(String connectorInstanceName) {
		this.connectorInstanceName = connectorInstanceName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
