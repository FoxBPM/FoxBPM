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
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.agent.AgentDetailsEntity;
import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.cmd.DeleteAgentCmd;
import org.foxbpm.engine.impl.cmd.DeleteAgentDetailsCmd;
import org.foxbpm.engine.impl.cmd.FindUserByIdCmd;
import org.foxbpm.engine.impl.cmd.FindUsersCmd;
import org.foxbpm.engine.impl.cmd.SaveAgentDetailsEntityCmd;
import org.foxbpm.engine.impl.cmd.SaveAgentEntityCmd;
import org.foxbpm.engine.impl.cmd.UpdatePersistentObjectCmd;
public class IdentityServiceImpl  extends ServiceImpl implements IdentityService {

	@Override
	public User getUser(String userId) {
		return  commandExecutor.execute(new FindUserByIdCmd(userId));
	}
	
	@Override
	public List<User> getUsers(String idLike, String nameLike) {
		return commandExecutor.execute(new FindUsersCmd(idLike,nameLike));
	}
	
	@Override
	public void addAgent(AgentEntity agentInfo) {
		commandExecutor.execute(new SaveAgentEntityCmd(agentInfo));
	}
	
	@Override
	public void addAgentDetails(AgentDetailsEntity agentDetails) {
		commandExecutor.execute(new SaveAgentDetailsEntityCmd(agentDetails));
	}
	
	public void updateAgentEntity(AgentEntity agentInfo){
		commandExecutor.execute(new UpdatePersistentObjectCmd(agentInfo));
	}
	
	public void updateAgentDetailsEntity(AgentDetailsEntity agentDetails){
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

}
