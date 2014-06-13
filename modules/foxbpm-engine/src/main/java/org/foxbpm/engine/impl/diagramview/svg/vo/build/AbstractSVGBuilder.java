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
package org.foxbpm.engine.impl.diagramview.svg.vo.build;

import java.awt.Font;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.builder.FoxBpmnViewBuilder;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.TextVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.VONode;

/**
 * 组件式样的构造，包括EVENT、ACTIVITY等组件
 * 需要设置的式样或属性包括ID、NAM、填充色、边框宽度、边框颜色、组件文本、组件文本式样、组件坐标、组件大小
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public abstract class AbstractSVGBuilder implements FoxBpmnViewBuilder {
	/**
	 * BPMN节点类型(例如：矩形，圆形)在SVG文档中的ID
	 */
	public static final String BPMN_NODE_ID = "bg_frame";
	public static final String COLOR_FLAG = "#";
	public static final String STROKE_DEFAULT = "black";
	public static final String STROKEWIDTH_DEFAULT = "1";
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
	public static AbstractSVGBuilder createSVGBuilder(VONode svgVo, String type) {

		if (StringUtils.equalsIgnoreCase(type,
				SVGTypeNameConstant.SVG_TYPE_EVENT)) {
			return new EventSVGBuilder((SvgVO) svgVo);
		}
		if (StringUtils.contains(type, SVGTypeNameConstant.SVG_TYPE_TASK)) {
			return new TaskSVGBuilder((SvgVO) svgVo);
		}
		if (StringUtils.contains(type, SVGTypeNameConstant.SVG_TYPE_CONNECTOR)) {
			return new ConnectorSVGBuilder((SvgVO) svgVo);
		}
		return null;
	}

	/**
	 * 设置线条的乖点
	 * 
	 * @param wayPointArray
	 */
	public abstract void setWayPoints(List<Point> pointList);

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
	public void setText(String text) {
		this.textVO.setElementValue(text);
	}

	public void setTextFont(String fontStr) {
		if (StringUtils.isBlank(fontStr)) {
			Font font = new Font("arial", Font.PLAIN, 11);
			this.textVO.setFont(font);
			return;
		}
		String[] fonts = fontStr.split(",");
		String style = "font-family:" + fonts[0] + ";font-size:" + fonts[1];
		Font font = new Font(fonts[0], Font.PLAIN, Integer.valueOf(fonts[1]));
		this.textVO.setFont(font);
		this.textVO.setStyle(style);
	}

	public void setTextStrokeWidth(String textStrokeWidth) {
		if (StringUtils.isBlank(textStrokeWidth)) {
			this.textVO.setStrokeWidth("0");
		} else {
			this.textVO.setStrokeWidth(textStrokeWidth);
		}
	}

	/**
	 * 设置显示名的X坐标
	 * 
	 * @param textX
	 */
	public void setTextX(String textX) {
		this.textVO.setX(textX);
	}

	/**
	 * 设置显示名的Y坐标
	 * 
	 * @param textY
	 */
	public void setTextY(String textY) {
		this.textVO.setY(textY);
	}

	/**
	 * 设置显示名的字体大小
	 * 
	 * @param textFontSize
	 */
	public void setTextFontSize(String textFontSize) {
		this.textVO.setFontSize(textFontSize);
	}

	/**
	 * 设置显示名的边框色
	 * 
	 * @param textStroke
	 */
	public void setTextStroke(String textStroke) {
		if (StringUtils.isBlank(textStroke)) {
			this.textVO.setStroke("black");
			return;
		}
		this.textVO.setStroke(COLOR_FLAG + textStroke);
	}

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
