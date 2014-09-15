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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 网关元素 BUILDER GatewaySVGBuilder
 * 
 * MAENLIANG 2014年7月1日 下午8:32:28
 * 
 * @version 1.0.0
 * 
 */
public class GatewaySVGBuilder extends AbstractSVGBuilder {
	private static final String DEFAULT_FILL = "ffffff";
	private PathVO pathVo;
	private float tempX;
	private float tempY;
	private float tempWidth;
	private float tempHeight;

	public GatewaySVGBuilder(SvgVO svgVo) {
		super(svgVo);
		this.pathVo = SVGUtils.getGatewayVOFromSvgVO(svgVo);
	}

	@Override
	public void setWidth(float width) {
		this.tempWidth = width;
	}

	@Override
	public void setHeight(float height) {
		this.tempHeight = height;
	}

	@Override
	public void setXAndY(float x, float y) {
		this.tempX = x;
		this.tempY = y;
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform(
				new StringBuffer(TRANSLANT_PREFIX).append(tempX).append(COMMA).append(tempY)
						.append(BRACKET_SUFFIX).toString());

		// 设置文本坐标
		String elementValue = textVO.getElementValue();
		if (StringUtils.isNotBlank(elementValue)) {
			int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(), elementValue);
			if (SVGUtils.isChinese(elementValue.charAt(0))) {
				textWidth = textWidth + (textWidth / 20) * 5;
			}
			super.setTextX((this.tempWidth / 2) - textWidth / 2);
			super.setTextY(this.tempHeight + 10);
		}

	}
	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			stroke = STROKE_DEFAULT;
		}
		this.pathVo.setStroke(COLOR_FLAG + stroke);
	}

	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.pathVo.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			fill = DEFAULT_FILL;
		}
		this.buildLinearGradient(fill, pathVo, 0, 0, 0, 40);
	}

	@Override
	public void setID(String id) {
		this.pathVo.setId(id);
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
