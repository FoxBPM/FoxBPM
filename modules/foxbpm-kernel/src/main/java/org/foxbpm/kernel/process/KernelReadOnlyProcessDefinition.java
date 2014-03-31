/**
 * 
 */
package org.foxbpm.kernel.process;

/**
 * @author kenshin
 * 
 */
public interface KernelReadOnlyProcessDefinition extends KernelCallableElement, KernelFlowElementsContainer {

	String getName();

	String getKey();

	String getDescription();

	KernelFlowNode getInitial();

	String getDiagramResourceName();

}
