/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.bpmn.parser;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.bpmn.parser.model.BaseElementParser;
import org.foxbpm.bpmn.parser.model.BoundaryEventParser;
import org.foxbpm.bpmn.parser.model.CallActivityParser;
import org.foxbpm.bpmn.parser.model.EndEventParser;
import org.foxbpm.bpmn.parser.model.ExclusiveGatewayParser;
import org.foxbpm.bpmn.parser.model.InclusiveGatewayParser;
import org.foxbpm.bpmn.parser.model.IntermediateEventParser;
import org.foxbpm.bpmn.parser.model.ParallelGatewayParser;
import org.foxbpm.bpmn.parser.model.ScriptTaskParser;
import org.foxbpm.bpmn.parser.model.SequenceFlowParser;
import org.foxbpm.bpmn.parser.model.StartEventParser;
import org.foxbpm.bpmn.parser.model.SubProcessParser;
import org.foxbpm.bpmn.parser.model.TaskParser;
import org.foxbpm.bpmn.parser.model.UserTaskParser;
import org.foxbpm.engine.impl.bpmn.behavior.ArtifactBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.kernel.behavior.KernelArtifactBehavior;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.behavior.KernelSequenceFlowBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.BoundaryEvent;
import org.foxbpm.model.CallActivity;
import org.foxbpm.model.EndEvent;
import org.foxbpm.model.ExclusiveGateway;
import org.foxbpm.model.InclusiveGateway;
import org.foxbpm.model.IntermediateCatchEvent;
import org.foxbpm.model.ParallelGateway;
import org.foxbpm.model.ScriptTask;
import org.foxbpm.model.SequenceFlow;
import org.foxbpm.model.StartEvent;
import org.foxbpm.model.SubProcess;
import org.foxbpm.model.Task;
import org.foxbpm.model.UserTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmnBehaviorEMFConverter {

	public static Logger log = LoggerFactory.getLogger(BpmnBehaviorEMFConverter.class);
	private static Map<Class<? extends BaseElement>, Class<? extends BaseElementParser>> elementParserMap = new HashMap<Class<? extends BaseElement>, Class<? extends BaseElementParser>>();
	static {
		elementParserMap.put(Task.class, TaskParser.class);
		elementParserMap.put(UserTask.class, UserTaskParser.class);
//		elementParserMap.put(ServiceTask.class, ServiceTaskParser.class);
		elementParserMap.put(ScriptTask.class, ScriptTaskParser.class);
//		elementParserMap.put(SendTaskImpl.class, SendTaskParser.class);
//		elementParserMap.put(ReceiveTaskImpl.class, ReceiveTaskParser.class);
//		elementParserMap.put(ManualTaskImpl.class, ManualTaskParser.class);
		elementParserMap.put(CallActivity.class, CallActivityParser.class);
//		elementParserMap.put(BusinessRuleTaskImpl.class, BusinessRuleTaskParser.class);

		elementParserMap.put(StartEvent.class, StartEventParser.class);
		elementParserMap.put(EndEvent.class, EndEventParser.class);

		elementParserMap.put(SubProcess.class, SubProcessParser.class);

		elementParserMap.put(ParallelGateway.class, ParallelGatewayParser.class);
		elementParserMap.put(InclusiveGateway.class, InclusiveGatewayParser.class);
		elementParserMap.put(ExclusiveGateway.class, ExclusiveGatewayParser.class);

//		elementParserMap.put(AssociationImpl.class, AssociationParser.class);
//		elementParserMap.put(GroupImpl.class, GroupParser.class);
//		elementParserMap.put(TextAnnotationImpl.class, TextAnnotationParser.class);

		elementParserMap.put(SequenceFlow.class, SequenceFlowParser.class);

//		elementParserMap.put(MultiInstanceLoopCharacteristics.class,
//				MultiInstanceLoopCharacteristicsParser.class);
//		elementParserMap.put(StandardLoopCharacteristics.class,
//				StandardLoopCharacteristicsParser.class);
		elementParserMap.put(BoundaryEvent.class, BoundaryEventParser.class);
		elementParserMap.put(IntermediateCatchEvent.class, IntermediateEventParser.class);
	}

	public static KernelFlowNodeBehavior getFlowNodeBehavior(BaseElement baseElement,
			KernelFlowElementsContainerImpl flowElementsContainer) {
		BaseElementBehavior baseElementBehavior = getBaseElementBehavior(baseElement,
				flowElementsContainer);
		if (baseElementBehavior instanceof KernelFlowNodeBehavior) {
			return (KernelFlowNodeBehavior) baseElementBehavior;
		}
		return null;
	}

	public static KernelSequenceFlowBehavior getSequenceFlowBehavior(BaseElement baseElement,
			KernelFlowElementsContainerImpl flowElementsContainer) {
		BaseElementBehavior baseElementBehavior = getBaseElementBehavior(baseElement,
				flowElementsContainer);
		if (baseElementBehavior instanceof KernelSequenceFlowBehavior) {
			return (KernelSequenceFlowBehavior) baseElementBehavior;
		}
		return null;
	}

	public static BaseElementBehavior getBaseElementBehavior(BaseElement baseElement,
			KernelFlowElementsContainerImpl flowElementsContainer) {
		Class<? extends BaseElementParser> baseParserClass = elementParserMap.get(baseElement
				.getClass());
		if (baseParserClass != null) {
			BaseElementParser parser = null;
			try {
				parser = baseParserClass.newInstance();
			} catch (Exception e) {
				log.error("转换元素：" + baseElement.getId() + " 失败！", e);
			}
			if (parser != null) {
				parser.init();
				parser.setFlowElementsContainer(flowElementsContainer);
				BaseElementBehavior baseElementBehavior = parser.parser(baseElement);
				return baseElementBehavior;
			}
		}
		return null;
	}

	public static KernelArtifactBehavior getArtifactBehavior(BaseElement baseElement,
			KernelFlowElementsContainerImpl flowElementsContainer) {
		BaseElementBehavior baseElementBehavior = getBaseElementBehavior(baseElement,
				flowElementsContainer);
		if (baseElementBehavior instanceof ArtifactBehavior) {
			return (ArtifactBehavior) baseElementBehavior;
		}
		return null;
	}

}
