package org.foxbpm.kernel.process.impl;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.runtime.KernelExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class KernelSequenceFlowImpl extends KernelFlowElementImpl implements KernelSequenceFlow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelFlowNodeImpl sourceRef;
	protected KernelFlowNodeImpl targetRef;
	//protected List<ExecutionListener> executionListeners;

	/** Graphical information: a list of waypoints: x1, y1, x2, y2, x3, y3, .. */
	protected List<Integer> waypoints = new ArrayList<Integer>();

	public KernelSequenceFlowImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

	public KernelFlowNodeImpl getSourceRef() {
		return sourceRef;
	}

	public void setTargetRef(KernelFlowNodeImpl targetRef) {
		this.targetRef = targetRef;
		targetRef.getIncomingSequenceFlows().add(this);
	}

	/*
	public void addExecutionListener(ExecutionListener executionListener) {
		if (executionListeners == null) {
			executionListeners = new ArrayList<ExecutionListener>();
		}
		executionListeners.add(executionListener);
	}*/

	public String toString() {
		return "(" + sourceRef.getId() + ")--" + (id != null ? id + "-->(" : ">(") + targetRef.getId() + ")";
	}

	/*
	@SuppressWarnings("unchecked")
	public List<ExecutionListener> getExecutionListeners() {
		if (executionListeners == null) {
			return Collections.EMPTY_LIST;
		}
		return executionListeners;
	}*/

	// getters and setters
	// //////////////////////////////////////////////////////

	protected void setSourceRef(KernelFlowNodeImpl sourceRef) {
		this.sourceRef = sourceRef;
	}

	public KernelFlowNodeImpl getTargetRef() {
		return targetRef;
	}

	/*
	public void setExecutionListeners(List<ExecutionListener> executionListeners) {
		this.executionListeners = executionListeners;
	}*/

	public List<Integer> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Integer> waypoints) {
		this.waypoints = waypoints;
	}

	public boolean isContinue(KernelExecutionContext executionContext) {
		// TODO Auto-generated method stub
		return false;
	}

	public void take(KernelExecutionContext executionContext) {
		KernelTokenImpl token=executionContext.getToken();
		token.setFlowNode(null);
		getTargetRef().enter(executionContext);
	}



}
