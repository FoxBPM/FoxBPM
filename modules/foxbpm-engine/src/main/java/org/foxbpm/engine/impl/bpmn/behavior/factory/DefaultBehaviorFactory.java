package org.foxbpm.engine.impl.bpmn.behavior.factory;

import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EventDefinitionBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TerminateEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TimerEventBehavior;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.EventDefinition;
import org.foxbpm.model.TerminateEventDefinition;
import org.foxbpm.model.TimerEventDefinition;

public class DefaultBehaviorFactory implements BehaviorFactory{
	
	public static EventDefinitionBehavior createEventDefinitionBehavior(EventDefinition eventDefinition){
		EventDefinitionBehavior eventDefinitionBehavior = null;
		if(eventDefinition instanceof TimerEventDefinition){
			eventDefinitionBehavior = new TimerEventBehavior((TimerEventDefinition)eventDefinition);
		}else if(eventDefinition instanceof TerminateEventDefinition){
			eventDefinitionBehavior = new TerminateEventBehavior();
		}
		if(eventDefinitionBehavior != null){
			eventDefinitionBehavior.setBaseElement(eventDefinition);
		}
		return eventDefinitionBehavior;
	}

	public BaseElementBehavior createBehavior(BaseElement baseElement) {
		// TODO Auto-generated method stub
		return null;
	}
}
