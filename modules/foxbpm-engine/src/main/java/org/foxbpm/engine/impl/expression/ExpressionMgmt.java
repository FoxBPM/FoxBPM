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
package org.foxbpm.engine.impl.expression;


import java.util.Map;

import com.founder.fix.bpmn2extensions.sqlmappingconfig.Sql;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.runtime.ExecutionContext;



public class ExpressionMgmt {

	
	public static Object execute(String scriptText) {

		return Context.getAbstractScriptLanguageMgmt().execute(scriptText);
		
	}
	
	public static Object execute(Sql sql,Map<String, Object> variableMap) {
		
		
		for (String variableKey : variableMap.keySet()) {
			setVariable(variableKey, variableMap.get(variableKey));
		}

		return Context.getAbstractScriptLanguageMgmt().execute(sql.getSqlValue());
		
	}

	public static Object execute(String scriptText, ExecutionContext executionContext) {

		return Context.getAbstractScriptLanguageMgmt().execute(scriptText, executionContext);
		
	}

	public static Object getVariable(String variableName) {
		return Context.getAbstractScriptLanguageMgmt().getVariable(variableName);
		
	}
	
	public static void setVariable(String variableName, Object variableObj,ExecutionContext executionContext) {

		Context.getAbstractScriptLanguageMgmt().setVariable(variableName,variableObj,executionContext);
		
	}
	

	public static void setVariable(String variableName, Object variableObj) {


		Context.getAbstractScriptLanguageMgmt().setVariable(variableName,variableObj);
		
	}

	public static Object execute(String scriptText, ProcessDefinitionBehavior processDefinition) {
		return Context.getAbstractScriptLanguageMgmt().execute(scriptText,processDefinition);
		
	}

	

}
