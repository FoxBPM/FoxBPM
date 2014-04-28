package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;

public abstract class FlowElementParser extends BaseElementParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		return super.parser(baseElement);
	}

	public void init() {
		super.init();
	}

}
