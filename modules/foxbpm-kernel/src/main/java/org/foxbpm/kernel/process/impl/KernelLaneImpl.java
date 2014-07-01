/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
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

	
	protected KernelLaneSet childLaneSet;
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
		return childLaneSet;
	}

	public void setChildLaneSet(KernelLaneSet childLaneSet) {
		this.childLaneSet=childLaneSet;
	}

	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public KernelBaseElement getPartitionElementRef() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPartitionElementRef(KernelBaseElement value) {
		// TODO Auto-generated method stub

	}
	
	// 图形信息
	protected int x = -1;
	protected int y = -1;
	protected int width = -1;
	protected int height = -1;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
