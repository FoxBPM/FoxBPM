package org.foxbpm.kernel.process.impl;

import java.util.List;

import org.foxbpm.kernel.process.KernelDefinitions;
import org.foxbpm.kernel.process.KernelRootElement;

public class KernelDefinitionsImpl extends KernelBaseElementImpl implements KernelDefinitions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KernelDefinitionsImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
		// TODO Auto-generated constructor stub
	}

	public List<KernelRootElement> getRootElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
