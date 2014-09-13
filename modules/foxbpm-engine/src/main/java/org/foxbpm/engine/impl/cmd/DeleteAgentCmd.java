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

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 删除代理信息，会级联删除代理详细信息。
 * @author ych
 *
 */
public class DeleteAgentCmd implements Command<Void>{
	
	private String agentId;
	public DeleteAgentCmd(String agentId) {
		this.agentId = agentId;
	}
	
	@Override
	public Void execute(CommandContext commandContext) {
		commandContext.getAgentManager().deleteAgentById(agentId);
		return null;
	}
	

}
