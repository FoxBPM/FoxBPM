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
package org.foxbpm.bpmn.converter.parser;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;

/**
 * 表达式转换
 * @author MAENLIANG
 *
 */
public class ExpressionParser {
	public static void parseExpressionElement(Element element,String expression,String name){
		Element expressionElement = element.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_EXPRESSION);
		expressionElement.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':'
		        + BpmnXMLConstants.ATTRIBUTE_NAME, name);
		expressionElement.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':'
		        + BpmnXMLConstants.ATTRIBUTE_TYPE, "foxbpm:Expression");
		expressionElement.setText(expression);
	}
}
