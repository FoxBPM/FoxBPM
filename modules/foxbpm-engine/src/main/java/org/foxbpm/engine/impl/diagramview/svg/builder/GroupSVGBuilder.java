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
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 
 * GroupSVGBuilder
 * 
 * MAENLIANG 2014年7月15日 下午9:00:39
 * 
 * @version 1.0.0
 * 
 */
public class GroupSVGBuilder extends AbstractSVGBuilder {

	private RectVO rectVO;
	public GroupSVGBuilder(SvgVO svgVo) {
		super(svgVo);
		rectVO = SVGUtils.getTaskVOFromSvgVO(svgVo);

		if (this.rectVO == null) {
			throw new FoxBPMException("GroupSVGBuilder构造 TASK SVG时，无法获取矩形对象");
		}
	}

	@Override
	public void setWayPoints(List<Point> pointList) {

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
		// 设置字体的相对偏移量,X相对是矩形宽度的一半减去文本本身屏宽的一半
		// TODO 目前支持全英文或者全中文、全日文、全韩文。
		String elementValue = textVO.getElementValue();
		if (StringUtils.isNotBlank(elementValue)) {
			int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(), elementValue);
			int languageShift = 0;
			if (SVGUtils.isChinese(elementValue.charAt(0))) {
				languageShift = 10;
				languageShift = languageShift + (textWidth >= 40 ? 3 : 0);
				textWidth = textWidth / 2;
			}
			super.setTextX((x + (this.rectVO.getWidth() / 2)) - textWidth / 2 - languageShift);
			super.setTextY(y + this.rectVO.getHeight() + 15);
		}
	}

	@Override
	public void setStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.rectVO.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setFill(String fill) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setID(String id) {
		this.rectVO.setId(id);
	}

	@Override
	public void setName(String name) {
		this.rectVO.setName(name);

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

}
