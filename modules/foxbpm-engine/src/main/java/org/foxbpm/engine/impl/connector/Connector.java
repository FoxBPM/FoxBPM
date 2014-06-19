/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.engine.impl.connector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

public class Connector implements KernelListener {


	private static final long serialVersionUID = 1L;
	protected String connectorId;
	protected String packageName;
	protected String className;
	protected String connectorInstanceId;
	protected String connectorInstanceName;
 	protected String eventType;
	protected String documentation;
	protected String errorHandling;
	protected String errorCode;
	protected List<ConnectorInputParam> connectorInputsParam;
	
	protected List<ConnectorOutputParam> connectorOutputsParam;
	

	protected boolean isTimeExecute = false;

	protected Expression timeExpression;

	protected Expression skipExpression;

	public String getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

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

	public Expression getSkipExpression() {
		return skipExpression;
	}

	public void setSkipExpression(Expression skipExpression) {
		this.skipExpression = skipExpression;
	}

	public boolean isTimeExecute() {
		return isTimeExecute;
	}

	public void setTimeExecute(boolean isTimeExecute) {
		this.isTimeExecute = isTimeExecute;
	}

	public Expression getTimeExpression() {
		return timeExpression;
	}

	public void setTimeExpression(Expression timeExpression) {
		this.timeExpression = timeExpression;
	}

	public List<ConnectorInputParam> getConnectorInputsParam() {

		if (this.connectorInputsParam == null) {
			this.connectorInputsParam = new ArrayList<ConnectorInputParam>();
		}

		return this.connectorInputsParam;
	}

	public List<ConnectorOutputParam> getConnectorOutputsParam() {

		if (this.connectorOutputsParam == null) {
			this.connectorOutputsParam = new ArrayList<ConnectorOutputParam>();
		}
		return connectorOutputsParam;
	}
	
	public void setConnectorInputsParam(List<ConnectorInputParam> connectorInputsParam) {
		this.connectorInputsParam = connectorInputsParam;
	}

	public void setConnectorOutputsParam(List<ConnectorOutputParam> connectorOutputsParam) {
		this.connectorOutputsParam = connectorOutputsParam;
	}

	public void notify(ListenerExecutionContext executionContext) throws Exception  {
		// TODO Auto-generated method stub

		try {

			if (this.skipExpression != null && this.skipExpression.getExpressionText() != null && !this.skipExpression.getExpressionText().equals("")) {
				Object timeExpressionObj = skipExpression.getValue((FlowNodeExecutionContext)executionContext);
				if (StringUtil.getBoolean(timeExpressionObj)) {
					return;
				}
			}

			String classNameObj = packageName + "." + className;
			Class<?> connectorHandlerClass = Class.forName(classNameObj);
			FlowConnectorHandler connectorInstance = (FlowConnectorHandler) connectorHandlerClass.newInstance();

			for (ConnectorInputParam connectorParameterInputs : this.getConnectorInputsParam()) {

				Class<?> ptypes[] = new Class[1];

				ptypes[0] = Class.forName(connectorParameterInputs.getDataType());

				String parameterInputsId = connectorParameterInputs.getId();

				String methodString = "set" + parameterInputsId.substring(0, 1).toUpperCase()
						+ parameterInputsId.substring(1, parameterInputsId.length());
				Method m = connectorHandlerClass.getMethod(methodString, ptypes);

				if (connectorParameterInputs.getExpression() != null) {
					
					if (!connectorParameterInputs.getExpression().isNullText()&&connectorParameterInputs.isExecute()) {
						Object arg[] = new Object[1];
						arg[0] = connectorParameterInputs.getExpression().getValue((FlowNodeExecutionContext)executionContext);
						m.invoke(connectorInstance, arg);
					}
				}

			}

			connectorInstance.execute((ConnectorExecutionContext)executionContext);

			for (ConnectorOutputParam connectorParameterOutputs : this.getConnectorOutputsParam()) {

				if (!StringUtil.isEmpty(connectorParameterOutputs.getOutputId())) {
					String parameterOutputsId = connectorParameterOutputs.getOutputId();

					String methodString = "get" + parameterOutputsId.substring(0, 1).toUpperCase()
							+ parameterOutputsId.substring(1, parameterOutputsId.length());
					Method m = connectorHandlerClass.getMethod(methodString);

					String variableTarget = connectorParameterOutputs.getVariableTarget();
					// Object arg[] = new Object[1];
					// arg[0] =Context.getBshInterpreter().eval(scriptString);

					Object objectValue = m.invoke(connectorInstance);

					ExpressionMgmt.setVariable(variableTarget, objectValue, (FlowNodeExecutionContext)executionContext);
				}

			}

		} catch (Exception e) {

			throw new FoxBPMConnectorException(e.getMessage(), e);

		}
	}

	

}
