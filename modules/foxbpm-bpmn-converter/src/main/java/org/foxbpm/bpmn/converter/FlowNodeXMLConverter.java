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
 * @author yangguangftlp
 */
package org.foxbpm.bpmn.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.FlowNode;

/**
 * 流程节点转换处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class FlowNodeXMLConverter extends FlowElementXMLConverter {
	
	@SuppressWarnings("rawtypes")
	@Override
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		
		FlowNode flowNode = (FlowNode) baseElement;
		String nodeName = null;
		Element elem = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_OUTGOING.equalsIgnoreCase(nodeName)) {
				List<String> outgoingFlows = flowNode.getOutgoingFlows();
				if (null == outgoingFlows) {
					outgoingFlows = new ArrayList<String>();
					flowNode.setOutgoingFlows(outgoingFlows);
				}
				outgoingFlows.add(BpmnXMLUtil.parse0utgoing(elem));
			} else if (BpmnXMLConstants.ELEMENT_INCOMING.equalsIgnoreCase(nodeName)) {
				List<String> incomingFlows = flowNode.getIncomingFlows();
				if (null == incomingFlows) {
					incomingFlows = new ArrayList<String>();
					flowNode.setIncomingFlows(incomingFlows);
				}
				incomingFlows.add(BpmnXMLUtil.parseIncoming(elem));
			}
		}
		super.convertXMLToModel(element, baseElement);
	}
	
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		// TODO Auto-generated method stub
		super.convertModelToXML(element, baseElement);
	}
}
