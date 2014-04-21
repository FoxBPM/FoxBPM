package org.foxbpm.bpmn.converter.model;

import org.eclipse.bpmn2.BaseElement;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;

public class BaseElementParser {
	
	protected BaseElementBehavior baseElementBehavior;

	
	public BaseElementBehavior parser(BaseElement baseElement){

		return baseElementBehavior;
	}
	
	public void init(){
		
		baseElementBehavior=new BaseElementBehavior();

	}

}
