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

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月16日
 */

public class TestCase {
	
	@Test
	public void testA() {
		
		Document doc = DocumentHelper.createDocument();
		// BPMNDI_PREFIX, ELEMENT_DI_DIAGRAM, BPMNDI_NAMESPACE
		// Element root = factory.createElement(new QName(null, null, null));
		// Namespace namespace =
		// DocumentFactory.getInstance().createNamespace("bpmn2",
		// "http://www.omg.org/spec/BPMN/20100524/MODEL");
		Element element = DocumentFactory.getInstance().createElement("bpmn2:definitions", "http://www.foxbpm.org");
		// element = doc.addElement("definitions", "http://www.foxbpm.org");
		element.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		element.addNamespace("bpmn2", "http://www.omg.org/spec/BPMN/20100524/MODEL");
		element.addNamespace("dc", "http://www.omg.org/spec/DD/20100524/DC");
		element.addNamespace("di", "http://www.omg.org/spec/DD/20100524/DI");
		element.addNamespace("foxbpm", "http://www.foxbpm.org/foxbpm");
		element.addNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		element.addAttribute("xmlns", "http://www.foxbpm.org");
		element.addAttribute("id", "Definitions_1");
		element.addAttribute("targetNamespace", "http://www.foxbpm.org");
		// Element element = doc.addElement("bpmn2:definitions");
		// element.addAttribute("xmlns:xsi",
		// "http://www.w3.org/2001/XMLSchema-instance");
		 doc.add(element);
		try {
			// 定义输出流的目的地
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			// 定义用于输出xml文件的XMLWriter对象
			XMLWriter xmlWriter = new XMLWriter(System.out, format);
			xmlWriter.write(doc);
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
