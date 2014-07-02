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

import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.sqlsession.ISqlSession;

public class GroupDeptImpl implements GroupDefinition {

	@SuppressWarnings("unchecked")
	public List<Group> selectGroupByUserId(String userId) {
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		List<Group> groups = (List<Group>)sqlsession.selectListWithRawParameter("selectDeptByUserId", userId);
		return groups;
	}
	
	@Override
	public String getType() {
		return "dept";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectUserIdsByGroupId(String groupId) {
		ISqlSession sqlsession = Context.getCommandContext().getSqlSession();
		List<String> userIds = (List<String>)sqlsession.selectListWithRawParameter("selectUserIdsByDeptId", groupId);
		return userIds;
	}

}
