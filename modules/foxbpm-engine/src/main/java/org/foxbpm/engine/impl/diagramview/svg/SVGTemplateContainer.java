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
package org.foxbpm.engine.impl.diagramview.svg;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.diagramview.svg.vo.SvgVO;
import org.foxbpm.engine.impl.diagramview.vo.VONode;
import org.foxbpm.engine.impl.util.ReflectUtil;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * SVG模版资源维护的单例对象
 * 
 * @author MAENLIANG
 * @date 2014-06-08
 * 
 */
public class SVGTemplateContainer {

	private static final String BPMN_PATH = "bpmn/view";
	private static final String FILE_SPERATOR = "/";
	private static SVGTemplateContainer container = new SVGTemplateContainer();
	private Map<String, VONode> svgTemplets = null;

	private SVGTemplateContainer() {
		// 初始化SVG模版资源
		if (this.svgTemplets == null) {
			this.initTemplate();
		}
	}

	/**
	 * 获取模版所在容器的实例
	 * 
	 * @return
	 */
	public static SVGTemplateContainer getContainerInstance() {
		return container;
	}

	/**
	 * 获取svg模版
	 * 
	 * @param templateName
	 * @return
	 */
	public VONode getCloneTemplateByName(String templateName) {
		// 第一次需要从svg文档初始化
		if (this.svgTemplets.get(templateName) == null) {
			this.init(templateName);
		}
		// 返回模版的深度克隆对象
		return SVGUtils.cloneSVGVo((SvgVO) this.svgTemplets.get(templateName));
	}

	/**
	 * 初始化模版
	 */
	private void initTemplate() {
		svgTemplets = new HashMap<String, VONode>();
		// 初始化加载空白开始事件
		this.init(SVGTemplateNameConstant.TEMPLATE_STARTEVENT_NONE);
		// 初始化加载空白结束事件
		this.init(SVGTemplateNameConstant.TEMPLATE_ENDEVENT_NONE);
		// 初始化加载空白UserTask
		this.init(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_TASK);
		// 加载网关
		this.init(SVGTemplateNameConstant.TEMPLATE_GATEWAY_PARALLEL);
		this.init(SVGTemplateNameConstant.TEMPLATE_GATEWAY_EXCLUSIVE);
		this.init(SVGTemplateNameConstant.TEMPLATE_GATEWAY_INCLUSIVE);
		// 加载泳道
		this.init(SVGTemplateNameConstant.TEMPLATE_LANESET);
		// 初始化加载空白UserTask
		this.init(SVGTemplateNameConstant.TEMPLATE_CONNECTOR_SEQUENCEFLOW);
		// 加载小部件
		this.init(SVGTemplateNameConstant.TEMPLATE_GROUP);
		this.init(SVGTemplateNameConstant.TEMPLATE_TEXTANNOTATION);
		this.init(SVGTemplateNameConstant.TEMPLATE_CONNECTOR_ASSOCIATION);

		// 加载子流程
		this.init(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS);
		this.init(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_CALLACTIVITY);
		this.init(SVGTemplateNameConstant.TEMPLATE_ACTIVITY_SUBPROCESS_COLLAPSED);

		// 初始化加载模版容器
		this.init(SVGTemplateNameConstant.SVG_TEMPLATE);
	}

	/**
	 * 第一次需要从svg文档加载
	 * 
	 * @param templateName
	 */
	private void init(String templateName) {
		try {
			JAXBContext context = JAXBContext.newInstance(SvgVO.class);
			Unmarshaller unMarshaller = context.createUnmarshaller();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 解析的时候忽略SVG命名空间，否则会出错
			factory.setNamespaceAware(true);
			XMLReader reader = factory.newSAXParser().getXMLReader();
			String sourcePath = new StringBuffer(BPMN_PATH).append(FILE_SPERATOR)
					.append(templateName).toString();
			Source source = new SAXSource(reader, new InputSource(
					ReflectUtil.getResourceAsStream(sourcePath)));
			VONode object = (VONode) unMarshaller.unmarshal(source);
			this.svgTemplets.put(templateName, object);
		} catch (Exception e) {
			throw new FoxBPMException("template svg file load exception", e);
		} finally {
		}

	}
}
