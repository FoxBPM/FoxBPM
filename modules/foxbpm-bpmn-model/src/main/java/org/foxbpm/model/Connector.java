package org.foxbpm.model;

import java.util.List;

public class Connector extends BaseElement {

	protected String packageName;
	protected String className;
	protected String connectorInstanceId;
	protected String connectorInstanceName;
	protected String eventType;
	protected String documentation;
	protected String errorHandling;
	protected String errorCode;
	protected List<InputParam> inputsParam;
	protected List<OutputParam> outputsParam;
	protected TimerEventDefinition timerEventDefinition;
	protected String skipExpression;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getConnectorInstanceId() {
		return connectorInstanceId;
	}

	public void setConnectorInstanceId(String connectorInstanceId) {
		this.connectorInstanceId = connectorInstanceId;
	}

	public String getConnectorInstanceName() {
		return connectorInstanceName;
	}

	public void setConnectorInstanceName(String connectorInstanceName) {
		this.connectorInstanceName = connectorInstanceName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getErrorHandling() {
		return errorHandling;
	}

	public void setErrorHandling(String errorHandling) {
		this.errorHandling = errorHandling;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<InputParam> getInputsParam() {
		return inputsParam;
	}

	public void setInputsParam(List<InputParam> inputsParam) {
		this.inputsParam = inputsParam;
	}

	public List<OutputParam> getOutputsParam() {
		return outputsParam;
	}

	public void setOutputsParam(List<OutputParam> outputsParam) {
		this.outputsParam = outputsParam;
	}

	public TimerEventDefinition getTimerEventDefinition() {
		return timerEventDefinition;
	}

	public void setTimerEventDefinition(TimerEventDefinition timerEventDefinition) {
		this.timerEventDefinition = timerEventDefinition;
	}

	public String getSkipExpression() {
		return skipExpression;
	}

	public void setSkipExpression(String skipExpression) {
		this.skipExpression = skipExpression;
	}

}
