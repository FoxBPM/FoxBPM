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
package org.foxbpm.bpmn.converter.parser;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.SkipStrategy;

/**
 * 跳过策略转换处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月20日
 */
public class SkipStrategyParser {
	public static SkipStrategy parser(Element element) {
		if (element == null || !BpmnXMLConstants.ELEMENT_SKIPSTRATEGY.equalsIgnoreCase(element.getName())) {
			return null;
		}
		
		SkipStrategy skipStrategy = new SkipStrategy();
		skipStrategy.setEnable(BpmnXMLUtil.parseBoolean(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISENABLE)));
		// 如果该属性不存在那么就是true
		if (StringUtils.isEmpty(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISCREATESKIPPROCESS))) {
			skipStrategy.setCreateSkipTaskRecord(true);
		} else {
			skipStrategy.setCreateSkipTaskRecord(BpmnXMLUtil.parseBoolean(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISCREATESKIPPROCESS)));
		}
		parserElement(element, skipStrategy);
		return skipStrategy;
	}
	
	@SuppressWarnings("unchecked")
	private static void parserElement(Element element, SkipStrategy skipStrategy) {
		String parentName = element.getName();
		String nodeName = null;
		String expression = null;
		Element elem = null;
		// 处理子节点
		for (Iterator<Element> iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_SKIPCOMMENT.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_SKIPASSIGNEE.equalsIgnoreCase(nodeName)) {
				parserElement(elem, skipStrategy);
				continue;
			}
			
			if (BpmnXMLConstants.ELEMENT_EXPRESSION.equalsIgnoreCase(nodeName)) {
				expression = BpmnXMLUtil.parseExpression(elem);
			}
			// 设置跳过策略属性
			if (BpmnXMLConstants.ELEMENT_SKIPSTRATEGY.equalsIgnoreCase(parentName)) {
				skipStrategy.setSkipExpression(expression);
			} else if (BpmnXMLConstants.ELEMENT_SKIPCOMMENT.equalsIgnoreCase(parentName)) {
				skipStrategy.setSkipComment(expression);
			} else if (BpmnXMLConstants.ELEMENT_SKIPASSIGNEE.equalsIgnoreCase(parentName)) {
				skipStrategy.setSkipAssignee(expression);
			}
		}
	}
}
