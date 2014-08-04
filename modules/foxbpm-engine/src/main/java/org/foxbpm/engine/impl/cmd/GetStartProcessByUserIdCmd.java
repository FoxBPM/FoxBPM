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

import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.config.TransactionPropagation;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandConfig;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetStartProcessByUserIdCmd implements Command<List<ProcessDefinition>>{

	private static final Logger log = LoggerFactory.getLogger(GetStartProcessByUserIdCmd.class);
	protected String userId;
	
	public GetStartProcessByUserIdCmd(String userId){
		this.userId=userId;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessDefinition> execute(CommandContext commandContext) {
		Cache<Object> userProcessDefinitionCache = commandContext.getProcessEngineConfigurationImpl().getUserProcessDefinitionCache();
		List<ProcessDefinition> result = (List<ProcessDefinition>)userProcessDefinitionCache.get(userId);
		if(result != null){
			return result;
		}
		result = new ArrayList<ProcessDefinition>();
		CommandExecutor commandExecutor = commandContext.getProcessEngineConfigurationImpl().getCommandExecutor();
		List<ProcessDefinitionEntity> processDefinitions = commandExecutor.execute(new GetProcessDefinitionGroupKeyCmd());
		
		CommandConfig commandConfig = new CommandConfig();
		commandConfig.setContextReuse(false);
		commandConfig.setPropagation(TransactionPropagation.REQUIRES_NEW);
		for(ProcessDefinitionEntity process : processDefinitions){
			boolean isVerify = false;
			try{
				isVerify= commandExecutor.execute(commandConfig,new VerificationStartUserCmd(userId, null, process.getId()));
			}catch(Exception ex){
				log.error("验证流程发起失败,流程编号:"+process.getId(),ex);
			}
			
			if(isVerify){
				result.add(process);
			}
		}
		userProcessDefinitionCache.add(userId, result);
		return result;
	}

}
