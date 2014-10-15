package org.foxbpm.model;

import java.util.List;

public class BaseElement {

	protected String id;
	
	protected List<Connector> connector;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
