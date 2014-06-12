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

import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.bpmn.parser.StyleOption;
import org.foxbpm.engine.impl.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.svg.SVGTemplateNameConstant;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.engine.impl.svg.vo.build.AbstractSVGBuilder;
import org.foxbpm.kernel.process.KernelFlowElement;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;

/**
 * FLOW单个节点SVG工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractFlowNodeSVGFactory {
	protected String svgTemplateFileName;
	protected KernelFlowElement kernelFlowElement;

	/**
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 */
	public AbstractFlowNodeSVGFactory(KernelFlowElement kernelFlowElement,
			String svgTemplateFileName) {
		this.svgTemplateFileName = svgTemplateFileName;
		this.kernelFlowElement = kernelFlowElement;
	}

	/**
	 * 构造流程元素SVG 包括连接线
	 * 
	 * @param svgType
	 * @return
	 */
	public VONode createFlowElementSVGVO(String svgType) {
		SvgVO svgVo = null;
		if (StringUtils.equalsIgnoreCase(svgType,
				SVGTypeNameConstant.SVG_TYPE_EVENT)
				|| StringUtils.equalsIgnoreCase(svgType,
						SVGTypeNameConstant.SVG_TYPE_CONNECTOR)) {
			svgVo = (SvgVO) this.createSVGVO();
		} else {
			svgVo = (SvgVO) this.createSVGVO(svgType);
		}
		AbstractSVGBuilder svgBuilder = AbstractSVGBuilder.createSVGBuilder(
				svgVo, svgType);

		// 构造节点元素
		if (kernelFlowElement instanceof KernelFlowNodeImpl) {
			KernelFlowNodeImpl kernelFlowNodeImpl = (KernelFlowNodeImpl) kernelFlowElement;
			svgBuilder.setText(kernelFlowNodeImpl.getName());
			svgBuilder.setXAndY(String.valueOf(kernelFlowNodeImpl.getX()),
					String.valueOf(kernelFlowNodeImpl.getY()));

			svgBuilder.setFill((String) kernelFlowNodeImpl
					.getProperty(StyleOption.Background));
			svgBuilder.setWidth(String.valueOf(kernelFlowNodeImpl.getWidth()));
			svgBuilder
					.setHeight(String.valueOf(kernelFlowNodeImpl.getHeight()));
			svgBuilder.setTextStroke((String) kernelFlowNodeImpl
					.getProperty(StyleOption.TextColor));

			// 构造
			// 构造
			// TODO 未知属性
			kernelFlowNodeImpl.getProperty(StyleOption.Font);
			svgBuilder.setStroke((String) kernelFlowNodeImpl
					.getProperty(StyleOption.Foreground));
			String style = (String) kernelFlowNodeImpl
					.getProperty(StyleOption.StyleObject);
		}
		// 线条元素
		if (kernelFlowElement instanceof KernelSequenceFlowImpl) {
			KernelSequenceFlowImpl kernelSequenceFlowImpl = (KernelSequenceFlowImpl) kernelFlowElement;
			kernelSequenceFlowImpl.getProperty("");
			// TODO 待操作
		}
		return svgVo;

	}

	/**
	 * 创建具体的工厂类
	 * 
	 * @param kernelFlowElement
	 * @param svgTemplateFileName
	 * @return
	 */
	public static AbstractFlowNodeSVGFactory createSVGFactory(
			KernelFlowElement kernelFlowElement, String svgTemplateFileName) {
		if (StringUtils.contains(svgTemplateFileName, "event")) {
			return new EventSVGFactory(kernelFlowElement, svgTemplateFileName);
		}
		if (StringUtils.contains(svgTemplateFileName, "activity")) {
			return new TaskSVGFactory(kernelFlowElement, svgTemplateFileName);
		}
		if (StringUtils.contains(svgTemplateFileName, "connector")) {
			return new ConnectorSVGFactory(kernelFlowElement,
					svgTemplateFileName);
		}
		return null;
	}

	/**
	 * 创建SVG模板容器对象
	 * 
	 * @return
	 */
	public static VONode createSVGTemplateContainerVO(
			Map<String, Object> processDefinitionProperties) {
		VONode svgTemplateContainer = SVGTemplateContainer
				.getContainerInstance().getTemplateByName(
						SVGTemplateNameConstant.SVG_TEMPLATE);
		return svgTemplateContainer;
	}

	/**
	 * 加载SVG模版
	 * 
	 * @param templateName
	 *            模版名称
	 * @return SVG模版
	 */
	protected VONode loadSVGVO(String templateName) {
		try {
			return SVGTemplateContainer.getContainerInstance()
					.getTemplateByName(templateName);
		} catch (Exception e) {
			throw new FoxBPMException("template svg file load exception", e);
		}
	}

	/**
	 * 操作之后的SVG转化成String字符串
	 * 
	 * @param svgVo
	 * @return
	 */
	public static String createFlowNodeSVGString(VONode svgVo) {
		try {
			JAXBContext context = JAXBContext.newInstance(SvgVO.class);
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();

			marshal.marshal(svgVo, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new FoxBPMException("svg object convert to String exception",
					e);
		}
	}

	/**
	 * 
	 * 根据SVG文件模版，以及SVG类型构造SVG对象，例如：构建TASK SVG对象，可以根据TASK SVG模板和TASK
	 * 类型《manualTask,scriptTask,,》构造SVG 对象
	 * 
	 * @param svgType
	 * @return
	 */
	protected abstract VONode createSVGVO(String svgType);

	/**
	 * 根据具体的SVG文件名称创建对象，例如： 如果是构造事件SVG对象，则根据事件类型 对应的具体SVG文件，创建SVG对象
	 * 
	 * @return
	 */
	protected abstract VONode createSVGVO();

}
