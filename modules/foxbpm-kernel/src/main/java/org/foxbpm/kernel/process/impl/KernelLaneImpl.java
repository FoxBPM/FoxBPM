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
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelLane;
import org.foxbpm.kernel.process.KernelLaneSet;

public class KernelLaneImpl extends KernelBaseElementImpl implements KernelLane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected KernelLaneSet childLaneSet;
	public KernelLaneImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}

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
		this.childLaneSet = childLaneSet;
	}

	public KernelBaseElement getPartitionElementRef() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPartitionElementRef(KernelBaseElement value) {
		// TODO Auto-generated method stub

	}
}
