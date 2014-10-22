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
 * @author yangguangftlp
 */
package org.foxbpm.bpmn.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.export.BPMNDIExport;
import org.foxbpm.bpmn.converter.export.ProcessExport;
import org.foxbpm.bpmn.converter.parser.BpmnDiagramParser;
import org.foxbpm.bpmn.converter.parser.ProcessParser;
import org.foxbpm.bpmn.exceptions.BpmnConverterException;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BpmnXML转换类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class BpmnXMLConverter {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(BpmnXMLConverter.class);
	
	protected static Map<String, BaseElementXMLConverter> convertersToBpmnMap = new HashMap<String, BaseElementXMLConverter>();
	protected static Map<Class<? extends BaseElement>, BaseElementXMLConverter> convertersToXMLMap = new HashMap<Class<? extends BaseElement>, BaseElementXMLConverter>();
	static {
		// events
		addConverter(new EndEventXMLConverter());
		addConverter(new StartEventXMLConverter());
		addConverter(new IntermediateCatchEventXMLConverter());
		addConverter(new BoundaryEventXMLConverter());
		// sequence
		addConverter(new SequenceFlowXMLConverter());
		// tasks
		addConverter(new UserTaskXMLConverter());
		addConverter(new ScriptTaskXMLConverter());
		// gateway
		addConverter(new ExclusiveGatewayXMLConverter());
		addConverter(new InclusiveGatewayXMLConverter());
		addConverter(new ParallelGatewayXMLConverter());
		// subProcess
		addConverter(new SubProcessXMLConverter());
		addConverter(new CallActivityXMLConverter());
		// laneset
	}
	
	protected BpmnDiagramParser bpmnDiagramParser;
	protected ProcessParser processParser;
	
	public static void addConverter(BaseElementXMLConverter converter) {
		convertersToBpmnMap.put(converter.getXMLElementName(), converter);
		convertersToXMLMap.put(converter.getBpmnElementType(), converter);
	}
	public BpmnXMLConverter() {
		bpmnDiagramParser = new BpmnDiagramParser();
		processParser = new ProcessParser();
	}
	
	@SuppressWarnings("rawtypes")
	public BpmnModel convertToBpmnModel(Document doc) {
		BpmnModel model = new BpmnModel();
		Element definitions = doc.getRootElement();
		String name = definitions.getName();
		// definitions
		Element elem = null;
		if (BpmnXMLConstants.ELEMENT_DEFINITIONS.equals(name)) {
			try {
				for (Iterator iterator = definitions.elements().iterator(); iterator.hasNext();) {
					elem = (Element) iterator.next();
					name = elem.getName();
					if (BpmnXMLConstants.ELEMENT_DI_DIAGRAM.equalsIgnoreCase(name)) {
						new BpmnDiagramParser().parse(elem, model);
					} else if (BpmnXMLConstants.ELEMENT_PROCESS.equalsIgnoreCase(name)) {
						model.getProcesses().add(processParser.parser(elem));
					}
				}
			} catch (Exception e) {
				LOGGER.error("Bpmn文件转换BpmnModel错误!", e);
				e.printStackTrace();
				throw new BpmnConverterException(e);
			}
		}
		return model;
	}
	/**
	 * 将bpmnModel转换成xml
	 * 
	 * @param model
	 *            bpmn模型
	 */
	public Document convertToXML(BpmnModel model) {
		DocumentFactory factory = DocumentFactory.getInstance();
		Document doc = factory.createDocument();
		Element element = DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_DEFINITIONS, BpmnXMLConstants.BPMN2_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.XSI_PREFIX, BpmnXMLConstants.XSI_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.DC_PREFIX, BpmnXMLConstants.DC_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.DI_PREFIX, BpmnXMLConstants.DI_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.BPMNDI_PREFIX, BpmnXMLConstants.BPMNDI_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.FOXBPM_PREFIX, BpmnXMLConstants.FOXBPM_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.XSD_PREFIX, BpmnXMLConstants.XSD_NAMESPACE);
		element.addNamespace(BpmnXMLConstants.EMPTY_STRING, BpmnXMLConstants.XMLNS_NAMESPACE);
		// 添加属性
		element.addAttribute(BpmnXMLConstants.TARGET_NAMESPACE_ATTRIBUTE, BpmnXMLConstants.XMLNS_NAMESPACE);
		element.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, "Definitions_1");
		doc.add(element);
		// 流程转换
		try {
			for (Iterator<Process> iterator = model.getProcesses().iterator(); iterator.hasNext();) {
				ProcessExport.writeProcess(iterator.next(), element);
			}
			// 位置坐标转换
			BPMNDIExport.writeBPMNDI(model, element);
		} catch (Exception e) {
			LOGGER.error("BpmnModel转换Bpmn文件错误!", e);
			e.printStackTrace();
			throw new BpmnConverterException(e);
		}
		return doc;
	}
	public static BaseElementXMLConverter getConverter(String key) {
		return convertersToBpmnMap.get(key);
	}
	public static BaseElementXMLConverter getConverter(Class<? extends BaseElement> key) {
		return convertersToXMLMap.get(key);
	}
}
