package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.behavior.ActivityBehavior;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;

public class ActivityParser extends FlowNodeParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		// TODO Auto-generated method stub
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new ActivityBehavior();
	}

}
