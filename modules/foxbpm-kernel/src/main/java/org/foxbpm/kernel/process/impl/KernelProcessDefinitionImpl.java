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
package org.foxbpm.kernel.process.impl;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.kernel.process.KernelArtifact;
import org.foxbpm.kernel.process.KernelDefinitions;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelLaneSet;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;

public class KernelProcessDefinitionImpl extends KernelFlowElementsContainerImpl
		implements
			KernelProcessDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String name;
	protected String key;
	protected String description;
	protected KernelFlowNodeImpl initial;
	protected KernelDefinitions definitions;
	protected List<KernelArtifact> artifacts=new ArrayList<KernelArtifact>();

	

	public KernelProcessDefinitionImpl(String id) {
		super(id, null);
		processDefinition = this;
	}

	public KernelProcessInstance createProcessInstance() {
		if (initial == null) {
			throw new KernelException("流程 '" + name + "' 没有指定启动节点不能启动.");
		}
		return createProcessInstanceForInitial(initial);
	}

	/** 通过一个指定启动节点创建一个流程实例 */
	public KernelProcessInstance createProcessInstanceForInitial(KernelFlowNodeImpl initial) {

		if (initial == null) {
			throw new KernelException(
					"Cannot start process instance, initial activity where the process instance should start is null.");
		}

		InterpretableProcessInstance processInstance = newProcessInstance(initial);
		processInstance.setProcessDefinition(this);
		processInstance.initialize();

		return processInstance;
	}

	public KernelSequenceFlowImpl createSequenceFlow(String id,
			KernelProcessDefinitionImpl processDefinition) {
		return new KernelSequenceFlowImpl(id, processDefinition);
	}

	protected InterpretableProcessInstance newProcessInstance(KernelFlowNodeImpl startFlowNode) {
		return new KernelProcessInstanceImpl(startFlowNode);
	}

	public String getDiagramResourceName() {
		return null;
	}

	public String getDeploymentId() {
		return null;
	}

	public void addLaneSet(KernelLaneSet newLaneSet) {
		getLaneSets().add(newLaneSet);
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	public KernelFlowNodeImpl getInitial() {
		return initial;
	}

	public void setInitial(KernelFlowNodeImpl initial) {
		this.initial = initial;
	}

	public String toString() {
		return "ProcessDefinition(" + id + ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return (String) getProperty("documentation");
	}

	public void setDefinitions(KernelDefinitions definitions) {
		this.definitions = definitions;
	}

	public KernelDefinitions getDefinitions() {
		return definitions;
	}
	
	public List<KernelArtifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<KernelArtifact> artifacts) {
		this.artifacts = artifacts;
	}

}
