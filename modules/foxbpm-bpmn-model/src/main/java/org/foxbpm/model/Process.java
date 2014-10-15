package org.foxbpm.model;

import java.util.List;

public class Process extends BaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<FlowNode> flowNodes;
	
	protected List<SequenceFlow> sequenceFlows;
}
