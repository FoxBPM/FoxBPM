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
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;
import org.foxbpm.engine.impl.runtime.ContextInstanceImpl;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.runtime.ContextInstance;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
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

	/** 流程定义 */
	protected KernelProcessDefinitionImpl processDefinition;

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
		super();
		// 设置流程实例的编号,通过静态方法获得Guid
		this.id = GuidUtil.CreateGuid();
	}

	// Constructor 构造函数
	// /////////////////////////////////////////////////////
	public ProcessInstanceEntity(KernelFlowNodeImpl startFlowNode) {

		super(startFlowNode);

		// 设置流程实例的编号,通过静态方法获得Guid
		this.id = GuidUtil.CreateGuid();

	}

	@Override
	public void start() {
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
		Context.getCommandContext().getTokenManager().insert(tokenObj);

		return tokenObj;
	}

	@Override
	public void initialize() {

		this.tasks = new ArrayList<TaskEntity>();

		this.dataVariableMgmtInstance = new DataVariableMgmtInstance(this);

		this.contextInstance = new ContextInstanceImpl();

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

	public KernelProcessDefinitionImpl getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(KernelProcessDefinitionImpl processDefinition) {
		this.processDefinition = processDefinition;
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

	public Map<String, Object> getPersistentState() {
		// TODO Auto-generated method stub
		return null;
	}

}
