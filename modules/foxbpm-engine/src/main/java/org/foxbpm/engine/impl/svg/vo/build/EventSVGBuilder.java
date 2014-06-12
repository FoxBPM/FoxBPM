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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.svg.vo.CircleVO;
import org.foxbpm.engine.impl.svg.vo.DefsVO;
import org.foxbpm.engine.impl.svg.vo.PathVO;
import org.foxbpm.engine.impl.svg.vo.RadialGradientVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;

/**
 * 默认空白事件式样构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class EventSVGBuilder extends AbstractSVGBuilder {
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
		List<CircleVO> circleVoList = voNode.getgVo().getCircleVoList();
		Iterator<CircleVO> iterator = circleVoList.iterator();
		while (iterator.hasNext()) {
			CircleVO next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), BPMN_NODE_ID)) {
				this.circleVO = next;
				break;
			}
		}
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
	public void setTypeStrokeWidth(String strokeWidth) {
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
	public void setWidth(String width) {
		this.circleVO.setR(String.valueOf(Float.valueOf(width) / 2));

	}

	@Override
	public void setHeight(String height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			this.textVO.setStroke("black");
			return;
		}
		this.circleVO.setStroke("#" + stroke);
	}

	@Override
	public void setStrokeWidth(String strokeWidth) {
		this.circleVO.setStrokeWidth(strokeWidth);

	}

	@Override
	public void setFill(String fill) {
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			RadialGradientVO radialGradientVo = defsVo.getRadialGradientVo();
			if (radialGradientVo != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				radialGradientVo.setId(backGroudUUID);
				if (StringUtils.isBlank(fill)) {
					this.circleVO.setFill("url(#" + backGroudUUID + ") white");
				} else {
					this.circleVO.setFill("url(#" + backGroudUUID + ") #"
							+ fill);
				}

				return;
			}
		}

		if (StringUtils.isBlank(fill)) {
			this.circleVO.setFill("white");
		} else {
			this.circleVO.setFill("#" + fill);
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

	@Override
	public void setXAndY(String x, String y) {
		// 如果存在子类型，例如ERROR
		if (this.pathVo != null) {
			// 整体 SHIFT
			this.svgVo.getgVo().setTransform("translate(" + x + ", " + y + ")");
		} else {
			this.circleVO.setCx(x);
			this.circleVO.setCy(y);
		}
	}
}
