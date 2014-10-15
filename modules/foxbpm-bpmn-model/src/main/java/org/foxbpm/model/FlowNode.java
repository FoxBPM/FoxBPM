package org.foxbpm.model;

import java.util.ArrayList;
import java.util.List;

public class FlowNode extends FlowElement {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<SequenceFlow> incomingFlows = new ArrayList<SequenceFlow>();
	protected List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();
	
	public List<SequenceFlow> getIncomingFlows() {
		return incomingFlows;
	}
	public void setIncomingFlows(List<SequenceFlow> incomingFlows) {
		this.incomingFlows = incomingFlows;
	}
	public List<SequenceFlow> getOutgoingFlows() {
		return outgoingFlows;
	}
	public void setOutgoingFlows(List<SequenceFlow> outgoingFlows) {
		this.outgoingFlows = outgoingFlows;
	}
	
	

}
