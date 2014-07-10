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
package org.foxbpm.engine.impl.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.runningtrack.RunningTrack;

public class RunningTrackEntity implements RunningTrack, PersistentObject {
	protected String id;
	protected String processInstanceId;
	protected String processDefinitionId;
	protected String processDefinitionKey;
	protected String tokenId;
	protected Date executionTime;
	protected String operator;
	protected String nodeId;
	protected String nodeName;
	protected String eventName;
	protected Date archiveTime;
	protected long trackRecord;

	@Override
	public Map<String, Object> getPersistentState() {
		Map<String, Object> objectParam = new HashMap<String, Object>();
		objectParam.put("id", this.id);
		objectParam.put("processInstanceId", this.processInstanceId);
		objectParam.put("processDefinitionId", this.processDefinitionId);
		objectParam.put("processDefinitionKey", this.processDefinitionKey);
		objectParam.put("operator", this.operator);
		objectParam.put("eventName", this.eventName);
		objectParam.put("tokenId", this.tokenId);
		objectParam.put("executionTime", this.executionTime);
		objectParam.put("archiveTime", this.archiveTime);
		objectParam.put("nodeId", this.nodeId);
		objectParam.put("nodeName", this.nodeName);
		objectParam.put("trackRecord", this.trackRecord);

		return objectParam;
	}

	@Override
	public boolean isModified() {
		return false;
	}

	public RunningTrackEntity() {
	}
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Date getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getArchiveTime() {
		return archiveTime;
	}
	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}
	public long getTrackRecord() {
		return trackRecord;
	}

	public void setTrackRecord(long trackRecord) {
		this.trackRecord = trackRecord;
	}

}
