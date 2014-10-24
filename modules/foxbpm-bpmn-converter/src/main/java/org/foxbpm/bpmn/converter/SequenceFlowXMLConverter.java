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

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.SequenceFlow;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class SequenceFlowXMLConverter extends FlowElementXMLConverter {
	
	public FlowElement cretateFlowElement() {
		return new SequenceFlow();
	}
	
	@Override
	public Class<? extends BaseElement> getBpmnElementType() {
		return SequenceFlow.class;
	}
	
	@Override
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		SequenceFlow sequenceFlow = (SequenceFlow) baseElement;
		sequenceFlow.setSourceRefId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_SOURCEREF));
		sequenceFlow.setTargetRefId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_TARGETREF));
		Element conditionElement = element.element("conditionExpression");
		if (conditionElement != null) {
			sequenceFlow.setFlowCondition(BpmnXMLUtil.parseExpression(conditionElement));
		}
		super.convertXMLToModel(element, baseElement);
	}
	
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		SequenceFlow sequenceFlow = (SequenceFlow) baseElement;
		element.addAttribute(BpmnXMLConstants.ATTRIBUTE_SOURCEREF, sequenceFlow.getSourceRefId());
		element.addAttribute(BpmnXMLConstants.ATTRIBUTE_TARGETREF, sequenceFlow.getTargetRefId());
		if (null != sequenceFlow.getFlowCondition()) {
			String condition = sequenceFlow.getFlowCondition();
			Element childElem = element.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_CONDITIONEXPRESSION);
			childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.BPMN2_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_FORMALEXPRESSION);
			childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.TYPE_FORMALEXPRESSION));
			childElem.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':' + BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(condition));
			childElem.add(new DOMCDATA(condition));
		}
		super.convertModelToXML(element, baseElement);
	}
	
	@Override
	public String getXMLElementName() {
		return BpmnXMLConstants.ELEMENT_SEQUENCEFLOW;
	}
	
	public Element cretateXMLElement() {
		return DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_SEQUENCEFLOW, BpmnXMLConstants.BPMN2_NAMESPACE);
	}
	
}
