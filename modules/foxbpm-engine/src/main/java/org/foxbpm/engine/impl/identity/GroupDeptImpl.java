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
import java.util.Map;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class GroupDeptImpl implements GroupDefinition {

	@SuppressWarnings("unchecked")
	public List<Group> selectGroupByUserId(String userId) {
		List<Group> groups = (List<Group>) CacheUtil.getIdentityCache().get("userDeptCache_" + userId);
		if(groups != null){
			return groups;
		}
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		groups = (List<Group>)sqlsession.selectListWithRawParameter("selectDeptByUserId", userId);
		CacheUtil.getIdentityCache().add("userDeptCache_" + userId, groups);
		return groups;
	}
	
	@Override
	public String getType() {
		return Constant.DEPT_TYPE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectUserIdsByGroupId(String groupId) {
		List<String> userIds =(List<String>) CacheUtil.getIdentityCache().get("deptUserCache_" + groupId);
		if(userIds != null){
			return userIds;
		}
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		userIds = (List<String>)sqlsession.selectListWithRawParameter("selectUserIdsByDeptId", groupId);
		CacheUtil.getIdentityCache().add("deptUserCache_" + groupId, userIds);
		return userIds;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> selectChildrenByGroupId(String groupId) {
		List<Group> groups = (List<Group>)CacheUtil.getIdentityCache().get("selectChildrenByGroupId_" + groupId);
		if(groups != null){
			return groups;
		}
		groups = new ArrayList<Group>();
		//获取本身
		Group group = selectGroupByGroupId(groupId);
		if(group != null){
			groups.add(group);
			//递归子组
			selectSubDept(groupId,groups);
		}
		return groups;
	}
	
	/**
	 * 递归子组
	 * @param groupId
	 * @param groups
	 */
	@SuppressWarnings("unchecked")
	public void selectSubDept(String groupId,List<Group> groups){
		ISqlSession sqlSession = Context.getCommandContext().getSqlSession();
		List<Group> tmpGroups = (List<Group>)sqlSession.selectListWithRawParameter("selectDeptBySupId", groupId);
		if(tmpGroups != null){
			groups.addAll(tmpGroups);
			for(Group tmp : tmpGroups){
				selectSubDept(tmp.getGroupId(),groups);
			}
		}
	}
	
	@Override
	public Group selectGroupByGroupId(String groupId) {
		Group group = (Group)CacheUtil.getIdentityCache().get("deptCache_" + groupId);
		if(group != null){
			return group;
		}
		ISqlSession sqlSession = Context.getCommandContext().getSqlSession();
		group = (Group) sqlSession.selectOne("selectDeptById", groupId);
		CacheUtil.getIdentityCache().add("deptCache_" + groupId, group);
		return group;
	}
	
	@Override
	public List<Group> selectAllGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Map<String, String>> selectAllRelation() {
		// TODO Auto-generated method stub
		return null;
	}

}
