/**
 * 
 */
package org.foxbpm.kernel.runtime;

import org.foxbpm.kernel.process.KernelBaseElement;


/**
 * @author kenshin
 *
 */
public interface ListenerExecutionContext extends DelegateExecutionContext {
	
	  String getEventName();

	  KernelBaseElement getEventSource();

}
