package org.foxbpm.model;

import java.util.List;

public class Event extends FlowNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<EventDefinition> eventDefinitions;

	public List<EventDefinition> getEventDefinitions() {
		return eventDefinitions;
	}

	public void setEventDefinitions(List<EventDefinition> eventDefinitions) {
		this.eventDefinitions = eventDefinitions;
	}

}
