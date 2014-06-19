package org.foxbpm.engine.impl.bpmn.parser.model;


import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessBehavior;


public class ProcessParser extends BaseElementParser {
	

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {

		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new ProcessBehavior();
	}

}
