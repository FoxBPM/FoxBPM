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
import java.util.List;

import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.parser.MultiInstanceParser;
import org.foxbpm.bpmn.converter.parser.SkipStrategyParser;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.Activity;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.LoopCharacteristics;
import org.foxbpm.model.MultiInstanceLoopCharacteristics;
import org.foxbpm.model.SkipStrategy;

/**
 * 活动元素处理
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class ActivityXMLConverter extends FlowNodeXMLConverter {
	
	public final static String ELEMENT_NAME_BPMN2_EXTENSIONELEMENT = BpmnXMLConstants.BPMN2_PREFIX + ':'
	        + BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS;
	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		Activity activity = (Activity) baseElement;
		Element childElem = null;
		String nodeName = null;
		// 处理跳过策略
		List<Element> elements = element.elements();
		for(Element elem : elements){
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equalsIgnoreCase(nodeName)) {
				for (Iterator<Element> childIterator = elem.elements().iterator(); childIterator.hasNext();) {
					childElem = childIterator.next();
					String subNodeName = childElem.getName();
					if (BpmnXMLConstants.ELEMENT_SKIPSTRATEGY.equalsIgnoreCase(subNodeName)) {
						activity.setSkipStrategy(SkipStrategyParser.parser(childElem));
					} 
				}
			}
			
			if (BpmnXMLConstants.ELEMENT_MULTIINSTANCELOOPCHARACTERISTICS.equalsIgnoreCase(nodeName)) {
				activity.setLoopCharacteristics(MultiInstanceParser.parser(elem));
			}
		}
		
		
		
		super.convertXMLToModel(element, baseElement);
	}
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		Activity activity = (Activity) baseElement;
		SkipStrategy skipStrategy = activity.getSkipStrategy(); 
		
		Element extensionElement = element.element(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS);
		if(extensionElement == null){
			extensionElement = element.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT);
		}
		if(skipStrategy!=null){
			//跳过策略
			Element skipStrategyElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_SKIPSTRATEGY,BpmnXMLConstants.FOXBPM_NAMESPACE);
			skipStrategyElement.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ATTRIBUTE_ISENABLE, String.valueOf(skipStrategy.isEnable()));
			 
			String skipExpression = skipStrategy.getSkipExpression();
			if(skipExpression!= null){
				
				BpmnXMLUtil.createExpressionElementByParent(skipStrategyElement, skipExpression);
			}
			
			String skipAssignee = skipStrategy.getSkipAssignee();
			if(skipAssignee != null){
				Element skipAssigneElement = skipStrategyElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_SKIPASSIGNEE);
				skipAssigneElement.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':'
				        + BpmnXMLConstants.ATTRIBUTE_TYPE, "foxbpm:SkipAssignee");
				BpmnXMLUtil.createExpressionElementByParent(skipAssigneElement, skipAssignee);
			
			}
			 	
			String skipComment = skipStrategy.getSkipComment();
			if(skipComment != null){
				Element skipCommentElement = skipStrategyElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_SKIPCOMMENT);
				skipCommentElement.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':'
				        + BpmnXMLConstants.ATTRIBUTE_TYPE, "foxbpm:SkipComment");
				BpmnXMLUtil.createExpressionElementByParent(skipCommentElement, skipComment);
			}
		} 
		//多实例转换
		LoopCharacteristics loopCharacteristics = activity.getLoopCharacteristics();
		if(loopCharacteristics != null){
			MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =(MultiInstanceLoopCharacteristics)loopCharacteristics;
			
			Element multiInstanceElement = element.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_MULTIINSTANCELOOPCHARACTERISTICS);
			multiInstanceElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, multiInstanceLoopCharacteristics.getId());
			Element multiInstanceExtensionElement = null;
			
			//输入数据集合
			String loopDataInputCollection = multiInstanceLoopCharacteristics.getLoopDataInputCollection();
			if(loopDataInputCollection != null
					&& !loopDataInputCollection.trim().equals("")){
				multiInstanceExtensionElement = multiInstanceElement.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT);
				Element loopDataInputElement = multiInstanceExtensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_LOOPDATAINPUTCOLLECTION,BpmnXMLConstants.FOXBPM_NAMESPACE);
				BpmnXMLUtil.createExpressionElementByParent(loopDataInputElement, loopDataInputCollection);
			}
			
			//输出数据集合
			String loopDataOutputCollection = multiInstanceLoopCharacteristics.getLoopDataOutputCollection();
			if(loopDataOutputCollection != null
					&& !loopDataOutputCollection.trim().equals("")){
				if(multiInstanceExtensionElement==null){
					multiInstanceExtensionElement = multiInstanceElement.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT);
				}
				Element loopDataOutputElement = multiInstanceExtensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_LOOPDATAOUTPUTCOLLECTION,BpmnXMLConstants.FOXBPM_NAMESPACE);
					BpmnXMLUtil.createExpressionElementByParent(loopDataOutputElement, loopDataOutputCollection);
			}
			
			//输入项
			String inputDataItem = multiInstanceLoopCharacteristics.getInputDataItem();
			if(inputDataItem != null 
					&& !inputDataItem.trim().equals("")){
				Element dataInputItemElement = multiInstanceElement.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_INPUTDATAITEM);
				dataInputItemElement.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':'
				        + BpmnXMLConstants.ATTRIBUTE_TYPE, "bpmn2:tDataInput");
				Element dataInputExtensionElement = dataInputItemElement.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT);
				BpmnXMLUtil.createExpressionElementByParent(dataInputExtensionElement, inputDataItem);
				
			}
			
			//输出项
			String outputDataItem = multiInstanceLoopCharacteristics.getOutputDataItem();
			if(outputDataItem != null 
					&& !inputDataItem.trim().equals("")){
				Element dataOutputItemElement = multiInstanceElement.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_OUTPUTDATAITEM);
				dataOutputItemElement.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':'
				        + BpmnXMLConstants.ATTRIBUTE_TYPE, "bpmn2:tDataOutput");
				Element dataOutputExtensionElement = dataOutputItemElement.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT);
				BpmnXMLUtil.createExpressionElementByParent(dataOutputExtensionElement, outputDataItem);
				
			}
			
			//完成条件
			String completeCondition = multiInstanceLoopCharacteristics.getCompletionCondition();
			if(completeCondition != null 
					&& !completeCondition.trim().equals("")){
				Element completeConditionElement = multiInstanceElement.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_COMPLETIONCONDITION);
				completeConditionElement.add(new DOMCDATA(completeCondition));
			}
			
		} 
		super.convertModelToXML(element, baseElement);
	}
	
	
	
}
