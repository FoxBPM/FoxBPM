package org.foxbpm.engine.impl.event;

import org.foxbpm.engine.event.TaskEvent;
import org.foxbpm.kernel.event.AbstractKernelEvent;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public abstract class AbstractTaskEvent extends AbstractKernelEvent implements TaskEvent {
	
	

	@Override
	protected KernelFlowElementsContainerImpl getContainer(InterpretableExecutionContext executionContext) {
		return (KernelFlowElementsContainerImpl)executionContext.getFlowNode();
	}
	

}
