package org.foxbpm.engine.impl.bpmn.behavior;

public class FormalExpressionBehavior extends ExpressionDefinition {
	
	protected String body;
	
	protected String language;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
