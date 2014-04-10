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

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelDIBounds;
import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelSequenceFlow;

public class KernelFlowNodeImpl extends KernelFlowElementsContainerImpl implements KernelFlowNode, KernelDIBounds {

	private static final long serialVersionUID = 1L;
	protected List<KernelSequenceFlowImpl> outgoingSequenceFlows = new ArrayList<KernelSequenceFlowImpl>();
	protected Map<String, KernelSequenceFlowImpl> namedOutgoingSequenceFlows = new HashMap<String, KernelSequenceFlowImpl>();
	protected List<KernelSequenceFlowImpl> incomingSequenceFlows = new ArrayList<KernelSequenceFlowImpl>();
	protected KernelFlowNodeBehavior flowNodeBehavior;
	protected KernelFlowElementsContainerImpl parent;
	protected boolean isScope;
	protected boolean isAsync;
	protected boolean isExclusive;
	

	// 图形信息
	protected int x = -1;
	protected int y = -1;
	protected int width = -1;
	protected int height = -1;

	public KernelFlowNodeImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

	public KernelSequenceFlowImpl createOutgoingSequenceFlow() {
		return createOutgoingSequenceFlow(null);
	}

	public KernelSequenceFlowImpl createOutgoingSequenceFlow(String sequenceFlowId) {
		KernelSequenceFlowImpl sequenceFlow = new KernelSequenceFlowImpl(sequenceFlowId, processDefinition);
		sequenceFlow.setSourceRef(this);
		outgoingSequenceFlows.add(sequenceFlow);

		if (sequenceFlowId != null) {
			if (namedOutgoingSequenceFlows.containsKey(sequenceFlowId)) {
				throw new KernelException("flownode '" + id + " has duplicate transition '" + sequenceFlowId + "'");
			}
			namedOutgoingSequenceFlows.put(sequenceFlowId, sequenceFlow);
		}

		return sequenceFlow;
	}

	public KernelSequenceFlowImpl findOutgoingSequenceFlow(String sequenceFlowId) {
		return namedOutgoingSequenceFlows.get(sequenceFlowId);
	}

	public String toString() {
		return "FlowNode(" + id + ")";
	}

	public KernelFlowNodeImpl getParentFlowNode() {
		if (parent instanceof KernelFlowNodeImpl) {
			return (KernelFlowNodeImpl) parent;
		}
		return null;
	}

	// restricted setters
	// ///////////////////////////////////////////////////////

	protected void setOutgoingSequenceFlows(List<KernelSequenceFlowImpl> outgoingSequenceFlows) {
		this.outgoingSequenceFlows = outgoingSequenceFlows;
	}

	protected void setParent(KernelFlowElementsContainerImpl parent) {
		this.parent = parent;
	}

	protected void setIncomingSequenceFlows(List<KernelSequenceFlowImpl> incomingSequenceFlows) {
		this.incomingSequenceFlows = incomingSequenceFlows;
	}

	// getters and setters
	// //////////////////////////////////////////////////////

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<KernelSequenceFlow> getOutgoingSequenceFlows() {

		// 创建比较器对象
		ComparatorSequence comparatorSequence = new ComparatorSequence();
		// 调用排序方法
		Collections.sort(outgoingSequenceFlows, comparatorSequence);

		return (List) outgoingSequenceFlows;
	}

	public KernelFlowNodeBehavior getKernelFlowNodeBehavior() {
		return flowNodeBehavior;
	}

	public void setFlowNodeBehavior(KernelFlowNodeBehavior flowNodeBehavior) {
		this.flowNodeBehavior = flowNodeBehavior;
	}

	public KernelFlowElementsContainerImpl getParent() {
		return parent;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<KernelSequenceFlow> getIncomingSequenceFlows() {
		return (List) incomingSequenceFlows;
	}

	public boolean isScope() {
		return isScope;
	}

	public void setScope(boolean isScope) {
		this.isScope = isScope;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public boolean isExclusive() {
		return isExclusive;
	}

	public void setExclusive(boolean isExclusive) {
		this.isExclusive = isExclusive;
	}

}
