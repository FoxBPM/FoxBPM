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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.factory.AbstractFlowElementVOFactory;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.diagramview.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.GVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.kernel.process.KernelBaseElement;

/**
 * FLOW单个节点SVG工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractFlowElementSVGFactory extends AbstractFlowElementVOFactory {
	/**
	 * static relation
	 */
	private final static Map<String, Class<?>> svgFactoryMap = initSvgFactoryMap();
	/**
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 */
	public AbstractFlowElementSVGFactory(KernelBaseElement kernelBaseElement, String svgTemplateFileName) {
		super(kernelBaseElement, svgTemplateFileName);
	}
	
	/**
	 * 
	 * initSvgBuildMap(static relation)
	 * 
	 * @return Map<String,Class<?>>
	 * @exception
	 * @since 1.0.0
	 */
	private static Map<String, Class<?>> initSvgFactoryMap() {
		Map<String, Class<?>> tempSvgFactoryMap = new HashMap<String, Class<?>>();
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_STARTEVENT_TIMER, EventSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_STARTEVENT_NONE, EventSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_CATCHEVENT_TIMER, EventSVGFactory.class);
		
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_ENDEVENT_NONE, EventSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_ENDEVENT_TERMINATE, EventSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS, SubProcessSVGFactory.class);
		
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS_COLLAPSED, SubProcessSVGFactory.class);
		
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK, TaskSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_CONNECTOR_ASSOCIATION, AssociationSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_CONNECTOR_SEQUENCEFLOW, ConnectorSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_CALLACTIVITY, CallActivitySVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_GATEWAY_EXCLUSIVE, GatewaySVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_GATEWAY_INCLUSIVE, GatewaySVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_GATEWAY_PARALLEL, GatewaySVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_GROUP, GroupSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_TEXTANNOTATION, TextAnnotationSVGFactory.class);
		tempSvgFactoryMap.put(SVGTemplateNameConstant.TEMPLATE_LANESET, LanesetSVGFactory.class);
		
		return tempSvgFactoryMap;
	}
	/**
	 * 创建具体的工厂类,目前支持三种实现
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowElementSVGFactory createSVGFactory(
	    KernelBaseElement kernelBaseElement, String svgTemplateFileName) {
		try {
			return (AbstractFlowElementSVGFactory) svgFactoryMap.get(svgTemplateFileName).getConstructor(KernelBaseElement.class, String.class).newInstance(kernelBaseElement, svgTemplateFileName);
		} catch (Exception e) {
			throw new FoxBPMException("createSVGFactory exception ", e);
		}
		
	}
	
	/**
	 * 将所有的元素对象转化成String字符串
	 * 
	 * @param voNodeList
	 *            所有的节点集合
	 * @return SVG字符串
	 */
	@Override
	public String convertNodeListToString(Map<String, Object> processDefinitionPorperties,
	    List<VONode> voNodeList) {
		VONode svgContainer = this.getDefaultSVGContainerFromFactory(processDefinitionPorperties);
		Iterator<VONode> voIter = voNodeList.iterator();
		SvgVO svgVo = null;
		GVO clone = null;
		List<GVO> getgVoList = null;
		while (voIter.hasNext()) {
			svgVo = (SvgVO) voIter.next();
			clone = SVGUtils.cloneGVO(svgVo.getgVo());
			getgVoList = ((SvgVO) svgContainer).getgVo().getgVoList();
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
		VONode svgTemplateContainer = SVGTemplateContainer.getContainerInstance().getCloneTemplateByName(SVGTemplateNameConstant.SVG_TEMPLATE);
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
			return SVGTemplateContainer.getContainerInstance().getCloneTemplateByName(templateName);
		} catch (Exception e) {
			throw new FoxBPMException("template svg file load exception", e);
		}
	}
	
	/**
	 * 获取模版容器对象
	 */
	private VONode getDefaultSVGContainerFromFactory(Map<String, Object> processDefinitionPorperties) {
		SvgVO svgTemplateContainer = (SvgVO) AbstractFlowElementSVGFactory.createSVGTemplateContainerVO(processDefinitionPorperties);
		Float svgMinX = (Float) processDefinitionPorperties.get(CANVAS_MINX);
		Float svgMinY = (Float) processDefinitionPorperties.get(CANVAS_MINY);
		Float svgMaxX = (Float) processDefinitionPorperties.get(CANVAS_MAXX);
		Float svgMaxY = (Float) processDefinitionPorperties.get(CANVAS_MAXY);
		svgTemplateContainer.setWidth(svgMaxX);
		svgTemplateContainer.setHeight(svgMaxY);
		svgTemplateContainer.setMinHeight(String.valueOf(svgMinY));
		svgTemplateContainer.setMinWidth(String.valueOf(svgMinX));
		// 初始化VOList
		svgTemplateContainer.getgVo().setgVoList(new ArrayList<GVO>());
		return svgTemplateContainer;
	}
}
