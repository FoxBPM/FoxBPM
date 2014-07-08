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

import java.util.List;

import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.cache.DefaultCache;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class GroupDeptImpl implements GroupDefinition {

	private static Cache<List<String>> deptUserCache = new DefaultCache<List<String>>(256);
	private static Cache<List<Group>> userDeptCache = new DefaultCache<List<Group>>(256);
	
	@SuppressWarnings("unchecked")
	public List<Group> selectGroupByUserId(String userId) {
		List<Group> groups = userDeptCache.get(userId);
		if(groups != null){
			return groups;
		}
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		groups = (List<Group>)sqlsession.selectListWithRawParameter("selectDeptByUserId", userId);
		userDeptCache.add(userId, groups);
		return groups;
	}
	
	@Override
	public String getType() {
		return "dept";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectUserIdsByGroupId(String groupId) {
		List<String> userIds = deptUserCache.get(groupId);
		if(userIds != null){
			return userIds;
		}
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		userIds = (List<String>)sqlsession.selectListWithRawParameter("selectUserIdsByDeptId", groupId);
		deptUserCache.add(groupId, userIds);
		return userIds;
	}

}
