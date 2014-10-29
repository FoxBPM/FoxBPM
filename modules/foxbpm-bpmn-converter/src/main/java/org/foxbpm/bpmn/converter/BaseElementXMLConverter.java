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

import java.util.Iterator;

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.FlowContainer;

/**
 * 模型基类转换处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class BaseElementXMLConverter implements FlowElementFactory {
	/**
	 * 转换器对应模型元素类型
	 * 
	 * @return 返回模型类型
	 */
	public abstract Class<? extends BaseElement> getBpmnElementType();
	/**
	 * 转换器对应XML元素名称
	 * 
	 * @return
	 */
	public abstract String getXMLElementName();
	
	/**
	 * 将xml元素转换model
	 * 
	 * @param element
	 *            xml元素
	 * @param baseElement
	 *            模型实例
	 */
	@SuppressWarnings("rawtypes")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		baseElement.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
		Element elem = null;
		String nodeName = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_DOCUMENTATION.equalsIgnoreCase(nodeName)) {
				baseElement.setDocumentation(elem.getText());
			}
		}
		// 处理流程容器
		if (baseElement instanceof FlowContainer) {
			BpmnXMLUtil.parseFlowContainer(element, baseElement);
		}
	}
	/**
	 * 将模型转换成XML元素
	 * 
	 * @param element
	 *            元素节点
	 * @param baseElement
	 *            模型实例
	 */
	public void convertModelToXML(Element element, BaseElement baseElement) {
		
		if (null != baseElement.getId()) {
			element.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, baseElement.getId());
		}
		if (null != baseElement.getDocumentation()) {
			Element childElem = element.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
			childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_DOCUMENTATION));
			childElem.setText(baseElement.getDocumentation());
		}
		
		// 处理流程容器
		if (baseElement instanceof FlowContainer) {
			BpmnXMLUtil.createFlowElement(element, ((FlowContainer) baseElement).getFlowElements());
		}
	}
}
