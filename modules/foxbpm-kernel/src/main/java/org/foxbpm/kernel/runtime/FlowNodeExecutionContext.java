/**
 * 
 */
package org.foxbpm.kernel.runtime;

import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

/**
 * @author kenshin
 * 
 */
public interface FlowNodeExecutionContext extends DelegateExecutionContext {
	
	KernelFlowNode getFlowNode();
	void execute();

	void signal();

	public void take(KernelSequenceFlow sequenceFlow);

	public void take(KernelFlowNodeImpl flowNode);
	
	void end();
}
