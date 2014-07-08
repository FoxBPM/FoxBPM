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
package org.foxbpm.engine.impl.identity;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class GroupRoleImpl implements GroupDefinition {

	@SuppressWarnings("unchecked")
	public List<Group> selectGroupByUserId(String userId) {
		List<Group> groups = (List<Group>) CacheUtil.getIdentityCache().get("userRoleCache_" + userId);
		if(groups != null){
			return groups;
		}
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		groups = (List<Group>)sqlsession.selectListWithRawParameter("selectRoleByUserId", userId);
		CacheUtil.getIdentityCache().add("userRoleCache_" + userId, groups);
		return groups;
	}
	
	@Override
	public String getType() {
		return Constant.ROLE_TYPE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectUserIdsByGroupId(String groupId) {
		List<String> userIds = (List<String>) CacheUtil.getIdentityCache().get("roleUserCache_" + groupId);
		if(userIds != null){
			return userIds;
		}
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		userIds = (List<String>)sqlsession.selectListWithRawParameter("selectUserIdsByRoleId", groupId);
		CacheUtil.getIdentityCache().add("roleUserCache_" + groupId, userIds);
		return userIds;
	}
	
	@Override
	public List<Group> selectChildrenByGroupId(String groupId) {
		List<Group> groups = new ArrayList<Group>();
		Group role = selectGroupByGroupId(groupId);
		if(role != null){
			groups.add(role);
		}
		return groups;
	}
	
	@Override
	public Group selectGroupByGroupId(String groupId) {
		Group group = (Group)CacheUtil.getIdentityCache().get("roleCache_" + groupId);
		if(group != null){
			return group;
		}
		ISqlSession sqlSession = Context.getCommandContext().getSqlSession();
		group = (Group) sqlSession.selectOne("selectRoleById", groupId);
		CacheUtil.getIdentityCache().add("roleCache_" + groupId, group);
		return group;
	}

}
