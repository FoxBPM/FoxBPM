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

import org.foxbpm.engine.impl.svg.vo.SvgVO;

/**
 * 任务组件式样构造
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 */
public class TaskSVGBuilder extends AbstractSVGBuilder {
	public TaskSVGBuilder(SvgVO svgVo) {
		super(svgVo);
	}

	@Override
	public void setX(String x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(String y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHight(String hight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStroke(String stroke) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStrokeWidth(String strokeWidth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFill(String fill) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setText(String text) {
		this.textVO.setElementValue(text);
	}

	@Override
	public void setTextX(String textX) {
		this.textVO.setX(textX);
	}

	@Override
	public void setTextY(String textY) {
		this.textVO.setY(textY);
	}

	@Override
	public void setTextFontSize(String textFontSize) {
		this.textVO.setFontSize(textFontSize);
	}

	@Override
	public void setTextStroke(String textStroke) {
		this.textVO.setStroke(textStroke);

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

}
