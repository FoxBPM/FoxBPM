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
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.agent.AgentTo;

public class UserEntity {
	
	private String userId;
	private String userName;
	private String password;
	private String email;
	private String tel;
	private String image;
	private Map<String, Object> propertyMap;
	private List<GroupEntity> groups = new ArrayList<GroupEntity>();
	private List<AgentTo> agentInfo;
	
	public UserEntity() {
		
	}
	
	public UserEntity(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	
	public UserEntity(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPropertyMap(Map<String, Object> propertyMap) {
		this.propertyMap = propertyMap;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public Map<String, Object> getPropertyMap() {
		return this.propertyMap;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public List<GroupEntity> getGroups() {
		return groups;
	}
	
	public Object getPropertyValue(String propertyName) {
		if (this.propertyMap != null) {
			return this.propertyMap.get(propertyName);
		}
		return null;
	}
	
	public List<AgentTo> getAgentInfo() {
		return agentInfo;
	}
	
	public void setAgentInfo(List<AgentTo> agentInfo) {
		this.agentInfo = agentInfo;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("userId", userId);
		persistentState.put("userName", userName);
		persistentState.put("email", email);
		persistentState.put("tel", tel);
		persistentState.put("image", image);
		return persistentState;
	}
	
}
