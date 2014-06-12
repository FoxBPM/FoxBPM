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
import org.foxbpm.engine.impl.svg.vo.DefsVO;
import org.foxbpm.engine.impl.svg.vo.GVO;
import org.foxbpm.engine.impl.svg.vo.RadialGradientVO;
import org.foxbpm.engine.impl.svg.vo.RectVO;
import org.foxbpm.engine.impl.svg.vo.SvgVO;

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
		List<RectVO> rectVoList = svgVo.getgVo().getRectVoList();
		Iterator<RectVO> iterator = rectVoList.iterator();
		while (iterator.hasNext()) {
			RectVO next = iterator.next();
			if (StringUtils.equalsIgnoreCase(next.getId(), BPMN_NODE_ID)) {
				rectVO = next;
				break;
			}
		}

		if (this.rectVO == null) {
			throw new FoxBPMException("TaskSVGBuilder构造 TASK SVG时，无法获取矩形对象");
		}

	}

	@Override
	public void setWidth(String width) {
		rectVO.setWidth(width);
	}

	@Override
	public void setHeight(String height) {
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
	public void setStrokeWidth(String strokeWidth) {
		if (StringUtils.isBlank(strokeWidth)) {
			this.rectVO.setStroke(STROKEWIDTH_DEFAULT);
			return;
		}
		this.rectVO.setStrokeWidth(strokeWidth);
	}

	@Override
	public void setFill(String fill) {
		if (StringUtils.isBlank(fill)) {
			fill = FILL_DEFAULT;
		}
		DefsVO defsVo = this.svgVo.getgVo().getDefsVo();
		if (defsVo != null) {
			RadialGradientVO radialGradientVo = defsVo.getRadialGradientVo();
			if (radialGradientVo != null) {
				String backGroudUUID = UUID.randomUUID().toString();
				radialGradientVo.setId(backGroudUUID);
				this.rectVO.setFill("url(#" + backGroudUUID + ") #" + fill);
				return;
			}
		}

		this.rectVO.setFill(COLOR_FLAG + fill);
	}

	@Override
	public void setXAndY(String x, String y) {
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform("translate(" + x + ", " + y + ")");

		// 矩形设置文字X坐标，矩形X坐标+宽度的一半
		this.textVO.setX(String.valueOf(Float.valueOf(x)
				+ (Float.valueOf(this.rectVO.getWidth()) / 2)));
		this.textVO.setY(String.valueOf(Float.valueOf(y)
				+ Float.valueOf(this.rectVO.getHeight()) + 10));
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
	public void setTypeStrokeWidth(String strokeWidth) {
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
	public void setWayPoints(String[] wayPointArray) {
		// TODO Auto-generated method stub

	}
}
