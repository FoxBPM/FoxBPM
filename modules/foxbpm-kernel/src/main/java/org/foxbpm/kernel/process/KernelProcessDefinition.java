/**
 * 
 */
package org.foxbpm.kernel.process;

import org.foxbpm.kernel.runtime.KernelProcessInstance;


/**
 * @author kenshin
 *
 */
public interface KernelProcessDefinition extends KernelCallableElement,KernelReadOnlyProcessDefinition,KernelFlowElementsContainer {
	
	
	String getDeploymentId();
	
	KernelProcessInstance createProcessInstance();


}
