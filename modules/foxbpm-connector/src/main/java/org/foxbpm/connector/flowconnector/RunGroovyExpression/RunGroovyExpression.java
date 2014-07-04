package org.foxbpm.connector.flowconnector.RunGroovyExpression;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.StringUtil;

public class RunGroovyExpression implements FlowConnectorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1923171231930850489L;

	private java.lang.String expressionText;

	private java.lang.Object outputObj;

	public void execute(ConnectorExecutionContext executionContext) throws Exception {
		if (StringUtil.isNotEmpty(expressionText)) {
			outputObj = ExpressionMgmt.execute(expressionText, executionContext);
		}
	}

	public void setExpressionText(java.lang.String expressionText) {
		this.expressionText = expressionText;
	}

	public java.lang.Object getOutputObj() {
		return outputObj;
	}
}