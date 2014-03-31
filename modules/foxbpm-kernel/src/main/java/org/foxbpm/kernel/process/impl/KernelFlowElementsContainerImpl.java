package org.foxbpm.kernel.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelFlowElementsContainer;
import org.foxbpm.kernel.process.KernelLaneSet;

public class KernelFlowElementsContainerImpl extends KernelFlowElementImpl implements KernelFlowElementsContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<KernelFlowNodeImpl> flowNodes = new ArrayList<KernelFlowNodeImpl>();
	protected Map<String, KernelFlowNodeImpl> namedFlowNodes = new HashMap<String, KernelFlowNodeImpl>();

	// protected Map<String, List<ExecutionListener>> executionListeners = new
	// HashMap<String, List<ExecutionListener>>();
	// protected IOSpecification ioSpecification;

	public KernelFlowElementsContainerImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

	public KernelFlowNodeImpl findFlowNode(String flowNodeId) {
		KernelFlowNodeImpl localFlowNode = namedFlowNodes.get(flowNodeId);
		if (localFlowNode != null) {
			return localFlowNode;
		}
		for (KernelFlowNodeImpl activity : flowNodes) {
			if (activity instanceof KernelFlowElementsContainer) {
				KernelFlowElementsContainer flowElementsContainer = (KernelFlowElementsContainer) activity;

				KernelFlowNodeImpl nestedFlowNode = (KernelFlowNodeImpl) flowElementsContainer.findFlowNode(flowNodeId);
				if (nestedFlowNode != null) {
					return nestedFlowNode;
				}

			}

		}
		return null;
	}

	public KernelFlowNodeImpl createFlowNode() {
		return createFlowNode(null);
	}

	public KernelFlowNodeImpl createFlowNode(String flowNodeId) {
		KernelFlowNodeImpl flowNode = new KernelFlowNodeImpl(flowNodeId, processDefinition);
		if (flowNodeId != null) {
			if (processDefinition.findFlowNode(flowNodeId) != null) {
				throw new KernelException("流程定义里已经存在 flowNode id '" + flowNodeId + "'");
			}
			namedFlowNodes.put(flowNodeId, flowNode);
		}
		flowNode.setParent(this);
		flowNodes.add(flowNode);
		return flowNode;
	}

	public boolean contains(String flowNodeId) {
		if (namedFlowNodes.containsKey(flowNodeId)) {
			return true;
		}
		for (KernelFlowNodeImpl nestedFlowNode : flowNodes) {
			if(nestedFlowNode instanceof KernelFlowElementsContainer){
				KernelFlowElementsContainer flowElementsContainer=(KernelFlowElementsContainer)nestedFlowNode;
				if (flowElementsContainer.contains(flowNodeId)) {
					return true;
				}
			}
			
		}
		return false;
	}

	// event listeners
	// //////////////////////////////////////////////////////////
	/*
	 * @SuppressWarnings("unchecked") public List<ExecutionListener>
	 * getExecutionListeners(String eventName) { List<ExecutionListener>
	 * executionListenerList = getExecutionListeners().get(eventName); if
	 * (executionListenerList != null) { return executionListenerList; } return
	 * Collections.EMPTY_LIST; }
	 * 
	 * public void addExecutionListener(String eventName, ExecutionListener
	 * executionListener) { addExecutionListener(eventName, executionListener,
	 * -1); }
	 * 
	 * public void addExecutionListener(String eventName, ExecutionListener
	 * executionListener, int index) { List<ExecutionListener> listeners =
	 * executionListeners.get(eventName); if (listeners == null) { listeners =
	 * new ArrayList<ExecutionListener>(); executionListeners.put(eventName,
	 * listeners); } if (index < 0) { listeners.add(executionListener); } else {
	 * listeners.add(index, executionListener); } }
	 * 
	 * public Map<String, List<ExecutionListener>> getExecutionListeners() {
	 * return executionListeners; }
	 */

	// getters and setters
	// //////////////////////////////////////////////////////


	public List<KernelLaneSet> getLaneSets() {
		return null;
	}

	public List<KernelFlowNodeImpl> getFlowNodes() {
		return flowNodes;
	}



	/*
	 * public IOSpecification getIoSpecification() { return ioSpecification; }
	 * 
	 * public void setIoSpecification(IOSpecification ioSpecification) {
	 * this.ioSpecification = ioSpecification; }
	 */


}
