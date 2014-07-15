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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelFlowElementsContainer;
import org.foxbpm.kernel.process.KernelLane;
import org.foxbpm.kernel.process.KernelLaneSet;

public class KernelFlowElementsContainerImpl extends KernelFlowElementImpl
		implements
			KernelFlowElementsContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<KernelFlowNodeImpl> flowNodes = new ArrayList<KernelFlowNodeImpl>();
	protected Map<String, KernelFlowNodeImpl> namedFlowNodes = new HashMap<String, KernelFlowNodeImpl>();
	protected Map<String, KernelSequenceFlowImpl> sequenceFlows = new HashMap<String, KernelSequenceFlowImpl>();

	protected List<KernelLaneSet> laneSets = new ArrayList<KernelLaneSet>();

	protected Map<String, List<KernelListener>> kernelListeners = new HashMap<String, List<KernelListener>>();

	public KernelFlowElementsContainerImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

	public KernelFlowNodeImpl findFlowNode(String flowNodeId) {
		KernelFlowNodeImpl localFlowNode = namedFlowNodes.get(flowNodeId);
		if (localFlowNode != null) {
			return localFlowNode;
		}
		KernelFlowElementsContainer flowElementsContainer = null;
		KernelFlowNodeImpl nestedFlowNode = null;
		for (KernelFlowNodeImpl activity : flowNodes) {
			if (activity instanceof KernelFlowElementsContainer) {
				flowElementsContainer = (KernelFlowElementsContainer) activity;
				nestedFlowNode = (KernelFlowNodeImpl) flowElementsContainer
						.findFlowNode(flowNodeId);
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

	public KernelSequenceFlowImpl findSequenceFlow(String sequenceFlowId) {
		return sequenceFlows.get(sequenceFlowId);
	}

	public Map<String, KernelSequenceFlowImpl> getSequenceFlows() {
		return sequenceFlows;
	}

	public void setSequenceFlows(Map<String, KernelSequenceFlowImpl> sequenceFlows) {
		this.sequenceFlows = sequenceFlows;
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
		KernelFlowElementsContainer flowElementsContainer = null;
		for (KernelFlowNodeImpl nestedFlowNode : flowNodes) {
			if (nestedFlowNode instanceof KernelFlowElementsContainer) {
				flowElementsContainer = (KernelFlowElementsContainer) nestedFlowNode;
				if (flowElementsContainer.contains(flowNodeId)) {
					return true;
				}
			}

		}
		return false;
	}

	// event listeners
	// //////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public List<KernelListener> getKernelListeners(String eventName) {
		List<KernelListener> executionListenerList = getKernelListeners().get(eventName);
		if (executionListenerList != null) {
			return executionListenerList;
		}
		return Collections.EMPTY_LIST;
	}

	public void addKernelListener(String eventName, KernelListener kernelListener) {
		addKernelListener(eventName, kernelListener, -1);
	}

	public void addKernelListener(String eventName, KernelListener kernelListener, int index) {
		List<KernelListener> listeners = kernelListeners.get(eventName);
		if (listeners == null) {
			listeners = new ArrayList<KernelListener>();
			kernelListeners.put(eventName, listeners);
		}
		if (index < 0) {
			listeners.add(kernelListener);
		} else {
			listeners.add(index, kernelListener);
		}
	}

	public Map<String, List<KernelListener>> getKernelListeners() {
		return kernelListeners;
	}
	// getters and setters
	// //////////////////////////////////////////////////////

	public List<KernelLaneSet> getLaneSets() {
		return laneSets;
	}

	public KernelLane getLaneForId(String id) {
		if (laneSets != null && laneSets.size() > 0) {
			KernelLane lane = null;
			for (KernelLaneSet set : laneSets) {
				lane = set.getLaneForId(id);
				if (lane != null) {
					return lane;
				}
			}
		}
		return null;
	}

	public List<KernelFlowNodeImpl> getFlowNodes() {
		return flowNodes;
	}

	public Map<String, KernelFlowNodeImpl> getNamedFlowNodes() {
		return namedFlowNodes;
	}
	/*
	 * public IOSpecification getIoSpecification() { return ioSpecification; }
	 * 
	 * public void setIoSpecification(IOSpecification ioSpecification) {
	 * this.ioSpecification = ioSpecification; }
	 */

}
