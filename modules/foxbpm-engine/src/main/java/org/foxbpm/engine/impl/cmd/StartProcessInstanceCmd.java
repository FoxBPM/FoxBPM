package org.foxbpm.engine.impl.cmd;

import java.io.Serializable;
import java.util.Map;

import org.foxbpm.engine.exception.ExceptionCode;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.runtime.ProcessInstance;

public class StartProcessInstanceCmd<T> implements Command<ProcessInstance>, Serializable {

	private static final long serialVersionUID = 1L;

	protected String processDefinitionKey;
	protected String processDefinitionId;
	protected Map<String, Object> variables;
	protected String bizKey;

	public StartProcessInstanceCmd(String processDefinitionKey, String processDefinitionId, String bizKey, Map<String, Object> variables) {
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
		this.bizKey = bizKey;
		this.variables = variables;
	}

	public ProcessInstance execute(CommandContext commandContext) {
		DeploymentManager deploymentCache = Context.getProcessEngineConfiguration().getDeploymentManager();

		// 查找流程定义
		ProcessDefinitionEntity processDefinition = null;
		if (processDefinitionId != null) {
			processDefinition = deploymentCache.findDeployedProcessDefinitionById(processDefinitionId);
			if (processDefinition == null) {
				throw new FoxBPMObjectNotFoundException(ExceptionCode.OBJECTNOTFOUNDEXCEPTION_FINDDEFINITIONBYID, processDefinitionId,
						ProcessDefinition.class);
			}
		} else if (processDefinitionKey != null) {
			processDefinition = deploymentCache.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
			if (processDefinition == null) {
				throw new FoxBPMObjectNotFoundException(ExceptionCode.OBJECTNOTFOUNDEXCEPTION_FINDDEFINITIONBYKEY, processDefinitionId,
						ProcessDefinition.class);
			}
		} else {
			throw new FoxBPMIllegalArgumentException(ExceptionCode.ILLEGALARGUMENTEXCEPTION_ISNULL,
					"processDefinitionKey、processDefinitionId");
		}

		// 如果流程定义是暂停状态则不允许启动流程实例
		if (processDefinition.isSuspended()) {
			throw new FoxBPMException("Cannot start process instance. Process definition " + processDefinition.getName() + " (id = "
					+ processDefinition.getId() + ") is suspended");
		}

		// 启动流程实例
		ProcessInstanceEntity processInstance = processDefinition.createProcessInstance(bizKey);
		if (variables != null) {
			processInstance.setVariables(variables);
		}
		processInstance.start();

		return processInstance;
	}
}
