/**
 * 
 */
package org.foxbpm.engine.execution;

import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * @author kenshin
 *
 */
public interface ConnectorExecutionContext extends ListenerExecutionContext {
	
	
	String getInitiator();
	
	String getAuthenticatedUserId();
	

}
