/**
 * 
 */
package org.foxbpm.kernel.behavior;

import java.io.Serializable;

import org.foxbpm.kernel.runtime.KernelExecutionContext;

/**
 * @author kenshin
 *
 */
public interface KernelFlowNodeBehavior extends Serializable {
	
	void enter(KernelExecutionContext executionContext);
	
	void execute(KernelExecutionContext executionContext);
	
	void leave(KernelExecutionContext executionContext);
	
	void cleanData(KernelExecutionContext executionContext);

}
