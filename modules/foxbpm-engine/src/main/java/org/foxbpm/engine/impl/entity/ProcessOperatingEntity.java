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
package org.foxbpm.engine.impl.entity;

import java.util.Date;
import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.operating.ProcessOperating;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.process.KernelFlowNode;

public class ProcessOperatingEntity  implements ProcessOperating,PersistentObject,HasRevision {
	

	protected String id;	
	
	protected String processInstanceId;
	
	protected String processDefinitionId;
	
	protected String processDefinitionKey;
		
	protected String processDefinitionName;
	
	protected String taskId;
	
	protected String tokenId;
	
	protected String nodeId;
	
	protected String nodeName;
	
	protected String commandId;
	
	protected String commandType;
	
	protected String commandMessage;
	
	protected String operatingComment;
	
	protected Date operatingTime;
	
	protected String operator;
	


	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getCommandMessage() {
		return commandMessage;
	}

	public void setCommandMessage(String commandMessage) {
		this.commandMessage = commandMessage;
	}

	public String getOperatingComment() {
		return operatingComment;
	}

	public void setOperatingComment(String operatingComment) {
		this.operatingComment = operatingComment;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public void setRevision(int revision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRevision() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRevisionNext() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void setTaskCommand(TaskCommand taskCommand) {
		if (taskCommand != null) {
			// 设置任务上点击的处理命令
			setCommandId(taskCommand.getId());
			// 设置任务上点击的处理命令类型
			setCommandType(taskCommand.getTaskCommandType());
			// 设置任务上点击的处理命令文本
			setCommandMessage(taskCommand.getName());
		}
	}
	
	public void setProcessDefinition(ProcessDefinitionEntity processDefinition) {
		if (processDefinition != null) {
			processDefinitionId = processDefinition.getId();
			processDefinitionKey = processDefinition.getKey();
			processDefinitionName = processDefinition.getName();
		}
	}
	
	
	public void setNode(KernelFlowNode node) {
		setNodeId(node.getId());
		setNodeName(node.getName());
	}
	
	public void setToken(TokenEntity token) {
		setTokenId(token.getId());
		setProcessInstance(token.getProcessInstance());
		setNode(token.getFlowNode());
	}
	
	public void setProcessInstance(ProcessInstanceEntity processInstance) {
		setProcessInstanceId(processInstance.getId());
		setProcessDefinition((ProcessDefinitionEntity) processInstance.getProcessDefinition());
	}
	
	public void setTask(TaskEntity task) {
		setTaskId(task.getId());
		setProcessDefinition(task.getProcessDefinition());
		setToken(task.getToken());
	}
}
