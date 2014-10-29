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
 * @author ych
 */
package org.foxbpm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点元素
 * 
 * @author ych
 * 
 */
public abstract class FlowNode extends FlowElement {

	private static final long serialVersionUID = 1L;

	/**
	 * 进入线条编号集合
	 */
	protected List<String> incomingFlows = new ArrayList<String>();

	/**
	 * 流出线条编号集合
	 */
	protected List<String> outgoingFlows = new ArrayList<String>();

	public List<String> getIncomingFlows() {
		return incomingFlows;
	}

	public void setIncomingFlows(List<String> incomingFlows) {
		this.incomingFlows = incomingFlows;
	}

	public List<String> getOutgoingFlows() {
		return outgoingFlows;
	}

	public void setOutgoingFlows(List<String> outgoingFlows) {
		this.outgoingFlows = outgoingFlows;
	}

}
