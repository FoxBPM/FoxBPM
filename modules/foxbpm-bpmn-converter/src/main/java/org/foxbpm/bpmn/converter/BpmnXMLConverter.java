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
import org.dom4j.QName;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.parser.BpmnDiagramParser;
import org.foxbpm.bpmn.converter.parser.ProcessParser;
import org.foxbpm.model.BpmnModel;

/**
 * BpmnXML转换类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class BpmnXMLConverter implements BpmnXMLConstants {
	
	// protected static final Logger LOGGER =
	// LoggerFactory.getLogger(BpmnXMLConverter.class);
	
	protected static Map<String, BaseElementXMLConverter> convertersToBpmnMap = new HashMap<String, BaseElementXMLConverter>();
	static {
		// events
		addConverter(new EndEventXMLConverter());
		addConverter(new StartEventXMLConverter());
		// tasks
		addConverter(new UserTaskXMLConverter());
		addConverter(new CallActivityXMLConverter());
	}
	
	protected BpmnDiagramParser bpmnDiagramParser;
	protected ProcessParser processParser;
	
	public static void addConverter(BaseElementXMLConverter converter) {
		convertersToBpmnMap.put(converter.getXMLElementName(), converter);
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
		if (ELEMENT_DEFINITIONS.equals(name)) {
			try {
				
				for (Iterator iterator = definitions.elements().iterator(); iterator.hasNext();) {
					elem = (Element) iterator.next();
					name = elem.getName();
					if (ELEMENT_DI_DIAGRAM.equalsIgnoreCase(name)) {
						new BpmnDiagramParser().parse(elem, model);
					} else if (ELEMENT_PROCESS.equalsIgnoreCase(name)) {
						new ProcessParser().parse(elem, model);
					}
				}
			} catch (Exception e) {
				
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
	public void convertToXML(BpmnModel model) {
		DocumentFactory factory = DocumentFactory.getInstance();
		//BPMNDI_PREFIX, ELEMENT_DI_DIAGRAM, BPMNDI_NAMESPACE
		Element root = factory.createElement(new QName(null, null, null));
		
		
		
	}
	public static BaseElementXMLConverter getConverter(String key) {
		return convertersToBpmnMap.get(key);
	}
}
