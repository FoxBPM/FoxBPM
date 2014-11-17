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
package org.foxbpm.bpmn.converter.export;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.LaneSetXmlConverter;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.DataVariableDefinition;
import org.foxbpm.model.LaneSet;
import org.foxbpm.model.PotentialStarter;
import org.foxbpm.model.Process;

public class ProcessExport {
	
	public static void writeProcess(Process process, Element parentElement) {
		if (null != process) {
			// 创建流程元素
			Element processEle = parentElement.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_PROCESS);
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, process.getId());
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, process.getName());
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_CATEGORY, process.getCategory());
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_KEY, process.getKey());
			if (!process.isPersistence()) {
				processEle.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':' + BpmnXMLConstants.ATTRIBUTE_ISPERSISTENCE, String.valueOf(false));
			}
			
			// 处理扩展
			createExtensionElement(processEle, process);
			
			createLaneSetElement(processEle, process);
			// 处理流程节点
			BpmnXMLUtil.createFlowElement(processEle, process.getFlowElements());
			// 处理流程线条
			// createSequenceFlowElement(processEle,
			// process.getSequenceFlows());
			// 描述
			Element childElem = null;
			if (null != process.getDocumentation()) {
				childElem = processEle.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_DOCUMENTATION));
				childElem.setText(process.getDocumentation());
			}
		}
	}
	
	private static void createLaneSetElement(Element processEle, Process process) {
		List<LaneSet> laneSets = process.getLaneSets();
		if (laneSets != null) {
			for (LaneSet lanset : laneSets) {
				LaneSetXmlConverter laneSetXmlConverter = new LaneSetXmlConverter();
				Element elementLaneSet = laneSetXmlConverter.cretateXMLElement();
				laneSetXmlConverter.convertModelToXML(elementLaneSet, lanset);
				processEle.add(elementLaneSet);
			}
		}
	}
	
	private static void createExtensionElement(Element processEle, Process process) {
		// 基本属性设置
		Element extensionElements = processEle.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS);
		
		Element childElem = null;
		Element expression = null;
		// 表单url
		if (null != process.getFormUri()) {
			childElem = extensionElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_FORMURI);
			expression = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(process.getFormUri()));
			expression.add(new DOMCDATA(process.getFormUri()));
		}
		// taskSubject
		if (null != process.getSubject()) {
			childElem = extensionElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_TASKSUBJECT);
			childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_TASKSUBJECT));
			expression = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(process.getSubject()));
			expression.add(new DOMCDATA(process.getSubject()));
		}
		// formUriView
		if (null != process.getFormUriView()) {
			childElem = extensionElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_FORMURIVIEW);
			expression = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(process.getFormUriView()));
			expression.add(new DOMCDATA(process.getFormUriView()));
		}
		
		// 数据变量
		createDataVariableElement(extensionElements, process.getDataVariables());
		// 启动人
		createStarterElement(extensionElements, process.getPotentialStarters());
		// 连接器
		BpmnXMLUtil.createConectorElement(extensionElements, BpmnXMLConstants.TYPE_FLOWCONNECTOR, process.getConnector());
	}
	
	private static void createStarterElement(Element parentElement, List<PotentialStarter> potentialStarters) {
		if (null != potentialStarters) {
			PotentialStarter potentialStarter = null;
			Element potentialStarterEle = null;
			Element childElem = null;
			String id = null;
			for (Iterator<PotentialStarter> iterator = potentialStarters.iterator(); iterator.hasNext();) {
				potentialStarter = iterator.next();
				potentialStarterEle = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_POTENTIAL_STARTER);
				potentialStarterEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_RESOURCETYPE, potentialStarter.getResourceType());
				potentialStarterEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_DESCRIPTION, potentialStarter.getDocumentation());
				
				childElem = potentialStarterEle.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_EXPRESSION);
				childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
				        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
				id = UniqueIDUtil.getInstance().generateElementID(null);
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, BpmnXMLConstants.ELEMENT_EXPRESSION + '_' + id);
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, id);
				childElem.add(new DOMCDATA(potentialStarter.getExpression()));
			}
		}
		
	}
	
	private static void createDataVariableElement(Element parentElement, List<DataVariableDefinition> dataVariables) {
		
		if (null != dataVariables) {
			DataVariableDefinition dataVariableDefinition = null;
			Element dataVariableEle = null;
			Element childElem = null;
			for (Iterator<DataVariableDefinition> iterator = dataVariables.iterator(); iterator.hasNext();) {
				dataVariableDefinition = iterator.next();
				dataVariableEle = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_DATAVARIABLE);
				dataVariableEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, dataVariableDefinition.getId());
				dataVariableEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_DATATYPE, dataVariableDefinition.getDataType());
				dataVariableEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_BIZTYPE, dataVariableDefinition.getBizType());
				dataVariableEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_FIELDNAME, dataVariableDefinition.getFieldName());
				if (dataVariableDefinition.isPersistence()) {
					dataVariableEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_ISPERSISTENCE, String.valueOf(dataVariableDefinition.isPersistence()));
				}
				// 描述
				if (null != dataVariableDefinition.getDocumentation()) {
					childElem = dataVariableEle.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.TYPE_DOCUMENTATION);
					childElem.setText(dataVariableDefinition.getDocumentation());
				}
				if (null != dataVariableDefinition.getExpression()) {
					// 表达式
					childElem = dataVariableEle.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_EXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
					childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(dataVariableDefinition.getExpression()));
					childElem.add(new DOMCDATA(dataVariableDefinition.getExpression()));
				}
			}
		}
	}
}
