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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.svg.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SendTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * PROCESS流程SVG工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-11
 * 
 */
public class ProcessDefinitionSVGFactory extends
		AbstractProcessDefinitionSVGFactory {
	/**
	 * 根据所有流程节点，和流程连接创建流程SVG文档字符串
	 */
	public String createProcessDefinitionSVGString(
			ProcessDefinitionEntity deployedProcessDefinition) {
		List<KernelFlowNodeImpl> flowNodes = deployedProcessDefinition
				.getFlowNodes();
		Map<String, KernelSequenceFlowImpl> sequenceFlows = deployedProcessDefinition
				.getSequenceFlows();

		// 遍历所有的流程节点
		Iterator<KernelFlowNodeImpl> flowNodeIterator = flowNodes.iterator();
		List<VONode> voNodeList = new ArrayList<VONode>();
		while (flowNodeIterator.hasNext()) {
			KernelFlowNodeImpl kernelFlowNodeImpl = flowNodeIterator.next();
			KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
					.getKernelFlowNodeBehavior();
			String taskType = "";
			String svgTemplateFileName = "";
			if (kernelFlowNodeBehavior instanceof UserTaskBehavior) {
				taskType = SVGTypeNameConstant.ACTIVITY_USERTASK;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
			}
			if (kernelFlowNodeBehavior instanceof SendTaskBehavior) {
				taskType = SVGTypeNameConstant.ACTIVITY_SENDTASK;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
			}
			if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
				taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_STARTEVENT_NONE;
			}
			if (kernelFlowNodeBehavior instanceof EndEventBehavior) {
				taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
				svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ENDEVENT_NONE;
			}
			VONode voNode = this.getNodeSVGStringFromFactory(
					kernelFlowNodeImpl, taskType, svgTemplateFileName);
			voNodeList.add(voNode);
		}

		// 遍历所有的流程连线
		Iterator<Entry<String, KernelSequenceFlowImpl>> sequenceFlowterator = sequenceFlows
				.entrySet().iterator();
		while (sequenceFlowterator.hasNext()) {
			Entry<String, KernelSequenceFlowImpl> nextConnector = sequenceFlowterator
					.next();
			KernelSequenceFlowImpl sequenceFlowImpl = nextConnector.getValue();
		}

		return this.convertNodeListToString(voNodeList);
	}

	private VONode getNodeSVGStringFromFactory(
			KernelFlowNodeImpl kernelFlowNodeImpl, String taskType,
			String svgTemplateFileName) {
		// 调用节点构造方法，创建SVG VALUE OBJECT 对象
		AbstractFlowNodeSVGFactory svgFactory = AbstractFlowNodeSVGFactory
				.createSVGFactory(svgTemplateFileName);
		return svgFactory.createSVGVO(kernelFlowNodeImpl,
				taskType);
	}

	private String convertNodeListToString(List<VONode> voNodeList) {
		StringBuffer svgBuffer = new StringBuffer();
		return svgBuffer.toString();
	}
}
