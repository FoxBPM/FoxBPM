/**
 * Copyright 1996-2013 Founder International Co.,Ltd.
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

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.kernel.event.KernelEvent;

public class TimeStartProcessInstanceCmd<T> implements
		Command<ProcessInstance>, Serializable {
	private static final long serialVersionUID = -4054848927162120048L;

	/**
	 * 流程定义id，唯一编号,不能为空。(数据库中的 id)
	 */
	protected String processDefinitionId;

	/**
	 * 流程定义key(xml定义里的 process id,数据库中的 key)
	 */
	protected String processDefinitionKey;

	/**
	 * 业务关联键
	 */
	protected String businessKey;

	/**
	 * 启动
	 */
	protected String startAuthor;

	/**
	 * 瞬态流程实例变量Map
	 */
	protected Map<String, Object> transientVariables = null;

	/**
	 * 持久化流程实例变量Map
	 */
	protected Map<String, Object> variables = null;

	/**
	 * 定时启动的节点
	 */
	protected String nodeId;

	/**
	 * 流程实例启动操作
	 * 
	 * @param processDefinitionKey
	 * @param processDefinitionId
	 * @param businessKey
	 * @param startAuthor
	 * @param transientVariables
	 * @param variables
	 */
	public TimeStartProcessInstanceCmd() {
	}

	public ProcessInstance execute(CommandContext commandContext) {
		DeploymentManager deploymentCache = Context
				.getProcessEngineConfiguration().getDeploymentManager();
		ProcessDefinitionEntity processDefinition = null;
		if (StringUtils.isNotBlank(processDefinitionId)) {
			processDefinition = deploymentCache
					.findDeployedProcessDefinitionById(processDefinitionId);
			if (processDefinition == null) {
				throw new FoxBPMException("通过 processDefinitionId 没有找到指定流程 = '"
						+ processDefinitionId + "'");
			}
		} else if (StringUtils.isNotBlank(processDefinitionKey)) {
			processDefinition = deploymentCache
					.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
			if (processDefinition == null) {
				throw new FoxBPMException("通过 processDefinitionKey 没有找到指定流程 '"
						+ processDefinitionKey + "'");
			}
		} else {
			throw new FoxBPMException(
					"processDefinitionKey 和 processDefinitionId 不能都为空");
		}

		// 如果流程定义是暂停状态则不允许启动流程实例
		if (processDefinition.isSuspended()) {
			throw new FoxBPMException("启动失败：流程定义 "
					+ processDefinition.getName() + " (id = "
					+ processDefinition.getId() + ") 为暂停状态");
		}

		// 启动流程实例
		ProcessInstanceEntity processInstance = processDefinition
				.createProcessInstance(this.businessKey);
		if (transientVariables != null) {
			processInstance.setVariables(transientVariables);
		}
		processInstance.start();
		return processInstance;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStartAuthor() {
		return startAuthor;
	}

	public void setStartAuthor(String startAuthor) {
		this.startAuthor = startAuthor;
	}

	public Map<String, Object> getTransientVariables() {
		return transientVariables;
	}

	public void setTransientVariables(Map<String, Object> transientVariables) {
		this.transientVariables = transientVariables;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
