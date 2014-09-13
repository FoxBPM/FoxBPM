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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.impl.agent.AgentEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 获取代理实例
 * 
 * @author yangguangftlp
 * @date 2014年6月24日
 */
public class FindAgentEntityCmd implements Command<AgentEntity> {

	private String agentUser;

	public FindAgentEntityCmd(String agentUser) {
		this.agentUser = agentUser;
	}

	@Override
	public AgentEntity execute(CommandContext commandContext) {
		return commandContext.getAgentManager().queryAgentEntity(agentUser);
	}

}
