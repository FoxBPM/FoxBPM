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
 * @author ych
 */
package org.foxbpm.engine.impl.scriptlanguage;

import groovy.lang.GroovyShell;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.scriptlanguage.AbstractScriptLanguageMgmt;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.model.DataVariableDefinition;

public class GroovyScriptLanguageMgmtImpl extends AbstractScriptLanguageMgmt {

	private GroovyShell groovyShell;

	public AbstractScriptLanguageMgmt init() {
		groovyShell = new GroovyShell();
		return this;
	}

	public void close() {
		groovyShell = null;
	}

	public Object execute(String scriptText, ProcessDefinitionEntity processDefinition) {
		List<String> dvList = getDataVariableList(scriptText);
		if (dvList.size() > 0) {
			for (String expressionId : dvList) {
				List<DataVariableDefinition> dataVariableBehaviors = processDefinition.getDataVariableMgmtDefinition().getDataVariableBehaviorsByProcess();
				for (DataVariableDefinition dataVariableBehavior : dataVariableBehaviors) {
					if (StringUtils.equals(dataVariableBehavior.getId(), expressionId)) {
						Object object = null;
						String expression = dataVariableBehavior.getExpression();
						if (StringUtil.isEmpty(expression)) {
							object = ExpressionMgmt.execute(expression, processDefinition);
						}
						ExpressionMgmt.setVariable(expressionId, object);
					}
				}
			}
		}
		String scriptTextTemp = getExpressionAll(scriptText);
		return groovyShell.evaluate(scriptTextTemp);
	}

	public void setVariable(String variableName, Object variableObj) {
		groovyShell.setVariable(variableName, variableObj);
	}

	public void setVariable(String variableName, Object variableObj, FlowNodeExecutionContext executionContext) {
		dataVariableCalculate(variableName, executionContext);
		String scriptText = getExpressionAll(variableName);
		groovyShell.setVariable(scriptText, variableObj);
	}

	public Object getVariable(String variableName) {
		return groovyShell.getVariable(variableName);
	}

	public Object execute(String scriptText, FlowNodeExecutionContext executionContext) {
		if (scriptText == null) {
			return null;
		}
		Object resultObj = false;
		// 绑定变量
		if (executionContext != null) {
			dataVariableCalculate(scriptText, executionContext);
			groovyShell.setVariable("processInfo", executionContext);
		}
		String scriptTextTemp = getExpressionAll(scriptText);
		resultObj = groovyShell.evaluate(scriptTextTemp);
		return resultObj;
	}

	public Object execute(String scriptText) {
		return groovyShell.evaluate(scriptText);
	}

}
