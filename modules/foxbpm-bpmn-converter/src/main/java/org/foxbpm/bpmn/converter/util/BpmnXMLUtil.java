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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMCDATA;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.BaseElementXMLConverter;
import org.foxbpm.bpmn.converter.BpmnXMLConverter;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.Connector;
import org.foxbpm.model.FlowContainer;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.InputParam;
import org.foxbpm.model.OutputParam;
import org.foxbpm.model.OutputParamDef;
import org.foxbpm.model.SequenceFlow;
import org.foxbpm.model.TimerEventDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * BpmnXML工具类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class BpmnXMLUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(BpmnXMLUtil.class);
	
	/**
	 * 将字符串转换成Boolean值
	 * 
	 * @param strBoolean
	 *            字符串
	 * @return 返回Boolean值
	 */
	public static boolean parseBoolean(String strBoolean) {
		return Boolean.parseBoolean(strBoolean);
	}
	
	/**
	 * 处理针对element节点限定名称(xx:aa)的本地部分aa
	 * 
	 * @param nodeName
	 *            element节点名称
	 * @return 名称
	 */
	public static String getEleLoclaName(String nodeName) {
		if(nodeName != null){
			int index = nodeName.indexOf(':');
			if (index > 0) {
				return nodeName.substring(index + 1);
			}
		}
		return nodeName;
	}
	/**
	 * 表达式解析
	 * 
	 * @param element
	 *            表达式节点
	 * @return 返回表达式
	 */
	@SuppressWarnings("rawtypes")
	public static String parseExpression(Element element) {
		Node node = null;
		if (element == null) {
			return null;
		}
		for (Iterator iterator = element.nodeIterator(); iterator.hasNext();) {
			node = (Node) iterator.next();
			if (Element.CDATA_SECTION_NODE == node.getNodeType()) {
				return node.getText();
			}
		}
		return null;
	}
 
	/**
	 * 表达式解析
	 * 
	 * @param element
	 *            sequenceFlow
	 * @return SequenceFlow 对象
	 */
	@SuppressWarnings("rawtypes")
	public static SequenceFlow parseSequenceFlow(Element element) {
		if(element != null){ 
			SequenceFlow sequenceFlow = new SequenceFlow();
			sequenceFlow.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
			sequenceFlow.setSourceRefId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_SOURCEREF));
			sequenceFlow.setTargetRefId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_TARGETREF));
			Element elem = null;
			String nodeName = null;
			for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
				elem = (Element) iterator.next();
				nodeName = elem.getName();
				if (BpmnXMLConstants.ELEMENT_DOCUMENTATION.equalsIgnoreCase(nodeName)) {
					sequenceFlow.setDocumentation(elem.getText());
				} else if (BpmnXMLConstants.ELEMENT_CONDITIONEXPRESSION.equalsIgnoreCase(nodeName)) {
					sequenceFlow.setFlowCondition(elem.getText());
				}
			}
			return sequenceFlow;
		}
		return null;
		
	}
	/**
	 * 连接器解析
	 * 
	 * @param element
	 *            连接器节点connectorInstanceElements
	 * @return 返回连接器实例对象
	 */
	@SuppressWarnings("rawtypes")
	public static List<Connector> parserConnectorElement(Element element) {
		Element elem = null;
		List<Connector> connectorList = new ArrayList<Connector>();
		Connector connector = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			connector = new Connector();
			parserElementConnector(connector, elem);
			connectorList.add(connector);
		}
		return connectorList;
	}
	/**
	 * 设置连接器属性
	 * 
	 * @param connector
	 *            连接器对象
	 * @param element
	 *            连接器节点或子节点
	 * 
	 * @return 返回连接器实例对象
	 */
	@SuppressWarnings("rawtypes")
	public static void parserElementConnector(Connector connector, Element element) {
		Element elem = null;
		String parentNodeName = element.getName();
		String nodeName = null;
		String expression = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_CONNECTORINSTANCE.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_INPUTS.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_TIMEEXPRESSION.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION.equalsIgnoreCase(nodeName)
			        || BpmnXMLConstants.ELEMENT_SKIPCOMMENT.equals(nodeName)) {
				LOGGER.debug("处理" + nodeName + "子节点");
				parserElementConnector(connector, elem);
				// 继续下一个
				continue;
			}
			if (BpmnXMLConstants.ELEMENT_EXPRESSION.equalsIgnoreCase(nodeName)) {
				// 表达式解析
				expression = parseExpression(elem);
			} else if (BpmnXMLConstants.ELEMENT_DOCUMENTATION.equalsIgnoreCase(nodeName)) {
				connector.setDocumentation(elem.getText());
			}
			/** 连接输入参数 */
			if (BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_INPUTS.equalsIgnoreCase(parentNodeName)) {
				
				if (null == connector.getInputsParam()) {
					connector.setInputsParam(new ArrayList<InputParam>());
				}
				InputParam inputParam = new InputParam();
				inputParam.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
				inputParam.setName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
				inputParam.setDataType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_DATATYPE));
				inputParam.setExecute(Boolean.valueOf(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISEXECUTE)));
				inputParam.setExpression(expression);
				connector.getInputsParam().add(inputParam);
			} else if (BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_OUTPUTS.equalsIgnoreCase(nodeName)) {
				if (null == connector.getOutputsParam()) {
					connector.setOutputsParam(new ArrayList<OutputParam>());
				}
				OutputParam outputParam = new OutputParam();
				// outputParam.setId(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
				outputParam.setVariableTarget(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_VARIABLETARGET));
				outputParam.setOutput(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_OUTPUT));
				
				connector.getOutputsParam().add(outputParam);
				
			} else /** 变量定义 */
			if (BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_OUTPUTSDEF.equalsIgnoreCase(nodeName)) {
				
				if (null == connector.getOutputsParamDef()) {
					connector.setOutputsParamDef(new ArrayList<OutputParamDef>());
				}
				OutputParamDef outputParamDef = new OutputParamDef();
				outputParamDef.setName(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
				outputParamDef.setDataType(elem.attributeValue(BpmnXMLConstants.ATTRIBUTE_DATATYPE));
				connector.getOutputsParamDef().add(outputParamDef);
			} else
			/** 时间表达式 */
			if (BpmnXMLConstants.ELEMENT_TIMEEXPRESSION.equalsIgnoreCase(parentNodeName)) {
				TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
				timerEventDefinition.setTimeDate(expression);
				connector.setTimerEventDefinition(timerEventDefinition);
				
			} else /** 跳过时间策略 */
			if (BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION.equalsIgnoreCase(parentNodeName)) {
				connector.setSkipExpression(expression);
			} else /** 策略描述 */
			if (BpmnXMLConstants.ELEMENT_SKIPCOMMENT.equalsIgnoreCase(parentNodeName)) {
				connector.setSkipComment(expression);
			}
		}
		
		/** 处理connectorInstance节点基本属性 */
		if (BpmnXMLConstants.ELEMENT_CONNECTORINSTANCE.equalsIgnoreCase(parentNodeName)) {
			connector.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CONNECTORID));
			connector.setPackageName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_PACKAGENAME));
			connector.setClassName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CLASSNAME));
			connector.setConnectorInstanceId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_ID));
			connector.setConnectorInstanceName(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_NAME));
			connector.setEventType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_EVENTTYPE));
			connector.setErrorCode(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ERRORCODE));
			connector.setErrorHandling(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ERRORHANDLING));
			connector.setIsTimeExecute(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ISTIMEEXECUTE));
			connector.setType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_TYPE));
		}
	}
	/**
	 * 处理流程、子流程 线条和node节点
	 * 
	 * @param element
	 * @param baseElement
	 */
	@SuppressWarnings("rawtypes")
	public static void parseFlowContainer(Element element, BaseElement baseElement) {
		Element elem;
		if (baseElement instanceof FlowContainer) {
			FlowContainer flowContainer = (FlowContainer) baseElement;
			String name = null;
			for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
				elem = (Element) iterator.next();
				name = elem.getName();
				if (null != BpmnXMLConverter.getConverter(name)) {
					BaseElementXMLConverter converter = BpmnXMLConverter.getConverter(name);
					FlowElement flowElement = converter.cretateFlowElement();
					if(flowElement instanceof FlowContainer){
						((FlowContainer)flowElement).setParentContainer(flowContainer);
					}
					converter.convertXMLToModel(elem, flowElement);
					if (BpmnXMLConstants.ELEMENT_SEQUENCEFLOW.equalsIgnoreCase(name)) {
						// 线条处理
						flowContainer.addSequenceFlow((SequenceFlow) flowElement);
					}
					flowContainer.addFlowElement(flowElement);
				}
			}
		}
	}
	
	/**
	 * 处理流程容器节点
	 * 
	 * @param parentElement
	 *            父节点
	 * @param flowElements
	 *            流程节点
	 */
	public static void createFlowElement(Element parentElement, List<FlowElement> flowElements) {
		if (null != flowElements) {
			FlowElement flowElement = null;
			BaseElementXMLConverter converter = null;
			Element childElem = null;
			for (Iterator<FlowElement> iterator = flowElements.iterator(); iterator.hasNext();) {
				flowElement = iterator.next();
				converter = BpmnXMLConverter.getConverter(flowElement.getClass());
				if (null != converter) {
					childElem = converter.cretateXMLElement();
					if (null != childElem) {
						converter.convertModelToXML(childElem, flowElement);
						parentElement.add(childElem);
					}
				}
			}
		}
	}
	
	/*******************************************************************************************/
	public static void createConectorElement(Element parentElement, String connrctorType, List<Connector> connectors) {
		if (null != connectors) {
			Connector connector = null;
			Element connectorInstanceElements = null;
			Element connectorInstanceElem = null;
			Element childElem = null;
			Element expressionElem = null;
			connectorInstanceElements = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS, BpmnXMLConstants.FOXBPM_NAMESPACE);
			connectorInstanceElements.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNRCTORTYPE, connrctorType);
			
			for (Iterator<Connector> iterator = connectors.iterator(); iterator.hasNext();) {
				connector = iterator.next();
				// 处理基本属性
				connectorInstanceElem = connectorInstanceElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_CONNECTORINSTANCE);
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_PACKAGENAME, connector.getPackageName());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ISTIMEEXECUTE, connector.getIsTimeExecute());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNECTORID, connector.getId());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CLASSNAME, connector.getClassName());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_ID, connector.getConnectorInstanceId());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_NAME, connector.getConnectorInstanceName());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_EVENTTYPE, connector.getEventType());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ERRORHANDLING, connector.getErrorHandling());
				connectorInstanceElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ERRORCODE, connector.getErrorCode());
				// 结束
				BpmnXMLUtil.addElemAttribute(connectorInstanceElem, BpmnXMLConstants.ATTRIBUTE_TYPE, connector.getType());
				// 处理输入参数
				createInputsParam(connectorInstanceElem, connector.getInputsParam());
				// 处理输出参数
				createOutputsParam(connectorInstanceElem, connector.getOutputsParam());
				// 处理输出参数
				createOutputsParamDef(connectorInstanceElem, connector.getOutputsParamDef());
				// 处理其他
				// 处理foxbpm:skipComment
				if (null != connector.getSkipComment()) {
					childElem = connectorInstanceElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_SKIPCOMMENT);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.TYPE_SKIPCOMMENT);
					expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_EXPRESSION);
					createExpressionElement(expressionElem, connector.getSkipComment());
				}
				// 处理foxbpm:timeExpression
				if (null != connector.getTimerEventDefinition()) {
					childElem = connectorInstanceElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_TIMEEXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_TIMEEXPRESSION);
					if (null != connector.getTimerEventDefinition().getTimeDate()) {
						expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
						        + BpmnXMLConstants.ELEMENT_EXPRESSION);
						createExpressionElement(expressionElem, connector.getTimerEventDefinition().getTimeDate());
					}
					
				}
				// foxbpm:timeSkipExpression
				if (null != connector.getSkipExpression()) {
					childElem = connectorInstanceElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION);
					expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_EXPRESSION);
					createExpressionElement(expressionElem, connector.getSkipExpression());
				}
				// 描述
				if (null != connector.getDocumentation()) {
					childElem = connectorInstanceElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.TYPE_DOCUMENTATION);
					childElem.setText(connector.getDocumentation());
				}
			}
		}
	}
	/**
	 * foxbpm:expression 给xml元素添加属性
	 * 
	 * @param element
	 *            foxbpm:expression
	 * @param obj
	 */
	public static void createExpressionElement(Element element, Object obj) {
		String expression = obj.toString();
		element.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
		        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
		element.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.TYPE_EXPRESSION));
		element.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(expression));
		element.add(new DOMCDATA(expression));
	}
	
	/**
	 * 创建foxbpm:expression 节点
	 * 
	 * @param element
	 * @param obj
	 */
	public static void createExpressionElementByParent(Element element, Object obj) {
		Element expressionElement = element.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_EXPRESSION, BpmnXMLConstants.FOXBPM_NAMESPACE);
		String expression = obj.toString();
		expressionElement.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
		        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
		expressionElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.TYPE_EXPRESSION));
		expressionElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(expression));
		expressionElement.add(new DOMCDATA(expression));
	}
	
	/**
	 * 给节点添加属性
	 * 
	 * @param element
	 *            元素节点
	 * @param name
	 *            名称
	 * @param value
	 *            值
	 */
	public static void addElemAttribute(Element element, String name, String value) {
		if (null != value) {
			element.addAttribute(name, value);
		}
	}
	/**
	 * 字符串截取
	 * 
	 * @param str
	 *            截取长度 字符串
	 * @return 返回截取字符串
	 */
	public static String interceptStr(String str) {
		return interceptStr(str, 10);
	}
	/**
	 * 字符串截取 0~length
	 * 
	 * @param str
	 * @param length
	 *            > 0 截取长度 字符串
	 * @return 返回截取字符串
	 */
	public static String interceptStr(String str, int length) {
		if (null != str && (length > 0 && length < str.length())) {
			return str.substring(0, length).replace("\"", BpmnXMLConstants.EMPTY_STRING);
		}
		return str;
	}
	
	/**
	 * 去掉特殊字符针对xml中的
	 * 
	 * @param text
	 *            字符串
	 * @return 返回处理后的字符串
	 */
	public static String removeSpecialStr(String text) {
		return removeSpecialStr(text, BpmnXMLConstants.XML_QUOT);
	}
	/**
	 * 去掉特殊字符针对xml中的
	 * 
	 * @param text
	 *            字符串
	 * @param specialStr
	 *            特殊字符串
	 * @return 返回处理后的字符串
	 */
	public static String removeSpecialStr(String text, String... specialStr) {
		StringBuffer sbuffer = new StringBuffer(text);
		if (null != specialStr) {
			int length = specialStr.length;
			for (int i = 0; i < length; i++) {
				sbuffer.replace(0, sbuffer.length(), (sbuffer.toString().replace(BpmnXMLConstants.XML_QUOT, BpmnXMLConstants.EMPTY_STRING)));
			}
		}
		return sbuffer.toString();
	}
	/**
	 * 添加特殊字符串前后
	 * 
	 * @param text
	 *            字符串
	 * @return 返回处理后的字符串
	 */
	public static String addSpecialStrBeforeAndAfter(String text) {
		return addSpecialStrBeforeAndAfter(text, BpmnXMLConstants.EMPTY_STRING);
	}
	/**
	 * 添加特殊字符串前后
	 * 
	 * @param text
	 *            字符串
	 * @param specialStr
	 *            特殊字符串
	 * 
	 * @return 返回处理后的字符串
	 */
	public static String addSpecialStrBeforeAndAfter(String text, String specialStr) {
		return new StringBuffer(specialStr).append(text).append(specialStr).toString();
	}
	
	private static void createInputsParam(Element parentElement, List<InputParam> inputsParam) {
		if (null != inputsParam && !inputsParam.isEmpty()) {
			InputParam inputParam = null;
			Element childElem = null;
			Element expressionElem = null;
			for (Iterator<InputParam> iterator = inputsParam.iterator(); iterator.hasNext();) {
				inputParam = iterator.next();
				childElem = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_INPUTS);
				childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
				        + ':' + BpmnXMLConstants.TYPE_CONNECTORPARAMETERINPUT);
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_ID, inputParam.getId());
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_NAME, inputParam.getName());
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_DATATYPE, inputParam.getDataType());
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_ISEXECUTE, String.valueOf(inputParam.isExecute()));
				if (null != inputParam.getExpression()) {
					expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_EXPRESSION);
					expressionElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
					expressionElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLUtil.interceptStr(inputParam.getExpression()));
					expressionElem.add(new DOMCDATA(inputParam.getExpression()));
				}
			}
		}
		
	}
	
	private static void createOutputsParam(Element parentElement, List<OutputParam> outputsParam) {
		if (null != outputsParam && !outputsParam.isEmpty()) {
			OutputParam outputParam = null;
			Element childElem = null;
			for (Iterator<OutputParam> iterator = outputsParam.iterator(); iterator.hasNext();) {
				outputParam = iterator.next();
				childElem = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_OUTPUTS);
				childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
				        + ':' + BpmnXMLConstants.TYPE_CONNECTORPARAMETEROUTPUT);
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_VARIABLETARGET, outputParam.getVariableTarget());
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_OUTPUT, outputParam.getOutput());
			}
		}
	}
	private static void createOutputsParamDef(Element parentElement, List<OutputParamDef> outputsParamDef) {
		if (null != outputsParamDef && !outputsParamDef.isEmpty()) {
			OutputParamDef outputParamDef = null;
			Element childElem = null;
			for (Iterator<OutputParamDef> iterator = outputsParamDef.iterator(); iterator.hasNext();) {
				outputParamDef = iterator.next();
				childElem = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_CONNECTORPARAMETER_OUTPUTSDEF);
				childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
				        + ':' + BpmnXMLConstants.TYPE_CONNECTORPARAMETEROUTPUTDEF);
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_NAME, outputParamDef.getName());
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_DATATYPE, outputParamDef.getDataType());
			}
		}
	}
	
}
