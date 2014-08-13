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
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.datavariable.VariableQueryImpl;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;
import org.foxbpm.engine.impl.runtime.ContextInstanceImpl;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ContextInstance;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceStatus;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * 流程实例实体
 * 
 * @author kenshin
 * 
 */
public class ProcessInstanceEntity extends KernelProcessInstanceImpl implements ProcessInstance, PersistentObject, HasRevision {

	private static final long serialVersionUID = 1L;

	/** 流程定义唯一版本编号 */
	protected String processDefinitionId;

	/** 流程定义编号 */
	protected String processDefinitionKey;

	/** 交互式流程文件定义号 */
	protected String definitionId;

	/** 根令牌编号 */
	protected String rootTokenId;

	/** 父流程实例用来启动子流程实例的令牌编号 */
	protected String parentTokenId;

	/** 父流程实例编号 */
	protected String parentId;

	/** 实例主题 */
	protected String subject;

	/** 流程实例的启动人 */
	protected String startAuthor;

	/** 流程任务的发起人 */
	protected String initiator;

	/** 业务关联键 */
	protected String bizKey;

	/** 流程实例启动时间 */
	protected Date startTime;

	/** 流程实例结束时间 */
	protected Date endTime;

	/** 流程实例最近一次操作时间 */
	protected Date updateTime;

	/** 流程实例归档时间 */
	protected Date archiveTime;

	/** 流程实例状态 */
	protected String instanceStatus;

	/** 流程实例位置 */
	protected String processLocation;

	/** 是否暂停 */
	protected boolean isSuspended = false;

	// 对象字段 /////////////////////

	/** 任务集合 */
	protected List<TaskEntity> tasks;

	/** 任务身份集合 */
	protected List<IdentityLinkEntity> identityLinks;

	/** 变量管理器 */
	protected DataVariableMgmtInstance dataVariableMgmtInstance;

	/** 实例内容管理器 */
	protected ContextInstance contextInstance;

	public boolean isModified() {
		return true;
	}

	/** 构造函数 */
	public ProcessInstanceEntity() {
		this(null);
	}

	// Constructor 构造函数
	// /////////////////////////////////////////////////////
	public ProcessInstanceEntity(KernelFlowNodeImpl startFlowNode) {
		super(startFlowNode);
		// 设置流程实例的编号,通过静态方法获得Guid
		this.id = GuidUtil.CreateGuid();
		this.dataVariableMgmtInstance = new DataVariableMgmtInstance(this);
		this.contextInstance = new ContextInstanceImpl();
	}

	@Override
	public void start() {
		this.instanceStatus = ProcessInstanceStatus.RUNNING;
		this.setStartTime(ClockUtil.getCurrentTime());

		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) getProcessDefinition();
		if (processDefinitionEntity.getSubject() != null) {
			this.setSubject(StringUtil.getString(processDefinitionEntity.getSubject().getValue(getRootToken())));
		}

