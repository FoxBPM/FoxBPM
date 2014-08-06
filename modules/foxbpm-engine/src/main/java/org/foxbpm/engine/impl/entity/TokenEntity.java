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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class TokenEntity extends KernelTokenImpl
		implements
			Token,
			ConnectorExecutionContext,
			PersistentObject,
			HasRevision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String processInstanceId;
	protected String nodeId;
	protected String parentId;
	protected Date startTime;
	protected Date endTime;
	

	protected Date nodeEnterTime;
	protected Date archiveTime;

	protected List<TaskEntity> tasks;

	protected TaskEntity assignTask;
	
	/** 临时指定分配任务的处理者 */
	protected String taskAssignee;

	

	protected String groupID;

	/** 流程定义唯一版本编号 */
	protected String processDefinitionId;

	/** 流程定义编号 */
	protected String processDefinitionKey;

	@Override
	public void setFlowNode(KernelFlowNodeImpl flowNode) {
		if (flowNode != null) {
			setNodeId(flowNode.getId());
		}

		super.setFlowNode(flowNode);
	}

	@Override
	protected void ensureFlowNodeInitialized() {
		if ((currentFlowNode == null) && (nodeId != null)) {
			currentFlowNode = getProcessDefinition().findFlowNode(nodeId);
		}
	}

	@Override
	protected void ensureParentInitialized() {
		if (this.parent == null && StringUtil.isNotBlank(this.parentId)) {
			this.parent = Context.getCommandContext().getTokenManager()
					.findTokenById(this.parentId);
		}
	}
	protected void ensureChildrenInitialized() {
		if (this.children.size() == 0 && StringUtil.isNotBlank(this.processInstanceId)) {
			List<TokenEntity> listResult = Context.getCommandContext().getTokenManager()
					.findChildTokensByProcessInstanceId(this.processInstanceId);
			Iterator<TokenEntity> iterator = listResult.iterator();
			while (iterator.hasNext()) {
				TokenEntity next = iterator.next();
				if (StringUtil.equals(next.getParentId(), this.id)) {
					children.add(next);
				}
			}

		}
	}
	@Override
	protected void ensureProcessInstanceInitialized() {
		if ((processInstance == null) && (processInstanceId != null)) {
			processInstance = Context.getCommandContext().getProcessInstanceManager()
					.findProcessInstanceById(processInstanceId);
		}
	}

	@Override
	public void setProcessInstance(KernelProcessInstanceImpl processInstance) {
		setProcessInstanceId(processInstance.getId());
		super.setProcessInstance(processInstance);
	}

	@Override
	public void setParent(KernelTokenImpl parent) {
		setParentId(parent.getId());
		super.setParent(parent);
	}

	@Override
	public void ensureEnterInitialized(KernelFlowNodeImpl flowNode) {
		/** 设置令牌进入节点的时间 */
		setNodeEnterTime(ClockUtil.getCurrentTime());
		super.ensureEnterInitialized(flowNode);
	}

	@Override
	public void clearExecutionContextData() {
		super.clearExecutionContextData();
	}

	@Override
	public String getId() {
		return this.id;
	}

	public void setRevision(int revision) {

	}

	public int getRevision() {
		return 0;
	}

	public int getRevisionNext() {
		return 0;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	

	@Override
	public boolean isEnded() {
		if(endTime==null){
			return false;
		}
		return super.isEnded();
	}

	public Date getNodeEnterTime() {
		return nodeEnterTime;
	}

	public void setNodeEnterTime(Date nodeEnterTime) {
		this.nodeEnterTime = nodeEnterTime;
	}

	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	public Map<String, Object> getPersistentState() {

		Map<String, Object> objectParam = new HashMap<String, Object>();
		objectParam.put("tokenId", getId());
		objectParam.put("name", getName());
		objectParam.put("startTime", getStartTime());
		objectParam.put("endTime", getEndTime());
		objectParam.put("nodeEnterTime", getNodeEnterTime());
		objectParam.put("isSuspended", String.valueOf(isSuspended()));
		objectParam.put("isLocked", String.valueOf(isLocked()));
		objectParam.put("nodeId", getNodeId());
		objectParam.put("processInstanceId", getProcessInstanceId());
		objectParam.put("parentId", getParentId());

		objectParam.put("processDefinitionId", getProcessDefinitionId());

		objectParam.put("processDefinitionKey", getProcessDefinitionKey());

		return objectParam;
	}

	public boolean isModified() {
		return true;
	}

	@Override
	public ProcessInstanceEntity getProcessInstance() {
		return (ProcessInstanceEntity) super.getProcessInstance();
	}

	public Object getVariableLocal(Object variableName) {
		return null;
	}

	public String getInitiator() {
		return getProcessInstance().getInitiator();
	}

	public String getAuthenticatedUserId() {
		return Authentication.getAuthenticatedUserId();
	}

	public String getStartAuthor() {
		return getProcessInstance().getStartAuthor();
	}

	public TaskEntity getAssignTask() {
		return assignTask;
	}

	public void setAssignTask(TaskEntity assignTask) {
		this.assignTask = assignTask;
	}

	// 任务对象
	// ///////////////////////////////////////////////////

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void ensureTasksInitialized() {
		if (tasks == null) {
			tasks = (List) Context.getCommandContext().getTaskManager().findTasksByTokenId(id);
		}
	}

	protected List<TaskEntity> getTasksInternal() {
		ensureTasksInitialized();
		return tasks;
	}

	public List<TaskEntity> getTasks() {
		return new ArrayList<TaskEntity>(getTasksInternal());
	}

	public void addTask(TaskEntity taskEntity) {
		getTasksInternal().add(taskEntity);
	}

	public void removeTask(TaskEntity task) {
		getTasksInternal().remove(task);
	}

	public void setProcessInstanceVariables(Map<String, Object> transientVariables) {
		if (transientVariables == null) {
			return;
		}

		for (String mapKey : transientVariables.keySet()) {
			ExpressionMgmt.setVariable(mapKey, transientVariables.get(mapKey));
		}
	}

	@Override
	public void end(boolean verifyParentTermination) {

		endTime = ClockUtil.getCurrentTime();
		super.end(verifyParentTermination);
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	@Override
	protected boolean isSignalParentToken() {
		if (isSubProcessRootToken) {
			if (!getParent().isEnded()) {
				return true;
			}
		}
		return super.isSignalParentToken();
	}
	
	
	

	@Override
	public void setEnded(boolean isEnded) {
		if(isEnded){
			this.endTime=ClockUtil.getCurrentTime();
		}else{
			this.endTime=null;
		}
		super.setEnded(isEnded);
		
	}
	
	public void setEndTime(Date endTime) {
		if(endTime!=null){
			super.setEnded(true);
		}else{
			super.setEnded(false);
		}
		this.endTime = endTime;
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

	@Override
	public KernelProcessInstance createSubProcessInstance(KernelProcessDefinition processDefinition) {

		return super.createSubProcessInstance(processDefinition);
	}

	public String getTaskAssignee() {
		return taskAssignee;
	}

	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
}
