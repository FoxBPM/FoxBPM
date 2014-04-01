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
package org.foxbpm.engine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.factory.ProcessObjectFactory;
import org.foxbpm.engine.impl.cmd.ContinueProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.DeleteProcessInstanceByInstanceIdAndDefKeyCmd;
import org.foxbpm.engine.impl.cmd.DeleteProcessInstanceByInstanceIdCmd;
import org.foxbpm.engine.impl.cmd.DeleteProcessInstanceVaribalesCmd;
import org.foxbpm.engine.impl.cmd.ExecuteRuleScriptCmd;
import org.foxbpm.engine.impl.cmd.ExpandCommonCmd;
import org.foxbpm.engine.impl.cmd.GetProcessCommand;
import org.foxbpm.engine.impl.cmd.GetProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.MessageStartProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.NoneStartProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.QueryVariablesCmd;
import org.foxbpm.engine.impl.cmd.SaveVariablesCmd;
import org.foxbpm.engine.impl.cmd.SuspendProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.TerminatProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.TimeStartProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.TokenSignalCmd;
import org.foxbpm.engine.impl.cmd.TokenTimeOutCmd;
import org.foxbpm.engine.impl.cmd.UpdateBusinessKeyCmd;
import org.foxbpm.engine.impl.command.MessageStartProcessInstanceCommand;
import org.foxbpm.engine.impl.command.QueryVariablesCommand;
import org.foxbpm.engine.impl.command.SaveVariablesCommand;
import org.foxbpm.engine.impl.command.StartProcessInstanceCommand;
import org.foxbpm.engine.impl.runtime.ProcessInstanceEntity;
import org.foxbpm.engine.impl.subscription.EventSubscriptionQueryImpl;
import org.foxbpm.engine.impl.task.TaskInstanceEntity;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.runtime.TokenQuery;
import org.foxbpm.engine.subscription.EventSubscriptionQuery;
import org.foxbpm.engine.task.TaskInstance;

