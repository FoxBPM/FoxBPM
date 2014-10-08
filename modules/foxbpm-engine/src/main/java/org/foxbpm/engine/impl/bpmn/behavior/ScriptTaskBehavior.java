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
package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class ScriptTaskBehavior extends TaskBehavior {

	private static final long serialVersionUID = 1L;
	
	protected String scriptFormat;

	protected Expression script;
	
	public String getScriptFormat() {
		return scriptFormat;
	}

	public void setScriptFormat(String scriptFormat) {
		this.scriptFormat = scriptFormat;
	}

	public Expression getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = new ExpressionImpl(script);
	}
	
	 
	public void execute(FlowNodeExecutionContext executionContext) {
		
		
		getScript().getValue(executionContext);
		
		executionContext.signal();
		
	}

}
