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
 * @author ych
 */
package org.foxbpm.engine.impl.agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.db.PersistentObject;

/**
 * 用户代理对象
 * @author ych
 *
 */
public class AgentEntity implements PersistentObject{

	
	private String id;
	/**
	 * 代理发起人
	 */
	private String agentFrom;
	
	/**
	 * 代理开始时间
	 */
	private Date startTime;
	
	/**
	 * 代理结束时间
	 */
	private Date endTime;
	
	private String status = "1";
	
	/**
	 * 代理信息明细
	 */
	private List<AgentDetailsEntity> agentDetails = new ArrayList<AgentDetailsEntity>();
	
	
	public void setAgentDetails(List<AgentDetailsEntity> agentDetails) {
		this.agentDetails = agentDetails;
	}
	
	public void setAgentFrom(String agentFrom) {
		this.agentFrom = agentFrom;
	}
	
	public List<AgentDetailsEntity> getAgentDetails() {
		return agentDetails;
	}
	
	public String getAgentFrom() {
		return agentFrom;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public Map<String, Object> getPersistentState() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("agentFrom", agentFrom);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("status", status);
		return map;
	}
	
	@Override
	public boolean isModified() {
		return false;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
