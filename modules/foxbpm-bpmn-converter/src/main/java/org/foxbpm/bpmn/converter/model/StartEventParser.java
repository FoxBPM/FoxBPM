package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.bpmn.converter.BpmnModelUtil;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.behavior.StartEventBehavior;

public class StartEventParser extends BaseElementParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		Boolean isPersistence = BpmnModelUtil.getStartEventPersistence(baseElement);
		StartEventBehavior startEventBehavior = (StartEventBehavior)baseElementBehavior;
		startEventBehavior.setPersistence(true);
		return super.parser(baseElement);
	}
	
	@Override
	public void init() {
		baseElementBehavior = new StartEventBehavior();
	}
}
