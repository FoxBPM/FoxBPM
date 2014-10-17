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
import org.foxbpm.model.Activity;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.MultiInstanceLoopCharacteristics;


/**
 * 活动元素处理
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class ActivityXMLConverter extends FlowNodeXMLConverter {

	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		Activity activity =(Activity)baseElement;
		Iterator<Element> elementIterator = element.elements().iterator();
		Element subElement = null;
		Element extentionElement = null;
		while(elementIterator.hasNext()){
			subElement = elementIterator.next();
			if(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equals(subElement.getName())){
				Iterator<Element> extentionIterator = subElement.elements().iterator();
				while(extentionIterator.hasNext()){
					extentionElement = extentionIterator.next();
					 if(BpmnXMLConstants.ELEMENT_MULTIINSTANCELOOPCHARACTERISTICS.equals(extentionElement.getName())){
						MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
						multiInstanceLoopCharacteristics.setId(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
						
						Element multiExtentionElement = extentionElement.element(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS);
						if(multiExtentionElement != null){
							multiInstanceLoopCharacteristics.setLoopDataInputCollection(BpmnXMLUtil.parseExpression(multiExtentionElement.element(BpmnXMLConstants.ELEMENT_LOOPDATAINPUTCOLLECTION).element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
							multiInstanceLoopCharacteristics.setLoopDataOutputCollection(BpmnXMLUtil.parseExpression(multiExtentionElement.element(BpmnXMLConstants.ELEMENT_LOOPDATAOUTPUTCOLLECTION).element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
						}
						multiInstanceLoopCharacteristics.setCompletionCondition(multiExtentionElement.element(BpmnXMLConstants.ELEMENT_COMPLETIONCONDITION).getText());
						multiInstanceLoopCharacteristics.setInputDataItem(BpmnXMLUtil.parseExpression(multiExtentionElement.element(BpmnXMLConstants.ELEMENT_INPUTDATAITEM).element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
						
						multiInstanceLoopCharacteristics.setOutputDataItem(BpmnXMLUtil.parseExpression(multiExtentionElement.element(BpmnXMLConstants.ELEMENT_OUTPUTDATAITEM).element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
						if(extentionElement.attributeValue(BpmnXMLConstants.ELEMENT_ISSEQUENTIAL) != null){
							multiInstanceLoopCharacteristics.setSequential(Boolean.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ELEMENT_ISSEQUENTIAL)));
						}
						
						activity.setLoopCharacteristics(multiInstanceLoopCharacteristics);
					}
					

				}
			}
		}
		
		super.convertXMLToModel(element, baseElement);
	}

	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		// TODO Auto-generated method stub
		super.convertModelToXML(element, baseElement);
	}

}
