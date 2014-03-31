package org.foxbpm.kernel.process.impl;

import java.util.List;

import org.foxbpm.kernel.process.KernelLane;
import org.foxbpm.kernel.process.KernelLaneSet;

public class KernelLaneSetImpl extends KernelBaseElementImpl implements KernelLaneSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public KernelLaneSetImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}



	public List<KernelLane> getLanes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String value) {
		// TODO Auto-generated method stub

	}

}
