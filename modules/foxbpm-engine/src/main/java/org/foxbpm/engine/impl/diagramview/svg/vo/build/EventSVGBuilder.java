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
import org.foxbpm.engine.impl.diagramview.svg.vo.CircleVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.DefsVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.LinearGradient;
import org.foxbpm.engine.impl.diagramview.svg.vo.PathVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.StopVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 默认空白事件式样构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class EventSVGBuilder extends AbstractSVGBuilder {
	private static final String FILL_DEFAULT = "ffffff";
	/**
	 * 事件子类型对象
	 */
	private PathVO pathVo;
	/**
	 * 事件圆圈对象
	 */
	private CircleVO circleVO;

	/**
	 * 事件节点Builder，获取Circle对象
	 * 
	 * @param voNode
	 */
	public EventSVGBuilder(SvgVO voNode) {
		super(voNode);
		this.circleVO = SVGUtils.getEventVOFromSvgVO(voNode);
		if (this.circleVO == null) {
			throw new FoxBPMException("EventSVGBuilder构造 EVENT SVG时，无法获取圆形对象");
		}
		List<PathVO> pathVoList = this.svgVo.getgVo().getPathVoList();
		if (pathVoList != null && pathVoList.size() > 0) {
			pathVo = pathVoList.get(0);
		}
	}

	public void setTypeStyle(String typeStyle) {
		if (pathVo == null) {
			return;
		}
		pathVo.setStyle(typeStyle);
	}

	@Override
	public void setTypeStroke(String stroke) {
		if (pathVo == null) {
			return;
		}
		this.pathVo.setStroke(stroke);
	}

	@Override
	public void setTypeStrokeWidth(float strokeWidth) {
		if (pathVo == null) {
			return;
		}
		this.pathVo.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setTypeFill(String fill) {
		if (pathVo == null) {
			return;
		}
		this.pathVo.setFill(fill);
	}

	@Override
	public void setWidth(float width) {
		this.circleVO.setR(width / 2);

	}

	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			this.circleVO.setStroke(STROKE_DEFAULT);
			return;
		}
		this.circleVO.setStroke(COLOR_FLAG + stroke);
	}

	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.circleVO.setStrokeWidth(strokeWidth);

	}

	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			fill = FILL_DEFAULT;
		}
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			LinearGradient linearGradient = defsVo.getLinearGradient();
			if (linearGradient != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				linearGradient.setId(backGroudUUID);
				linearGradient.setX1(this.circleVO.getCx()
						- this.circleVO.getR());
				linearGradient.setX2(this.circleVO.getCx()
						- this.circleVO.getR());
				linearGradient.setY1(this.circleVO.getCy()
						- this.circleVO.getR());
				linearGradient.setY2(this.circleVO.getCy()
						+ this.circleVO.getR());
				List<StopVO> stopVoList = linearGradient.getStopVoList();
				if (stopVoList != null && stopVoList.size() > 0) {
					StopVO stopVO = stopVoList.get(LINEARGRADIENT_INDEX);
					this.circleVO.setFill("url(#" + backGroudUUID + ")");
					stopVO.setStopColor(COLOR_FLAG + fill);
					return;
				}

			}
		}
	}

	@Override
	public void setID(String id) {
		this.circleVO.setId(id);
	}

	@Override
	public void setName(String name) {
		this.circleVO.setName(name);
	}

	@Override
	public void setStyle(String style) {
		this.circleVO.setStyle(style);
	}

	/**
	 * @TODO 圆心坐标设置是绝对坐标值，后期如果需要添加子类型，则采用transform的形式
	 * 
	 */
	@Override
	public void setXAndY(float x, float y) {
		// 流程图定义的是圆对应矩形左上角的坐标，所以对应的SVG坐标需要将坐标值加半径
		y = y + this.circleVO.getR();
		x = x + this.circleVO.getR();
		// 如果是事件节点，字体横坐标和圆心的横坐标一直，纵坐标等圆心坐标值加圆的半径值
		if (StringUtils.isNotBlank(textVO.getElementValue())) {
			int textWidth = SVGUtils.getTextWidth(this.textVO.getFont(),
					this.textVO.getElementValue());
			int languageShift = 0;
			if (SVGUtils.isChinese(this.textVO.getElementValue().charAt(0))) {
				languageShift = 8;
			}
			this.setTextX(x - textWidth / 2 - languageShift);
			this.setTextY(y + this.circleVO.getR() + 20);
		}

		// 如果存在子类型，例如ERROR
		if (this.pathVo != null) {
			// 整体 SHIFT
			this.svgVo.getgVo().setTransform("translate(" + x + ", " + y + ")");
			// TODO 同时需要设置文本的相对偏移量
		} else {
			this.circleVO.setCx(x);
			this.circleVO.setCy(y);
		}
	}

	/**
	 * 圆圈不需要设置拐点
	 */
	public void setWayPoints(List<Point> pointList) {
		// TODO Auto-generated method stub

	}

	/**
	 * 圆圈已经设置半径，不需要在设置高度
	 */
	public void setHeight(float height) {
		// TODO Auto-generated method stub

	}
}
