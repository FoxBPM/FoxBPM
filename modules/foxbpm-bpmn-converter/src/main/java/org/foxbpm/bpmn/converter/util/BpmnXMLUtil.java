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
package org.foxbpm.bpmn.converter.util;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.FlowElement;

/**
 * 
 * BpmnXML工具类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class BpmnXMLUtil implements BpmnXMLConstants {
	
	/**
	 * 处理针对element节点限定名称(xx:aa)的本地部分aa
	 * 
	 * @param nodeName
	 *            element节点名称
	 * @return 名称
	 */
	public static String getEleLoclaName(String nodeName) {
		int index = nodeName.indexOf(':');
		if (index > 0) {
			return nodeName.substring(index + 1);
		}
		return nodeName;
	}
	
	public static void parserConnectorElement(FlowElement flowElment, Element element) {
	}
	public static void parserElementConnector(FlowElement flowElment, Element element) {
	}
}
