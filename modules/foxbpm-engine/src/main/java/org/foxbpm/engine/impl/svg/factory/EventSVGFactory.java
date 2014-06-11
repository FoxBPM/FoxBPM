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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.impl.bpmn.parser.StyleOption;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.engine.impl.svg.vo.build.EventSVGBuilder;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.model.config.style.Style;

/**
 * BPMN2.0事件元素的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public class EventSVGFactory extends AbstractFlowNodeSVGFactory {

	public EventSVGFactory(String svgTemplateFileName) {
		super(svgTemplateFileName);
	}

	@Override
	public VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl) {
		SvgVO startEventNone = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		// 根据式样构造SVG图像
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				startEventNone, SVGTypeNameConstant.SVG_TYPE_EVENT);
		// 获取所有式样
		Map<String, Object> svgStyleProps = kernelFlowNodeImpl.getProperties();
		// 设置整个组件包括子类型的坐标、大小等等
		svgBuilder.setXAndY(String.valueOf(kernelFlowNodeImpl.getX()),
				String.valueOf(kernelFlowNodeImpl.getY()));
		svgBuilder.setWidth(String.valueOf(kernelFlowNodeImpl.getWidth()));
		svgBuilder.setFill((String) svgStyleProps.get(StyleOption.Background));
		svgBuilder.setTextStroke((String) svgStyleProps
				.get(StyleOption.TextColor));

		// TODO 未知属性
		svgStyleProps.get(StyleOption.Font);
		svgStyleProps.get(StyleOption.Foreground);
		String style = (String) svgStyleProps.get(StyleOption.StyleObject);

		return startEventNone;
	}

	@Override
	public VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl,
			String svgType) {
		SvgVO startEventNone = (SvgVO) super.loadSVGVO(svgTemplateFileName);
		// 根据式样构造SVG图像
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				startEventNone, svgType);
		// 获取所有式样
		Map<String, Object> svgStyleProps = kernelFlowNodeImpl.getProperties();
		// 设置整个组件包括子类型的坐标、大小等等
		svgBuilder.setXAndY(String.valueOf(kernelFlowNodeImpl.getX()),
				String.valueOf(kernelFlowNodeImpl.getY()));
		svgBuilder.setWidth(String.valueOf(kernelFlowNodeImpl.getWidth()));
		svgBuilder.setFill((String) svgStyleProps.get(StyleOption.Background));
		svgBuilder.setTextStroke((String) svgStyleProps
				.get(StyleOption.TextColor));

		// TODO 未知属性
		svgStyleProps.get(StyleOption.Font);
		svgStyleProps.get(StyleOption.Foreground);
		String style = (String) svgStyleProps.get(StyleOption.StyleObject);

		return startEventNone;
	}

}
