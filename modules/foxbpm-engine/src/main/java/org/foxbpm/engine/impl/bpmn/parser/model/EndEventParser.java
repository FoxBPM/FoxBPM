/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 * @author ych
 */
package org.foxbpm.engine.impl.bpmn.parser.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.impl.EndEventImpl;
import org.eclipse.bpmn2.impl.TerminateEventDefinitionImpl;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TerminateEventBehavior;

public class EndEventParser extends FlowNodeParser {

	
	 
	public BaseElementBehavior parser(BaseElement baseElement) {
		EndEventImpl endEventImpl = (EndEventImpl)baseElement;
		List<EventDefinition> eventDefinitions = endEventImpl.getEventDefinitions();
		
		List<org.foxbpm.engine.impl.bpmn.behavior.EventDefinition> behaviorEventDefinitions = new ArrayList<org.foxbpm.engine.impl.bpmn.behavior.EventDefinition>();
		for(EventDefinition eventDefinition :eventDefinitions){
			if(eventDefinition instanceof TerminateEventDefinitionImpl){
				TerminateEventBehavior terminateEventDefinition = new TerminateEventBehavior();
				behaviorEventDefinitions.add(terminateEventDefinition);
			}
		}
		((EndEventBehavior) baseElementBehavior).setEventDefinitions(behaviorEventDefinitions);
		return super.parser(baseElement);
	}
	
	 
	public void init() {
		baseElementBehavior = new EndEventBehavior();
	}
}
