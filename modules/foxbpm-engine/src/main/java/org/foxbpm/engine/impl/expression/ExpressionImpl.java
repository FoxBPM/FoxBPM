/**
 * Copyright 1996-2014 FoxBPM ORG.
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
