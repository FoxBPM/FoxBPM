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

import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.bpmn.behavior.TimerEventBehavior;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.foxbpm.model.Connector;
import org.foxbpm.model.InputParam;
import org.foxbpm.model.OutputParam;

public class ConnectorListener implements KernelListener {
	private static final long serialVersionUID = 1L;
	private static final String SETFUNCTION_PREFFIX = "set";
	private static final String GETFUNCTION_PREFFIX = "get";
	
	private Connector connector;

	public void notify(ListenerExecutionContext executionContext) throws Exception {
		String skipExpression = connector.getSkipExpression();
		
		if (StringUtil.isNotEmpty(skipExpression)) {
			Object skipExpressionObj = new ExpressionImpl(skipExpression).getValue(
					(FlowNodeExecutionContext) executionContext);
			if (StringUtil.getBoolean(skipExpressionObj)) {
				return;
			}
		}
		
		TimerEventBehavior timerBehavior = null;
		if(connector.getTimerEventDefinition() != null){
			timerBehavior = new TimerEventBehavior(connector.getTimerEventDefinition());
		}
		if (timerBehavior != null) {
			// 定时器执行方式
			Object[] params = new String[]{connector.getConnectorInstanceId(), connector.getEventType(),
					executionContext.getEventSource().getId()};
			timerBehavior.execute((KernelTokenImpl) executionContext,
					TimerEventBehavior.EVENT_TYPE_CONNECTOR, params);
		} else {
			// 直接执行方式
			execute(executionContext);
		}
	}

	public void execute(ListenerExecutionContext executionContext) throws Exception {
		
		String packageName = connector.getPackageName();
		String className = connector.getClassName();
		try {
			String classNameObj = packageName + "." + className;
			Class<?> connectorHandlerClass = Class.forName(classNameObj);
			FlowConnectorHandler connectorInstance = (FlowConnectorHandler) connectorHandlerClass
					.newInstance();

			FlowNodeExecutionContext flowNodeExecutionContext = (FlowNodeExecutionContext) executionContext;
			for (InputParam connectorParameterInputs : connector.getInputsParam()) {
				Class<?> ptypes[] = new Class[1];
				ptypes[0] = Class.forName(connectorParameterInputs.getDataType());
				String parameterInputsId = connectorParameterInputs.getId();
				String methodString = SETFUNCTION_PREFFIX
						+ parameterInputsId.substring(0, 1).toUpperCase()
						+ parameterInputsId.substring(1, parameterInputsId.length());
				Method m = connectorHandlerClass.getMethod(methodString, ptypes);
				if (connectorParameterInputs.getExpression() != null) {
					Object arg[] = new Object[1];
					if (StringUtil.isNotEmpty(connectorParameterInputs.getExpression())
							&& connectorParameterInputs.isExecute()) {
						arg[0] = new ExpressionImpl(connectorParameterInputs.getExpression()).getValue(
								flowNodeExecutionContext);
					} else {
						arg[0] = connectorParameterInputs.getExpression();
					}
					m.invoke(connectorInstance, arg);
				}

			}

			connectorInstance.execute((ConnectorExecutionContext) executionContext);
			for (OutputParam connectorParameterOutputs : connector.getOutputsParam()) {
				if (!StringUtil.isEmpty(connectorParameterOutputs.getVariableTarget())) {
					String parameterOutputsId = connectorParameterOutputs.getOutput();
					String methodString = GETFUNCTION_PREFFIX
							+ parameterOutputsId.substring(0, 1).toUpperCase()
							+ parameterOutputsId.substring(1, parameterOutputsId.length());
					Method m = connectorHandlerClass.getMethod(methodString);
					String variableTarget = connectorParameterOutputs.getVariableTarget();
					Object objectValue = m.invoke(connectorInstance);
					ExpressionMgmt.setVariable(variableTarget, objectValue,
							flowNodeExecutionContext);
				}
			}

		} catch (Exception e) {
			throw new FoxBPMConnectorException(e.getMessage(), e);
		}
	}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}
}
