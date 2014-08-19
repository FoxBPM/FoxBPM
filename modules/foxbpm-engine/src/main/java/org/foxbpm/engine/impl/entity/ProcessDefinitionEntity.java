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

import org.foxbpm.engine.db.HasRevision;
import org.foxbpm.engine.db.PersistentObject;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.identity.PotentialStarter;
import org.foxbpm.engine.impl.mgmt.DataVariableMgmtDefinition;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;

public class ProcessDefinitionEntity extends KernelProcessDefinitionImpl
		implements
			ProcessDefinition,
			PersistentObject,
			HasRevision {

	private static final long serialVersionUID = 1L;

	protected static final int VERSION_DEFAULT = 1;

	protected int version = VERSION_DEFAULT;

	protected String diagramResourceName;

	protected Expression subject;

	private Expression formUri;

	private Expression formUriView;

	protected String resourceId;

	protected String deploymentId;

	protected String resourceName;

	protected String category;

	private boolean isPersistence = true;
	
	/**发布时间 */
	private Date deploymentTime;
	
	/** 启动表单  */
	private String startFormUri;
	
	/** 控制并发修改标示 */
	protected int revision;

	protected DataVariableMgmtDefinition dataVariableMgmtDefinition;

	protected Map<String, TaskDefinition> taskDefinitions = new HashMap<String, TaskDefinition>();
	
	protected List<PotentialStarter> potentialStarters=new ArrayList<PotentialStarter>();

	public TaskDefinition getSubTaskDefinition() {
		if (getInitial() != null) {
			List<KernelSequenceFlow> sequenceFlows = getInitial().getOutgoingSequenceFlows();
			if (sequenceFlows != null && sequenceFlows.size() > 0) {
				String subNodeId = sequenceFlows.get(0).getTargetRef().getId();
				return taskDefinitions.get(subNodeId);
			}
		}
		return null;
	}

	public Map<String, TaskDefinition> getTaskDefinitions() {
		return taskDefinitions;
	}

	public void setTaskDefinitions(Map<String, TaskDefinition> taskDefinitions) {
		this.taskDefinitions = taskDefinitions;
	}

	public ProcessDefinitionEntity() {
		super(null);

	}

	public ProcessDefinitionEntity(String id) {
		super(id);

	}

	public Expression getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = new ExpressionImpl(subject);
	}

	public Expression getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = new ExpressionImpl(formUri);
	}

	public Expression getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = new ExpressionImpl(formUriView);
	}
	
	public void setStartFormUri(String startFormUri) {
		this.startFormUri = startFormUri;
	}
	
	public String getStartFormUri() {
		return startFormUri;
	}

	/**
	 * 返回 '<em><b>Version</b></em>' 字段. <!-- 开始-用户-文档 -->
	 * <p>
	 * 返回流程定义版本号
	 * </p>
	 * <!-- 结束-用户-文档 -->
	 * 
	 * @return 该值为 '<em>Version</em>' 字段.
	 * @see #setVersion(int)
	 * @generated
	 */
	public int getVersion() {
		return version;
	}

	/** 设置流程定义本版号 */
	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	/** 增加流程版本号 */
	public void addVersion() {
		this.version = this.version + 1;
	}

	public ProcessInstanceEntity createProcessInstance(String bizKey, KernelFlowNodeImpl initial) {
		ProcessInstanceEntity processInstance = null;

		if (initial == null) {
			processInstance = (ProcessInstanceEntity) super.createProcessInstance();
		} else {
			processInstance = (ProcessInstanceEntity) super
					.createProcessInstanceForInitial(initial);
		}

		processInstance.setProcessDefinition(processDefinition);
		if (bizKey != null) {
			processInstance.setBizKey(bizKey);
		}

		Context.getCommandContext().getProcessInstanceManager().insert(processInstance);

		return processInstance;
	}

	public ProcessInstanceEntity createProcessInstance(String bizKey) {
		return createProcessInstance(bizKey, null);
	}

	public ProcessInstanceEntity createProcessInstance() {
		return createProcessInstance(null);
	}

	@Override
	protected InterpretableProcessInstance newProcessInstance(KernelFlowNodeImpl startFlowNode) {
		ProcessInstanceEntity processInstance = new ProcessInstanceEntity(startFlowNode);
		return processInstance;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDeploymentId() {
		return this.deploymentId;
	}
	
	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	
	public Date getDeploymentTime() {
		return deploymentTime;
	}

	protected Map<String, Object> extensionFields = new HashMap<String, Object>();

	public Object getExtensionField(String fieldName) {
		return extensionFields.get(fieldName);
	}

	public Map<String, Object> getExtensionFields() {
		return extensionFields;
	}

	public void setExtensionFields(Map<String, Object> extensionFields) {
		this.extensionFields = extensionFields;
	}

	public void addExtensionField(String fieldName, Object fieldValue) {
		this.extensionFields.put(fieldName, fieldValue);
	}

	@Override
	public KernelSequenceFlowImpl createSequenceFlow(String id,
			KernelProcessDefinitionImpl processDefinition) {
		return super.createSequenceFlow(id, processDefinition);
	}

	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("name", this.name);
		persistentState.put("key", this.key);
		persistentState.put("category", getCategory());
		persistentState.put("version", this.version);
		persistentState.put("resourceName", this.resourceName);
		persistentState.put("resourceId", this.resourceId);
		persistentState.put("deploymentId", this.deploymentId);
		persistentState.put("diagramResourceName", this.diagramResourceName);
		persistentState.put("deployTime", this.deploymentTime);
		persistentState.put("startFormUri", this.getStartFormUri());
		persistentState.putAll(this.extensionFields);
		return persistentState;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean verification = true;

	public boolean isVerification() {
		return this.verification;

	}
	public boolean isPersistence() {
		return isPersistence;
	}

	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}

	public DataVariableMgmtDefinition getDataVariableMgmtDefinition() {
		return dataVariableMgmtDefinition;
	}

	public void setDataVariableMgmtDefinition(DataVariableMgmtDefinition dataVariableMgmtDefinition) {
		this.dataVariableMgmtDefinition = dataVariableMgmtDefinition;
	}

	public String getDiagramResourceName() {
		return diagramResourceName;
	}

	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
	}

	public boolean hasStartFormKey() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRevision(int revision) {
			this.revision = revision;
	}

	public int getRevision() {
		return revision;
	}

	public int getRevisionNext() {
		return revision + 1;
	}

	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<PotentialStarter> getPotentialStarters() {
		return potentialStarters;
	}

	public void setPotentialStarters(List<PotentialStarter> potentialStarters) {
		this.potentialStarters = potentialStarters;
	}
}
