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

import org.foxbpm.engine.impl.diagramview.svg.Point;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;

/**
 * 
 * 
 * TextAnnotationSVGBuilder
 * 
 * MAENLIANG 2014年7月15日 下午9:00:56
 * 
 * @version 1.0.0
 * 
 */
public class TextAnnotationSVGBuilder extends AbstractSVGBuilder {

	public TextAnnotationSVGBuilder(SvgVO svgVo) {
		super(svgVo);
	}

	 
	public void setWayPoints(List<Point> pointList) {

	}

	 
	public void setWidth(float width) {
		// TODO Auto-generated method stub

	}

	 
	public void setHeight(float height) {
		// TODO Auto-generated method stub

	}

	 
	public void setXAndY(float x, float y) {
		// 设置整体坐标，包括子类型
		this.svgVo.getgVo().setTransform(
				new StringBuffer(TRANSLANT_PREFIX).append(x).append(COMMA).append(y)
						.append(BRACKET_SUFFIX).toString());
	}

	 
	public void setStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	 
	public void setStrokeWidth(float strokeWidth) {
		// TODO Auto-generated method stub

	}

	 
	public void setFill(String fill) {
		// TODO Auto-generated method stub

	}

	 
	public void setID(String id) {
		// TODO Auto-generated method stub

	}

	 
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	 
	public void setStyle(String style) {
		// TODO Auto-generated method stub

	}

	 
	public void setTypeStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	 
	public void setTypeStrokeWidth(float strokeWidth) {
		// TODO Auto-generated method stub

	}

	 
	public void setTypeFill(String fill) {
		// TODO Auto-generated method stub

	}

	 
	public void setTypeStyle(String style) {
		// TODO Auto-generated method stub

	}

}
