package org.foxbpm.bpmn.converter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.ServiceTask;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.UserTask;
import org.foxbpm.bpmn.converter.model.BaseElementParser;
import org.foxbpm.engine.impl.behavior.BaseElementBehavior;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;

public class BpmnBehaviorEMFConverter {

	private static Map<Class<?>, BaseElementParser> elementParserMap = new HashMap<Class<?>, BaseElementParser>() {
		private static final long serialVersionUID = 1L;

		{
			//task
			put(Task.class, null);
			put(UserTask.class, null);
			put(ServiceTask.class, null);
			put(ScriptTask.class, null);
			//event
			put(StartEvent.class, null);
			put(EndEvent.class, null);
		
		}
	};

	public KernelFlowNodeBehavior getFlowNodeBehavior(BaseElement baseElement) {
		
		
		
		BaseElementParser baseElementParser=elementParserMap.get(baseElement);
		
		
		
		BaseElementBehavior baseElementBehavior=baseElementParser.parser(baseElement);
		
		if(baseElementBehavior instanceof KernelFlowNodeBehavior){
			return (KernelFlowNodeBehavior)baseElementBehavior;
		}
		
		return null;
	}

}
