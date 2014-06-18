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
import org.foxbpm.engine.impl.bpmn.behavior.SequenceFlowBehavior;
import org.foxbpm.engine.impl.bpmn.parser.StyleOption;
import org.foxbpm.engine.impl.diagramview.builder.FoxBpmnViewBuilder;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.PointUtils;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.factory.AbstractFlowNodeSVGFactory;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;
import org.foxbpm.engine.impl.diagramview.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.kernel.process.KernelFlowElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * FLOW单个节点VO工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractFlowNodeVOFactory {
	public static final String SPLIT_SEPERATOR = "/";
	protected String voTemplateFileName;
	protected KernelFlowElement kernelFlowElement;

	/**
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 */
	public AbstractFlowNodeVOFactory(KernelFlowElement kernelFlowElement,
			String voTemplateFileName) {
		this.voTemplateFileName = voTemplateFileName;
		this.kernelFlowElement = kernelFlowElement;
	}

	/**
	 * 构造流程元素信息 包括连接线
	 * 
	 * @param svgType
	 * @return
	 */
	public VONode createFlowElementSVGVO(String svgType) {
		VONode voNode = null;
		if (StringUtils.equalsIgnoreCase(svgType,
				SVGTypeNameConstant.SVG_TYPE_EVENT)
				|| StringUtils.equalsIgnoreCase(svgType,
						SVGTypeNameConstant.SVG_TYPE_CONNECTOR)) {
			voNode = this.createSVGVO();
		} else {
			voNode = this.createSVGVO(svgType);
		}
		FoxBpmnViewBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				voNode, svgType);

		// 构造节点元素,需要考虑构造顺序，注意依赖关系
		// 1、过滤
		// 2、构造文本信息
		// 3、构造宽度，高度，边框
		// 4、构造XY坐标
		// 5、构造FILL式样
		if (kernelFlowElement instanceof KernelFlowNodeImpl) {
			// 过滤
			this.filterActivityTaskVO(voNode, new String[] { "callActivity" });
			this.filterChildVO(voNode,
					Arrays.asList(svgType.split(SPLIT_SEPERATOR)));
			KernelFlowNodeImpl kernelFlowNodeImpl = (KernelFlowNodeImpl) kernelFlowElement;
			svgBuilder.setID(kernelFlowNodeImpl.getId());
			if (StringUtils.isNotBlank(kernelFlowNodeImpl.getName())) {
				svgBuilder.setText(kernelFlowNodeImpl.getName());
				svgBuilder.setTextStroke((String) kernelFlowNodeImpl
						.getProperty(StyleOption.TextColor));
				svgBuilder.setTextStrokeWidth(0);
				svgBuilder.setTextFont((String) kernelFlowNodeImpl
						.getProperty(StyleOption.Font));
			}

			// 如果是事件节点，必须先设置width属性，即设置圆的直径,
			svgBuilder.setWidth(kernelFlowNodeImpl.getWidth());
			svgBuilder.setHeight(kernelFlowNodeImpl.getHeight());

			svgBuilder.setStroke((String) kernelFlowNodeImpl
					.getProperty(StyleOption.Foreground));

			// 设置节点的坐标包括对应文本字体的坐标，文本坐标依赖于文本式样字体大小等
			svgBuilder.setXAndY(kernelFlowNodeImpl.getX(),
					kernelFlowNodeImpl.getY());
			// 线性渐变设置会用到矩形的Height属性，
			svgBuilder.setFill((String) kernelFlowNodeImpl
					.getProperty(StyleOption.Background));
			// TODO 未知属性 StyleOption.StyleObject
		}
		// 线条元素
		// 先构造拐点，再构造文本坐标
		if (kernelFlowElement instanceof KernelSequenceFlowImpl) {
			KernelSequenceFlowImpl kernelSequenceFlowImpl = (KernelSequenceFlowImpl) kernelFlowElement;
			SequenceFlowBehavior sequenceFlowBehavior = (SequenceFlowBehavior) kernelSequenceFlowImpl
					.getSequenceFlowBehavior();
			String[] filterConfition = new String[] { "", "default" };
			if (sequenceFlowBehavior == null
					|| StringUtils.isBlank(sequenceFlowBehavior
							.getConditionExpression())) {
				filterConfition[0] = "conditional";
			}
			// 过滤
			this.filterConnectorVO(voNode, filterConfition);
			List<Integer> waypoints = kernelSequenceFlowImpl.getWaypoints();
			List<Point> pointList = SVGUtils
					.convertWaypointsTOPointList(waypoints);
			// 构造
			svgBuilder.setWayPoints(pointList);
			if (StringUtils.isNotBlank(kernelSequenceFlowImpl.getName())) {
				svgBuilder.setText(kernelSequenceFlowImpl.getName());
				// 设置文本的相对位置
				Point textPoint = PointUtils.caclCenterPoint(pointList);
				svgBuilder.setTextX(textPoint.getX());
				svgBuilder.setTextY(textPoint.getY());
			}

			svgBuilder.setStroke((String) kernelSequenceFlowImpl
					.getProperty(StyleOption.Foreground));
		}
		return voNode;
	}

	public abstract String convertNodeListToString(
			Map<String, Object> processDefinitionPorperties,
			List<VONode> voNodeList);

	/**
	 * 创建具体的工厂类
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowNodeVOFactory createSVGFactory(
			KernelFlowElement kernelFlowElement, String svgTemplateFileName) {
		// 当前实现是SVG格式，后期可能支持微软的XML
		return AbstractFlowNodeSVGFactory.createSVGFactory(kernelFlowElement,
				svgTemplateFileName);
	}

	/**
	 * 创建具体的工厂类
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowNodeVOFactory createSignedSVGFactory(
			KernelFlowElement kernelFlowElement, String svgTemplateFileName,
			String taskState,
			AbstractFlowNodeVOFactory abstractFlowNodeVOFactory) {
		// 当前实现是SVG格式，后期可能支持微软的XML
		return AbstractFlowNodeSVGFactory.createSVGFactory(kernelFlowElement,
				svgTemplateFileName, taskState, abstractFlowNodeVOFactory);
	}

	/**
	 * 文档内容过滤
	 * 
	 * @param voNode
	 * @param filterCondition
	 */
	public abstract void filterParentVO(VONode voNode, String[] filterCondition);

	/**
	 * 过滤任务类型
	 * 
	 * @param voNode
	 * @param filterCondition
	 */
	public abstract void filterActivityTaskVO(VONode voNode,
			String[] filterCondition);

	/**
	 * 过滤连接器类型
	 * 
	 * @param voNode
	 * @param filterCondition
	 */
	public abstract void filterConnectorVO(VONode voNode,
			String[] filterCondition);

	/**
	 * 过滤子类型
	 * 
	 * @param voNode
	 * @param filterCondition
	 */
	public abstract void filterChildVO(VONode voNode,
			List<String> filterCondition);

	/**
	 * 根据子类型构造
	 * 
	 * @param svgType
	 * @return
	 */
	public abstract VONode createSVGVO(String svgType);

	/**
	 * 构造空白类型
	 * 
	 * @return
	 */
	public abstract VONode createSVGVO();
}
