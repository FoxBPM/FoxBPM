package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.StartEvent;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.behavior.StartEventBehavior;

public class StartEventParser extends BaseElementParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		StartEvent startEvent = (StartEvent)baseElement;
		
		StartEventBehavior startEventBehavior = (StartEventBehavior)baseElementBehavior;
		startEventBehavior.setPersistence(true);
		return super.parser(baseElement);
	}
	
	@Override
	public void init() {
		baseElementBehavior = new StartEventBehavior();
	}
}
