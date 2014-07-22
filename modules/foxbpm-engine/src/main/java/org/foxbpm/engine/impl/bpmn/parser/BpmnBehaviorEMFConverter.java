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
package org.foxbpm.engine.impl.bpmn.parser;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.impl.AssociationImpl;
import org.eclipse.bpmn2.impl.BaseElementImpl;
import org.eclipse.bpmn2.impl.BoundaryEventImpl;
import org.eclipse.bpmn2.impl.BusinessRuleTaskImpl;
import org.eclipse.bpmn2.impl.CallActivityImpl;
import org.eclipse.bpmn2.impl.EndEventImpl;
import org.eclipse.bpmn2.impl.ExclusiveGatewayImpl;
import org.eclipse.bpmn2.impl.GroupImpl;
import org.eclipse.bpmn2.impl.InclusiveGatewayImpl;
import org.eclipse.bpmn2.impl.ManualTaskImpl;
import org.eclipse.bpmn2.impl.MultiInstanceLoopCharacteristicsImpl;
import org.eclipse.bpmn2.impl.ParallelGatewayImpl;
import org.eclipse.bpmn2.impl.ProcessImpl;
import org.eclipse.bpmn2.impl.ReceiveTaskImpl;
import org.eclipse.bpmn2.impl.ScriptTaskImpl;
import org.eclipse.bpmn2.impl.SendTaskImpl;
import org.eclipse.bpmn2.impl.SequenceFlowImpl;
import org.eclipse.bpmn2.impl.ServiceTaskImpl;
import org.eclipse.bpmn2.impl.StandardLoopCharacteristicsImpl;
import org.eclipse.bpmn2.impl.StartEventImpl;
import org.eclipse.bpmn2.impl.SubProcessImpl;
import org.eclipse.bpmn2.impl.TaskImpl;
import org.eclipse.bpmn2.impl.TextAnnotationImpl;
import org.eclipse.bpmn2.impl.UserTaskImpl;
import org.foxbpm.engine.impl.bpmn.behavior.ArtifactBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessBehavior;
import org.foxbpm.engine.impl.bpmn.parser.model.AssociationParser;
import org.foxbpm.engine.impl.bpmn.parser.model.BaseElementParser;
import org.foxbpm.engine.impl.bpmn.parser.model.BoundaryEventParser;
import org.foxbpm.engine.impl.bpmn.parser.model.BusinessRuleTaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.CallActivityParser;
import org.foxbpm.engine.impl.bpmn.parser.model.EndEventParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ExclusiveGatewayParser;
import org.foxbpm.engine.impl.bpmn.parser.model.GroupParser;
import org.foxbpm.engine.impl.bpmn.parser.model.InclusiveGatewayParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ManualTaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.MultiInstanceLoopCharacteristicsParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ParallelGatewayParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ProcessParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ReceiveTaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ScriptTaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.SendTaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.SequenceFlowParser;
import org.foxbpm.engine.impl.bpmn.parser.model.ServiceTaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.StandardLoopCharacteristicsParser;
import org.foxbpm.engine.impl.bpmn.parser.model.StartEventParser;
import org.foxbpm.engine.impl.bpmn.parser.model.SubProcessParser;
import org.foxbpm.engine.impl.bpmn.parser.model.TaskParser;
import org.foxbpm.engine.impl.bpmn.parser.model.TextAnnotationParser;
import org.foxbpm.engine.impl.bpmn.parser.model.UserTaskParser;
import org.foxbpm.kernel.behavior.KernelArtifactBehavior;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.behavior.KernelSequenceFlowBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmnBehaviorEMFConverter {

	public static Logger log = LoggerFactory.getLogger(BpmnBehaviorEMFConverter.class);
	private static Map<Class<? extends BaseElementImpl>, Class<? extends BaseElementParser>> elementParserMap = new HashMap<Class<? extends BaseElementImpl>, Class<? extends BaseElementParser>>();
	static {
		elementParserMap.put(TaskImpl.class, TaskParser.class);
		elementParserMap.put(UserTaskImpl.class, UserTaskParser.class);
		elementParserMap.put(ServiceTaskImpl.class, ServiceTaskParser.class);
		elementParserMap.put(ScriptTaskImpl.class, ScriptTaskParser.class);
		elementParserMap.put(SendTaskImpl.class, SendTaskParser.class);
		elementParserMap.put(ReceiveTaskImpl.class, ReceiveTaskParser.class);
		elementParserMap.put(ManualTaskImpl.class, ManualTaskParser.class);
		elementParserMap.put(CallActivityImpl.class, CallActivityParser.class);
		elementParserMap.put(BusinessRuleTaskImpl.class, BusinessRuleTaskParser.class);

		elementParserMap.put(StartEventImpl.class, StartEventParser.class);
		elementParserMap.put(EndEventImpl.class, EndEventParser.class);

		elementParserMap.put(ProcessImpl.class, ProcessParser.class);
		elementParserMap.put(SubProcessImpl.class, SubProcessParser.class);

		elementParserMap.put(ParallelGatewayImpl.class, ParallelGatewayParser.class);
		elementParserMap.put(InclusiveGatewayImpl.class, InclusiveGatewayParser.class);
		elementParserMap.put(ExclusiveGatewayImpl.class, ExclusiveGatewayParser.class);

		elementParserMap.put(AssociationImpl.class, AssociationParser.class);
		elementParserMap.put(GroupImpl.class, GroupParser.class);
		elementParserMap.put(TextAnnotationImpl.class, TextAnnotationParser.class);

		elementParserMap.put(SequenceFlowImpl.class, SequenceFlowParser.class);

		elementParserMap.put(MultiInstanceLoopCharacteristicsImpl.class,
				MultiInstanceLoopCharacteristicsParser.class);
		elementParserMap.put(StandardLoopCharacteristicsImpl.class,
				StandardLoopCharacteristicsParser.class);

		elementParserMap.put(BoundaryEventImpl.class, BoundaryEventParser.class);
		
		elementParserMap.put(CallActivityImpl.class, CallActivityParser.class);
		
		
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

	public static ProcessBehavior getProcessBehavior(BaseElement baseElement,
			KernelFlowElementsContainerImpl flowElementsContainer) {
		BaseElementBehavior baseElementBehavior = getBaseElementBehavior(baseElement,
				flowElementsContainer);
		if (baseElementBehavior instanceof ProcessBehavior) {
			return (ProcessBehavior) baseElementBehavior;
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
