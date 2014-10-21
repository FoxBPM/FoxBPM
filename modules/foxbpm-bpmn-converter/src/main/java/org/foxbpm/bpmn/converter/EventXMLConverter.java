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

import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.Event;
import org.foxbpm.model.EventDefinition;
import org.foxbpm.model.TerminateEventDefinition;
import org.foxbpm.model.TimerEventDefinition;

/**
 * 事件转换处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class EventXMLConverter extends FlowNodeXMLConverter {
	
	@SuppressWarnings("rawtypes")
	@Override
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		Event event = (Event) baseElement;
		Element childElem = null;
		String nodeName = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			childElem = (Element) iterator.next();
			nodeName = childElem.getName();
			if (BpmnXMLConstants.ELEMENT_TERMINATEEVENTDEFINITION.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_TIMEREVENTDEFINITION.equalsIgnoreCase(nodeName)) {
				if (null == event.getEventDefinitions()) {
					event.setEventDefinitions(new ArrayList<EventDefinition>());
				}
				event.getEventDefinitions().add(convertEventDefinition(childElem));
			}
		}
		super.convertXMLToModel(element, baseElement);
	}
	
	@SuppressWarnings("unchecked")
	private EventDefinition convertEventDefinition(Element element) {
		String nodeName = element.getName();
		TimerEventDefinition timerEventDefinition = null;
		TerminateEventDefinition terminateEventDefinition = null;
		if (BpmnXMLConstants.ELEMENT_TERMINATEEVENTDEFINITION.equalsIgnoreCase(nodeName)) {
			terminateEventDefinition = new TerminateEventDefinition();
			terminateEventDefinition.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
			return terminateEventDefinition;
		} else if (BpmnXMLConstants.ELEMENT_TIMEREVENTDEFINITION.equalsIgnoreCase(nodeName)) {
			timerEventDefinition = new TimerEventDefinition();
			timerEventDefinition.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
			Element elem = null;
			for (Iterator<Element> iterator = element.elements().iterator(); iterator.hasNext();) {
				elem = (Element) iterator.next();
				nodeName = elem.getName();
				if (BpmnXMLConstants.ELEMENT_TIMEDATE.equalsIgnoreCase(nodeName)) {
					timerEventDefinition.setTimeDate(BpmnXMLUtil.parseCDATA(elem));
				} else if (BpmnXMLConstants.ELEMENT_TIMEDURATION.equalsIgnoreCase(nodeName)) {
					timerEventDefinition.setTimeDuration(BpmnXMLUtil.parseCDATA(elem));
				} else if (BpmnXMLConstants.ELEMENT_TIMECYCLE.equalsIgnoreCase(nodeName)) {
					timerEventDefinition.setTimeCycle(BpmnXMLUtil.parseCDATA(elem));
				}
			}
			return timerEventDefinition;
		}
		return null;
	}
	
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		Event event = (Event) baseElement;
		if (null != event.getEventDefinitions()) {
			EventDefinition eventDefinition = null;
			TimerEventDefinition timerEventDefinition = null;
			TerminateEventDefinition terminateEventDefinition = null;
			Element childElem = null;
			Element nodeElem = null;
			for (Iterator<EventDefinition> iterator = event.getEventDefinitions().iterator(); iterator.hasNext();) {
				eventDefinition = iterator.next();
				// 处理时间
				if (eventDefinition instanceof TimerEventDefinition) {
					timerEventDefinition = (TimerEventDefinition) eventDefinition;
					childElem = element.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_TIMEREVENTDEFINITION);
					childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, timerEventDefinition.getId());
					if (null != timerEventDefinition.getTimeCycle()) {
						nodeElem = childElem.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
						        + BpmnXMLConstants.ELEMENT_TIMECYCLE);
						nodeElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.BPMN2_PREFIX
						        + ':' + BpmnXMLConstants.TYPE_FORMALEXPRESSION);
						nodeElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.FORMALEXPRESSION));
						nodeElem.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':' + BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(timerEventDefinition.getTimeCycle()));
						nodeElem.add(new DOMCDATA(timerEventDefinition.getTimeCycle()));
					}
					if (null != timerEventDefinition.getTimeDuration()) {
						nodeElem = childElem.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
						        + BpmnXMLConstants.ELEMENT_TIMEDURATION);
						nodeElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.BPMN2_PREFIX
						        + ':' + BpmnXMLConstants.TYPE_FORMALEXPRESSION);
						nodeElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.FORMALEXPRESSION));
						nodeElem.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':' + BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(timerEventDefinition.getTimeDuration()));
						nodeElem.add(new DOMCDATA(timerEventDefinition.getTimeDuration()));
					}
					if (null != timerEventDefinition.getTimeDate()) {
						nodeElem = childElem.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
						        + BpmnXMLConstants.ELEMENT_TIMEDATE);
						nodeElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.BPMN2_PREFIX
						        + ':' + BpmnXMLConstants.TYPE_FORMALEXPRESSION);
						nodeElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.FORMALEXPRESSION));
						nodeElem.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':' + BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(timerEventDefinition.getTimeDate()));
						nodeElem.add(new DOMCDATA(timerEventDefinition.getTimeDate()));
					}
				} else if (eventDefinition instanceof TerminateEventDefinition) {
					terminateEventDefinition = (TerminateEventDefinition) eventDefinition;
					childElem = element.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_TERMINATEEVENTDEFINITION);
					childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, terminateEventDefinition.getId());
				}
			}
		}
		super.convertModelToXML(element, baseElement);
	}
}
