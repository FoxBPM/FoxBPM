package org.foxbpm.engine.impl.bpmn.parser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.impl.BaseElementImpl;
import org.eclipse.bpmn2.impl.EndEventImpl;
import org.eclipse.bpmn2.impl.ParallelGatewayImpl;
import org.eclipse.bpmn2.impl.ProcessImpl;
import org.eclipse.bpmn2.impl.ScriptTaskImpl;
import org.eclipse.bpmn2.impl.ServiceTaskImpl;
import org.eclipse.bpmn2.impl.StartEventImpl;
import org.eclipse.bpmn2.impl.TaskImpl;
import org.eclipse.bpmn2.impl.UserTaskImpl;
import org.foxbpm.engine.impl.bpmn.parser.model.BaseElementParser;
import org.foxbpm.engine.impl.bpmn.parser.model.EndEventParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ParallelGatewayParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ProcessParser;
import org.foxbpm.engine.impl.bpmn.parser.model.StartEventParser;
import org.foxbpm.engine.impl.bpmn.parser.model.UserTaskParser;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessBehavior;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmnBehaviorEMFConverter {

	public static Logger log = LoggerFactory.getLogger(BpmnBehaviorEMFConverter.class);
	private static Map<Class<? extends BaseElementImpl>, Class<? extends BaseElementParser>> elementParserMap = new HashMap<Class<? extends BaseElementImpl>, Class <? extends BaseElementParser>>();
	static{
		elementParserMap.put(TaskImpl.class, null);
		elementParserMap.put(UserTaskImpl.class, UserTaskParser.class);
		elementParserMap.put(ServiceTaskImpl.class, null);
		elementParserMap.put(ScriptTaskImpl.class, null);
		elementParserMap.put(StartEventImpl.class, StartEventParser.class);
		elementParserMap.put(EndEventImpl.class, EndEventParser.class);
		elementParserMap.put(ProcessImpl.class, ProcessParser.class);
		elementParserMap.put(ParallelGatewayImpl.class, ParallelGatewayParser.class);
	}

	public static KernelFlowNodeBehavior getFlowNodeBehavior(BaseElement baseElement,KernelFlowElementsContainerImpl  flowElementsContainer) {
		Class<? extends BaseElementParser> baseParserClass = elementParserMap.get(baseElement.getClass());
		if(baseParserClass != null){
			BaseElementParser parser = null;
			try {
				parser = baseParserClass.newInstance();
			} catch (Exception e) {
				log.error("转换元素："+baseElement.getId()+" 失败！",e);
			}
			if(parser != null){
				parser.init();
				parser.setFlowElementsContainer(flowElementsContainer);
				BaseElementBehavior baseElementBehavior=parser.parser(baseElement);
				if(baseElementBehavior instanceof KernelFlowNodeBehavior){
					return (KernelFlowNodeBehavior)baseElementBehavior;
				}
			}
		}
		return null;
	}
	
	public static ProcessBehavior getProcessBehavior(BaseElement baseElement,KernelFlowElementsContainerImpl  flowElementsContainer) {
		Class<? extends BaseElementParser> baseParserClass = elementParserMap.get(baseElement.getClass());
		if(baseParserClass != null){
			BaseElementParser parser = null;
			try {
				parser = baseParserClass.newInstance();
			} catch (Exception e) {
				log.error("转换元素："+baseElement.getId()+" 失败！",e);
			}
			if(parser != null){
				parser.init();
				parser.setFlowElementsContainer(flowElementsContainer);
				BaseElementBehavior baseElementBehavior=parser.parser(baseElement);
				if(baseElementBehavior instanceof ProcessBehavior){
					return (ProcessBehavior)baseElementBehavior;
				}
			}
		}
		return null;
	}

}
