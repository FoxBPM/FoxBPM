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
package org.foxbpm.engine.impl.diagramview.svg.builder;

import java.awt.Font;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 泳道元素BUILDER LanesetSVGBuilder
 * 
 * MAENLIANG 2014年7月1日 下午8:33:07
 * 
 * @version 1.0.0
 * 
 */
public class LanesetSVGBuilder extends AbstractSVGBuilder {
	private static final String FILL_DEFAULT = "F7F7F7";
	/**
	 * 矩形对象
	 */
	private RectVO rectVO;

	public LanesetSVGBuilder(SvgVO svgVo) {
		super(svgVo);
		rectVO = SVGUtils.getTaskVOFromSvgVO(svgVo);
	}

	@Override
	public void setWidth(float width) {
		this.rectVO.setWidth(width);
	}

	@Override
	public void setHeight(float height) {
		this.rectVO.setHeight(height);
	}

	@Override
	public void setXAndY(float x, float y) {
		this.rectVO.setX(x);
		this.rectVO.setY(y);

	}

	/**
	 * 根据垂直水平泳道设置 泳道文本坐标
	 */
	public void setTextLocationByHerizonFlag(boolean herizonFlag) {
		// 设置文本坐标
		float textX = 0;
		float textY = 0;
		int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(), this.textVO.getElementValue());
		if (herizonFlag) {
			// 水平泳道文本需要旋转
			textX = this.rectVO.getX() + 10.0f;
			textY = this.rectVO.getY() + this.rectVO.getHeight() / 2 + textWidth / 2;
			this.textVO.setTransform("rotate(180 " + textX + COMMA + textY + BRACKET_SUFFIX);
			StringBuffer styleBuffer = new StringBuffer(this.textVO.getStyle());
			this.textVO.setStyle(styleBuffer.append(";writing-mode: tb;").toString());

		} else {
			// 垂直泳道 文本不需要旋转
			textX = this.rectVO.getX() + this.rectVO.getWidth() / 2 - textWidth / 2;
			textY = this.rectVO.getY() + 10.0f;
		}
		this.textVO.setX(textX);
		this.textVO.setY(textY);

	}

	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			this.rectVO.setStroke(STROKE_DEFAULT);
			return;
		}
		this.rectVO.setStroke(COLOR_FLAG + stroke);
	}

	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.rectVO.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			fill = FILL_DEFAULT;
		}
		float x1 = this.rectVO.getX();
		float x2 = x1;
		float y1 = this.rectVO.getY();
		float y2 = y1 + this.rectVO.getHeight();
		this.buildLinearGradient(fill, rectVO, x1, x2, y1, y2);

	}

	public void setTextFont(String fontStr) {
		if (StringUtils.isBlank(fontStr)) {
			Font font = new Font(ARIAL, Font.CENTER_BASELINE, 11);
			this.textVO.setFont(font);
			return;
		}
		String[] fonts = fontStr.split(COMMA);
		StringBuffer styleBuffer = new StringBuffer();
		String style = styleBuffer.append("font-family:").append(fonts[0]).append(";font-size:")
				.append(fonts[1]).toString();
		Font font = new Font(fonts[0], Font.PLAIN, Integer.valueOf(fonts[1]));
		this.textVO.setFont(font);
		this.textVO.setStyle(style);
		this.textVO.setFontSize(fonts[1]);
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStyle(String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStrokeWidth(float strokeWidth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeFill(String fill) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeStyle(String style) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWayPoints(List<Point> pointList) {
		// TODO Auto-generated method stub

	}

}
