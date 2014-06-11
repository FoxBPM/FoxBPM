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
package org.foxbpm.engine.impl.svg.factory;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.engine.impl.svg.vo.build.EventSVGBuilder;

/**
 * BPMN2.0事件元素的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class EventSVGFactory extends AbstractSVGFactory {

	public EventSVGFactory(String svgTemplateFileName) {
		super(svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO() {
		SvgVO startEventNone = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		// 根据式样构造SVG图像
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				startEventNone, SVGTypeNameConstant.SVG_TYPE_EVENT);

		svgBuilder.setID("000001");
		svgBuilder.setName("000002");
		svgBuilder.setFill("white");
		svgBuilder.setStroke("red");
		svgBuilder.setStrokeWidth("2");

		svgBuilder.setTypeFill("red");
		svgBuilder.setTypeStrokeWidth("3");
		return startEventNone;
	}

	@Override
	public VONode createSVGVO(String svgType) {
		// TODO Auto-generated method stub
		return null;
	}

}
