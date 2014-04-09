package org.foxbpm.kernel.test.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class AutomaticBehavior extends CommonNodeBehavior {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(FlowNodeExecutionContext executionContext) {
		LOG.debug("执行节点: "+executionContext.getFlowNode().getId());
		executionContext.signal();
	}

	

}
