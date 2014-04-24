package org.foxbpm.engine.impl;

import java.util.Map;

import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.cmd.GetStartProcessByUserIdCmd;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.runtime.ProcessInstance;

public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceByKey(
			String processDefinitionKey, String bizKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceByKey(
			String processDefinitionKey, Map<String, Object> variables) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceByKey(
			String processDefinitionKey, String bizKey,
			Map<String, Object> variables) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,
			String bizKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,
			Map<String, Object> variables) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,
			String bizKey, Map<String, Object> variables) {
		// TODO Auto-generated method stub
		return null;
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
