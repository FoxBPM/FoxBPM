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
package org.foxbpm.engine.impl.identity;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.cmd.FindGroupByIdCmd;
import org.foxbpm.engine.impl.cmd.FindGroupChildrenIncludeByGroupIdCmd;
import org.foxbpm.engine.impl.cmd.FindUserByGroupIdAndTypeCmd;
import org.foxbpm.engine.impl.cmd.FindUserByIdNoCacheCmd;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;

public abstract class Authentication {
	
	static ThreadLocal<String> authenticatedUserIdThreadLocal = new ThreadLocal<String>();
	private final static String SYSTEM_ID = "admin";
	/**
	 * 设置当前引擎的操作人
	 * 
	 * @param authenticatedUserId
	 */
	public static void setAuthenticatedUserId(String authenticatedUserId) {
		authenticatedUserIdThreadLocal.set(authenticatedUserId);
	}
	
	public static String getSystemId() {
		return SYSTEM_ID;
	}
	
	/**
	 * 获取引擎当前的操作人
	 * 
	 * @return
	 */
	public static String getAuthenticatedUserId() {
		return authenticatedUserIdThreadLocal.get();
	}
	
	/**
	 * 根据用户获取用户所在的组集合
	 * 
	 * @param userId
	 * @return
	 */
	public static List<GroupEntity> selectGroupByUserId(String userId) {
		UserEntity user = selectUserByUserId(userId);
		if (user == null) {
			throw new FoxBPMObjectNotFoundException("为找到ID:" + userId + "的用户！");
		}
		return user.getGroups();
	}
	
	/**
	 * 根据用户编号获取用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public static UserEntity selectUserByUserId(String userId) {
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		
		UserEntity result = (UserEntity) CacheUtil.getIdentityCache().get("user_" + userId);
		if (result != null) {
			return result;
		}
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		UserEntity user = commandExecutor.execute(new FindUserByIdNoCacheCmd(userId));
		CacheUtil.getIdentityCache().add("user_" + userId, user);
		return user;
	}
	
	/**
	 * 获取指定组编号和类型下的所有人员对象
	 * 
	 * @param groupId
	 * @param groupType
	 * @return
	 */
	public static List<UserEntity> selectUserByGroupIdAndType(String groupId, String groupType) {
		List<UserEntity> users = new ArrayList<UserEntity>();
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		List<String> userIds = commandExecutor.execute(new FindUserByGroupIdAndTypeCmd(groupId, groupType));
		for (String userId : userIds) {
			UserEntity user = selectUserByUserId(userId);
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}
	
	/**
	 * 获取指定组编号和类型下的所有人员ID
	 * 
	 * @param groupId
	 * @param groupType
	 * @return
	 */
	public static List<String> selectUserIdsByGroupIdAndType(String groupId, String groupType) {
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		List<String> userIds = commandExecutor.execute(new FindUserByGroupIdAndTypeCmd(groupId, groupType));
		return userIds;
	}
	
	/**
	 * 通过组编号获取下面的子组(包含父组)
	 * 
	 * @param groupId
	 *            组编号
	 * @return 子组的集合(包含父组)
	 */
	public static List<GroupEntity> findGroupChildMembersIncludeByGroupId(String groupId,
	    String groupType) {
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		List<GroupEntity> groups = commandExecutor.execute(new FindGroupChildrenIncludeByGroupIdCmd(groupId, groupType));
		return groups;
	}
	
	public static GroupEntity findGroupById(String groupId, String groupType) {
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		GroupEntity group = commandExecutor.execute(new FindGroupByIdCmd(groupType, groupId));
		return group;
	}
	
}