public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {

	public ProcessInstance noneStartProcessInstance(StartProcessInstanceCommand startProcessInstanceCommand) {
		return commandExecutor.execute(new NoneStartProcessInstanceCmd<ProcessInstance>(startProcessInstanceCommand));
	}

	public ProcessInstance timeStartProcessInstance(StartProcessInstanceCommand startProcessInstanceCommand) {
		return commandExecutor.execute(new TimeStartProcessInstanceCmd<ProcessInstance>(startProcessInstanceCommand));
	}

	public ProcessInstance messageStartProcessInstance(MessageStartProcessInstanceCommand messageStartProcessInstanceCommand) {
		return commandExecutor.execute(new MessageStartProcessInstanceCmd<ProcessInstance>(messageStartProcessInstanceCommand));
	}

	public boolean deleteProcessInstance(String processInstanceId, boolean cascade) {
		return commandExecutor.execute(new DeleteProcessInstanceByInstanceIdCmd(processInstanceId, cascade));
	}

	public boolean deleteProcessInstance(String processDefinitionKey, String businessKey, boolean cascade) {
		return commandExecutor.execute(new DeleteProcessInstanceByInstanceIdAndDefKeyCmd(processDefinitionKey, businessKey, cascade));
	}
	
	public void continueProcessInstance(String processInstanceId) {
		
		commandExecutor.execute(new ContinueProcessInstanceCmd(processInstanceId));
	}
	
	public void suspendProcessInstance(String processInstanceId) {
		
		commandExecutor.execute(new SuspendProcessInstanceCmd(processInstanceId));
	}
	
	public void terminatProcessInstance(String processInstanceId) {
		
		commandExecutor.execute(new TerminatProcessInstanceCmd(processInstanceId));
		
	}
	
	public ProcessInstanceQuery createProcessInstanceQuery() {
		return ProcessObjectFactory.FACTORYINSTANCE.createProcessInstanceQuery(commandExecutor);
	}

	public TokenQuery createTokenQuery() {
		return ProcessObjectFactory.FACTORYINSTANCE.createTokenQuery(commandExecutor);
	}

	public void updateProcessInstanceBusinessKey(String processInstanceId, String businessKey) {
		commandExecutor.execute(new UpdateBusinessKeyCmd(processInstanceId, businessKey));
	}

	public void setProcessInstanceVariable(String processInstanceId, String variableName, Object value) {
		SaveVariablesCommand saveVariablesCommand = new SaveVariablesCommand();
		saveVariablesCommand.setProcessInstanceId(processInstanceId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(variableName, value);
		saveVariablesCommand.setVariables(map);
		commandExecutor.execute(new SaveVariablesCmd(saveVariablesCommand));
	}

	public void setProcessInstanceVariables(String processInstanceId, Map<String, ? extends Object> variables) {
		SaveVariablesCommand saveVariablesCommand = new SaveVariablesCommand();
		saveVariablesCommand.setProcessInstanceId(processInstanceId);
		saveVariablesCommand.setVariables(variables);
		commandExecutor.execute(new SaveVariablesCmd(saveVariablesCommand));
	}
	
	public void deleteProcessInstanceVariable(String processInstanceId, String variableName) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(processInstanceId);
		List<String> variableNames = new ArrayList<String>();
		variableNames.add(variableName);
		queryVariablesCommand.setVariableNames(variableNames);
		commandExecutor.execute(new DeleteProcessInstanceVaribalesCmd(queryVariablesCommand));
	}
	
	public void deleteProcessInstanceVariables(String processInstanceId, List<String> variableNames) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(processInstanceId);
		queryVariablesCommand.setVariableNames(variableNames);
		commandExecutor.execute(new DeleteProcessInstanceVaribalesCmd(queryVariablesCommand));
	}
	
	public void deleteProcessInstanceVariables(String processInstanceId) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(processInstanceId);
		commandExecutor.execute(new DeleteProcessInstanceVaribalesCmd(queryVariablesCommand));
	}

	public Object getProcessInstanceVariable(String processInstanceId, String variableName) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(processInstanceId);
		List<String> variableNames = new ArrayList<String>();
		variableNames.add(variableName);
		queryVariablesCommand.setVariableNames(variableNames);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map.get(variableName);
	}

	public Map<String, Object> getProcessInstanceVariables(String processInstanceId) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(processInstanceId);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map;
	}

	public Map<String, Object> getProcessInstanceVariables(String processInstanceId, List<String> variableNames) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setProcessInstanceId(processInstanceId);
		queryVariablesCommand.setVariableNames(variableNames);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map;
	}

	public void setTokenVariable(String tokenId, String variableName, Object value) {
		SaveVariablesCommand saveVariablesCommand = new SaveVariablesCommand();
		saveVariablesCommand.setTokenId(tokenId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(variableName, value);
		saveVariablesCommand.setVariables(map);
		commandExecutor.execute(new SaveVariablesCmd(saveVariablesCommand));
	}

	public void setTokenVariables(String tokenId, Map<String, ? extends Object> variables) {
		SaveVariablesCommand saveVariablesCommand = new SaveVariablesCommand();
		saveVariablesCommand.setTokenId(tokenId);
		saveVariablesCommand.setVariables(variables);
		commandExecutor.execute(new SaveVariablesCmd(saveVariablesCommand));
	}

	public Object getTokenVariable(String tokenId, String variableName) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setTokenId(tokenId);
		List<String> variableNames = new ArrayList<String>();
		variableNames.add(variableName);
		queryVariablesCommand.setVariableNames(variableNames);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map.get(variableName);
	}

	public Map<String, Object> getTokenVariables(String tokenId) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setTokenId(tokenId);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map;
	}

	public Map<String, Object> getTokenVariables(String tokenId, List<String> variableNames) {
		QueryVariablesCommand queryVariablesCommand = new QueryVariablesCommand();
		queryVariablesCommand.setTokenId(tokenId);
		queryVariablesCommand.setVariableNames(variableNames);
		Map<String, Object> map = commandExecutor.execute(new QueryVariablesCmd<Map<String, Object>>(queryVariablesCommand));
		return map;
	}

	public EventSubscriptionQuery createEventSubscriptionQuery() {
		return new EventSubscriptionQueryImpl(commandExecutor);
	}

	public void tokenSignal(String tokenId, Map<String, Object> transientVariables) {
		commandExecutor.execute(new TokenSignalCmd(tokenId, transientVariables,null));
	}
	
	public void tokenSignal(String tokenId, Map<String, Object> transientVariables,Map<String, Object> dataVariables) {
		commandExecutor.execute(new TokenSignalCmd(tokenId, transientVariables,dataVariables));
	}
	

	public void signal(Map<String, Object> transientVariables) {
		commandExecutor.execute(new TokenSignalCmd(null, transientVariables,null));
		
	}

	public void signal(Map<String, Object> transientVariables, Map<String, Object> dataVariables) {
		commandExecutor.execute(new TokenSignalCmd(null, transientVariables,dataVariables));
		
	}


	public ProcessInstance startProcessInstanceByKey(StartProcessInstanceCommand startProcessInstanceCommand) {
		return commandExecutor.execute(new NoneStartProcessInstanceCmd<ProcessInstance>(startProcessInstanceCommand));
	}

	public ProcessInstance startProcessInstanceById(StartProcessInstanceCommand startProcessInstanceCommand) {
		return commandExecutor.execute(new NoneStartProcessInstanceCmd<ProcessInstance>(startProcessInstanceCommand));
	}

	public ProcessInstance startProcessInstanceByMessage(MessageStartProcessInstanceCommand messageStartProcessInstanceCommand) {

		return commandExecutor.execute(new MessageStartProcessInstanceCmd<ProcessInstance>(messageStartProcessInstanceCommand));
	}
	
	public void tokenTimeOut(String tokenId,Map<String, Object> transientVariables) {
		commandExecutor.execute(new TokenTimeOutCmd(tokenId,null,transientVariables));
	}

	public void tokenTimeOut(String tokenId,String nodeId,Map<String, Object> transientVariables) {
		commandExecutor.execute(new TokenTimeOutCmd(tokenId,nodeId,transientVariables));
	}

	public boolean insertPigeonholeData() {
		return true;
	}

	
	public <T> T ExpandCmd(String cmdId, Map<String, Object> parameterMap, T classReturn) {

		return commandExecutor.execute(new ExpandCommonCmd<T>(cmdId, parameterMap));

	}

	public List<TaskInstance> getNotDoneTask(ProcessInstance processInstance) {
		List<TaskInstanceEntity> taskInstanceEntities= ((ProcessInstanceEntity)processInstance).getTaskMgmtInstance().getTaskInstanceEntitys();
		List<TaskInstance> taskInstances=new ArrayList<TaskInstance>();
		for (TaskInstanceEntity taskInstanceEntity : taskInstanceEntities) {
			if(!taskInstanceEntity.hasEnded()){
				taskInstances.add(taskInstanceEntity);
			}
			
		}
		return taskInstances;
	}

	public Object executeRuleScript(String ruleScript) {

		return commandExecutor.execute(new ExecuteRuleScriptCmd<Object>(ruleScript));

	}

	public List<Map<String, Object>> GetProcessCommandByProcessInstanceId(String processInstanceId) {
		
		return commandExecutor.execute(new GetProcessCommand(processInstanceId));
	}

	public ProcessInstance getProcessInstance(String processInstanceId) {

		return commandExecutor.execute(new GetProcessInstanceCmd(processInstanceId));
	}

	



}