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
package org.foxbpm.engine.impl.diagramview.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.foxbpm.engine.impl.bpmn.behavior.BusinessRuleTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ManualTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ReceiveTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ScriptTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SendTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ServiceTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelFlowElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * PROCESS流程图形工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-11
 * 
 */
public class ConcreteProcessDefinitionVOFactory extends
		AbstractProcessDefinitionVOFactory {
	private static final String EMPTY_STRING = "";
	/**
	 * 创建节点工厂
	 */
	private AbstractFlowNodeVOFactory flowNodeVOFactory;

	/**
	 * 根据所有流程节点，和流程连接创建流程SVG文档字符串
	 */
	public String createProcessDefinitionVOString(
			ProcessDefinitionEntity deployedProcessDefinition) {
		List<KernelFlowNodeImpl> flowNodes = deployedProcessDefinition
				.getFlowNodes();
		Map<String, KernelSequenceFlowImpl> sequenceFlows = deployedProcessDefinition
				.getSequenceFlows();

		List<VONode> voNodeList = new ArrayList<VONode>();
		// 遍历所有的流程节点
		Iterator<KernelFlowNodeImpl> flowNodeIterator = flowNodes.iterator();
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		while (flowNodeIterator.hasNext()) {
			KernelFlowNodeImpl kernelFlowNodeImpl = flowNodeIterator.next();
			String[] typeTemplateArray = this
					.getTypeAndTemplateNameByFlowNode(kernelFlowNodeImpl);
			taskType = typeTemplateArray[0];
			svgTemplateFileName = typeTemplateArray[1];
			VONode voNode = this.getNodeSVGFromFactory(kernelFlowNodeImpl,
					taskType, svgTemplateFileName);
			voNodeList.add(voNode);
		}

		// 遍历所有的流程连线
		Iterator<Entry<String, KernelSequenceFlowImpl>> sequenceFlowterator = sequenceFlows
				.entrySet().iterator();
		while (sequenceFlowterator.hasNext()) {
			Entry<String, KernelSequenceFlowImpl> nextConnector = sequenceFlowterator
					.next();
			KernelSequenceFlowImpl sequenceFlowImpl = nextConnector.getValue();
			taskType = SVGTypeNameConstant.SVG_TYPE_CONNECTOR;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_CONNECTOR_SEQUENCEFLOW;
			VONode voNode = this.getNodeSVGFromFactory(sequenceFlowImpl,
					taskType, svgTemplateFileName);
			voNodeList.add(voNode);
		}

		String svgStr = flowNodeVOFactory.convertNodeListToString(
				deployedProcessDefinition.getProperties(), voNodeList);
		System.out.println(svgStr);
		return svgStr;
	}

	/**
	 * 创建流程元素SVG
	 * 
	 * @param kernelFlowNodeImpl
	 * @param svgType
	 * @param svgTemplateFileName
	 * @return
	 */
	private VONode getNodeSVGFromFactory(KernelFlowElement kernelFlowNodeImpl,
			String svgType, String svgTemplateFileName) {
		// 调用节点构造方法，创建SVG VALUE OBJECT 对象
		flowNodeVOFactory = AbstractFlowNodeVOFactory.createSVGFactory(
				kernelFlowNodeImpl, svgTemplateFileName);
		return flowNodeVOFactory.createFlowElementSVGVO(svgType);
	}

	/**
	 * 获取nodeType和模版名称
	 * 
	 * @param kernelFlowNodeImpl
	 * @return
	 */
	private String[] getTypeAndTemplateNameByFlowNode(
			KernelFlowNodeImpl kernelFlowNodeImpl) {
		KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
				.getKernelFlowNodeBehavior();
		String taskType = EMPTY_STRING;
		String svgTemplateFileName = EMPTY_STRING;
		if (kernelFlowNodeBehavior instanceof UserTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_USERTASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof SendTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_SENDTASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof ServiceTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_SERVICETASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof ManualTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_MANUALTASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof BusinessRuleTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_BUSINESSRULETASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof ReceiveTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_RECEIVETASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof ScriptTaskBehavior) {
			taskType = SVGTypeNameConstant.ACTIVITY_SCRIPTTASK;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK;
		} else if (kernelFlowNodeBehavior instanceof StartEventBehavior) {
			taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_STARTEVENT_NONE;
		} else if (kernelFlowNodeBehavior instanceof EndEventBehavior) {
			taskType = SVGTypeNameConstant.SVG_TYPE_EVENT;
			svgTemplateFileName = SVGTemplateNameConstant.TEMPLATE_ENDEVENT_NONE;
		}
		return new String[] { taskType, svgTemplateFileName };
	}
}
