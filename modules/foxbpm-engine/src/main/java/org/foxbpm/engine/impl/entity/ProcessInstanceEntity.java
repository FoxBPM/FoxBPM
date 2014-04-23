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
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtInstance;
import org.foxbpm.engine.impl.runtime.ContextInstanceImpl;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.runtime.ContextInstance;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class ProcessInstanceEntity extends KernelProcessInstanceImpl implements ProcessInstance,PersistentObject,HasRevision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	protected String subject;

	protected String processDefinitionId;

	protected String processDefinitionKey;

	protected String definitionId;

	protected String rootTokenId;

	protected String parentTokenId;

	protected String parentId;

	protected String startAuthor;

	protected String initiator;

	protected String bizKey;

	protected Date startTime;

	protected Date endTime;

	protected Date updateTime;

	protected Date archiveTime;

	protected String instanceStatus;

	protected String processLocation;

	protected boolean isSuspended = false;

	// 对象字段 /////////////////////

	// 流程定义
	protected KernelProcessDefinitionImpl processDefinition;
	
	/** 任务集合 */
	protected List<TaskEntity> tasks;
	/** 任务身份集合 */
	protected List<IdentityLinkEntity> identityLinks;


	// 变量管理器
	protected DataVariableMgmtInstance dataVariableMgmtInstance;

	// 实例内容管理器
	protected ContextInstance contextInstance;

	// 构造函数 ///////////////////////////////

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
	public KernelTokenImpl createRootToken() {
		super.createRootToken();
		this.rootTokenId = this.rootToken.getId();
		return this.rootToken;
	}
	
	@Override
	public KernelTokenImpl createToken() {
		TokenEntity tokenObj=new TokenEntity();
		String tokenObjId=GuidUtil.CreateGuid();
		tokenObj.setId(tokenObjId);
		return new TokenEntity();
	}
	
	

	@Override
	public void initialize() {

		this.tasks = new ArrayList<TaskEntity>();

		this.dataVariableMgmtInstance = new DataVariableMgmtInstance(this);

		this.contextInstance = new ContextInstanceImpl();

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
