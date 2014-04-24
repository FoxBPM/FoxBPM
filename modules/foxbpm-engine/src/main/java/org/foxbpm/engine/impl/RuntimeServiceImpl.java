package org.foxbpm.engine.impl;

import java.util.Map;

import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.cmd.StartProcessInstanceCmd;
import org.foxbpm.engine.runtime.ProcessInstance;

public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, null, null));
	}

	public ProcessInstance startProcessInstanceByKey(
			String processDefinitionKey, String bizKey) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, bizKey, null));
	}

	public ProcessInstance startProcessInstanceByKey(
			String processDefinitionKey, Map<String, Object> variables) {
		 return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, null, variables));
	}

	public ProcessInstance startProcessInstanceByKey(
			String processDefinitionKey, String bizKey,
			Map<String, Object> variables) {
		 return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, bizKey, variables));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, null, null));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,
			String bizKey) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, bizKey, null));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,
			Map<String, Object> variables) {
		  return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, null, variables));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,
			String bizKey, Map<String, Object> variables) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, bizKey, variables));
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName,
			String bizKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName,
			Map<String, Object> processVariables) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName,
			String bizKey, Map<String, Object> processVariables) {
		// TODO Auto-generated method stub
		return null;
	}


}
