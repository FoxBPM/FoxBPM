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
package org.foxbpm.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelBaseElementImpl;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

public class ProcessDefinitionBuilder {

	protected KernelProcessDefinitionImpl processDefinition;
	

	protected Stack<KernelFlowElementsContainerImpl> containerStack = new Stack<KernelFlowElementsContainerImpl>();
	protected KernelBaseElementImpl processElement = processDefinition;
	protected KernelSequenceFlowImpl sequenceFlow;
	protected List<Object[]> unresolvedSequenceFlows = new ArrayList<Object[]>();

	public ProcessDefinitionBuilder() {
		this(null);
	}

	public ProcessDefinitionBuilder(String processDefinitionId) {
		processDefinition = createProcessDefinition(processDefinitionId);
		containerStack.push(processDefinition);
	}
	
	protected KernelProcessDefinitionImpl createProcessDefinition(String processDefinitionId){
		return new KernelProcessDefinitionImpl(processDefinitionId);
	}

	public ProcessDefinitionBuilder createFlowNode(String id) {
		KernelFlowNodeImpl flowNode = containerStack.peek().createFlowNode(id);
		containerStack.push(flowNode);
		processElement = flowNode;

		sequenceFlow = null;

		return this;
	}

	public ProcessDefinitionBuilder endFlowNode() {
		containerStack.pop();
		processElement = containerStack.peek();

		sequenceFlow = null;

		return this;
	}

	public ProcessDefinitionBuilder initial() {
		processDefinition.setInitial(getFlowNode());
		return this;
	}

	public ProcessDefinitionBuilder startSequenceFlow(String destinationFlowNodeId) {
		return startSequenceFlow(destinationFlowNodeId, null);
	}

	public ProcessDefinitionBuilder startSequenceFlow(String targetFlowNodeId, String sequenceFlowId) {
		if (targetFlowNodeId == null) {
			throw new KernelException("targetFlowNodeId is null");
		}
		KernelFlowNodeImpl flowNode = getFlowNode();
		sequenceFlow = flowNode.createOutgoingSequenceFlow(sequenceFlowId);
		unresolvedSequenceFlows.add(new Object[] { sequenceFlow, targetFlowNodeId });
		processElement = sequenceFlow;
		return this;
	}

	public ProcessDefinitionBuilder endSequenceFlow() {
		processElement = containerStack.peek();
		sequenceFlow = null;
		return this;
	}

	public ProcessDefinitionBuilder sequenceFlow(String targetFlowNodeId) {
		return sequenceFlow(targetFlowNodeId, null);
	}

	public ProcessDefinitionBuilder sequenceFlow(String targetFlowNodeId, String sequenceFlowId) {
		startSequenceFlow(targetFlowNodeId, sequenceFlowId);
		endSequenceFlow();
		return this;
	}

	public ProcessDefinitionBuilder behavior(KernelFlowNodeBehavior flowNodeBehaviour) {
		getFlowNode().setFlowNodeBehavior(flowNodeBehaviour);
		return this;
	}

	public ProcessDefinitionBuilder property(String name, Object value) {
		processElement.setProperty(name, value);
		return this;
	}

	public KernelProcessDefinition buildProcessDefinition() {
		for (Object[] unresolvedSequenceFlow : unresolvedSequenceFlows) {
			KernelSequenceFlowImpl sequenceFlow = (KernelSequenceFlowImpl) unresolvedSequenceFlow[0];
			String targetFlowNodeName = (String) unresolvedSequenceFlow[1];
			KernelFlowNodeImpl destination = processDefinition.findFlowNode(targetFlowNodeName);
			if (destination == null) {
				throw new RuntimeException("target '" + targetFlowNodeName + "' not found.  (referenced from sequenceFlow in '"
						+ sequenceFlow.getSourceRef().getId() + "')");
			}
			sequenceFlow.setTargetRef(destination);
		}
		return processDefinition;
	}

	protected KernelFlowNodeImpl getFlowNode() {
		return (KernelFlowNodeImpl) containerStack.peek();
	}

	public ProcessDefinitionBuilder scope() {
		getFlowNode().setScope(true);
		return this;
	}

	
	public ProcessDefinitionBuilder executionListener(KernelListener kernelListener) {
		if (sequenceFlow != null) {
			sequenceFlow.addKernelListener(kernelListener);
		} else {
			throw new KernelException("不是 sequenceFlow 对象");
		}
		return this;
	}

	public ProcessDefinitionBuilder executionListener(String eventName, KernelListener kernelListener) {
		if (sequenceFlow == null) {
			containerStack.peek().addKernelListener(eventName, kernelListener);
		} else {
			throw new KernelException("必须是个 containerStack ");
		}
		return this;
	}
	
	public KernelProcessDefinitionImpl getProcessDefinition() {
		return processDefinition;
	}
	
}
