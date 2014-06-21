package org.foxbpm.engine.impl.agent;

import java.util.Date;

public class AgentTo {

	private String id;
	private String agentFrom;
	private Date startTime;
	private Date endTime;
	private String processKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAgentFrom() {
		return agentFrom;
	}
	public void setAgentFrom(String agentFrom) {
		this.agentFrom = agentFrom;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
}
