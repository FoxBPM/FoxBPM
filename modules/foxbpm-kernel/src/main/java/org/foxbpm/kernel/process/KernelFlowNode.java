/**
 * 
 */
package org.foxbpm.kernel.process;

import java.util.List;



/**
 * @author kenshin
 * 
 */
public interface KernelFlowNode extends KernelFlowElement {


	boolean isAsync();

	boolean isExclusive();

	KernelFlowElementsContainer getParent();

	List<KernelSequenceFlow> getIncomingSequenceFlows();

	List<KernelSequenceFlow> getOutgoingSequenceFlows();

	KernelSequenceFlow findOutgoingSequenceFlow(String sequenceFlowId);
	
	
}
