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
 * @author kenshin
 */
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;

public class GetStartProcessByUserIdCmd implements Command<List<Map<String, Object>>>{

	protected String userId;
	
	public GetStartProcessByUserIdCmd(String userId){
		this.userId=userId;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> execute(CommandContext commandContext) {
		Cache<Object> userProcessDefinitionCache = commandContext.getProcessEngineConfigurationImpl().getUserProcessDefinitionCache();
		List<Map<String,Object>> result = (List<Map<String,Object>>)userProcessDefinitionCache.get(userId);
		if(result != null){
			return result;
		}
		result = new ArrayList<Map<String,Object>>();
		CommandExecutor commandExecutor = commandContext.getProcessEngineConfigurationImpl().getCommandExecutor();
		List<ProcessDefinitionEntity> processDefinitions = commandExecutor.execute(new GetProcessDefinitionGroupKeyCmd());
		for(ProcessDefinitionEntity process : processDefinitions){
			boolean isVerify = commandExecutor.execute(new VerificationStartUserCmd(userId, null, process.getId()));
			if(isVerify){
				result.add(process.getPersistentState());
			}
		}
		userProcessDefinitionCache.add(userId, result);
		return result;
	}

}
