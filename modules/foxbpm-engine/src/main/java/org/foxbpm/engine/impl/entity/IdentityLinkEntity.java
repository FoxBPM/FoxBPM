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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.task.IdentityLink;

public class IdentityLinkEntity implements Serializable, IdentityLink, PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 持久化字段
	protected String id;

	protected String type;

	protected String userId;

	protected String groupId;

	protected String groupType;

	protected String taskId;

	protected Date archiveTime;
	
	//增加域类型和域编号
	protected String scopeType;
	
	protected String scopeId;

	// get和set方法
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

	// 定义对象
	protected TaskEntity task;

	// 对象set和get方法
	public TaskEntity getTask() {
		return task;
	}

	public void setTask(TaskEntity task) {
		this.task = task;
		this.taskId = task.getId();
	}

	public boolean isUser() {
		return userId != null;
	}

	public void insert() {
		Context.getCommandContext().getIdentityLinkManager().insert(this);

	}
	public Map<String, Object> getPersistentState() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", getId());
		map.put("type", getType());
		map.put("groupId", getGroupId());
		map.put("GroupType", getGroupType());
		map.put("userId", getUserId());
		map.put("taskId", getTaskId());
		map.put("scopeId", getScopeId());
		map.put("scopeType", getScopeType());
		return map;
		
	}

	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

}
