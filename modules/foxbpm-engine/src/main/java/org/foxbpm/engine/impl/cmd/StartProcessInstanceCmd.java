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
 * @author ych
 */
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
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.runtime.ProcessInstance;

public class StartProcessInstanceCmd<T> implements Command<ProcessInstance>, Serializable {

	private static final long serialVersionUID = 1L;

	protected String processDefinitionKey;
	protected String processDefinitionId;
	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;
	protected String bizKey;

	public StartProcessInstanceCmd(String processDefinitionKey, String processDefinitionId, String bizKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionId = processDefinitionId;
		this.bizKey = bizKey;
		this.transientVariables = transientVariables;
		this.persistenceVariables = persistenceVariables;
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
			throw new FoxBPMException("启动失败：流程定义 " + processDefinition.getName() + " (id = "
					+ processDefinition.getId() + ") 为暂停状态");
		}

		// 启动流程实例
		ProcessInstanceEntity processInstance = processDefinition.createProcessInstance(bizKey);
		if (transientVariables != null) {
			processInstance.setVariables(transientVariables);
		}
		String initiator = Authentication.getAuthenticatedUserId();
		processInstance.setInitiator(initiator);
		processInstance.start();

		return processInstance;
	}
}
