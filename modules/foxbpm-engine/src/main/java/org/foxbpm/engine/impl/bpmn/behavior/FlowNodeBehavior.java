package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;


public abstract class FlowNodeBehavior extends FlowElementBehavior implements KernelFlowNodeBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void enter(FlowNodeExecutionContext executionContext) {
		executionContext.execute();
	}

	public void execute(FlowNodeExecutionContext executionContext) {
		executionContext.signal();
	}

	public void leave(FlowNodeExecutionContext executionContext) {
		((KernelTokenImpl)executionContext).leave();
	}

	public void cleanData(FlowNodeExecutionContext executionContext) {
		// TODO Auto-generated method stub
		
	}

}
