package org.foxbpm.model;

import java.io.Serializable;
import java.util.List;

public class BaseElement implements Serializable{

	private static final long serialVersionUID = 1L;

	protected String id;
	
	protected List<Connector> connector;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
