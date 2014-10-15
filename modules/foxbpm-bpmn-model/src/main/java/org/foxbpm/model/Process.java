package org.foxbpm.model;

import java.util.List;

public class Process extends BaseElement {

	protected List<FlowNode> flowNodes;
	
	protected List<SequenceFlow> sequenceFlows;
}
