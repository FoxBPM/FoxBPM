package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.behavior.TaskBehavior;

public class TaskParser extends ActivityParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		// TODO Auto-generated method stub
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new TaskBehavior();
	}
	
	

}
