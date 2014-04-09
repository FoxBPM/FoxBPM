/**
 * 
 */
package org.foxbpm.kernel.behavior;

import java.io.Serializable;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public interface KernelFlowNodeBehavior extends Serializable {
	
	void enter(FlowNodeExecutionContext executionContext);
	
	void execute(FlowNodeExecutionContext executionContext);
	
	void leave(FlowNodeExecutionContext executionContext);
	
	void cleanData(FlowNodeExecutionContext executionContext);

}
