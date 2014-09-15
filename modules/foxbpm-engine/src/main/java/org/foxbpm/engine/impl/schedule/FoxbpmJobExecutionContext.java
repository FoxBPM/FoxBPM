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
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * FOXBPM 工作上下文
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public class FoxbpmJobExecutionContext {
	/**
	 * 工作、组名称后缀
	 */
	public final static String NAME_SUFFIX_JOBDETAIL = "_JOBDETAIL";
	public final static String NAME_SUFFIX_JOBTRIGGER = "_TRIGGER";
	public final static String NAME_SUFFIX_JOBGROUP = "_GROUP";
	/**
	 * 自动调度环境变量名称
	 */
	public final static String PROCESS_DEFINITION_ID = "processId";
	public final static String PROCESS_INSTANCE_ID = "processInstanceId";
	public final static String NODE_ID = "nodeId";
	public final static String FLOW_NODE = "flowNode";
	public final static String PROCESS_DEFINITION_KEY = "processKey";
	public final static String PROCESS_DEFINITION_NAME = "processName";
	public final static String BUSINESS_KEY = "bizKey";
	public final static String TOKEN_ID = "tokenId";
	public final static String CONNECTOR_ID = "connectorId";
	public final static String EVENT_NAME = "eventName";

	public final static String CONNECTOR_INSTANCE_ID = "connectorInstanceId";
	public final static String CONNECTOR_INSTANCE_NAME = "connectorInstanceName";
	public final static String EVENT_TYPE = "eventType";
	public final static String TASK_ID = "taskId";

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
	private String eventName;
	private String taskId;
	private Job scheduleJob;

	public FoxbpmJobExecutionContext(JobExecutionContext jobExecutionContext) {
		JobDataMap jobDataMap = jobExecutionContext.getJobDetail()
				.getJobDataMap();
		scheduleJob = jobExecutionContext.getJobInstance();
		this.tokenId = jobDataMap.getString(TOKEN_ID);
		this.processInstanceId = jobDataMap.getString(PROCESS_INSTANCE_ID);
		this.nodeId = jobDataMap.getString(NODE_ID);
		this.processKey = jobDataMap.getString(PROCESS_DEFINITION_KEY);
		this.processId = jobDataMap.getString(PROCESS_DEFINITION_ID);
		this.processName = jobDataMap.getString(PROCESS_DEFINITION_NAME);
		this.bizKey = jobDataMap.getString(BUSINESS_KEY);
		this.jobType = jobDataMap.getString("jobType");
		this.connectorId = jobDataMap.getString(CONNECTOR_ID);
		this.connectorInstanceId = jobDataMap.getString(CONNECTOR_INSTANCE_ID);
		this.connectorInstanceName = jobDataMap
				.getString(CONNECTOR_INSTANCE_NAME);
		this.eventType = jobDataMap.getString(EVENT_TYPE);
		this.eventName = jobDataMap.getString(EVENT_NAME);
		this.taskId = jobDataMap.getString(TASK_ID);

	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Job getScheduleJob() {
		return scheduleJob;
	}

	public void setScheduleJob(Job scheduleJob) {
		this.scheduleJob = scheduleJob;
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
