package org.foxbpm.model;

public class SequenceFlow extends FlowElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int flowCondition;

	/**
	 * @roseuid 543D1EA102D3
	 */
	public SequenceFlow() {

	}

	/**
	 * Access method for the flowCondition property.
	 * 
	 * @return the current value of the flowCondition property
	 */
	public int getFlowCondition() {
		return flowCondition;
	}

	/**
	 * Sets the value of the flowCondition property.
	 * 
	 * @param aFlowCondition
	 *            the new value of the flowCondition property
	 */
	public void setFlowCondition(int aFlowCondition) {
		flowCondition = aFlowCondition;
	}
}
