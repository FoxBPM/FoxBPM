/**
 * 
 */
package org.foxbpm.kernel.runtime;

import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;


/**
 * @author kenshin
 *
 */
public interface InterpretableProcessInstance extends KernelProcessInstance {

	
	void initialize();
	
	void setProcessDefinition(KernelProcessDefinitionImpl processDefinition);

}
