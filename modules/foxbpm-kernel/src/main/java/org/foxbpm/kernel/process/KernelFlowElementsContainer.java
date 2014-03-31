/**
 * 
 */
package org.foxbpm.kernel.process;

import java.util.List;

import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;


/**
 * @author kenshin
 * 
 */
public interface KernelFlowElementsContainer extends KernelBaseElement {

	List<KernelLaneSet> getLaneSets();

	List<? extends KernelFlowElement> getFlowNodes();
	
	KernelFlowNodeImpl findFlowNode(String flowNodeId);
	
	boolean contains(String flowNodeId);

}
