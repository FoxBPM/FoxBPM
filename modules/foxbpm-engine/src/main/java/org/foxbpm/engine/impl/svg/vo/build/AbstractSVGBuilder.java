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
import org.foxbpm.engine.impl.svg.vo.SvgVO;

/**
 * 组件式样的构造，包括EVENT、ACTIVITY等组件
 * 需要设置的式样或属性包括ID、NAM、填充色、边框宽度、边框颜色、组件文本、组件文本式样、组件坐标、组件大小
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public abstract class AbstractSVGBuilder {
	protected SvgVO svgVo;

	public AbstractSVGBuilder(SvgVO svgVo) {
		this.svgVo = svgVo;
	}

	public static AbstractSVGBuilder createSVGBuilder(SvgVO svgVo, String type) {

		if (StringUtils.equalsIgnoreCase(type, SVGTypeNameConstant.TYPE_EVENT)) {
			return new TypeEventSVGBuilder(svgVo);
		}
		if (StringUtils.equalsIgnoreCase(type,
				SVGTypeNameConstant.TYPE_EVENT_NONE)) {
			return new EventSVGBuilder(svgVo);
		}
		if (StringUtils.equalsIgnoreCase(type,
				SVGTypeNameConstant.TYPE_ACTIVITY_MANUALTASK_LOOP)) {
			return new TaskSVGBuilder(svgVo);
		}
		return null;
	}

	/**
	 * 设置SVG的宽度
	 * 
	 * @param width
	 */
	public void setSvgWidth(String width) {
		this.svgVo.setWidth(width);
	}

	/**
	 * 设置SVG的高度
	 * 
	 * @param width
	 */
	public void setSvgHight(String hight) {
		this.svgVo.setHeight(hight);
	}

	public abstract void setX(String x);

	public abstract void setY(String y);

	public abstract void setWidth(String width);

	public abstract void setHight(String hight);

	public abstract void setStroke(String stroke);

	public abstract void setStrokeWidth(String strokeWidth);

	public abstract void setFill(String fill);

	public abstract void setText(String text);

	public abstract void setTextX(String textX);

	public abstract void setTextY(String textY);

	public abstract void setTextFontSize(String textFontSize);

	public abstract void setTextStroke(String textStroke);

	public abstract void setID(String id);

	public abstract void setName(String name);

	public abstract void setStyle(String style);

	public abstract void setTypeStroke(String stroke);

	public abstract void setTypeStrokeWidth(String strokeWidth);

	public abstract void setTypeFill(String fill);

	public abstract void setTypeStyle(String style);

}
