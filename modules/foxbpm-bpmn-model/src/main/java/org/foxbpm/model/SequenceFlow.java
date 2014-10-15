package org.foxbpm.model;

public class SequenceFlow extends FlowElement {

	private static final long serialVersionUID = 1L;
	private String flowCondition;

	public String getFlowCondition() {
		return flowCondition;
	}

	public void setFlowCondition(String flowCondition) {
		this.flowCondition = flowCondition;
	}

}
