package org.foxbpm.kernel.runtime.impl;

import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessInstanceImpl;
import org.foxbpm.kernel.runtime.KernelExecutionContext;
import org.foxbpm.kernel.runtime.KernelToken;

public class KernelTokenImpl implements KernelToken {
	
	
	protected KernelFlowNodeImpl currentFlowNode;

	public KernelFlowNodeImpl getFlowNode() {
		return currentFlowNode;
	}

	public void setFlowNode(KernelFlowNodeImpl flowNode) {
		this.currentFlowNode = flowNode;
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
		KernelExecutionContextImpl executionContext=new KernelExecutionContextImpl();
		executionContext.setToken(this);
		return executionContext;
	}
	
	public void execute(KernelExecutionContext executionContext) {
		this.currentFlowNode.execute(executionContext);
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

	
	

}
