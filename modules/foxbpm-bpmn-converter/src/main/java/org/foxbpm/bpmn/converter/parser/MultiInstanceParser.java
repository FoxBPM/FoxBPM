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

import java.util.Iterator;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.LoopCharacteristics;
import org.foxbpm.model.MultiInstanceLoopCharacteristics;

/**
 * 多实例解析
 * 
 * @author yangguangftlp
 * @date 2014年10月18日
 */
public class MultiInstanceParser {
	
	public static LoopCharacteristics parser(Element element) {
		if (!BpmnXMLConstants.ELEMENT_MULTIINSTANCELOOPCHARACTERISTICS.equalsIgnoreCase(element.getName())) {
			return null;
		}
		MultiInstanceLoopCharacteristics multiInstance = new MultiInstanceLoopCharacteristics();
		multiInstance.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
		multiInstance.setSequential(BpmnXMLUtil.parseBoolean(element.attributeValue(BpmnXMLConstants.ELEMENT_ISSEQUENTIAL)));
		parserElement(element, multiInstance);
		return multiInstance;
	}
	
	@SuppressWarnings("rawtypes")
	private static void parserElement(Element element, MultiInstanceLoopCharacteristics multiInstance) {
		Element elem = null;
		String parentName = element.getName();
		String nodeName = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_INPUTDATAITEM.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_OUTPUTDATAITEM.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_LOOPDATAINPUTCOLLECTION.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_LOOPDATAOUTPUTCOLLECTION.equalsIgnoreCase(nodeName)) {
				parserElement(elem, multiInstance);
				continue;
			}
			
			if (BpmnXMLConstants.ELEMENT_INPUTDATAITEM.equalsIgnoreCase(parentName)) {
				multiInstance.setInputDataItem(BpmnXMLUtil.parseExpression(elem));
			} else if (BpmnXMLConstants.ELEMENT_OUTPUTDATAITEM.equalsIgnoreCase(parentName)) {
				multiInstance.setOutputDataItem(BpmnXMLUtil.parseExpression(elem));
			} else if (BpmnXMLConstants.ELEMENT_LOOPDATAINPUTCOLLECTION.equalsIgnoreCase(parentName)) {
				multiInstance.setLoopDataInputCollection(BpmnXMLUtil.parseExpression(elem));
			} else if (BpmnXMLConstants.ELEMENT_LOOPDATAOUTPUTCOLLECTION.equalsIgnoreCase(parentName)) {
				multiInstance.setLoopDataOutputCollection(BpmnXMLUtil.parseExpression(elem));
			}
		}
	}
}
