package org.foxbpm.kernel.test.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class EndBehavior extends CommonNodeBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void execute(FlowNodeExecutionContext executionContext) {
		executionContext.end();
	}

}
