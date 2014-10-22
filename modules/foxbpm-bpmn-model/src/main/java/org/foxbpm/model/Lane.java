package org.foxbpm.model;

import java.util.ArrayList;
import java.util.List;

public class Lane extends FlowElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LaneSet childLaneSet;
	
	private List<String> flowElementRefs = new ArrayList<String>();
	
	public void setChildLaneSet(LaneSet childLaneSet) {
		this.childLaneSet = childLaneSet;
	}
	
	public LaneSet getChildLaneSet() {
		return childLaneSet;
	}
	
	public void setFlowElementRefs(List<String> flowElementRefs) {
		this.flowElementRefs = flowElementRefs;
	}
	
	public List<String> getFlowElementRefs() {
		return flowElementRefs;
	}
}
