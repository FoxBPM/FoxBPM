package org.foxbpm.model;

import java.io.Serializable;

public class BaseElement implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;

	protected String documentation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

}
