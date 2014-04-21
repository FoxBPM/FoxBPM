package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.UserTask;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.behavior.UserTaskBehavior;

public class UserTaskParser extends TaskParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		

		UserTaskBehavior userTaskBehavior=(UserTaskBehavior)baseElementBehavior;
		
		UserTask userTask=(UserTask)baseElement;
		userTask.getResources();
		userTaskBehavior.setId("");
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new UserTaskBehavior();
	}

}
