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
package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.agent.AgentTo;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.AgentManager;

/**
 * 从数据库中查询user对象
 * 此方法不加载组织机构
 * 如果需要完整user对象，请使用Authentication.selectUserByUserId(String userId)
 * @author ych
 *
 */
public class FindUserByIdNoCacheCmd implements Command<User>{
	
	private String userId;
	public FindUserByIdNoCacheCmd(String userId) {
		this.userId = userId;
	}
	
	@Override
	public UserEntity execute(CommandContext commandContext) {
		UserEntity user = commandContext.getUserEntityManager().findUserById(userId);
		
		//处理组织机构
		List<GroupDefinition> groupDefinitions = commandContext.getProcessEngineConfigurationImpl().getGroupDefinitions();
		for(GroupDefinition groupDefinition : groupDefinitions){
			List<Group> tmpGroups = groupDefinition.selectGroupByUserId(userId);
			if(tmpGroups != null && tmpGroups.size() >0){
				user.getGroups().addAll(tmpGroups);
			}
		}
		
		//处理代理信息
		//AgentManager agentManager = commandContext.getAgentManager();
		//List<AgentTo> agentTos = agentManager.getAgentTos(userId);
		//user.setAgentInfo(agentTos);
		
		return user;
	}
}
