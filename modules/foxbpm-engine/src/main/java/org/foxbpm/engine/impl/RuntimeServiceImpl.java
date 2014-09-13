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
package org.foxbpm.engine.impl;

import java.util.Map;

import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.cmd.BoundaryTimeSignalCmd;
import org.foxbpm.engine.impl.cmd.DeleteProcessInstanceByIdCmd;
import org.foxbpm.engine.impl.cmd.SignalCmd;
import org.foxbpm.engine.impl.cmd.StartProcessInstanceCmd;
import org.foxbpm.engine.impl.cmd.TimeExecuteConnectorCmd;
import org.foxbpm.engine.impl.datavariable.VariableQueryImpl;
import org.foxbpm.engine.impl.runningtrack.RunningTrackQueryImpl;
import org.foxbpm.engine.impl.runtime.ProcessInstanceQueryImpl;
import org.foxbpm.engine.impl.runtime.TokenQueryImpl;
import org.foxbpm.engine.runningtrack.RunningTrackQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.runtime.TokenQuery;

/**
 * runTimeService的具体实现
 * 
 * @author kenshin
 * 
 */
public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, null, null, null));
	}

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String bizKey) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, bizKey, null, null));
	}

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, null, transientVariables, persistenceVariables));
	}

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String bizKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, bizKey, transientVariables, persistenceVariables));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, null, null, null));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId, String bizKey) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, bizKey, null, null));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, null, transientVariables, persistenceVariables));
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId, String bizKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		return commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(null, processDefinitionId, bizKey, transientVariables, persistenceVariables));
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName) {
		throw new FoxBPMBizException("功能尚未完成");
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName, String bizKey) {
		throw new FoxBPMBizException("功能尚未完成");
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName, Map<String, Object> processVariables) {
		throw new FoxBPMBizException("功能尚未完成");
	}

	public ProcessInstance startProcessInstanceByMessage(String messageName, String bizKey, Map<String, Object> processVariables) {
		throw new FoxBPMBizException("功能尚未完成");
	}

	public void signal(String executionId) {
		commandExecutor.execute(new SignalCmd(executionId, null, null, null, null));
	}

	public void signal(String executionId, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		commandExecutor.execute(new SignalCmd(executionId, null, null, transientVariables, persistenceVariables));
	}

	public TokenQuery createTokenQuery() {
		return new TokenQueryImpl(commandExecutor);
	}

	public ProcessInstanceQuery createProcessInstanceQuery() {
		return new ProcessInstanceQueryImpl(commandExecutor);
	}

	@Override
	public VariableQuery createVariableQuery() {
		return new VariableQueryImpl(commandExecutor);
	}

	@Override
	public RunningTrackQuery createRunningTrackQuery() {
		return new RunningTrackQueryImpl(commandExecutor);
	}

	/**
	 * 边界事件定时器启动推动令牌
	 * 
	 * @param tokenId
	 *            令牌编号
	 * @param nodeId
	 *            触发的边界事件节点号
	 * @param isCancelActivity
	 *            是否中断
	 * @param transientVariables
	 *            瞬态变量
	 */
	public void boundaryTimeSignal(String tokenId, String nodeId, boolean isCancelActivity, Map<String, Object> transientVariables) {
		commandExecutor.execute(new BoundaryTimeSignalCmd(tokenId, nodeId, isCancelActivity, transientVariables, null));
	}

	/**
	 * 边界事件定时器启动推动令牌
	 * 
	 * @param tokenId
	 *            令牌编号
	 * @param nodeId
	 *            触发的边界事件节点号
	 * @param isCancelActivity
	 *            是否中断
	 * @param transientVariables
	 *            瞬态变量
	 * @param persistenceVariables
	 *            持久化变量
	 */
	public void boundaryTimeSignal(String tokenId, String nodeId, boolean isCancelActivity, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		commandExecutor.execute(new BoundaryTimeSignalCmd(tokenId, nodeId, isCancelActivity, transientVariables, persistenceVariables));
	}

	/**
	 * 
	 * autoExecuteConnector(调度器执行 Connector方法)
	 * 
	 * @param processInstanceID
	 *            流程实例ID
	 * @param connectorID
	 *            连接器ID
	 * @param eventName
	 *            事件名称
	 * @param tokenID
	 *            令牌ID
	 * @param nodeID
	 *            节点ID void
	 * @exception
	 * @since 1.0.0
	 */
	public void autoExecuteConnector(String processInstanceID, String connectorID, String eventName, String tokenID, String nodeID) {
		commandExecutor.execute(new TimeExecuteConnectorCmd(processInstanceID, connectorID, eventName, tokenID, nodeID));
	}

	/**
	 * 
	 * autoStartProcessInstance(调度器启动流程实例)
	 * 
	 * @param processDefinitionKey
	 *            流程定义KEY
	 * @param processDefinitionId
	 *            流程定义ID
	 * @param bizKey
	 *            业务关联键
	 * @param transientVariables
	 *            瞬时变量
	 * @param persistenceVariables
	 *            持久变量 void
	 * @exception
	 * @since 1.0.0
	 */
	public void autoStartProcessInstance(String processDefinitionKey, String processDefinitionId, String bizKey, Map<String, Object> transientVariables, Map<String, Object> persistenceVariables) {
		this.commandExecutor.execute(new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, processDefinitionId, bizKey, transientVariables, persistenceVariables));
	}

	@Override
	public void deleteProcessInstance(String processInstanceId) {
		this.commandExecutor.execute(new DeleteProcessInstanceByIdCmd(processInstanceId));
	}
	
	@Override
	public Class<?> getInterfaceClass() {
		return RuntimeService.class;
	}
}
