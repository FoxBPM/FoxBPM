package org.foxbpm.kernel.process.impl;

import java.util.List;

import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.KernelDIBounds;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelLane;
import org.foxbpm.kernel.process.KernelLaneSet;

public class KernelLaneImpl extends KernelBaseElementImpl implements KernelLane, KernelDIBounds {

	public KernelLaneImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KernelBaseElement getPartitionElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPartitionElement(KernelBaseElement value) {
		// TODO Auto-generated method stub

	}

	public List<KernelFlowNode> getFlowNodeRefs() {
		// TODO Auto-generated method stub
		return null;
	}

	public KernelLaneSet getChildLaneSet() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setChildLaneSet(KernelLaneSet value) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String value) {
		// TODO Auto-generated method stub

	}

	public KernelBaseElement getPartitionElementRef() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPartitionElementRef(KernelBaseElement value) {
		// TODO Auto-generated method stub

	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	public void setHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

}
