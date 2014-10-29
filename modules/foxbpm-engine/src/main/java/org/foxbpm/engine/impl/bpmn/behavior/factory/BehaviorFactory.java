package org.foxbpm.engine.impl.bpmn.behavior.factory;

import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.model.BaseElement;

public interface BehaviorFactory {

	BaseElementBehavior createBehavior(BaseElement baseElement);
}
