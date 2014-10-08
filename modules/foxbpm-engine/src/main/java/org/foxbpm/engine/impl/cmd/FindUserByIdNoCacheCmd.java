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

import org.foxbpm.engine.identity.GroupDefinition;
import org.foxbpm.engine.impl.agent.AgentTo;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 从数据库中查询user对象
 * 此方法不加载组织机构
 * 如果需要完整user对象，请使用Authentication.selectUserByUserId(String userId)
 * @author ych
 *
 */
public class FindUserByIdNoCacheCmd implements Command<UserEntity>{
	
	private String userId;
	public FindUserByIdNoCacheCmd(String userId) {
		this.userId = userId;
	}
	
	 
	public UserEntity execute(CommandContext commandContext) {
		UserEntity user = commandContext.getUserEntityManager().findUserById(userId);
		if(user == null){
			return null;
		}
		//处理组织机构
		List<GroupDefinition> groupDefinitions = commandContext.getProcessEngineConfigurationImpl().getGroupDefinitions();
		List<GroupEntity> tmpGroups = null;
		for(GroupDefinition groupDefinition : groupDefinitions){
			tmpGroups = groupDefinition.selectGroupByUserId(userId);
			if(tmpGroups != null && tmpGroups.size() >0){
				user.getGroups().addAll(tmpGroups);
			}
		}
		List<AgentTo> agentTos = commandContext.getAgentManager().getAgentTos(userId);
		user.setAgentInfo(agentTos);
		return user;
	}
}
