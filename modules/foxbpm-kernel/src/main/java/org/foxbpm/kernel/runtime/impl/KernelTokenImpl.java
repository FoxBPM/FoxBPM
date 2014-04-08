package org.foxbpm.kernel.runtime.impl;

import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.KernelExecutionContext;
import org.foxbpm.kernel.runtime.KernelToken;

public class KernelTokenImpl implements KernelToken {

	protected boolean isEnded = false;

	protected boolean isActive = true;

	protected KernelFlowNodeImpl currentFlowNode;

	// 事件 ///////////////////////////////////////////////////////////////////
	protected String eventName;
	protected KernelBaseElement eventSource;
	protected int KernelListenerIndex = 0;
	protected KernelEvent nextEvent;
	protected boolean isOperating = false;
	
	

	public KernelFlowNodeImpl getFlowNode() {
		return currentFlowNode;
	}

	public void setFlowNode(KernelFlowNodeImpl flowNode) {
		this.currentFlowNode = flowNode;
	}

	public String getId() {
		return null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected KernelProcessInstanceImpl processInstance;

	public KernelProcessInstanceImpl getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(KernelProcessInstanceImpl processInstance) {
		this.processInstance = processInstance;
	}

	public void signal() {
		this.currentFlowNode.leave(createExecutionContext());
	}

	public void signal(KernelExecutionContext executionContext) {
		this.currentFlowNode.leave(executionContext);
	}

	public KernelExecutionContextImpl createExecutionContext() {
		KernelExecutionContextImpl executionContext = new KernelExecutionContextImpl();
		executionContext.setToken(this);
		return executionContext;
	}

	public void execute(KernelExecutionContext executionContext) {
		this.currentFlowNode.execute(executionContext);
	}

	public void fireEvent(KernelEvent kernelEvent) {
		kernelEvent.execute(this.createExecutionContext());
	}

	public void fireEvent(KernelEvent kernelEvent, KernelExecutionContext executionContext) {
		
		
		this.nextEvent = kernelEvent;
		if (!isOperating) {
			isOperating = true;
			while (nextEvent != null) {
				KernelEvent currentEvent = this.nextEvent;
				this.nextEvent = null;
				currentEvent.execute(executionContext);
			}
			isOperating = false;
		}

	}

	public Integer getKernelListenerIndex() {
		return KernelListenerIndex;
	}

	public void setKernelListenerIndex(int kernelListenerIndex) {
		KernelListenerIndex = kernelListenerIndex;
	}

	public void take(KernelExecutionContext executionContext, KernelSequenceFlow sequenceFlow) {
		// 执行线条的进入方法
		this.currentFlowNode.cleanData(executionContext);
		sequenceFlow.take(executionContext);
	}

	public void take(KernelExecutionContext executionContext, KernelFlowNodeImpl flowNode) {
		this.currentFlowNode.cleanData(executionContext);
		flowNode.enter(executionContext);
	}

	public void end() {
		isActive = false;
		isEnded = true;
	}

	public void inactivate() {
		this.isActive = false;
	}

	public boolean isEnded() {
		return isEnded;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public KernelBaseElement getEventSource() {
		return eventSource;
	}

	public void setEventSource(KernelBaseElement eventSource) {
		this.eventSource = eventSource;
	}

}
