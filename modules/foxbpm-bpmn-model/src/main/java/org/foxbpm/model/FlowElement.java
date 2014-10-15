package org.foxbpm.model;

import java.util.List;

public class FlowElement extends BaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected List<Connector> connector;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Connector> getConnector() {
		return connector;
	}

	public void setConnector(List<Connector> connector) {
		this.connector = connector;
	}

}
