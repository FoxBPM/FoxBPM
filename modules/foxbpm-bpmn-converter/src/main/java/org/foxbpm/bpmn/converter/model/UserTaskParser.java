package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.UserTask;
import org.foxbpm.bpmn.converter.BpmnModelUtil;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.behavior.UserTaskBehavior;
import org.foxbpm.model.bpmn.foxbpm.Expression;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;

public class UserTaskParser extends TaskParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		UserTaskBehavior userTaskBehavior=(UserTaskBehavior)baseElementBehavior;
		
		UserTask userTask=(UserTask)baseElement;
		Expression formUriExpression = (Expression) BpmnModelUtil.getExtensionElement(userTask, FoxBPMPackage.Literals.FORM_URI__EXPRESSION);
		String formUri = formUriExpression.getValue();
		userTaskBehavior.setFormUri(formUri);
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new UserTaskBehavior();
	}

}
