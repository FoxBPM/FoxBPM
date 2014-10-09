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

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.db.PersistentObject;

/**
 * 代理信息的子类，用来存储代理信息明细 
 * @author ych
 */
public class AgentDetailsEntity implements PersistentObject{

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 外键，关联到代理号
	 */
	private String agentId;
	
	/**
	 * 代理接受人
	 */
	private String agentTo;
	
	/**
	 * 代理流程Key
	 */
	private String processKey;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAgentId() {
		return agentId;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public String getProcessKey() {
		return processKey;
	}
	
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getAgentTo() {
		return agentTo;
	}

	public void setAgentTo(String agentTo) {
		this.agentTo = agentTo;
	}
	
	 
	public Map<String, Object> getPersistentState() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("agentId", agentId);
		map.put("processKey", processKey);
		map.put("agentTo", agentTo);
		return map;
	}
	
	 
	public boolean isModified() {
		return false;
	}
	
}
