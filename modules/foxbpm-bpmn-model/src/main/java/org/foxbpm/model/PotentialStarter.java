package org.foxbpm.model;

public class PotentialStarter extends BaseElement {
	
	private static final long serialVersionUID = 1L;

	protected String resourceType;

	protected String expression;

	protected String documentation;

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
}
