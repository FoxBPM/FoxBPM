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
 */
package org.foxbpm.engine.impl.entity;

import java.io.Serializable;
import java.util.Map;

public class GroupEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String groupId;
	private String groupName;
	private String groupType;
	private String supGroupId;
	private Map<String,Object> propertyMap;
	
	public GroupEntity(){
		
	}
	
	public GroupEntity(String groupId,String groupType){
		this.groupId = groupId;
		this.groupType = groupType;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public void setPropertyMap(Map<String, Object> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public Map<String, Object> getPropertyMap() {
		return this.propertyMap;
	}
	
	public void setSupGroupId(String supGroupId) {
		this.supGroupId = supGroupId;
	}
	
	public String getSupGroupId() {
		return supGroupId;
	}

	public Object getPropertyValue(String propertyName) {
		if(this.propertyMap != null){
			return this.propertyMap.get(propertyName);
		}
		return null;
	}
}
