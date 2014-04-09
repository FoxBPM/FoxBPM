package org.foxbpm.kernel.event;

import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public class KernelEventNodeEnter extends AbstractKernelEvent {

	@Override
	protected KernelFlowElementsContainerImpl getContainer(InterpretableExecutionContext executionContext) {
		return (KernelFlowElementsContainerImpl)executionContext.getFlowNode();
	}

	@Override
	protected String getEventName() {
		return KernelEventType.EVENTTYPE_NODE_ENTER;
	}

	@Override
	protected void eventNotificationsCompleted(InterpretableExecutionContext executionContext) {
		// TODO Auto-generated method stub
		
	}

}
