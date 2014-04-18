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
package org.foxbpm.kernel.runtime;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author kenshin
 * 
 */
public interface KernelVariableScope {

	Map<String, Object> getVariables();

	Map<String, Object> getVariablesLocal();

	Object getVariable(String variableName);

	Object getVariableLocal(Object variableName);

	Set<String> getVariableNames();

	Set<String> getVariableNamesLocal();

	void setVariable(String variableName, Object value);

	Object setVariableLocal(String variableName, Object value);

	void setVariables(Map<String, ? extends Object> variables);

	void setVariablesLocal(Map<String, ? extends Object> variables);

	boolean hasVariables();

	boolean hasVariablesLocal();

	boolean hasVariable(String variableName);

	boolean hasVariableLocal(String variableName);

	void createVariableLocal(String variableName, Object value);

	void removeVariable(String variableName);

	void removeVariableLocal(String variableName);

	void removeVariables(Collection<String> variableNames);

	void removeVariablesLocal(Collection<String> variableNames);

	void removeVariables();

	void removeVariablesLocal();

}
