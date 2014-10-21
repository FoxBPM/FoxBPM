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

import java.util.Iterator;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.parser.MultiInstanceParser;
import org.foxbpm.bpmn.converter.parser.SkipStrategyParser;
import org.foxbpm.model.Activity;
import org.foxbpm.model.BaseElement;

/**
 * 活动元素处理
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class ActivityXMLConverter extends FlowNodeXMLConverter {
	
	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		Activity activity = (Activity) baseElement;
		Element elem = null;
		Element childElem = null;
		String nodeName = null;
		// 处理跳过策略
		for (Iterator<Element> iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equalsIgnoreCase(nodeName)) {
				for (Iterator<Element> childIterator = elem.elements().iterator(); childIterator.hasNext();) {
					childElem = childIterator.next();
					nodeName = elem.getName();
					if (BpmnXMLConstants.ELEMENT_SKIPSTRATEGY.equalsIgnoreCase(nodeName)) {
						activity.setSkipStrategy(SkipStrategyParser.parser(element));
					} else if (BpmnXMLConstants.ELEMENT_MULTIINSTANCELOOPCHARACTERISTICS.equalsIgnoreCase(nodeName)) {
						activity.setLoopCharacteristics(MultiInstanceParser.parser(childElem));
					}
				}
				break;
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
