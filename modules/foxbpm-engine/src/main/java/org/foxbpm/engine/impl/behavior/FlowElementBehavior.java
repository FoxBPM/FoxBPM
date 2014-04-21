package org.foxbpm.engine.impl.behavior;

public abstract class FlowElementBehavior extends BaseElementBehavior {
	
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
