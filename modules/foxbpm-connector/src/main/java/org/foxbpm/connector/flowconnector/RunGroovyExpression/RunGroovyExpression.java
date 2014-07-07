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
 * @author yangguangftlp
 */
package org.foxbpm.connector.flowconnector.RunGroovyExpression;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;
import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * groovy 脚本执行
 * 
 * @author yangguangftlp
 * @date 2014年7月7日
 */
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