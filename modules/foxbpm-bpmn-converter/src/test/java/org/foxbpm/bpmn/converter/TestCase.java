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

import java.io.File;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.foxbpm.model.BpmnModel;
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
		
		BpmnXMLConverter converter = new BpmnXMLConverter();
		SAXReader reader = new SAXReader();
		try {
//			Document doc = reader.read(new File("E:\\workspace_6.0\\FoxBPM.0.1\\Test\\selector\\test\\a_1.bpmn"));
			Document doc = reader.read(new File("E:\\workspace_foxbpm\\BPMN\\GeneralNodeTest_1.bpmn"));
			BpmnModel bpmnModel = converter.convertToBpmnModel(doc);
			doc = converter.convertToXML(bpmnModel);
			try {
				// 定义输出流的目的地
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");
				XMLWriter xmlWriter = new XMLWriter(System.out, format);
				xmlWriter.setEscapeText(false);
				xmlWriter.write(doc);
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//@Test
	public void testB() {
		BpmnXMLConverter converter = new BpmnXMLConverter();
		Document doc = converter.convertToXML(null);
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
