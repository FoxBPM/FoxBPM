/* Licensed under the Apache License, Version 2.0 (the "License");
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
 */
package org.foxbpm.bpmn.converter.export;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.BpmnXMLConverter;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.Connector;
import org.foxbpm.model.DataVariableDefinition;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.InputParam;
import org.foxbpm.model.OutputParam;
import org.foxbpm.model.OutputParamDef;
import org.foxbpm.model.PotentialStarter;
import org.foxbpm.model.Process;
import org.foxbpm.model.SequenceFlow;
import org.springframework.util.StringUtils;

public class ProcessExport extends BpmnExport {
	
	public static void writeProcess(Process process, Element parentElement) {
		if (null != process) {
			// 创建流程元素
			Element processEle = parentElement.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_PROCESS);
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, process.getId());
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, process.getName());
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_CATEGORY, process.getCategory());
			processEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_KEY, process.getKey());
			
			// 处理扩展
			createExtensionElement(processEle, process);
			// 处理流程节点
			createFlowElement(processEle, process.getFlowElements());
			// 处理流程线条
			createSequenceFlowElement(processEle, process.getSequenceFlows());
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
	
	private static void createExtensionElement(Element processEle, Process process) {
		// 基本属性设置
		Element extensionElements = null;
		if (null != process.getFormUri() || null != process.getSubject()) {
			extensionElements = processEle.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS);
		}
		
		if (null != extensionElements) {
			Element childElem = null;
			Element expression = null;
			// 表单url
			childElem = extensionElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_FORMURI);
			expression = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.XSD_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLConstants.EMPTY_STRING);
			expression.add(new DOMCDATA(process.getFormUri()));
			// taskSubject
			childElem = extensionElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_TASKSUBJECT);
			expression = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.XSD_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLConstants.EMPTY_STRING);
			expression.add(new DOMCDATA(process.getSubject()));
			// formUriView
			childElem = extensionElements.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_FORMURIVIEW);
			expression = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.XSD_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
			        + ':' + BpmnXMLConstants.TYPE_EXPRESSION);
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
			expression.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, BpmnXMLConstants.EMPTY_STRING);
			expression.add(new DOMCDATA(process.getFormUriView()));
			
			// 数据变量
			createDataVariableElement(extensionElements, process.getDataVariables());
			// 启动人
			createStarterElement(extensionElements, process.getPotentialStarters());
			// 连接器
			createConectorElement(extensionElements, process.getConnector());
		}
		
	}
	private static void createConectorElement(Element parentElement, List<Connector> connectors) {
		if (null != connectors) {
			Connector connector = null;
			Element connectorInstanceElement = null;
			Element childElem = null;
			Element expressionElem = null;
			connectorInstanceElement = parentElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
			        + BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS);
			connectorInstanceElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNRCTORTYPE, "flowConnector");
			
			for (Iterator<Connector> iterator = connectors.iterator(); iterator.hasNext();) {
				connector = iterator.next();
				// 处理基本属性
				childElem = connectorInstanceElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_CONNECTORINSTANCE);
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_PACKAGENAME, connector.getPackageName());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, connector.getId());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CLASSNAME, connector.getClassName());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_ID, connector.getConnectorInstanceId());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_CONNECTORINSTANCE_NAME, connector.getConnectorInstanceName());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_EVENTTYPE, connector.getEventType());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ERRORHANDLING, connector.getErrorHandling());
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ERRORCODE, connector.getErrorCode());
				// 结束
				BpmnXMLUtil.addElemAttribute(childElem, BpmnXMLConstants.ATTRIBUTE_TYPE, connector.getType());
				// 处理输入参数
				createInputsParam(childElem, connector.getInputsParam());
				// 处理输出参数
				createOutputsParam(childElem, connector.getOutputsParam());
				// 处理输出参数
				createOutputsParamDef(childElem, connector.getOutputsParamDef());
				// 处理其他
				/*
				 * <foxbpm:skipComment xsi:type="foxbpm:SkipComment">
				 * <foxbpm:expression xsi:type="foxbpm:Expression"
				 * name="ddddd"><![CDATA["ddddd"]]></foxbpm:expression>
				 * </foxbpm:skipComment>
				 * 
				 * 
				 * 
				 * <foxbpm:timeExpression xsi:type="foxbpm:timeExpression">
				 * <foxbpm:expression xsi:type="foxbpm:Expression"
				 * name="xxxx"><![CDATA["xxxx"]]></foxbpm:expression>
				 * </foxbpm:timeExpression> <foxbpm:timeSkipExpression
				 * xsi:type="foxbpm:timeSkipExpression"> <foxbpm:expression
				 * xsi:type="foxbpm:Expression"
				 * name="ddddd"><![CDATA["ddddd"]]></foxbpm:expression>
				 * </foxbpm:timeSkipExpression> <foxbpm:documentation
				 * xsi:type="foxbpm:Documentation">dddd</foxbpm:documentation>
				 */
				// 处理foxbpm:skipComment
				childElem = connectorInstanceElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_SKIPCOMMENT);
				childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
				        + ':' + BpmnXMLConstants.ELEMENT_SKIPCOMMENT);
				expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_EXPRESSION);
				expressionElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
				        + ':' + BpmnXMLConstants.ELEMENT_EXPRESSION);
				expressionElem.add(new DOMCDATA(connector.getSkipComment()));
				// 处理foxbpm:timeExpression
				if (null != connector.getTimerEventDefinition()) {
					childElem = connectorInstanceElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_TIMEEXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_TIMEEXPRESSION);
					expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_EXPRESSION);
					expressionElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_EXPRESSION);
					expressionElem.add(new DOMCDATA(connector.getTimerEventDefinition().getTimeDuration()));
					
				}
				// foxbpm:timeSkipExpression
				if (null != connector.getSkipExpression()) {
					childElem = connectorInstanceElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_TIMESKIPEXPRESSION);
					expressionElem = childElem.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_EXPRESSION);
					expressionElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_EXPRESSION);
					expressionElem.add(new DOMCDATA(connector.getSkipExpression()));
				}
				// 描述
				if (null != connector.getDocumentation()) {
					childElem = connectorInstanceElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.FOXBPM_PREFIX
					        + ':' + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
					childElem.setText(connector.getDocumentation());
				}
			}
		}
	}
	private static void createInputsParam(Element parentElement, List<InputParam> inputsParam) {
		if (null != inputsParam && !inputsParam.isEmpty()) {
			InputParam inputParam = null;
			Element childElem = null;
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
	
	private static void createStarterElement(Element parentElement, List<PotentialStarter> potentialStarters) {
		if (null != potentialStarters) {
			PotentialStarter potentialStarter = null;
			Element potentialStarterEle = null;
			Element childElem = null;
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
				childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_EXPRESSION));
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
					childElem.add(new DOMCDATA(dataVariableDefinition.getExpression()));
				}
			}
		}
	}
	private static void createFlowElement(Element parentElement, List<FlowElement> flowElements) {
		if (null != flowElements) {
			FlowElement flowElement = null;
			for (Iterator<FlowElement> iterator = flowElements.iterator(); iterator.hasNext();) {
				flowElement = iterator.next();
				BpmnXMLConverter.getConverter(flowElement.getClass()).convertModelToXML(parentElement, flowElement);
			}
		}
	}
	
	/*
	 * 创建线条element
	 * 
	 * @param sequenceFlows 线条
	 * 
	 * @param processEle 父节点
	 */
	private static void createSequenceFlowElement(Element parentElement, Map<String, SequenceFlow> sequenceFlows) {
		if (null != sequenceFlows) {
			Entry<String, SequenceFlow> sequenceFlow = null;
			Element sequenceFlowEle = null;
			Element childElem = null;
			for (Iterator<Entry<String, SequenceFlow>> iterator = sequenceFlows.entrySet().iterator(); iterator.hasNext();) {
				sequenceFlow = iterator.next();
				
				sequenceFlowEle = parentElement.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
				        + BpmnXMLConstants.ELEMENT_SEQUENCEFLOW);
				sequenceFlowEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, sequenceFlow.getValue().getId());
				sequenceFlowEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, sequenceFlow.getValue().getName());
				/** 排序未处理 */
				if (!StringUtils.isEmpty(sequenceFlow.getValue().getSort())) {
					sequenceFlowEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_FOXBPM, sequenceFlow.getValue().getSort());
				}
				sequenceFlowEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_SOURCEREF, sequenceFlow.getValue().getSourceRefId());
				sequenceFlowEle.addAttribute(BpmnXMLConstants.ATTRIBUTE_TARGETREF, sequenceFlow.getValue().getTargetRefId());
				// 描述
				if (!StringUtils.isEmpty(sequenceFlow.getValue().getDocumentation())) {
					childElem = sequenceFlowEle.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_DOCUMENTATION);
					childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_DOCUMENTATION));
					childElem.setText(sequenceFlow.getValue().getDocumentation());
				}
				// 表达式
				if (null != sequenceFlow.getValue().getFlowCondition()) {
					childElem = sequenceFlowEle.addElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
					        + BpmnXMLConstants.ELEMENT_CONDITIONEXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.XSI_PREFIX + ':' + BpmnXMLConstants.TYPE, BpmnXMLConstants.BPMN2_PREFIX
					        + ':' + BpmnXMLConstants.TYPE_FORMALEXPRESSION);
					childElem.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.FORMALEXPRESSION));
					if (!StringUtils.isEmpty(sequenceFlow.getValue().getName())) {
						childElem.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ':' + BpmnXMLConstants.ATTRIBUTE_NAME, sequenceFlow.getValue().getName());
					}
					childElem.add(new DOMCDATA(sequenceFlow.getValue().getFlowCondition()));
				}
			}
		}
	}
}
