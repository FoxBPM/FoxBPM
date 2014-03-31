package org.foxbpm.kernel.process.impl;

import java.util.ArrayList;
import java.util.List;


import org.foxbpm.kernel.process.KernelDefinitions;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelLaneSet;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.InterpretableProcessInstance;
import org.foxbpm.kernel.runtime.KernelProcessInstance;

public class KernelProcessDefinitionImpl extends KernelFlowElementsContainerImpl implements KernelProcessDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String name;
	protected String key;
	protected String description;
	protected KernelFlowNodeImpl initial;
	protected List<KernelLaneSet> laneSets;
	protected KernelDefinitions definitions;

	public KernelProcessDefinitionImpl(String id) {
		super(id, null);
		processDefinition = this;
	}

	public KernelProcessInstance createProcessInstance() {
		if (initial == null) {
			throw new KernelException(
					"流程 '"
							+ name
							+ "' 没有指定启动节点不能启动.");
		}
		return createProcessInstanceForInitial(initial);
	}

	/** 通过一个指定启动节点创建一个流程实例 */
	public KernelProcessInstance createProcessInstanceForInitial(KernelFlowNodeImpl initial) {

		if (initial == null) {
			throw new KernelException("Cannot start process instance, initial activity where the process instance should start is null.");
		}

		InterpretableProcessInstance processInstance = newProcessInstance(initial);
		processInstance.setProcessDefinition(this);
		processInstance.initialize();

		

		return processInstance;
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


	public List<KernelLaneSet> getLaneSets() {
		if (laneSets == null) {
			laneSets = new ArrayList<KernelLaneSet>();
		}
		return laneSets;
	}

	public void setDefinitions(KernelDefinitions definitions) {
		this.definitions = definitions;
	}

	public KernelDefinitions getDefinitions() {
		return definitions;
	}



	
}
