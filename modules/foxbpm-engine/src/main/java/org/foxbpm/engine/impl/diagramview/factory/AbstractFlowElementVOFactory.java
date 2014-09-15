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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.bpmn.behavior.TextAnnotationBehavior;
import org.foxbpm.engine.impl.bpmn.parser.StyleOption;
import org.foxbpm.engine.impl.diagramview.builder.FoxBpmnViewBuilder;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.builder.AbstractSVGBuilder;
import org.foxbpm.engine.impl.diagramview.svg.factory.AbstractFlowElementSVGFactory;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelBaseElement;
import org.foxbpm.kernel.process.KernelLane;
import org.foxbpm.kernel.process.impl.KernelArtifactImpl;
import org.foxbpm.kernel.process.impl.KernelAssociationImpl;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * FLOW单个节点VO工厂类、构造元素包括，事件、活动、线条、网关、泳道、附件
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractFlowElementVOFactory {
	protected static final String SPLIT_SEPERATOR = "/";
	protected static final String ELEMENT_TYPE_EVENT = "event";
	protected static final String ELEMENT_TYPE_ACTIVITY = "activity";
	protected static final String ELEMENT_TYPE_CONNECTOR = "connector";
	protected static final String ELEMENT_TYPE_GATEWAY = "gateway";
	protected static final String ELEMENT_TYPE_LANE = "lane";
	
	/**
	 * 流程定义的画布坐标MINX
	 */
	protected static final String CANVAS_MINX = "canvas_minX";
	/**
	 * 流程定义的画布坐标MINY
	 */
	protected static final String CANVAS_MINY = "canvas_minY";
	/**
	 * 流程定义的画布坐标MAXX
	 */
	protected static final String CANVAS_MAXX = "canvas_maxX";
	/**
	 * 流程定义的画布坐标MAXY
	 */
	protected static final String CANVAS_MAXY = "canvas_maxY";
	protected String voTemplateFileName;
	protected KernelBaseElement kernelBaseElement;
	
	/**
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 */
	public AbstractFlowElementVOFactory(KernelBaseElement kernelBaseElement, String voTemplateFileName) {
		this.voTemplateFileName = voTemplateFileName;
		this.kernelBaseElement = kernelBaseElement;
	}
	
	/**
	 * 构造流程元素信息 包括连接线
	 * 
	 * @param svgType
	 * @return
	 */
	public VONode createFlowElementSVGVO(String svgType) {
		VONode voNode = this.createSVGVO();
		if (kernelBaseElement instanceof KernelSequenceFlowImpl) {
			// 连接线
			this.filterChildVO(voNode, null);
		}
		FoxBpmnViewBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(voNode, svgType);
		
		SvgElementBuildDistincter svgElementBuildDistincter = new SvgElementBuildDistincter();
		svgElementBuildDistincter.setSvgBuilder(svgBuilder);
		
		// 构造节点元素,需要考虑构造顺序，注意依赖关系
		// 1、过滤
		// 2、构造文本信息
		// 3、构造宽度，高度，边框
		// 4、构造XY坐标
		// 5、构造FILL式样
		if (kernelBaseElement instanceof KernelFlowNodeImpl) {
			// 流程节点
			if (svgType.contains(SVGTypeNameConstant.SVG_TYPE_TASK)
			        || svgType.contains(SVGTypeNameConstant.SVG_TYPE_CALLACTIVITY)) {
				this.filterChildVO(voNode, Arrays.asList(svgType.split(SPLIT_SEPERATOR)));
			}
			
			svgElementBuildDistincter.setKernelBaseElement(kernelBaseElement);
			svgElementBuildDistincter.createCommoneElement();
		} else if (kernelBaseElement instanceof KernelSequenceFlowImpl) {
			// 连接线
			KernelSequenceFlowImpl kernelSequenceFlowImpl = (KernelSequenceFlowImpl) kernelBaseElement;
			svgElementBuildDistincter.createSequenceElement(kernelSequenceFlowImpl.getId(), kernelSequenceFlowImpl.getName(), (String) kernelSequenceFlowImpl.getProperty(StyleOption.Foreground), kernelSequenceFlowImpl.getWaypoints());
		} else if (kernelBaseElement instanceof KernelAssociationImpl) {
			// 连接线
			KernelAssociationImpl kernelAssociationImpl = (KernelAssociationImpl) kernelBaseElement;
			svgElementBuildDistincter.createSequenceElement(kernelAssociationImpl.getId(), kernelAssociationImpl.getName(), (String) kernelAssociationImpl.getProperty(StyleOption.Foreground), kernelAssociationImpl.getWaypoints());
		} else if (kernelBaseElement instanceof KernelLane) {
			// 泳道
			svgElementBuildDistincter.setKernelBaseElement(kernelBaseElement);
			svgElementBuildDistincter.createCommoneElement();
			
			svgBuilder.setStrokeWidth(0.5f);
			svgBuilder.setTextLocationByHerizonFlag((Boolean) kernelBaseElement.getProperty(StyleOption.IsHorizontal));
		} else if (kernelBaseElement instanceof KernelArtifactImpl) {
			// 小部件
			
			if (StringUtils.equalsIgnoreCase(svgType, SVGTypeNameConstant.SVG_TYPE_GROUP)) {
				svgElementBuildDistincter.setKernelBaseElement(kernelBaseElement);
				svgElementBuildDistincter.createCommoneElement();
			} else if (StringUtils.equalsIgnoreCase(svgType, SVGTypeNameConstant.SVG_TYPE_TEXTANNOTATION)) {
				// 小部件-注释
				TextAnnotationBehavior textAnnotationBehavior = (TextAnnotationBehavior) ((KernelArtifactImpl) kernelBaseElement).getArtifactBehavior();
				svgElementBuildDistincter.setKernelBaseElement(kernelBaseElement);
				svgElementBuildDistincter.createTextAnnotation(textAnnotationBehavior.getText(), textAnnotationBehavior.getTextFormat());
			}
			
		}
		return voNode;
	}
	
	/**
	 * 创建具体的工厂类
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowElementVOFactory createSVGFactory(
	    KernelBaseElement kernelBaseElement, String svgTemplateFileName) {
		// 当前实现是SVG格式，后期可能支持微软的XML
		return AbstractFlowElementSVGFactory.createSVGFactory(kernelBaseElement, svgTemplateFileName);
	}
	
	/**
	 * 
	 * convertNodeListToString(将根据流程定义处理过的VO 集合转化成SVG字符串)
	 * 
	 * @param processDefinitionPorperties
	 * @param voNodeList
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public abstract String convertNodeListToString(Map<String, Object> processDefinitionPorperties,
	    List<VONode> voNodeList);
	
	/**
	 * 过滤子类型
	 * 
	 * @param voNode
	 * @param filterCondition
	 */
	public abstract void filterChildVO(VONode voNode, List<String> filterCondition);
	
	/**
	 * 根据子类型构造
	 * 
	 * @param svgType
	 * @return
	 */
	public abstract VONode createSVGVO();
}
