package org.foxbpm.engine.impl.entity;

import java.util.Date;
import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.task.DelegationState;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskType;
import org.foxbpm.kernel.runtime.impl.KernelVariableScopeImpl;

public class TaskEntity extends KernelVariableScopeImpl implements Task, PersistentObject, HasRevision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String name;

	protected String description;

	protected String processInstanceId;

	protected String processDefinitionId;

	protected String processDefinitionKey;

	protected String processDefinitionName;

	protected int version;

	protected String tokenId;

	protected String nodeId;

	protected String nodeName;

	protected String parentId;

	protected String assignee;

	protected Date claimTime;

	protected Date createTime;

	protected Date startTime;

	protected Date endTime;

	protected Date dueDate;

	protected boolean isBlocking = false;

	protected int priority = Task.PRIORITY_NORMAL;

	protected String category;

	protected String owner;

	protected DelegationState delegationState;

	protected String bizKey;

	protected String taskComment;

	protected String formUri;

	protected String formUriView;

	protected String taskGroup;

	protected String taskType = TaskType.FIXFLOWTASK;

	protected boolean isCancelled = false;

	protected boolean isSuspended = false;

	protected boolean isOpen = true;

	protected boolean isDraft = false;

	protected int expectedExecutionTime = 0;

	protected String agent;

	protected String admin;

	protected String callActivityInstanceId;

	protected String pendingTaskId;

	protected Date archiveTime;

	protected String commandId;

	protected String commandType;

	protected String commandMessage;
	
	
	
	

	public DelegationState getDelegationState() {
		return delegationState;
	}

	public void setDelegationState(DelegationState delegationState) {
		this.delegationState = delegationState;
	}

	public String getDelegationStateString() {
		return (delegationState != null ? delegationState.toString() : null);
	}

	public void setDelegationStateString(String delegationStateString) {
		this.delegationState = (delegationStateString != null ? 
				DelegationState.valueOf(DelegationState.class, delegationStateString)
				: null);
	}

	public void setRevision(int revision) {
		// TODO Auto-generated method stub

	}

	public int getRevision() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getRevisionNext() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KernelVariableScopeImpl getParentVariableScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void ensureParentInitialized() {
		// TODO Auto-generated method stub

	}

	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

}
