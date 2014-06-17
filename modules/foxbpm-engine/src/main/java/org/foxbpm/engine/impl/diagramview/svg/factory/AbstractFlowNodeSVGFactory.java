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
package org.foxbpm.engine.impl.diagramview.svg.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.factory.AbstractFlowNodeVOFactory;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;
import org.foxbpm.kernel.process.KernelFlowElement;

/**
 * FLOW单个节点SVG工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractFlowNodeSVGFactory extends
		AbstractFlowNodeVOFactory {
	private static final String NODE_TYPE_EVENT = "event";
	private static final String NODE_TYPE_ACTIVITY = "activity";
	private static final String NODE_TYPE_CONNECTOR = "connector";
	private static final String NODE_TYPE_GATEWAY = "gateway";

	/**
	 * 流程定义的SVG画布坐标MINX
	 */
	protected static final String SVG_MINX = "canvas_minX";
	/**
	 * 流程定义的SVG画布坐标MINY
	 */
	protected static final String SVG_MINY = "canvas_minY";
	/**
	 * 流程定义的SVG画布坐标MAXX
	 */
	protected static final String SVG_MAXX = "canvas_maxX";
	/**
	 * 流程定义的SVG画布坐标MAXY
	 */
	protected static final String SVG_MAXY = "canvas_maxY";

	/**
	 * SVG模版容器VO对象ID
	 */
	protected static final String SVG_CONTAINER = "SVG_CONTAINER";

	/**
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 */
	public AbstractFlowNodeSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName) {
		super(kernelFlowElement, svgTemplateFileName);
	}

	/**
	 * 创建具体的工厂类,目前支持三种实现
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowNodeSVGFactory createSVGFactory(
			KernelFlowElement kernelFlowElement, String svgTemplateFileName,
			String taskState,
			AbstractFlowNodeVOFactory abstractFlowNodeSVGFactory) {
		return new SignProcessStateSVGFactory(kernelFlowElement,
				svgTemplateFileName, abstractFlowNodeSVGFactory, taskState);
	}

	/**
	 * 创建具体的工厂类,目前支持三种实现
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowNodeSVGFactory createSVGFactory(
			KernelFlowElement kernelFlowElement, String svgTemplateFileName) {
		if (StringUtils.contains(svgTemplateFileName, NODE_TYPE_EVENT)) {
			return new EventSVGFactory(kernelFlowElement, svgTemplateFileName);
		} else if (StringUtils
				.contains(svgTemplateFileName, NODE_TYPE_ACTIVITY)) {
			return new TaskSVGFactory(kernelFlowElement, svgTemplateFileName);
		} else if (StringUtils.contains(svgTemplateFileName,
				NODE_TYPE_CONNECTOR)) {
			return new ConnectorSVGFactory(kernelFlowElement,
					svgTemplateFileName);
		} else if (StringUtils.contains(svgTemplateFileName, NODE_TYPE_GATEWAY)) {
			return new GatewaySVGFactory(kernelFlowElement, svgTemplateFileName);
		}
		return null;
	}

	/**
	 * 将所有的元素对象转化成String字符串
	 * 
	 * @param voNodeList
	 *            所有的节点集合
	 * @return SVG字符串
	 */
	@Override
	public String convertNodeListToString(
			Map<String, Object> processDefinitionPorperties,
			List<VONode> voNodeList) {
		VONode svgContainer = this
				.getDefaultSVGContainerFromFactory(processDefinitionPorperties);
		Iterator<VONode> voIter = voNodeList.iterator();
		while (voIter.hasNext()) {
			SvgVO svgVo = (SvgVO) voIter.next();
			GVO clone = SVGUtils.cloneGVO(svgVo.getgVo());
			List<GVO> getgVoList = ((SvgVO) svgContainer).getgVo().getgVoList();
			getgVoList.add(clone);
		}
		return SVGUtils.createSVGString(svgContainer);
	}

	/**
	 * 创建SVG模板容器对象
	 * 
	 * @return
	 */
	public static VONode createSVGTemplateContainerVO(
			Map<String, Object> processDefinitionProperties) {
		VONode svgTemplateContainer = SVGTemplateContainer
				.getContainerInstance().getCloneTemplateByName(
						SVGTemplateNameConstant.SVG_TEMPLATE);
		return svgTemplateContainer;
	}

	/**
	 * 加载SVG模版
	 * 
	 * @param templateName
	 *            模版名称
	 * @return SVG模版
	 */
	protected VONode loadSVGVO(String templateName) {
		try {
			return SVGTemplateContainer.getContainerInstance()
					.getCloneTemplateByName(templateName);
		} catch (Exception e) {
			throw new FoxBPMException("template svg file load exception", e);
		}
	}

	/**
	 * 获取模版容器对象
	 */
	private VONode getDefaultSVGContainerFromFactory(
			Map<String, Object> processDefinitionPorperties) {
		SvgVO svgTemplateContainer = (SvgVO) AbstractFlowNodeSVGFactory
				.createSVGTemplateContainerVO(processDefinitionPorperties);
		Float svgMinX = (Float) processDefinitionPorperties.get(SVG_MINX);
		Float svgMinY = (Float) processDefinitionPorperties.get(SVG_MINY);
		Float svgMaxX = (Float) processDefinitionPorperties.get(SVG_MAXX);
		Float svgMaxY = (Float) processDefinitionPorperties.get(SVG_MAXY);
		svgTemplateContainer.setWidth(svgMaxX);
		svgTemplateContainer.setHeight(svgMaxY);
		svgTemplateContainer.setMinHeight(String.valueOf(svgMinY));
		svgTemplateContainer.setMinWidth(String.valueOf(svgMinX));
		// 初始化VOList
		svgTemplateContainer.getgVo().setgVoList(new ArrayList<GVO>());
		return svgTemplateContainer;
	}
}
