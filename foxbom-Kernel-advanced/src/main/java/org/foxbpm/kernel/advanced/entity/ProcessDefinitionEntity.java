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
 */
package org.foxbpm.kernel.advanced.entity;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.kernel.advanced.db.PersistentObject;
import org.foxbpm.kernel.advanced.mgmt.DataVariableMgmtDefinition;
import org.foxbpm.kernel.process.KernelDefinitions;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;




public class ProcessDefinitionEntity extends KernelProcessDefinitionImpl implements PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessDefinitionEntity() {
		super(null);

	}

	/**
	 * '{@link #isIsExecutable() <em>Version</em>}' 字段的默认值. <!-- 开始-用户-文档 -->
	 * <!-- 结束-用户-文档 -->
	 * 
	 * @see #isIsExecutable()
	 * @generated
	 * @ordered
	 */
	protected static final int VERSION_DEFAULT = 1;

	/**
	 * 该值缓存 '{@link #getVersion() <em>Version</em>}' 字段. <!-- 开始-用户-文档 -->
	 * <p>
	 * 流程版本定义,默认值为 1. 每当 {@link #getVersion() <em>流程定义版本号 Version</em>} 增加 1,
	 * {@link #com.founder.fix.fixflow.core.Definitions.getVersion()
	 * <em>业务定义版本号 Version</em>} 也同时增加 1.
	 * </p>
	 * <!-- 结束-用户-文档 -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected int version = VERSION_DEFAULT;

	protected String diagramResourceName;

	protected String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	/**
	 * <!-- 开始-用户-文档 --> 设置流程定义本版号 <!-- 结束-用户-文档 -->
	 * 
	 * @param version
	 *            版本号
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	/**
	 * <!-- 开始-用户-文档 --> 增加流程版本号 <!-- 结束-用户-文档 -->
	 */
	public void addVersion() {
		this.version = this.version + 1;
	}

	public ProcessInstanceEntity createProcessInstance(String bizKey, KernelFlowNodeImpl initial) {
		ProcessInstanceEntity processInstance = null;

		if (initial == null) {
			processInstance = (ProcessInstanceEntity) super.createProcessInstance();
		} else {
			processInstance = (ProcessInstanceEntity) super.createProcessInstanceForInitial(initial);
		}

		processInstance.setProcessDefinition(processDefinition);

		if (bizKey != null) {
			processInstance.setBizKey(bizKey);
		}

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

	protected String resourceId;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	protected String deploymentId;

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDeploymentId() {
		return this.deploymentId;
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

	

	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("processDefinitionId", this.processDefinitionId);
		persistentState.put("processDefinitionName", this.name);
		persistentState.put("processDefinitionKey", this.id);
		persistentState.put("category", getCategory());
		persistentState.put("version", this.version);
		persistentState.put("resourceName", this.resourceName);
		persistentState.put("resourceId", this.resourceId);
		persistentState.put("deploymentId", this.deploymentId);
		persistentState.put("diagramResourceName", this.diagramResourceName);
		persistentState.putAll(this.extensionFields);
		return persistentState;
	}

	protected String resourceName;

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	protected String processDefinitionId;

	public String getProcessDefinitionKey() {
		return this.id;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.id = processDefinitionKey;

	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	protected String category;

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	KernelDefinitions definitions;

	public void setDefinitions(KernelDefinitions definitions) {
		this.definitions = definitions;
	}

	public KernelDefinitions getDefinitions() {
		return definitions;
	}

	public boolean verification = true;

	public boolean isVerification() {
		return this.verification;

	}

	protected DataVariableMgmtDefinition dataVariableMgmtDefinition;

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
		this.id=id;
	}

}
