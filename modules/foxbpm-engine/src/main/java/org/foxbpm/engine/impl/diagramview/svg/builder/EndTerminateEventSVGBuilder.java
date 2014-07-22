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

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.diagramview.svg.SVGUtils;
import org.foxbpm.engine.impl.diagramview.svg.vo.CircleVO;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 
 * EndTerminateEventSVGBuilder 终止事件构造器
 * 
 * MAENLIANG 2014年7月22日 下午7:56:59
 * 
 * @version 1.0.0
 * 
 */
public class EndTerminateEventSVGBuilder extends EventSVGBuilder {
	private CircleVO terminateCircleVO;
	public EndTerminateEventSVGBuilder(SvgVO voNode) {
		super(voNode);
		this.terminateCircleVO = SVGUtils.getEndTerminateEventVOFromSvgVO(voNode);
	}
	@Override
	public void setStroke(String stroke) {
		if (StringUtils.isBlank(stroke)) {
			this.terminateCircleVO.setStroke(STROKE_DEFAULT);
			return;
		}
		this.terminateCircleVO.setStroke(COLOR_FLAG + stroke);
		super.setStroke(stroke);
	}
	@Override
	public void setStrokeWidth(float strokeWidth) {
		this.terminateCircleVO.setStrokeWidth(strokeWidth);
		super.setStrokeWidth(strokeWidth);
	}
	@Override
	public void setFill(String fill) {
		super.setFill(fill);
	}
	@Override
	public void setXAndY(float x, float y) {
		this.terminateCircleVO.setCx(x + this.circleVO.getR());
		this.terminateCircleVO.setCy(y + this.circleVO.getR());
		super.setXAndY(x, y);
	}
}
