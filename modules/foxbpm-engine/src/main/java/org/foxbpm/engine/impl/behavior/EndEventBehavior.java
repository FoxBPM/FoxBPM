package org.foxbpm.engine.impl.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class EndEventBehavior extends EventBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void enter(FlowNodeExecutionContext executionContext) {
		System.out.println("end进入");
		executionContext.execute();
	}
	
	public void execute(FlowNodeExecutionContext executionContext) {
		System.out.print("end结束");
		executionContext.end();
	}
	
}
