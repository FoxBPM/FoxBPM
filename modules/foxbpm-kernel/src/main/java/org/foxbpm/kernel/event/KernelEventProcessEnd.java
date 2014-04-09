package org.foxbpm.kernel.event;

import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public class KernelEventProcessEnd extends AbstractKernelEvent {

	@Override
	protected KernelFlowElementsContainerImpl getContainer(InterpretableExecutionContext executionContext) {
		return executionContext.getProcessDefinition();
	}

	@Override
	protected String getEventName() {
		return KernelEventType.EVENTTYPE_PROCESS_END;
	}

	@Override
	protected void eventNotificationsCompleted(InterpretableExecutionContext executionContext) {
		// TODO Auto-generated method stub
		
	}

}
