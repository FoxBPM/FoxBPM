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
import org.foxbpm.engine.impl.svg.SVGTemplateContainer;
import org.foxbpm.engine.impl.svg.SVGTypeNameConstant;
import org.foxbpm.engine.impl.svg.vo.SvgVO;
import org.foxbpm.engine.impl.svg.vo.VONode;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

/**
 * FLOW单个节点SVG工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractFlowNodeSVGFactory {
	protected String svgTemplateFileName;

	public AbstractFlowNodeSVGFactory(String svgTemplateFileName) {
		this.svgTemplateFileName = svgTemplateFileName;
	}

	public static AbstractFlowNodeSVGFactory createSVGFactory(
			String svgTemplateFileName) {
		if (StringUtils.contains(svgTemplateFileName, "event")) {
			return new EventSVGFactory(svgTemplateFileName);
		}
		if (StringUtils.contains(svgTemplateFileName, "activity")) {
			return new TaskSVGFactory(svgTemplateFileName);
		}
		if (StringUtils.contains(svgTemplateFileName, "connector")) {
			return new ConnectorSVGFactory(svgTemplateFileName);
		}
		return null;
	}

	/**
	 * 
	 * 根据SVG文件模版，以及SVG类型构造SVG对象，例如：构建TASK SVG对象，可以根据TASK SVG模板和TASK
	 * 类型《manualTask,scriptTask,,》构造SVG 对象
	 * 
	 * @param svgType
	 * @return
	 */
	public abstract VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl,
			String svgType);

	/**
	 * 根据具体的SVG文件名称创建对象，例如： 如果是构造事件SVG对象，则根据事件类型 对应的具体SVG文件，创建SVG对象
	 * 
	 * @return
	 */
	public abstract VONode createSVGVO(KernelFlowNodeImpl kernelFlowNodeImpl);

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
	public String createFlowNodeSVGString(KernelFlowNodeImpl kernelFlowNodeImpl,
			String svgType) {
		try {
			JAXBContext context = JAXBContext.newInstance(SvgVO.class);
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();

			SvgVO svgVo = null;
			if (StringUtils.equalsIgnoreCase(svgType,
					SVGTypeNameConstant.SVG_TYPE_EVENT)
					|| StringUtils.equalsIgnoreCase(svgType,
							SVGTypeNameConstant.SVG_TYPE_CONNECTOR)) {
				svgVo = (SvgVO) this.createSVGVO(kernelFlowNodeImpl);
			} else {
				svgVo = (SvgVO) this.createSVGVO(kernelFlowNodeImpl, svgType);
			}

			marshal.marshal(svgVo, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new FoxBPMException("svg object convert to String exception",
					e);
		}
	}

}
