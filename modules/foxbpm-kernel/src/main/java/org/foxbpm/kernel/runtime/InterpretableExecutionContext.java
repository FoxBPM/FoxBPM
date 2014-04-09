/**
 * 
 */
package org.foxbpm.kernel.runtime;

import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * @author kenshin
 * 
 */
public interface InterpretableExecutionContext extends FlowNodeExecutionContext, ListenerExecutionContext,KernelToken {

	void enter(KernelFlowNodeImpl flowNode);
	
	void setFlowNode(KernelFlowNodeImpl flowNode);
	
	KernelSequenceFlowImpl getSequenceFlow();

	void setSequenceFlow(KernelSequenceFlowImpl sequenceFlow);

	Integer getKernelListenerIndex();

	void setKernelListenerIndex(int kernelListenerIndex);

	void setEventName(String eventName);

	void setEventSource(KernelBaseElement eventSource);
	
	void fireEvent(KernelEvent kernelEvent);
	
	KernelProcessDefinitionImpl getProcessDefinition();

}
