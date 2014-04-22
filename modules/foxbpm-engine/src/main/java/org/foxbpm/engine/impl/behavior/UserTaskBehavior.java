package org.foxbpm.engine.impl.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class UserTaskBehavior extends TaskBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String formUri;

	public String getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = formUri;
	}
	
	@Override
	public void enter(FlowNodeExecutionContext executionContext) {
		System.out.println("UserTaskEnter");
	}
	
}
