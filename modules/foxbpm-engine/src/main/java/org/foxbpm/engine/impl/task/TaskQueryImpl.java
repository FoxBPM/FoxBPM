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
package org.foxbpm.engine.impl.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foxbpm.engine.Constant;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.agent.AgentTo;
import org.foxbpm.engine.impl.entity.GroupEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.query.AbstractQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;

/**
 * @author kenshin
 */
public class TaskQueryImpl extends AbstractQuery<TaskQuery, Task> implements
		TaskQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String taskId;
	protected String name;
	protected String nameLike;
	protected String description;
	protected String descriptionLike;
	protected String assignee;
	protected String owner;
	//默认查询所有
	protected int assignedFlag = 2;
	protected String candidateUser;
	protected String end;
	protected String businessKey;
	protected String businessKeyLike;
	protected boolean isAgent = false;
	protected String agentId;
	protected String nodeId;
	protected String processInstanceId;
	protected Date createTime;
	protected Date createTimeBefore;
	protected Date createTimeAfter;
	protected Date dueDate;
	protected Date dueDateBefore;
	protected Date dueDateAfter;
	
	protected Date endTime;
	protected Date endTimeBefore;
	protected Date endTimeAfter;
	protected String processDefinitionKey;
	protected String processDefinitionId;
	protected String processDefinitionName;
	protected String processDefinitionNameLike;
	protected String subject;
	protected String subjectLike;
	protected String initiator;
	protected String isSuspended;
	protected String tokenId;
	
	// 查询代理任务时，用来存放原始任务处理人
	protected String oldAssigneeId;
	protected List<String> taskTypeList = new ArrayList<String>();

	public TaskQueryImpl() {
	}

	public TaskQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public TaskQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	public TaskQueryImpl taskId(String taskId) {
		if (taskId == null) {
			throw new FoxBPMIllegalArgumentException("Task id is null");
		}
		this.taskId = taskId;
		return this;
	}

	public TaskQuery nodeId(String nodeId) {
		this.nodeId = nodeId;
		return this;
	}

	public TaskQueryImpl taskName(String name) {
		this.name = name;
		return this;
	}

	public TaskQueryImpl taskNameLike(String nameLike) {
		if (nameLike == null) {
			throw new FoxBPMIllegalArgumentException("Task namelike is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	// isAgent
	/**
	 * 查询agentId代理给当前用户的所有流程， 如果存在"foxbpm_all_flow" 或者不是代理状态,则返回空的list；
	 * 
	 * @return
	 */
	public List<String> getAgentProcessKey() {
		List<String> processKeys = new ArrayList<String>();
		if (!this.isAgent) {
			return processKeys;
		}
		UserEntity user = Authentication.selectUserByUserId(this.oldAssigneeId);
		if (user == null) {
			throw new FoxBPMException("未找到userid为{}的代理人信息！", oldAssigneeId);
		}
		List<AgentTo> agentInfo = user.getAgentInfo();
		Date nowDate = new Date();
		if (agentInfo != null) {
			for (AgentTo agent : agentInfo) {
				if (agent.getAgentFrom().equals(this.agentId)
						&& (agent.getEndTime().after(nowDate))) {
					// 如果存在foxbpm_all_flow，则直接清空所有key，并中断循环,返回空List
					if (agent.getProcessKey().equals(Constant.FOXBPM_ALL_FLOW)) {
						processKeys.clear();
						break;
					}
					processKeys.add(agent.getProcessKey());
				}
			}
		}
		return processKeys;
	}

	public TaskQueryImpl isAgent(boolean isAgent) {
		this.isAgent = isAgent;
		return this;
	}

	/**
	 * 逻辑： 使用方法
	 * 
	 * @param agentId
	 * @return
	 */
	public TaskQuery agentId(String agentId) {
		if (this.assignee == null && this.candidateUser == null) {
			throw new FoxBPMBizException(
					"agentId()方法必须要在assignee()方法或candidateUser()方法之后调用");
		}
		if (this.assignee != null) {
			this.oldAssigneeId = this.assignee;
			this.assignee = agentId;
		}
		if (this.candidateUser != null) {
			if (this.oldAssigneeId == null) {
				this.oldAssigneeId = this.candidateUser;
			}
			this.candidateUser = agentId;
		}
		this.agentId = agentId;
		return this;
	}

	public TaskQuery isSuspended(boolean isSuspended) {
		this.isSuspended = String.valueOf(isSuspended);
		return this;
	}

	public TaskQuery tokenId(String tokenId) {
		this.tokenId = tokenId;
		return this;
	}

	public TaskQueryImpl businessKey(String businessKey) {
		if (businessKey == null) {
			throw new FoxBPMIllegalArgumentException("businessKey is null!");
		}
		this.businessKey = businessKey;
		return this;
	}

	public TaskQueryImpl businessKeyLike(String businessKey) {
		if (businessKey == null) {
			throw new FoxBPMIllegalArgumentException("businessKeyLike is null!");
		}
		this.businessKeyLike = businessKey;
		return this;
	}

	public TaskQueryImpl addTaskType(String taskInstanceType) {

		if (taskInstanceType == null) {
			throw new FoxBPMIllegalArgumentException("TaskType is null");
		}
		for (String taskInstanceTypeObj : taskTypeList) {
			if (taskInstanceType.equals(taskInstanceTypeObj)) {
				// 如果已存在，则直接返回
				return this;
			}
		}
		this.taskTypeList.add(taskInstanceType);
		return this;
	}

	public TaskQueryImpl taskDescription(String description) {
		if (description == null) {
			throw new FoxBPMIllegalArgumentException("Description is null");
		}
		this.description = description;
		return this;
	}

	public TaskQuery taskDescriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new FoxBPMIllegalArgumentException("Descriptionlike is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	 
	public TaskQuery taskSubject(String subject) {
		if (subject == null) {
			throw new FoxBPMIllegalArgumentException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	 
	public TaskQuery taskSubjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new FoxBPMIllegalArgumentException("subjectLike is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public TaskQueryImpl taskAssignee(String assignee) {
		if (assignee == null) {
			throw new FoxBPMIllegalArgumentException("Assignee is null");
		}
		if (this.agentId != null) {
			throw new FoxBPMBizException("请在agentId()方法之前调用此方法！");
		}
		this.assignee = assignee;
		return this;
	}

	public TaskQueryImpl taskOwner(String owner) {
		if (owner == null) {
			throw new FoxBPMIllegalArgumentException("Owner is null");
		}
		this.owner = owner;
		return this;
	}

	public TaskQuery taskUnnassigned() {
		this.assignedFlag = 0;
		return this;
	}
	public TaskQuery taskAssigned() {
		this.assignedFlag = 1;
		return this;
	}
	
	public TaskQuery ignorTaskAssigned() {
		this.assignedFlag = 2;
		return this;
	}
	
	

	public TaskQueryImpl taskCandidateUser(String candidateUser) {
		if (candidateUser == null) {
			throw new FoxBPMIllegalArgumentException("candidateUser  is null!");
		}
		if (this.agentId != null) {
			throw new FoxBPMBizException("请在agentId()方法之前调用此方法！");
		}
		this.candidateUser = candidateUser;
		return this;
	}

	public TaskQueryImpl processInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
		return this;
	}

	public TaskQueryImpl taskCreatedOn(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public TaskQuery taskCreatedBefore(Date before) {
		this.createTimeBefore = before;
		return this;
	}

	public TaskQuery taskCreatedAfter(Date after) {
		this.createTimeAfter = after;
		return this;
	}
	
	 
	public TaskQuery taskDueDateOn(Date dueDate) {
		this.dueDate = dueDate;
		return this;
	}
	
	 
	public TaskQuery taskDueDateBefore(Date before) {
		this.dueDateBefore = before;
		return this;
	}
	
	 
	public TaskQuery taskDueDateAfter(Date after) {
		this.dueDateAfter = after;
		return this;
	}
	
	 
	public TaskQuery taskEndTimeOn(Date endTime) {
		this.endTime = endTime;
		return this;
	}
	
	 
	public TaskQuery taskEndTimeBefore(Date before) {
		this.endTimeBefore = before;
		return this;
	}
	
	 
	public TaskQuery taskEndTimeAfter(Date after) {
		this.endTimeAfter = after;
		return this;
	}
	
	public TaskQuery processDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
		return this;
	}

	public TaskQuery processDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
		return this;
	}

	public TaskQuery processDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
		return this;
	}

	public TaskQuery processDefinitionNameLike(String processDefinitionLike) {
		this.processDefinitionNameLike = processDefinitionLike;
		return this;
	}

	public List<GroupEntity> getCandidateGroups() {
		if (candidateUser != null) {
			return getGroupsForCandidateUser(candidateUser);
		}
		return null;
	}

	protected List<GroupEntity> getGroupsForCandidateUser(String candidateUser) {
		return Authentication.selectGroupByUserId(candidateUser);
	}

	// ordering ////////////////////////////////////////////////////////////////

	public TaskQuery orderByTaskId() {
		return orderBy(TaskQueryProperty.TASK_ID);
	}

	public TaskQuery orderByTaskName() {
		return orderBy(TaskQueryProperty.NAME);
	}

	public TaskQuery orderByTaskDescription() {
		return orderBy(TaskQueryProperty.DESCRIPTION);
	}

	public TaskQuery orderByTaskPriority() {
		return orderBy(TaskQueryProperty.PRIORITY);
	}

	public TaskQuery orderByProcessInstanceId() {
		return orderBy(TaskQueryProperty.PROCESS_INSTANCE_ID);
	}

	public TaskQuery orderByTaskAssignee() {
		return orderBy(TaskQueryProperty.ASSIGNEE);
	}

	public TaskQuery orderByTaskCreateTime() {
		return orderBy(TaskQueryProperty.CREATE_TIME);
	}

	public TaskQuery orderByDueDate() {
		return orderBy(TaskQueryProperty.DUE_DATE);
	}

	public TaskQuery orderByEndTime() {
		return orderBy(TaskQueryProperty.END_TIME);
	}

	// results ////////////////////////////////////////////////////////////////

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Task> executeList(CommandContext commandContext) {
		// ensureVariablesInitialized();
		checkQueryOk();
		return (List) commandContext.getTaskManager().findTasksByQueryCriteria(
				this);
	}

	public long executeCount(CommandContext commandContext) {
		// ensureVariablesInitialized();
		checkQueryOk();
		return commandContext.getTaskManager().findTaskCountByQueryCriteria(
				this);
	}

	// getters ////////////////////////////////////////////////////////////////

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public String getAssignee() {
		return assignee;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public String getBusinessKeyLike() {
		return businessKeyLike;
	}

	public String getOwner() {
		return owner;
	}

	public int getUnassigned() {
		return assignedFlag;
	}

	public String getCandidateUser() {
		return candidateUser;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionLike() {
		return descriptionLike;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getCreateTimeBefore() {
		return createTimeBefore;
	}

	public Date getCreateTimeAfter() {
		return createTimeAfter;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public String getProcessDefinitionNameLike() {
		return processDefinitionNameLike;
	}

	public List<String> getTaskTypeList() {
		return taskTypeList;
	}

	public String getEnd() {
		return end;
	}

	public TaskQuery taskIsEnd() {
		this.end = " not null ";
		return this;
	}

	public TaskQuery taskNotEnd() {
		this.end = " null ";
		return this;
	}

	public TaskQuery initiator(String initiator) {
		this.initiator = initiator;
		return this;
	}

	public String getInitiator() {
		return initiator;
	}

	public boolean getIsAgent() {
		return isAgent;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getIsSuspended() {
		return isSuspended;
	}

	/* 变量查询 */
	protected String taskVariableKey;
	protected String taskVariableValue;
	protected boolean taskVariableValueIsLike;

	protected String processInstanceVariableKey;
	protected String processInstanceVariableValue;
	protected boolean processInstanceVariableValueIsLike;

	public TaskQuery variableData(String variableValue, boolean isLike) {
		this.taskVariableValue = variableValue;
		this.taskVariableValueIsLike = isLike;
		return this;
	}

	public TaskQuery variableData(String variableKey, String variableValue,
			boolean isLike) {
		this.taskVariableValue = variableValue;
		this.taskVariableValueIsLike = isLike;
		this.taskVariableKey = variableKey;

		return this;
	}

	public TaskQuery processInstanceVariableData(String variableValue,
			boolean isLike) {
		this.processInstanceVariableValue = variableValue;
		this.processInstanceVariableValueIsLike = isLike;
		return this;
	}

	public TaskQuery processInstanceVariableData(String variableKey,
			String variableValue, boolean isLike) {
		this.processInstanceVariableValue = variableValue;
		this.processInstanceVariableValueIsLike = isLike;
		this.processInstanceVariableKey = variableKey;
		return this;
	}

	public String getTaskVariableKey() {
		return taskVariableKey;
	}

	public String getTaskVariableValue() {
		return taskVariableValue;
	}

	public boolean isTaskVariableValueIsLike() {
		return taskVariableValueIsLike;
	}

	public String getProcessInstanceVariableKey() {
		return processInstanceVariableKey;
	}

	public String getProcessInstanceVariableValue() {
		return processInstanceVariableValue;
	}

	public boolean isProcessInstanceVariableValueIsLike() {
		return processInstanceVariableValueIsLike;
	}
}
