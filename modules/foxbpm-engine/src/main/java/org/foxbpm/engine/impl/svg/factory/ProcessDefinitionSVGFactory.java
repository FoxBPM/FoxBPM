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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.SendTaskBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.StartEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.SVGUtils;
import org.foxbpm.engine.impl.svg.vo.GVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
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
	private static final String EMPTY_STRING = "";

	/**
	 * 根据所有流程节点，和流程连接创建流程SVG文档字符串
	 */
	public String createProcessDefinitionSVGString(
			ProcessDefinitionEntity deployedProcessDefinition) {
		List<KernelFlowNodeImpl> flowNodes = deployedProcessDefinition
				.getFlowNodes();
		Map<String, KernelSequenceFlowImpl> sequenceFlows = deployedProcessDefinition
				.getSequenceFlows();

		List<VONode> voNodeList = new ArrayList<VONode>();
		VONode svgTemplateContainer = this
				.getDefaultSVGContainerFromFactory(deployedProcessDefinition
						.getProperties());

		// 遍历所有的流程节点
		Iterator<KernelFlowNodeImpl> flowNodeIterator = flowNodes.iterator();
		while (flowNodeIterator.hasNext()) {
			KernelFlowNodeImpl kernelFlowNodeImpl = flowNodeIterator.next();
			KernelFlowNodeBehavior kernelFlowNodeBehavior = kernelFlowNodeImpl
					.getKernelFlowNodeBehavior();
			String taskType = EMPTY_STRING;
			String svgTemplateFileName = EMPTY_STRING;
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
			// KernelSequenceFlowImpl sequenceFlowImpl =
			// nextConnector.getValue();
		}

		String svgStr = this.convertNodeListToString(
				(SvgVO) svgTemplateContainer, voNodeList);
		System.out.println(svgStr);
		return svgStr;
	}

	/**
	 * s构造SVG容器设置大小
	 * 
	 * @param processDefinitionPorperties
	 * @return
	 */
	private VONode getDefaultSVGContainerFromFactory(
			Map<String, Object> processDefinitionPorperties) {
		SvgVO svgTemplateContainer = (SvgVO) AbstractFlowNodeSVGFactory
				.createSVGTemplateContainerVO(processDefinitionPorperties);
		Float svgMinX = (Float) processDefinitionPorperties.get(SVG_MINX);
		Float svgMinY = (Float) processDefinitionPorperties.get(SVG_MINY);
		Float svgMaxX = (Float) processDefinitionPorperties.get(SVG_MAXX);
		Float svgMaxY = (Float) processDefinitionPorperties.get(SVG_MAXY);
		svgTemplateContainer.setWidth(String.valueOf(svgMaxX));
		svgTemplateContainer.setHeight(String.valueOf(svgMaxY));
		svgTemplateContainer.setMinHeight(String.valueOf(svgMinY));
		svgTemplateContainer.setMinWidth(String.valueOf(svgMinX));
		// 初始化VOList
		svgTemplateContainer.getgVo().setgVoList(new ArrayList<GVO>());
		return svgTemplateContainer;
	}

	/**
	 * 创建流程元素SVG
	 * 
	 * @param kernelFlowNodeImpl
	 * @param svgType
	 * @param svgTemplateFileName
	 * @return
	 */
	private VONode getNodeSVGFromFactory(KernelFlowNodeImpl kernelFlowNodeImpl,
			String svgType, String svgTemplateFileName) {
		// 调用节点构造方法，创建SVG VALUE OBJECT 对象
		AbstractFlowNodeSVGFactory svgFactory = AbstractFlowNodeSVGFactory
				.createSVGFactory(kernelFlowNodeImpl, svgTemplateFileName);
		return svgFactory.createFlowElementSVGVO(svgType);
	}

	/**
	 * 将所有的元素对象转化成String字符串
	 * 
	 * @param voNodeList
	 *            所有的节点集合
	 * @return SVG字符串
	 */
	private String convertNodeListToString(SvgVO svgContainer,
			List<VONode> voNodeList) {
		Iterator<VONode> voIter = voNodeList.iterator();
		while (voIter.hasNext()) {
			SvgVO svgVo = (SvgVO) voIter.next();
			GVO clone = SVGUtils.cloneGVO(svgVo.getgVo());
			List<GVO> getgVoList = svgContainer.getgVo().getgVoList();
			getgVoList.add(clone);
		}
		return AbstractFlowNodeSVGFactory.createFlowNodeSVGString(svgContainer);
	}
}
