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
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.bpmn.behavior.factory.DefaultBehaviorFactory;
import org.foxbpm.model.Event;
import org.foxbpm.model.EventDefinition;


public abstract class EventBehavior extends FlowNodeBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<EventDefinitionBehavior> getEventDefinitionBehaviors(){
		
		Event event = (Event)baseElement;
		List<EventDefinitionBehavior> result = new ArrayList<EventDefinitionBehavior>();
		List<EventDefinition> eventDefinitions = event.getEventDefinitions();
		if(eventDefinitions != null){
			for(EventDefinition eventTmp : eventDefinitions){
				EventDefinitionBehavior eventDefinitionBehavior = DefaultBehaviorFactory.createEventDefinitionBehavior(eventTmp);
				result.add(eventDefinitionBehavior);
			}
		}
		return result;
	}

}
