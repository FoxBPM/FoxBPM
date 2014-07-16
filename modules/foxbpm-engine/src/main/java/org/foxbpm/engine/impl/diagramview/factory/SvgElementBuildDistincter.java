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
import org.foxbpm.kernel.process.impl.KernelBaseElementImpl;

/**
 * 
 * 
 * SvgElementBuildDistincter
 * 
 * MAENLIANG 2014年7月16日 上午10:10:57
 * 
 * @version 1.0.0
 * 
 */
public class SvgElementBuildDistincter {
	private FoxBpmnViewBuilder svgBuilder;
	private KernelBaseElementImpl kernelBaseElementImpl;
	public SvgElementBuildDistincter() {

	}
	public SvgElementBuildDistincter(FoxBpmnViewBuilder svgBuilder,
			KernelBaseElementImpl kernelBaseElementImpl) {
		this.svgBuilder = svgBuilder;
		this.kernelBaseElementImpl = kernelBaseElementImpl;
	}

	/**
	 * 
	 * createCommoneElement(构造通用元素，活动，事件，泳道，组)
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void createCommoneElement() {
		if (svgBuilder != null && kernelBaseElementImpl != null) {
			// ID
			svgBuilder.setID(kernelBaseElementImpl.getId());
			// 文本
			if (StringUtils.isNotBlank(kernelBaseElementImpl.getName())) {
				svgBuilder.setText(kernelBaseElementImpl.getName());
				svgBuilder.setTextStroke((String) kernelBaseElementImpl
						.getProperty(StyleOption.TextColor));
				svgBuilder.setTextFill((String) kernelBaseElementImpl
						.getProperty(StyleOption.TextColor));
				svgBuilder.setTextStrokeWidth(0);
				svgBuilder
						.setTextFont((String) kernelBaseElementImpl.getProperty(StyleOption.Font));
			}
			// 宽度，高度
			svgBuilder.setWidth(kernelBaseElementImpl.getWidth());
			svgBuilder.setHeight(kernelBaseElementImpl.getHeight());
			// 边框式样
			svgBuilder
					.setStroke((String) kernelBaseElementImpl.getProperty(StyleOption.Foreground));
			// 坐标
			svgBuilder.setXAndY(kernelBaseElementImpl.getX(), kernelBaseElementImpl.getY());
			// 背景色
			svgBuilder.setFill((String) kernelBaseElementImpl.getProperty(StyleOption.Background));
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
		if (svgBuilder != null && kernelBaseElementImpl != null) {
			// ID
			svgBuilder.setID(kernelBaseElementImpl.getId());
			// 文本
			if (StringUtils.isNotBlank(text)) {
				svgBuilder.setText(text);
				svgBuilder.setTextStroke((String) kernelBaseElementImpl
						.getProperty(StyleOption.TextColor));
				svgBuilder.setTextFill((String) kernelBaseElementImpl
						.getProperty(StyleOption.TextColor));
				svgBuilder.setTextStrokeWidth(0);
				svgBuilder
						.setTextFont((String) kernelBaseElementImpl.getProperty(StyleOption.Font));
			}
			// 宽度，高度
			svgBuilder.setWidth(kernelBaseElementImpl.getWidth());
			svgBuilder.setHeight(kernelBaseElementImpl.getHeight());
			// 边框式样
			svgBuilder
					.setStroke((String) kernelBaseElementImpl.getProperty(StyleOption.Foreground));
			// 坐标
			svgBuilder.setXAndY(kernelBaseElementImpl.getX(), kernelBaseElementImpl.getY());
			// 背景色
			svgBuilder.setFill((String) kernelBaseElementImpl.getProperty(StyleOption.Background));
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
			svgBuilder.setText(name);
			// 设置文本的相对位置
			Point textPoint = PointUtils.caclDetailCenterPoint(pointList);
			svgBuilder.setTextX(textPoint.getX());
			svgBuilder.setTextY(textPoint.getY());
		}

		svgBuilder.setStroke(stroke);

	}

	public void setSvgBuilder(FoxBpmnViewBuilder svgBuilder) {
		this.svgBuilder = svgBuilder;
	}
	public void setKernelBaseElementImpl(KernelBaseElementImpl kernelBaseElementImpl) {
		this.kernelBaseElementImpl = kernelBaseElementImpl;
	}

}
