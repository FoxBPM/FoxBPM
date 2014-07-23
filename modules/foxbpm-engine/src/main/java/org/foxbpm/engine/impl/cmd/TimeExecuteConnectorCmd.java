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
 * @author MAENLIANG
 * 
 */
package org.foxbpm.engine.impl.cmd;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * 
 * 
 * TimeExecuteConnectorCmd
 * 
 * MAENLIANG 2014年6月27日 下午3:14:43
 * 
 * @version 1.0.0
 * 
 */
public class TimeExecuteConnectorCmd implements Command<KernelListener>,
		Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -1798798267378714892L;
	/**
	 * 流程定义id，唯一编号,不能为空。(数据库中的 id)
	 */
	protected String processInstanceID;
	/**
	 * 连接器唯一ID
	 */
	protected String connectorID;
	/**
	 * 连接器所属事件，事件名称
	 */
	protected String eventName;
	/**
	 * 当前令牌ID
	 */
	protected String tokenID;
	/**
	 * 连接器所属节点的,节点ID
	 */
	protected String nodeID;

	/**
	 * 当前分派任务的ID
	 */
	protected String taskID;

	@Override
	public KernelListener execute(CommandContext commandContext) {
		ProcessInstanceEntity processInstance = commandContext
				.getProcessInstanceManager().findProcessInstanceById(
						processInstanceID);
		KernelProcessDefinitionImpl processDefinition = processInstance
				.getProcessDefinition();

		TokenEntity tokenEntity = (TokenEntity) this
				.getExecutionContextFromInstance(processInstance);
		tokenEntity.setAssignTask(this.getTaskEntity(tokenEntity));

		// 获取当前任务记录的CONNECTOR
		Connector connector = this
				.getListenersFromDefinition(processDefinition);
		if (connector == null) {
			throw new FoxBPMException(
					"TimeExecuteConnectorCmd自动执行连接器时候,连接器无法获取");
		}
		try {
			connector.execute(tokenEntity);
		} catch (Exception e) {
			throw new FoxBPMException("TimeExecuteConnectorCmd自动执行连接器时候报异常", e);
		}
		return null;
	}

	private TaskEntity getTaskEntity(TokenEntity tokenEntity) {
		List<TaskEntity> tasks = tokenEntity.getTasks();
		Iterator<TaskEntity> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			TaskEntity next = iterator.next();
			if (StringUtils.equalsIgnoreCase(this.taskID, next.getId())) {
				return next;
			}
		}
		return null;
	}

	/**
	 * 
	 * getListenersFromDefinition(从特定节点获取) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param processDefinition
	 * @return Connector
	 * @since 1.0.0
	 */
	private Connector getListenersFromDefinition(
			KernelProcessDefinitionImpl processDefinition) {
		List<KernelFlowNodeImpl> flowNodes = processDefinition.getFlowNodes();
		Iterator<KernelFlowNodeImpl> iterator = flowNodes.iterator();
		while (iterator.hasNext()) {
			KernelFlowNodeImpl next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), this.nodeID)) {

				Map<String, List<KernelListener>> kernelListeners = next
						.getKernelListeners();
				List<KernelListener> list = kernelListeners.get(this.eventName);
				if (list != null && list.size() > 0) {
					Iterator<KernelListener> connectorIter = list.iterator();
					while (connectorIter.hasNext()) {
						KernelListener listener = connectorIter.next();
						if (listener instanceof Connector) {
							Connector tempConnector = (Connector) listener;
							if (StringUtils.equalsIgnoreCase(
									tempConnector.getConnectorId(),
									this.connectorID)) {
								return tempConnector;
							}
						}

					}
				}
			}
		}
		return null;
	}

	public void setConnectorID(String connectorID) {
		this.connectorID = connectorID;
	}

	public void setProcessInstanceID(String processInstanceID) {
		this.processInstanceID = processInstanceID;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	/**
	 * 
	 * getExecutionContextFromInstance(根据记录的tokenID获取ListenerExecutionContext)
	 * 
	 * @param processInstance
	 * @return ListenerExecutionContext
	 * 
	 * @since 1.0.0
	 */
	private KernelTokenImpl getExecutionContextFromInstance(
			ProcessInstanceEntity processInstance) {
		List<KernelTokenImpl> tokens = processInstance.getTokens();
		Iterator<KernelTokenImpl> iterator = tokens.iterator();
		while (iterator.hasNext()) {
			KernelTokenImpl next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), this.tokenID)) {
				return next;
			}
		}
		return null;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
}
