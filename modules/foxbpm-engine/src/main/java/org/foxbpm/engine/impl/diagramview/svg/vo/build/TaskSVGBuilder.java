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

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.DefsVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.LinearGradient;
import org.foxbpm.engine.impl.diagramview.svg.vo.RectVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.StopVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 任务组件式样构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGBuilder extends AbstractSVGBuilder {
	private static final String FILL_DEFAULT = "ffffcc";

	/**
	 * 矩形对象
	 */
	private RectVO rectVO;

	/**
	 * 任务节点Builder、获取节点矩形
	 * 
	 * @param svgVo
	 */
	public TaskSVGBuilder(SvgVO svgVo) {
		super(svgVo);
		rectVO = SVGUtils.getTaskVOFromSvgVO(svgVo);

		if (this.rectVO == null) {
			throw new FoxBPMException("TaskSVGBuilder构造 TASK SVG时，无法获取矩形对象");
		}

	}

	@Override
	public void setWidth(float width) {
		rectVO.setWidth(width);
	}

	@Override
	public void setHeight(float height) {
		this.rectVO.setHeight(height);
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
		this.buildLinearGradient(fill);
	}

	@Override
	public void setXAndY(float x, float y) {
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform("translate(" + x + ", " + y + ")");
		// 设置相对位置
		this.rectVO.setX(0.0F);
		this.rectVO.setY(0.0F);
		// 设置字体的相对偏移量,X相对是矩形宽度的一半减去文本本身屏宽的一半
		if (StringUtils.isNotBlank(textVO.getElementValue())) {
			int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(),
					this.textVO.getElementValue());
			int languageShift = -5;
			if (SVGUtils.isChinese(this.textVO.getElementValue().charAt(0))) {
				languageShift = 10;
			}
			super.setTextX((this.rectVO.getWidth() / 2) - textWidth / 2
					- languageShift);
			super.setTextY(this.rectVO.getHeight() / 2 + 5);
		}
	}

	@Override
	public void setID(String id) {

	}

	@Override
	public void setName(String name) {

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

	/**
	 * 线性渐变
	 * 
	 * @param fill
	 */
	private void buildLinearGradient(String fill) {
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			LinearGradient linearGradient = defsVo.getLinearGradient();
			if (linearGradient != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				linearGradient.setId(backGroudUUID);
				linearGradient.setX1(0.0F);
				linearGradient.setX2(0.0F);
				linearGradient.setY1(0.0F);
				linearGradient.setY2(this.rectVO.getHeight());
				List<StopVO> stopVoList = linearGradient.getStopVoList();
				if (stopVoList != null && stopVoList.size() > 0) {
					StopVO stopVO = stopVoList.get(LINEARGRADIENT_INDEX);
					this.rectVO.setFill("url(#" + backGroudUUID + ")");
					stopVO.setStopColor(COLOR_FLAG + fill);
				}
			}

		}
	}
}
