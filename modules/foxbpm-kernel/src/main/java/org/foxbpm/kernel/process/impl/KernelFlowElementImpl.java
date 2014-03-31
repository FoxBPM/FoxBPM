package org.foxbpm.kernel.process.impl;

import org.foxbpm.kernel.process.KernelFlowElement;





public class KernelFlowElementImpl extends KernelBaseElementImpl implements KernelFlowElement {


	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	public KernelFlowElementImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

	
	// getters and setters
	// //////////////////////////////////////////////////////

	public void setName(String name){
    	this.name=name;
    }


	

	public String getName() {
		return this.name;
	}

}
