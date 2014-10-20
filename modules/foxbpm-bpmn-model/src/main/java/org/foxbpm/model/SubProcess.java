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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内部子流程
 * 
 * @author ych
 * 
 */
public class SubProcess extends Activity implements FlowContainer {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 流程元素集合
	 */
	protected List<FlowElement> flowElements;
	
	/**
	 * 线条map
	 */
	protected Map<String, SequenceFlow> sequenceFlows;
	
	public List<FlowElement> getFlowElements() {
		return flowElements;
	}
	
	public void setFlowElements(List<FlowElement> flowElements) {
		this.flowElements = flowElements;
	}
	
	public void addFlowElement(FlowElement flowElement) {
		if (null == flowElements) {
			flowElements = new ArrayList<FlowElement>();
		}
		flowElements.add(flowElement);
	}
	
	public void addSequenceFlow(SequenceFlow sequenceFlow) {
		if (null == sequenceFlows) {
			sequenceFlows = new HashMap<String, SequenceFlow>();
		}
		sequenceFlows.put(sequenceFlow.getId(), sequenceFlow);
	}
}
