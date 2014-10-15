package org.foxbpm.model;
public class InputParam extends BaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String name;

	protected String dataType;

	protected boolean isExecute = true;

	protected String expression;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isExecute() {
		return isExecute;
	}

	public void setExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
