package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.IntermediateCatchEventBehavior;

public class IntermediateEventParser extends CatchEventParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		this.baseElementBehavior = new IntermediateCatchEventBehavior();
	}

}