		super.start();
	}

	@Override
	public KernelTokenImpl createRootToken() {
		super.createRootToken();
		this.rootTokenId = this.rootToken.getId();
		return this.rootToken;
	}

	@Override
	public KernelTokenImpl createToken() {
		TokenEntity tokenObj = new TokenEntity();
		String tokenObjId = GuidUtil.CreateGuid();
		tokenObj.setId(tokenObjId);
		tokenObj.setStartTime(ClockUtil.getCurrentTime());
		tokenObj.setProcessInstance(this);
		addToken(tokenObj);
		Context.getCommandContext().getTokenManager().insert(tokenObj);

		return tokenObj;
	}

	
	
	
	@Override
	public void initialize() {
		super.initialize();
		this.tasks = new ArrayList<TaskEntity>();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void ensureTasksInitialized() {
		if (tasks == null) {
			tasks = (List) Context.getCommandContext().getTaskManager().findTasksByProcessInstanceId(id);
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

	@Override
	public void ensureParentProcessInstanceTokenInitialized() {

		if (parentProcessInstanceToken == null && parentTokenId != null) {
			TokenEntity token = Context.getCommandContext().getTokenManager().findTokenById(parentTokenId);
			setParentProcessInstanceToken(token);
		}

	}
	
	

	@Override
	public KernelProcessInstance createSubProcessInstance(KernelProcessDefinitionImpl processDefinition, KernelTokenImpl token) {
		
		ProcessInstanceEntity createSubProcessInstance = (ProcessInstanceEntity) super.createSubProcessInstance(processDefinition, token);
		
		//创建流程实例，并持久化。
		
		Context.getCommandContext().getProcessInstanceManager().insert(createSubProcessInstance);

		return createSubProcessInstance;
	}

	@Override
	protected void ensureParentProcessInstanceInitialized() {
		if (parentProcessInstance == null && parentId != null) {
			ProcessInstanceEntity parentProcessInstance = Context.getCommandContext().getProcessInstanceManager().findProcessInstanceById(parentId);
			setParentProcessInstance(parentProcessInstance);
		}
	}

	@Override
	public void ensureRootTokenInitialized() {
		if (rootToken == null && rootTokenId != null) {
			TokenEntity token = Context.getCommandContext().getTokenManager().findTokenById(rootTokenId);
			setRootToken(token);
		}
	}

	@Override
	public void ensureProcessDefinitionInitialized() {
		if ((processDefinition == null) && (processDefinitionId != null)) {
			ProcessDefinitionEntity deployedProcessDefinition = Context.getProcessEngineConfiguration().getDeploymentManager().findDeployedProcessDefinitionById(processDefinitionId);
			setProcessDefinition(deployedProcessDefinition);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void ensureTokensInitialized() {
		if (tokens == null) {
			this.tokens = (List) Context.getCommandContext().getTokenManager().findChildTokensByProcessInstanceId(id);
		}
	}

	@Override
	public void setParentProcessInstance(KernelProcessInstanceImpl parentProcessInstance) {
		ProcessInstanceEntity processInstanceEntity = ((ProcessInstanceEntity) parentProcessInstance);
		this.parentProcessInstance = processInstanceEntity;
		this.parentId = processInstanceEntity.getParentId();
	}

	@Override
	public void setParentProcessInstanceToken(KernelTokenImpl parentProcessInstanceToken) {
		this.parentProcessInstanceToken = parentProcessInstanceToken;
		this.parentTokenId = parentProcessInstanceToken.getId();
	}

	@Override
	public void setRootToken(KernelTokenImpl rootToken) {
		this.rootTokenId = rootToken.getId();
		super.setRootToken(rootToken);
		
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getRootTokenId() {
		return rootTokenId;
	}

	public void setRootTokenId(String rootTokenId) {
		this.rootTokenId = rootTokenId;
	}

	public String getParentTokenId() {
		return parentTokenId;
	}

	public void setParentTokenId(String parentTokenId) {
		this.parentTokenId = parentTokenId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStartAuthor() {
		return startAuthor;
	}

	public void setStartAuthor(String startAuthor) {
		this.startAuthor = startAuthor;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
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

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getArchiveTime() {
		return archiveTime;
	}

	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}

	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public String getProcessLocation() {
		return processLocation;
	}

	public void setProcessLocation(String processLocation) {
		this.processLocation = processLocation;
	}

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public DataVariableMgmtInstance getDataVariableMgmtInstance() {
		return dataVariableMgmtInstance;
	}

	public void setDataVariableMgmtInstance(DataVariableMgmtInstance dataVariableMgmtInstance) {
		this.dataVariableMgmtInstance = dataVariableMgmtInstance;
	}

	@Override
	public void setProcessDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
		this.processDefinitionId = processDefinition.getId();
		this.processDefinitionKey = processDefinition.getKey();
	}

	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRevision(int revision) {

	}

	public int getRevision() {
		return 0;
	}

	public int getRevisionNext() {
		return 0;
	}

	public Map<String, Object> getPersistentState() {
		Map<String, Object> mapPersistentState = new HashMap<String, Object>();
		mapPersistentState.put("id", id);
		mapPersistentState.put("subject", subject);
		mapPersistentState.put("processDefinitionKey", processDefinitionKey);
		mapPersistentState.put("processDefinitionId", processDefinitionId);
		mapPersistentState.put("rootTokenId", rootTokenId);
		mapPersistentState.put("definitionId", definitionId);
		mapPersistentState.put("parentId", parentId);
		mapPersistentState.put("parentTokenId", parentTokenId);
		mapPersistentState.put("initiator", initiator);
		mapPersistentState.put("startAuthor", startAuthor);
		mapPersistentState.put("bizKey", bizKey);
		mapPersistentState.put("startTime", startTime);
		mapPersistentState.put("endTime", endTime);
		mapPersistentState.put("updateTime", updateTime);
		mapPersistentState.put("archiveTime", archiveTime);
		mapPersistentState.put("isSuspended", isSuspended);
		mapPersistentState.put("processLocation", processLocation);
		mapPersistentState.put("instanceStatus", instanceStatus);
		return mapPersistentState;
	}

	public void setVariables(Map<String, Object> variables) {
		if (variables == null) {
			return;
		}
		for (String mapKey : variables.keySet()) {
			VariableQuery variableQuery = new VariableQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
			variableQuery.addVariableKey(mapKey);
			variableQuery.processInstanceId(this.id);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<VariableInstanceEntity> variableInstances = (List) variableQuery.list();
			if (variableInstances != null && variableInstances.size() == 1) {
				// 更新
				Context.getCommandContext().getVariableManager().update(variableInstances.get(0));
				dataVariableMgmtInstance.getDataVariableEntities().add(variableInstances.get(0));
			} else {
				if (variableInstances != null && variableInstances.size() > 1) {
					throw new FoxBPMException("一个流程实例中含有两个相同的key,key(" + mapKey + ") instanceId(" + this.id
							+ ")");
				} else {
					VariableInstanceEntity variableInstanceEntity = addVariableToMgmt(mapKey,variables.get(mapKey));
					Context.getCommandContext().getVariableManager().insert(variableInstanceEntity);
				}
			}
			ExpressionMgmt.setVariable(mapKey, variables.get(mapKey));
		}
	}
	
	public void setTransVariables(Map<String, Object> transientVariables) {
		if (transientVariables == null) {
			return;
		}
		for (String mapKey : transientVariables.keySet()) {
			addVariableToMgmt(mapKey,transientVariables.get(mapKey));
			ExpressionMgmt.setVariable(mapKey, transientVariables.get(mapKey));
		}
	}
	
	private VariableInstanceEntity addVariableToMgmt(String key,Object value){
		VariableInstanceEntity variableInstanceEntity = new VariableInstanceEntity();
		variableInstanceEntity.setKey(key);
		variableInstanceEntity.setValue(value);
		variableInstanceEntity.setProcessInstanceId(this.id);
		variableInstanceEntity.setProcessDefinitionId(processDefinitionId);
		variableInstanceEntity.setProcessDefinitionKey(processDefinitionKey);
		variableInstanceEntity.setId(GuidUtil.CreateGuid());
		dataVariableMgmtInstance.getDataVariableEntities().add(variableInstanceEntity);
		return variableInstanceEntity;
	}

	public boolean isEnd() {

		return endTime != null;
	}

	@Override
	public void end() {
		this.instanceStatus = ProcessInstanceStatus.COMPLETE;
		endTime = ClockUtil.getCurrentTime();
		super.end();
	}
	
	public void termination() {
		this.instanceStatus = ProcessInstanceStatus.TERMINATION;
		endTime = ClockUtil.getCurrentTime();
		super.end();
	}

	public String getProcessDefinitionName() {
		ensureProcessDefinitionInitialized();
		if (processDefinition != null) {
			return processDefinition.getName();
		}
		return null;
	}
	
	@Override
	protected KernelProcessInstanceImpl newProcessInstance() {
		return new ProcessInstanceEntity();
	}

}
