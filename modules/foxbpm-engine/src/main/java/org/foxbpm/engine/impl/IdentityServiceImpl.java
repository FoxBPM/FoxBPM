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
package org.foxbpm.engine.impl;

import java.util.List;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.cmd.DeleteAgentCmd;
import org.foxbpm.engine.impl.cmd.DeleteAgentDetailsCmd;
import org.foxbpm.engine.impl.cmd.FindAgentEntityCmd;
import org.foxbpm.engine.impl.cmd.FindUserByIdCmd;
import org.foxbpm.engine.impl.cmd.FindUsersCmd;
import org.foxbpm.engine.impl.cmd.FindUsersCountCmd;
import org.foxbpm.engine.impl.cmd.GetAllGroupByTypeCmd;
import org.foxbpm.engine.impl.cmd.GetAllGroupDefinitionsCmd;
import org.foxbpm.engine.impl.cmd.GetAllGroupRelationByTypeCmd;
import org.foxbpm.engine.impl.cmd.SaveAgentDetailsEntityCmd;
import org.foxbpm.engine.impl.cmd.SaveAgentEntityCmd;
import org.foxbpm.engine.impl.cmd.UpdatePersistentObjectCmd;
import org.foxbpm.engine.impl.db.Page;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.GroupRelationEntity;

public class IdentityServiceImpl extends ServiceImpl implements IdentityService {

	@Override
	public UserEntity getUser(String userId) {
		return commandExecutor.execute(new FindUserByIdCmd(userId));
	}

	@Override
	public List<UserEntity> getUsers(String idLike, String nameLike) {
		return commandExecutor.execute(new FindUsersCmd(idLike, nameLike, null));
	}

	@Override
	public void addAgent(AgentEntity agentInfo) {
		commandExecutor.execute(new SaveAgentEntityCmd(agentInfo));
	}

	@Override
	public void addAgentDetails(AgentDetailsEntity agentDetails) {
		commandExecutor.execute(new SaveAgentDetailsEntityCmd(agentDetails));
	}

	public void updateAgentEntity(AgentEntity agentInfo) {
		commandExecutor.execute(new UpdatePersistentObjectCmd(agentInfo));
	}

	public void updateAgentDetailsEntity(AgentDetailsEntity agentDetails) {
		commandExecutor.execute(new UpdatePersistentObjectCmd(agentDetails));
	}

	@Override
	public void deleteAgent(String agentId) {
		commandExecutor.execute(new DeleteAgentCmd(agentId));
	}

	@Override
	public void deleteAgentDetails(String agentDetailsId) {
		commandExecutor.execute(new DeleteAgentDetailsCmd(agentDetailsId));
	}

	@Override
	public AgentEntity queryAgent(String agentUser) {
		return commandExecutor.execute(new FindAgentEntityCmd(agentUser));
	}

	@Override
	public List<UserEntity> getUsers(String idLike, String nameLike, Page page) {
		return commandExecutor.execute(new FindUsersCmd(idLike, nameLike, page));
	}

	@Override
	public Long getUsersCount(String idLike, String nameLike) {
		return commandExecutor.execute(new FindUsersCountCmd(idLike, nameLike));
	}
	
	@Override
	public List<GroupEntity> getAllGroup(String groupType) {
		return commandExecutor.execute(new GetAllGroupByTypeCmd(groupType));
	}
	
	@Override
	public List<GroupRelationEntity> getAllGroupRelation(String groupType) {
		return commandExecutor.execute(new GetAllGroupRelationByTypeCmd(groupType));
	}
	
	@Override
	public List<GroupDefinition> getAllGroupDefinitions() {
		return commandExecutor.execute(new GetAllGroupDefinitionsCmd());
	}
	
	@Override
	public GroupDefinition getGroupDefinition(String groupType) {
		if(groupType == null){
			return null;
		}
		List<GroupDefinition> groupDefinitions = getAllGroupDefinitions();
		for(GroupDefinition group : groupDefinitions){
			if(group.getType().equals(groupType)){
				return group;
			}
		}
		return null;
	}
}
