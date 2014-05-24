package org.foxbpm.engine.impl.expression;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.KernelVariableScope;

public class ExpressionImpl implements Expression {

	private static final long serialVersionUID = 1L;
	
	protected String expressionText;
	
	public ExpressionImpl(String expressionText){
		this.expressionText=expressionText;
	}

	public Object getValue(FlowNodeExecutionContext executionContext) {
		if(StringUtil.isNotEmpty(expressionText)){
			return ExpressionMgmt.execute(expressionText,executionContext);
		}
		return null;
	}

	public void setValue(Object value, KernelVariableScope variableScope) {
		
	}

	public String getExpressionText() {
		return expressionText;
	}

	public boolean isNullText() {
		return StringUtil.isEmpty(expressionText);
	}

}
