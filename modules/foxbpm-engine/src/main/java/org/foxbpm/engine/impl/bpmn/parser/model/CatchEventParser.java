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
package org.foxbpm.engine.impl.bpmn.parser.model;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.TimerEventDefinition;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.CatchEventBehavior;

public class CatchEventParser extends EventParser {
	
	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		
		CatchEventBehavior catchEventBehavior=(CatchEventBehavior)baseElementBehavior;
		
		
		

		CatchEvent catchEvent=(CatchEvent)baseElement;
		List<EventDefinition> eventDefinitions = catchEvent.getEventDefinitions();
		for (EventDefinition eventDefinition : eventDefinitions) {
			
			
			if(eventDefinition instanceof TimerEventDefinition){
				org.foxbpm.engine.impl.bpmn.behavior.TimerEventDefinition timerEventDefinitionNew=new org.foxbpm.engine.impl.bpmn.behavior.TimerEventDefinition();
				
				TimerEventDefinition timerEventDefinition=(TimerEventDefinition)eventDefinition;
				timerEventDefinitionNew.setId(timerEventDefinition.getId());
				timerEventDefinitionNew.setTimeCycle(timerEventDefinition.getTimeCycle()!=null?((FormalExpression)timerEventDefinition.getTimeCycle()).getBody():null);
				timerEventDefinitionNew.setTimeDate(timerEventDefinition.getTimeDate()!=null?((FormalExpression)timerEventDefinition.getTimeDate()).getBody():null);
				timerEventDefinitionNew.setTimeDuration(timerEventDefinition.getTimeDuration()!=null?((FormalExpression)timerEventDefinition.getTimeDuration()).getBody():null);
				catchEventBehavior.getEventDefinitions().add(timerEventDefinitionNew);
			}
			
			
		}

		
		return super.parser(baseElement);
	}
	
}
