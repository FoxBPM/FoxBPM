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
package org.foxbpm.engine.impl.svg.vo.build;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.TextVO;

/**
 * 组件式样的构造，包括EVENT、ACTIVITY等组件
 * 需要设置的式样或属性包括ID、NAM、填充色、边框宽度、边框颜色、组件文本、组件文本式样、组件坐标、组件大小
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public abstract class AbstractSVGBuilder {
	/**
	 * BPMN节点类型(例如：矩形，圆形)在SVG文档中的ID
	 */
	public static final String BPMN_NODE_ID = "bg_frame";
	/**
	 * SVG 对象
	 */
	protected SvgVO svgVo;
	/**
	 * 文本对象
	 */
	protected TextVO textVO;

	public AbstractSVGBuilder(SvgVO svgVo) {
		this.svgVo = svgVo;
		this.textVO = svgVo.getgVo().getTextVo();
	}

	/**
	 * 根据type创建Builder对象，例如任务SVG，事件SVG，连接线SVG，，，
	 * 
	 * @param svgVo
	 * @param type
	 * @return
	 */
	public static AbstractSVGBuilder createSVGBuilder(SvgVO svgVo, String type) {

		if (StringUtils.equalsIgnoreCase(type,
				SVGTypeNameConstant.SVG_TYPE_EVENT)) {
			return new EventSVGBuilder(svgVo);
		}
		if (StringUtils.contains(type, SVGTypeNameConstant.SVG_TYPE_TASK)) {
			return new TaskSVGBuilder(svgVo);
		}
		if (StringUtils.contains(type, SVGTypeNameConstant.SVG_TYPE_CONNECTOR)) {
			return new ConnectorSVGBuilder(svgVo);
		}
		return null;
	}

	/**
	 * 设置SVG的宽度
	 * 
	 * @param width
	 */
	public abstract void setWidth(String width);

	/**
	 * 设置SVG的高度
	 * 
	 * @param width
	 */
	public abstract void setHeight(String height);

	/**
	 * 设置元素的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void setXAndY(String x, String y);

	/**
	 * 设置元素的边框色
	 * 
	 * @param stroke
	 */
	public abstract void setStroke(String stroke);

	/**
	 * 设置元素的边框宽度
	 * 
	 * @param strokeWidth
	 */
	public abstract void setStrokeWidth(String strokeWidth);

	/**
	 * 设置元素的填充色，即背景色
	 * 
	 * @param fill
	 */
	public abstract void setFill(String fill);

	/**
	 * 设置元素的显示名
	 * 
	 * @param text
	 */
	public abstract void setText(String text);

	/**
	 * 设置显示名的X坐标
	 * 
	 * @param textX
	 */
	public abstract void setTextX(String textX);

	/**
	 * 设置显示名的Y坐标
	 * 
	 * @param textY
	 */
	public abstract void setTextY(String textY);

	/**
	 * 设置显示名的字体大小
	 * 
	 * @param textFontSize
	 */
	public abstract void setTextFontSize(String textFontSize);

	/**
	 * 设置显示名的边框色
	 * 
	 * @param textStroke
	 */
	public abstract void setTextStroke(String textStroke);

	/**
	 * 设置元素ID
	 * 
	 * @param id
	 */
	public abstract void setID(String id);

	/**
	 * 设置元素Name
	 * 
	 * @param name
	 */
	public abstract void setName(String name);

	/**
	 * 设置元素的式样
	 * 
	 * @param style
	 */
	public abstract void setStyle(String style);

	/**
	 * 设置子类型的边框颜色
	 * 
	 * @param stroke
	 */
	public abstract void setTypeStroke(String stroke);

	/**
	 * 设置子类型的边框宽度
	 * 
	 * @param strokeWidth
	 */
	public abstract void setTypeStrokeWidth(String strokeWidth);

	/**
	 * 设置子类型的填充式样
	 * 
	 * @param fill
	 */
	public abstract void setTypeFill(String fill);

	/**
	 * 设置子类型的式样
	 * 
	 * @param style
	 */
	public abstract void setTypeStyle(String style);

}
