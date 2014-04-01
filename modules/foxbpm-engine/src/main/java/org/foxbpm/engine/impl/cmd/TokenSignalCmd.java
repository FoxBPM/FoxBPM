/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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

import java.util.Map;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.exception.FixFlowException;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.ProcessInstanceManager;
import org.foxbpm.engine.impl.runtime.ProcessInstanceEntity;
import org.foxbpm.engine.impl.runtime.TokenEntity;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.Token;

public class TokenSignalCmd implements Command<Void> {

	protected String tokenId;
	
	/**
	 * 瞬态流程实例变量Map
	 */
	protected Map<String, Object> transientVariables = null;
	
	protected Map<String, Object> dataVariables = null;
	

	public TokenSignalCmd(String tokenId,Map<String, Object> transientVariables,Map<String, Object> dataVariables) {
		this.tokenId = tokenId;
		this.transientVariables=transientVariables;
		this.dataVariables=dataVariables;
	}

	public Void execute(CommandContext commandContext) {
		
		ProcessEngine processEngine=ProcessEngineManagement.getDefaultProcessEngine();
		Token token=processEngine.getRuntimeService().createTokenQuery().tokenId(tokenId).singleResult();
		String processInstanceId = token.getProcessInstanceId();

		ProcessInstance processInstance=processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		
		
		ProcessInstanceManager processInstanceManager = commandContext.getProcessInstanceManager();

	
		ProcessDefinitionManager processDefinitionManager = commandContext.getProcessDefinitionManager();

		ProcessDefinitionBehavior processDefinition = processDefinitionManager.findLatestProcessDefinitionById(processInstance.getProcessDefinitionId());

		

		ProcessInstanceEntity processInstanceImpl = processInstanceManager.findProcessInstanceById(processInstanceId, processDefinition);
		TokenEntity tokenEntity = processInstanceImpl.getTokenMap().get(tokenId);
		if(transientVariables!=null&&transientVariables.keySet().size()>0){
			processInstanceImpl.getContextInstance().setTransientVariableMap(transientVariables);
		
		}
		if(dataVariables!=null&&dataVariables.keySet().size()>0){
			processInstanceImpl.getContextInstance().setDataVariable(dataVariables);
		}
		
		tokenEntity.signal();

		try {
			processInstanceManager.saveProcessInstance(processInstanceImpl);
		} catch (Exception e) {
			throw new FixFlowException("流程实例持久化失败!", e);
		}
		return null;
		
		

	}

}
