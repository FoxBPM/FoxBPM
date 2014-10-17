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
import org.foxbpm.bpmn.converter.parser.EventDefintionParser;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.Event;
import org.foxbpm.model.EventDefinition;

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
		Element elem = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			if (null == event.getEventDefinitions()) {
				event.setEventDefinitions(new ArrayList<EventDefinition>());
			}
			event.getEventDefinitions().add(EventDefintionParser.parserEventDefinition(elem));
		}
		super.convertXMLToModel(element, baseElement);
	}
	/*
	 * private void parseTerminateEventDefinition(){ <bpmn2:timeDate
	 * xsi:type="bpmn2:tFormalExpression" id="FormalExpression_2"
	 * foxbpm:name=""><![CDATA["DDD"]]></bpmn2:timeDate> <bpmn2:timeDuration
	 * xsi:type="bpmn2:tFormalExpression" id="FormalExpression_3"
	 * foxbpm:name=""><![CDATA["XXX"]]></bpmn2:timeDuration> <bpmn2:timeCycle
	 * xsi:type="bpmn2:tFormalExpression" id="FormalExpression_4"
	 * foxbpm:name=""><![CDATA["XXX"]]></bpmn2:timeCycle> }
	 */
}
