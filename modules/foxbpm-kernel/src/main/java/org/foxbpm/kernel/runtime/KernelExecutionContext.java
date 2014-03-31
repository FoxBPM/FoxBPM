/**
 * 
 */
package org.foxbpm.kernel.runtime;

import java.util.Map;

import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * @author kenshin
 * 
 */
public interface KernelExecutionContext {

	KernelTokenImpl getToken();

	void setProperty(String name, Object value);

	Object getProperty(String name);

	Map<String, Object> getProperties();

}
