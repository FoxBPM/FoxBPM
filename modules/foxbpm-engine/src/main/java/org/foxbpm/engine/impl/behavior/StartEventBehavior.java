package org.foxbpm.engine.impl.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class StartEventBehavior extends CatchEventBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isPersistence;

	public boolean isPersistence() {
		return isPersistence;
	}

	public void setPersistence(boolean isPersistence) {
		this.isPersistence = isPersistence;
	}
	
	@Override
	public void enter(FlowNodeExecutionContext executionContext) {
		System.out.println("startTask进入");
		executionContext.execute();
	}
	
	@Override
	public void execute(FlowNodeExecutionContext executionContext) {
		// TODO Auto-generated method stub
		System.out.println("startTask执行");
		executionContext.signal();
	}
	
}
