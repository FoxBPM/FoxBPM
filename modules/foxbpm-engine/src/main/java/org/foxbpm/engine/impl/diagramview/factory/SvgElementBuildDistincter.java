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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.bpmn.parser.StyleOption;
import org.foxbpm.engine.impl.diagramview.builder.FoxBpmnViewBuilder;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.PointUtils;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.kernel.process.KernelBaseElement;

/**
 * 
 * 
 * SvgElementBuildDistincter 根据不同类型的流程定义元素进行创建
 * 
 * MAENLIANG 2014年7月16日 上午10:10:57
 * 
 * @version 1.0.0
 * 
 */
public class SvgElementBuildDistincter {
	/**
	 * FOBBPM构造接口
	 */
	private FoxBpmnViewBuilder svgBuilder;
	
	/**
	 * 流程节点定义元素
	 */
	private KernelBaseElement kernelBaseElement;
	
	/**
	 * 
	 * 创建一个新的实例 SvgElementBuildDistincter.
	 * 
	 */
	public SvgElementBuildDistincter() {
	}
	
	/**
	 * 创建一个新的实例 SvgElementBuildDistincter.
	 * 
	 * @param svgBuilder
	 *            构造器
	 * @param kernelBaseElement
	 *            元素
	 */
	public SvgElementBuildDistincter(FoxBpmnViewBuilder svgBuilder, KernelBaseElement kernelBaseElement) {
		this.svgBuilder = svgBuilder;
		this.kernelBaseElement = kernelBaseElement;
	}
	
	/**
	 * 
	 * createCommoneElement(构造通用元素，活动，事件，泳道，组)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void createCommoneElement() {
		if (svgBuilder != null && kernelBaseElement != null) {
			// ID
			svgBuilder.setID(kernelBaseElement.getId());
			// 文本
			if (StringUtils.isNotBlank(kernelBaseElement.getName())) {
				svgBuilder.setText(kernelBaseElement.getName());
				svgBuilder.setTextStroke((String) kernelBaseElement.getProperty(StyleOption.TextColor));
				svgBuilder.setTextFill((String) kernelBaseElement.getProperty(StyleOption.TextColor));
				svgBuilder.setTextStrokeWidth(0);
				svgBuilder.setTextFont((String) kernelBaseElement.getProperty(StyleOption.Font));
			}
			// 宽度，高度
			svgBuilder.setWidth(kernelBaseElement.getWidth());
			svgBuilder.setHeight(kernelBaseElement.getHeight());
			// 边框式样
			svgBuilder.setStroke((String) kernelBaseElement.getProperty(StyleOption.Foreground));
			// 坐标
			svgBuilder.setXAndY(kernelBaseElement.getX(), kernelBaseElement.getY());
			// 背景色
			svgBuilder.setFill((String) kernelBaseElement.getProperty(StyleOption.Background));
		}
		
	}
	/**
	 * 
	 * createCommoneElement(构造注释)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void createTextAnnotation(String text, String textFormat) {
		if (svgBuilder != null && kernelBaseElement != null) {
			// ID
			svgBuilder.setID(kernelBaseElement.getId());
			// 文本
			if (StringUtils.isNotBlank(text)) {
				svgBuilder.setText(text);
				svgBuilder.setTextStroke((String) kernelBaseElement.getProperty(StyleOption.TextColor));
				svgBuilder.setTextFill((String) kernelBaseElement.getProperty(StyleOption.TextColor));
				svgBuilder.setTextStrokeWidth(0);
				svgBuilder.setTextFont((String) kernelBaseElement.getProperty(StyleOption.Font));
			}
			// 宽度，高度
			svgBuilder.setWidth(kernelBaseElement.getWidth());
			svgBuilder.setHeight(kernelBaseElement.getHeight());
			// 边框式样
			svgBuilder.setStroke((String) kernelBaseElement.getProperty(StyleOption.Foreground));
			// 坐标
			svgBuilder.setXAndY(kernelBaseElement.getX(), kernelBaseElement.getY());
			// 背景色
			svgBuilder.setFill((String) kernelBaseElement.getProperty(StyleOption.Background));
		}
		
	}
	/**
	 * 
	 * createSequenceElement(构造线条)
	 * 
	 * @param id
	 * @param name
	 * @param stroke
	 * @param waypoints
	 *            线条拐点 void
	 * @exception
	 * @since 1.0.0
	 */
	public void createSequenceElement(String id, String name, String stroke, List<Integer> waypoints) {
		List<Point> pointList = SVGUtils.convertWaypointsTOPointList(waypoints);
		// 构造
		svgBuilder.setID(id);
		svgBuilder.setWayPoints(pointList);
		if (StringUtils.isNotBlank(name)) {
			svgBuilder.setTextStroke((String) kernelBaseElement.getProperty(StyleOption.TextColor));
			svgBuilder.setTextFill((String) kernelBaseElement.getProperty(StyleOption.TextColor));
			svgBuilder.setTextStrokeWidth(0);
			svgBuilder.setTextFont((String) kernelBaseElement.getProperty(StyleOption.Font));

			svgBuilder.setText(name);
			// 设置文本的相对位置
			Point textPoint = PointUtils.caclDetailCenterPoint(pointList,svgBuilder.getTextVo());
			svgBuilder.setTextX(textPoint.getX());
			svgBuilder.setTextY(textPoint.getY());
		}
		
		svgBuilder.setStroke(stroke);
		
	}
	
	public void setSvgBuilder(FoxBpmnViewBuilder svgBuilder) {
		this.svgBuilder = svgBuilder;
	}
	public void setKernelBaseElement(KernelBaseElement kernelBaseElement) {
		this.kernelBaseElement = kernelBaseElement;
	}
	
}
