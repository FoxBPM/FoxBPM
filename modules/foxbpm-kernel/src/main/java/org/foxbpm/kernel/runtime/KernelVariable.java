/**
 * 
 */
package org.foxbpm.kernel.runtime;

import java.util.Map;

/**
 * @author kenshin
 * 
 */
public interface KernelVariable {

	boolean hasVariable(String variableName);

	void setVariable(String variableName, Object value);

	Object getVariable(String variableName);

	Map<String, Object> getVariables();

}
