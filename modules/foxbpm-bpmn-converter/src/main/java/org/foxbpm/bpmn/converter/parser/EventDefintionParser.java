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
import org.foxbpm.model.EventDefinition;
import org.foxbpm.model.TerminateEventDefinition;
import org.foxbpm.model.TimerEventDefinition;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月17日
 */
public class EventDefintionParser {
	
	@SuppressWarnings("rawtypes")
	public static EventDefinition parserEventDefinition(Element element) {
		String nodeName = element.getName();
		if (BpmnXMLConstants.ELEMENT_TERMINATEEVENTDEFINITION.equalsIgnoreCase(nodeName)) {
			TerminateEventDefinition terminateEventDefinition = new TerminateEventDefinition();
			terminateEventDefinition.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
			return terminateEventDefinition;
		} else if (BpmnXMLConstants.ELEMENT_TIMEREVENTDEFINITION.equalsIgnoreCase(nodeName)) {
			TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
			timerEventDefinition.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
			Element elem = null;
			for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
				elem = (Element) iterator.next();
				nodeName = elem.getName();
				if (BpmnXMLConstants.ELEMENT_TIMEDATE.equalsIgnoreCase(nodeName)) {
					timerEventDefinition.setTimeDate(BpmnXMLUtil.parseExpression(elem));
				} else if (BpmnXMLConstants.ELEMENT_TIMEDURATION.equalsIgnoreCase(nodeName)) {
					timerEventDefinition.setTimeDuration(BpmnXMLUtil.parseExpression(elem));
				} else if (BpmnXMLConstants.ELEMENT_TIMECYCLE.equalsIgnoreCase(nodeName)) {
					timerEventDefinition.setTimeCycle(BpmnXMLUtil.parseExpression(elem));
				}
			}
			return timerEventDefinition;
		}
		return null;
	}
}
