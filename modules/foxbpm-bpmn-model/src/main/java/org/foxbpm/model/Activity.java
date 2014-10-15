package org.foxbpm.model;

import java.util.List;

public class Activity extends FlowNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SkipStrategy skipStrategy;

	protected LoopCharacteristics loopCharacteristics;

	protected List<BoundaryEvent> boundaryEvents;

	public SkipStrategy getSkipStrategy() {
		return skipStrategy;
	}

	public void setSkipStrategy(SkipStrategy skipStrategy) {
		this.skipStrategy = skipStrategy;
	}

	public LoopCharacteristics getLoopCharacteristics() {
		return loopCharacteristics;
	}

	public void setLoopCharacteristics(LoopCharacteristics loopCharacteristics) {
		this.loopCharacteristics = loopCharacteristics;
	}

	public List<BoundaryEvent> getBoundaryEvents() {
		return boundaryEvents;
	}

	public void setBoundaryEvents(List<BoundaryEvent> boundaryEvents) {
		this.boundaryEvents = boundaryEvents;
	}

}
