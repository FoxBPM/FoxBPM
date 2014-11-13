/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.engine.impl.runtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;
import org.foxbpm.engine.impl.query.AbstractQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;

/**
 * 流程实例查询实现
 * 
 * @author kenshin
 * 
 */
public class ProcessInstanceQueryImpl extends AbstractQuery<ProcessInstanceQuery, ProcessInstance>
		implements
			ProcessInstanceQuery {

	private static final long serialVersionUID = 1L;

	protected boolean subjectUnionInitiator = false;
	protected String processInstanceId;
	protected String businessKey;
	protected String businessKeyLike;
	protected String processDefinitionId;
	protected String processDefinitionKey;
	protected String initiator;
	protected String taskParticipants;
	protected Date updateTime;
	protected String isSuspended;
	protected List<String> processDefinitionKeyList = new ArrayList<String>();
	protected String status;
	protected String processDefinitionName;
	protected String processDefinitionNameLike;
	protected String isEnd;
	protected String initiatorLike;
	protected String subject;
	protected String subjectLike;
	protected Date startTime;
	protected Date startTimeBefore;
	protected Date startTimeAfter;
	protected Date endTime;
	protected Date endTimeBefore;
	protected Date endTimeAfter;

	protected CommandExecutor commandExecutor;

	/* 变量查询 */
	protected String processInstanceVariableKey;
	protected String processInstanceVariableValue;
	protected boolean processInstanceVariableValueIsLike;
	
	public ProcessInstanceQueryImpl(CommandContext commandContext) {
		super(commandContext);
	}

	public ProcessInstanceQueryImpl(CommandExecutor commandExecutor) {
		super(commandExecutor);
	}

	public ProcessInstanceQueryImpl processInstanceId(String processInstanceId) {
		if (processInstanceId == null) {
			return this;
		}
		this.processInstanceId = processInstanceId;
		return this;
	}

	public ProcessInstanceQuery processInstanceBusinessKey(String businessKey) {
		if (businessKey == null) {
			return this;
		}
		this.businessKey = businessKey;
		return this;
	}

	public ProcessInstanceQuery processInstanceBusinessKeyLike(String businessKey) {
		if (businessKey == null) {
			return this;
		}
		this.businessKeyLike = businessKey;
		return this;
	}

	public ProcessInstanceQueryImpl processDefinitionId(String processDefinitionId) {
		if (processDefinitionId == null) {
			return this;
		}
		this.processDefinitionId = processDefinitionId;
		return this;
	}

	public ProcessInstanceQueryImpl taskParticipants(String taskParticipants) {
		this.taskParticipants = taskParticipants;
		return this;
	}

	public ProcessInstanceQueryImpl initiator(String initiator) {
		this.initiator = initiator;
		return this;
	}

	public ProcessInstanceQuery isEnd() {
		isEnd = " not null";
		return this;
	}

	public ProcessInstanceQuery notEnd() {
		isEnd = " null";
		return this;
	}

	public ProcessInstanceQueryImpl processDefinitionKey(String processDefinitionKey) {
		if (processDefinitionKey == null) {
			return this;
		}
		this.processDefinitionKey = processDefinitionKey;
		return this;
	}

	public ProcessInstanceQuery processDefinitionKey(List<String> processDefinitionKeyList) {
		if (processDefinitionKeyList != null && processDefinitionKeyList.size() > 0) {
			this.processDefinitionKeyList = processDefinitionKeyList;
		} else {
			return this;
		}
		return this;
	}

	public ProcessInstanceQuery orderByProcessInstanceId() {
		this.orderProperty = ProcessInstanceQueryProperty.PROCESS_INSTANCE_ID;
		return this;
	}

	public ProcessInstanceQuery orderByStartTime() {
		this.orderProperty = ProcessInstanceQueryProperty.START_TIME;
		return this;
	}

	public ProcessInstanceQuery orderByProcessDefinitionId() {
		this.orderProperty = ProcessInstanceQueryProperty.PROCESS_DEFINITION_ID;
		return this;
	}

	public ProcessInstanceQuery orderByProcessDefinitionKey() {
		this.orderProperty = ProcessInstanceQueryProperty.PROCESS_DEFINITION_KEY;
		return this;
	}
	// 按更新时间排序--by ych 2013-07-23
	public ProcessInstanceQuery orderByUpdateTime() {
		this.orderProperty = ProcessInstanceQueryProperty.UPDATE_TIME;
		return this;
	}

	public ProcessInstanceQuery processInstanceStatus(String status) {
		this.status = status;
		return this;
	}

	public ProcessInstanceQuery processDefinitionName(String definitionName) {
		this.processDefinitionName = definitionName;
		return this;
	}

	public ProcessInstanceQuery processDefinitionNameLike(String definitionNameLike) {
		this.processDefinitionNameLike = definitionNameLike;
		return this;
	}

	// results /////////////////////////////////////////////////////////////////

	public long executeCount(CommandContext commandContext) {
		checkQueryOk();
		// ensureVariablesInitialized();
		return commandContext.getProcessInstanceManager().findProcessInstanceCountByQueryCriteria(this);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<ProcessInstance> executeList(CommandContext commandContext) {
		checkQueryOk();
		// ensureVariablesInitialized();
		return (List) commandContext.getProcessInstanceManager().findProcessInstanceByQueryCriteria(this);
	}

	// getters /////////////////////////////////////////////////////////////////

	public boolean getOnlyProcessInstances() {
		return true; // See dynamic query in runtime.mapping.xml
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public String getBusinessKeyLike() {
		return businessKeyLike;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public String getProcessDefinitionNameLike() {
		return processDefinitionNameLike;
	}

	public String getInitiator() {
		return initiator;
	}

	public String getTaskParticipants() {
		return taskParticipants;
	}

	public String getStatus() {
		return status;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getEndTimeBefore() {
		return endTimeBefore;
	}

	public Date getEndTimeAfter() {
		return endTimeAfter;
	}

	public ProcessInstanceQuery initiatorLike(String initiatorLike) {
		this.initiatorLike = initiatorLike;
		return this;
	}

	public ProcessInstanceQuery subject(String subject) {
		this.subject = subject;
		return this;
	}

	public ProcessInstanceQuery subjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
		return this;
	}

	public ProcessInstanceQuery startTimeOn(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public ProcessInstanceQuery subjectUnionInitiator(){
		this.subjectUnionInitiator = true;
		return this;
	}
	public ProcessInstanceQuery startTimeBefore(Date startTimeBefore) {
		this.startTimeBefore = startTimeBefore;
		return this;
	}

	public ProcessInstanceQuery startTimeAfter(Date startTimeAfter) {
		this.startTimeAfter = startTimeAfter;
		return this;
	}

	public ProcessInstanceQuery endTimeOn(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	public ProcessInstanceQuery endTimeBefore(Date endTimeBefore) {
		this.endTimeBefore = endTimeBefore;
		return this;
	}

	public ProcessInstanceQuery endTimeAfter(Date endTimeAfter) {
		this.endTimeAfter = endTimeAfter;
		return this;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public String getInitiatorLike() {
		return initiatorLike;
	}

	public String getSubject() {
		return subject;
	}

	public String getSubjectLike() {
		return subjectLike;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getStartTimeBefore() {
		return startTimeBefore;
	}

	public Date getStartTimeAfter() {
		return startTimeAfter;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public String getIsSuspended() {
		return isSuspended;
	}

	public List<String> getProcessDefinitionKeyList() {
		return processDefinitionKeyList;
	}
	public ProcessInstanceQuery processInstanceVariableData(String variableValue, boolean isLike) {
		this.processInstanceVariableValue = variableValue;
		this.processInstanceVariableValueIsLike = isLike;
		return this;
	}

	public ProcessInstanceQuery processInstanceVariableData(String variableKey, String variableValue, boolean isLike) {
		this.processInstanceVariableValue = variableValue;
		this.processInstanceVariableValueIsLike = isLike;
		this.processInstanceVariableKey = variableKey;
		return this;
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
	
	@Override
	public String getOrderBy() {
		// TODO Auto-generated method stub
		return "RES.ID";
	}

}
