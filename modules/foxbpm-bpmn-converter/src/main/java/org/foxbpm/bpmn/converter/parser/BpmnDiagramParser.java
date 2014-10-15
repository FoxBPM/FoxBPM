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
package org.foxbpm.bpmn.converter.parser;

import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.BpmnModel;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class BpmnDiagramParser extends BpmnParser {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.foxbpm.bpmn.converter.parser.BpmnParser#parse(org.w3c.dom.Element,
	 * org.foxbpm.model.BpmnModel)
	 */
	@Override
	public void parse(Element element, BpmnModel model) throws Exception {
		NodeList nodeList = element.getChildNodes();
		Node node = null;
		String name = null;
		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			node = nodeList.item(i);
			name = BpmnXMLUtil.getEleLoclaName(node.getNodeName());
			if (node instanceof Element) {
				if (ELEMENT_DI_PLANE.equalsIgnoreCase(name) || ELEMENT_DI_SHAPE.equalsIgnoreCase(name)
				        || ELEMENT_DI_EDGE.equalsIgnoreCase(name)) {
					parse((Element) node, model);
					break;
				}
				if (ELEMENT_DI_BOUNDS.equalsIgnoreCase(name)) {
					
				} else if (ELEMENT_DI_WAYPOINT.equalsIgnoreCase(name)) {
					
				}
			}
		}
	}
}
