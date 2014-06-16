package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElement;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.FlowElementBehavior;

public abstract class FlowElementParser extends BaseElementParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		
		FlowElementBehavior flowElementBehavior=(FlowElementBehavior)baseElementBehavior;
		FlowElement flowElement=(FlowElement)baseElement;
		flowElementBehavior.setName(flowElement.getName());
		
		return super.parser(baseElement);
	}

	public void init() {
		super.init();
	}

}
