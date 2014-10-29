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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.bpmn.converter.util.UniqueIDUtil;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.CommandParameter;
import org.foxbpm.model.Connector;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.FormParam;
import org.foxbpm.model.TaskCommand;
import org.foxbpm.model.UserTask;

/**
 * 人工任务转化类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class UserTaskXMLConverter extends TaskXMLConverter {
	
	private final static String ELEMENT_NAME_FOXBPM_TASKCOMMAND = BpmnXMLConstants.FOXBPM_PREFIX + ":"
	        + BpmnXMLConstants.ELEMENT_TASKCOMMAND;
	private final static String ELEMENT_NAME_FOXBPM_PARAMS = BpmnXMLConstants.FOXBPM_PREFIX + ":"
	        + BpmnXMLConstants.ELEMENT_PARAMS;
	private final static String ELEMENT_NAME_BPMN2_POTENTIALOWNER = BpmnXMLConstants.BPMN2_PREFIX + ":"
	        + BpmnXMLConstants.ELEMENT_POTENTIALOWNER;
	
	private final static String ATTRIBUTE_NAME_XSI_TYPE = BpmnXMLConstants.XSI_PREFIX + ":"
	        + BpmnXMLConstants.ATTRIBUTE_TYPE;
	public FlowElement cretateFlowElement() {
		return new UserTask();
	}
	
	public Class<? extends BaseElement> getBpmnElementType() {
		return UserTask.class;
	}
	
	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		UserTask userTask = (UserTask) baseElement;
		// 领取方式
		userTask.setClaimType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_FOXBPM_CLAIMTYPE));
		// 任务类型
		userTask.setTaskType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_FOXBPM_TASKTYPE));
		Iterator<Element> elementIterator = element.elements().iterator();
		Element subElement = null;
		Element extentionElement = null;
		while (elementIterator.hasNext()) {
			subElement = elementIterator.next();
			// 扩展元素
			if (BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equals(subElement.getName())) {
				Iterator<Element> extentionIterator = subElement.elements().iterator();
				while (extentionIterator.hasNext()) {
					extentionElement = extentionIterator.next();
					
					// 任务命令
					if (BpmnXMLConstants.ELEMENT_TASKCOMMAND.equals(extentionElement.getName())) {
						List<TaskCommand> listTaskCommand = userTask.getTaskCommands();
						if (listTaskCommand == null) {
							listTaskCommand = new ArrayList<TaskCommand>();
							userTask.setTaskCommands(listTaskCommand);
						}
						TaskCommand taskCommand = new TaskCommand();
						taskCommand.setName(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
						taskCommand.setId(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
						taskCommand.setTaskCommandType(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_COMMANDTYPE));
						
						// 命令参数
						Iterator<Element> taskCommandParamIter = extentionElement.elementIterator(BpmnXMLConstants.ELEMENT_PARAMS);
						List<CommandParameter> commandParams = taskCommand.getCommandParams();
						Element taskCommandParamElement = null;
						while (taskCommandParamIter.hasNext()) {
							taskCommandParamElement = taskCommandParamIter.next();
							if (commandParams == null) {
								commandParams = new ArrayList<CommandParameter>();
								taskCommand.setCommandParams(commandParams);
							}
							
							CommandParameter commandParameter = new CommandParameter();
							commandParameter.setBizType(taskCommandParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_BIZTYPE));
							commandParameter.setDataType(taskCommandParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DATATYPE));
							commandParameter.setDescription(taskCommandParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DESCRIPTION));
							commandParameter.setName(taskCommandParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
							commandParameter.setExpression(BpmnXMLUtil.parseExpression(taskCommandParamElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
							commandParameter.setKey(taskCommandParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_KEY));
							commandParams.add(commandParameter);
						}
						
						Element taskCommandExpElement = extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION);
						if (taskCommandExpElement != null) {
							taskCommand.setExpression(taskCommandExpElement.getText());
						}
						listTaskCommand.add(taskCommand);
					} else if (BpmnXMLConstants.ELEMENT_TASKSUBJECT.equals(extentionElement.getName())) {
						// 任务主题
						userTask.setSubject(BpmnXMLUtil.parseExpression(extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
					} else if (BpmnXMLConstants.ELEMENT_TASKDESCRIPTION.equals(extentionElement.getName())) {
						// 任务描述
						userTask.setTaskDescription(BpmnXMLUtil.parseExpression(extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
					} else if (BpmnXMLConstants.ELEMENT_COMPLETETASKDESCRIPTION.equals(extentionElement.getName())) {
						// 任务完成描述
						userTask.setCompleteDescription(BpmnXMLUtil.parseExpression(extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
					} else if (BpmnXMLConstants.ELEMENT_FORMURI.equals(extentionElement.getName())) {
						// 任务表单
						userTask.setFormUri(BpmnXMLUtil.parseExpression(extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
					} else if (BpmnXMLConstants.ELEMENT_FORMURIVIEW.equals(extentionElement.getName())) {
						// 任务查看表单
						userTask.setFormUriView(BpmnXMLUtil.parseExpression(extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
					} else if (BpmnXMLConstants.ELEMENT_TASKPRIORITY.equals(extentionElement.getName())) {
						// 任务优先级
						userTask.setTaskPriority(BpmnXMLUtil.parseExpression(extentionElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
					} else if (BpmnXMLConstants.ELEMENT_EXPECTEDEXECUTIONTIME.equals(extentionElement.getName())) {
						// 任务期望执行时间
						if (extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DAY) != null
						        && extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DAY).trim() != "") {
							userTask.setExpectedExecuteDay(Integer.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DAY)));
						}
						if (extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_HOUR) != null
						        && extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_HOUR).trim() != "") {
							userTask.setExpectedExecuteHour(Integer.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_HOUR)));
							
						}
						if (extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_MINUTE) != null
						        && extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_MINUTE).trim() != "") {
							userTask.setExpectedExecuteMinute(Integer.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_MINUTE)));
							
						}
					} else if (BpmnXMLConstants.ELEMENT_FORMPARAMCONTAINER.equals(extentionElement.getName())) {
						// 任务表单参数
						List<FormParam> listFormParam = userTask.getFormParams();
						if (listFormParam == null) {
							listFormParam = new ArrayList<FormParam>();
							userTask.setFormParams(listFormParam);
						}
						Iterator<Element> formParamIter = extentionElement.elementIterator(BpmnXMLConstants.ELEMENT_FORMPARAM);
						Element formParamElement = null;
						while (formParamIter.hasNext()) {
							formParamElement = formParamIter.next();
							FormParam formParam = new FormParam();
							formParam.setParamType(formParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_PARAMTYPE));
							formParam.setParamKey(formParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_PARAMKEY));
							formParam.setExpression(BpmnXMLUtil.parseExpression(formParamElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
							listFormParam.add(formParam);
						}
					}
					
				}
			} else if (BpmnXMLConstants.ELEMENT_POTENTIALOWNER.equals(subElement.getName())) {
				// 任务分配连接器
				userTask.setActorConnectors(BpmnXMLUtil.parserConnectorElement(subElement.element(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS).element(BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS)));
			}
		}
		
		super.convertXMLToModel(element, baseElement);
	}
	
	public String getXMLElementName() {
		return BpmnXMLConstants.ELEMENT_TASK_USER;
	}
	
	public void convertModelToXML(Element element, BaseElement baseElement) {
		UserTask userTask = (UserTask) baseElement;
		element.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ":" + BpmnXMLConstants.ATTRIBUTE_FOXBPM_CLAIMTYPE, userTask.getClaimType());
		element.addAttribute(BpmnXMLConstants.FOXBPM_PREFIX + ":" + BpmnXMLConstants.ATTRIBUTE_FOXBPM_TASKTYPE, userTask.getTaskType());
		
		Element extensionElement = element.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT);
		
		// 转换任务命令
		List<TaskCommand> taskCommands = userTask.getTaskCommands();
		if (taskCommands != null) {
			Element taskCommandElement = null;
			List<CommandParameter> commandParams = null;
			for (TaskCommand taskCommand : taskCommands) {
				taskCommandElement = extensionElement.addElement(ELEMENT_NAME_FOXBPM_TASKCOMMAND, BpmnXMLConstants.FOXBPM_NAMESPACE);
				taskCommandElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, taskCommand.getId());
				taskCommandElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, taskCommand.getName());
				taskCommandElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_COMMANDTYPE, taskCommand.getTaskCommandType());
				
				// 任务命令参数
				commandParams = taskCommand.getCommandParams();
				if (commandParams != null) {
					Element commandParamElement = null;
					for (CommandParameter commandParameter : commandParams) {
						commandParamElement = taskCommandElement.addElement(ELEMENT_NAME_FOXBPM_PARAMS);
						
						commandParamElement.addAttribute(ATTRIBUTE_NAME_XSI_TYPE, "foxbpm:CommandParam");
						commandParamElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_DATATYPE, commandParameter.getDataType());
						commandParamElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_BIZTYPE, commandParameter.getBizType());
						commandParamElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_NAME, commandParameter.getName());
						commandParamElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_DESCRIPTION, commandParameter.getDescription());
						commandParamElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_KEY, commandParameter.getKey());
						if (commandParameter.getExpression() != null) {
							BpmnXMLUtil.createExpressionElementByParent(commandParamElement, commandParameter.getExpression());
						}
					}
				}
				
				String taskCommandExpression = taskCommand.getExpression();
				if (taskCommandExpression != null) {
					BpmnXMLUtil.createExpressionElementByParent(taskCommandElement, taskCommandExpression);
				}
				
			}
		}
		
		// 任务优先级
		String taskPriority = userTask.getTaskPriority();
		if (taskPriority != null) {
			Element taskPriorityElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_TASKPRIORITY, BpmnXMLConstants.FOXBPM_NAMESPACE);
			BpmnXMLUtil.createExpressionElementByParent(taskPriorityElement, taskPriority);
		}
		
		// 操作表单
		String formUri = userTask.getFormUri();
		if (formUri != null) {
			Element formUriElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_FORMURI, BpmnXMLConstants.FOXBPM_NAMESPACE);
			BpmnXMLUtil.createExpressionElementByParent(formUriElement, formUri);
		}
		
		// 浏览表单
		String formUriView = userTask.getFormUriView();
		if (formUriView != null) {
			Element formUriViewElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_FORMURIVIEW, BpmnXMLConstants.FOXBPM_NAMESPACE);
			BpmnXMLUtil.createExpressionElementByParent(formUriViewElement, formUriView);
		}
		
		// 任务主题
		String taskSubject = userTask.getSubject();
		if (taskSubject != null) {
			Element taskSubjectElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_TASKSUBJECT, BpmnXMLConstants.FOXBPM_NAMESPACE);
			BpmnXMLUtil.createExpressionElementByParent(taskSubjectElement, taskSubject);
			
		}
		
		// 任务描述
		String taskDescription = userTask.getTaskDescription();
		if (taskDescription != null) {
			Element taskDescriptionElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_TASKDESCRIPTION, BpmnXMLConstants.FOXBPM_NAMESPACE);
			BpmnXMLUtil.createExpressionElementByParent(taskDescriptionElement, taskDescription);
			
		}
		
		// 任务完成描述
		String completeDescription = userTask.getCompleteDescription();
		if (completeDescription != null) {
			Element completeDescriptionElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_COMPLETETASKDESCRIPTION, BpmnXMLConstants.FOXBPM_NAMESPACE);
			BpmnXMLUtil.createExpressionElementByParent(completeDescriptionElement, completeDescription);
		}
		
		// 预期执行时间
		int expectedExecuteDay = userTask.getExpectedExecuteDay();
		int expectedExecuteHour = userTask.getExpectedExecuteHour();
		int expectedExecuteMinute = userTask.getExpectedExecuteMinute();
		if (expectedExecuteDay != 0 || expectedExecuteHour != 0 || expectedExecuteMinute != 0) {
			Element expectedExecuteElement = extensionElement.addElement(BpmnXMLConstants.FOXBPM_PREFIX + ":"
			        + BpmnXMLConstants.ELEMENT_EXPECTEDEXECUTIONTIME, BpmnXMLConstants.FOXBPM_NAMESPACE);
			if (expectedExecuteDay != 0) {
				expectedExecuteElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_DAY, String.valueOf(expectedExecuteDay));
			}
			if (expectedExecuteHour != 0) {
				expectedExecuteElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_HOUR, String.valueOf(expectedExecuteHour));
			}
			if (expectedExecuteMinute != 0) {
				expectedExecuteElement.addAttribute(BpmnXMLConstants.ATTRIBUTE_MINUTE, String.valueOf(expectedExecuteMinute));
			}
		}
		
		// 任务分配
		List<Connector> actorConnectors = userTask.getActorConnectors();
		if (actorConnectors != null && actorConnectors.size() > 0) {
			
			Element potentialOwner = element.addElement(ELEMENT_NAME_BPMN2_POTENTIALOWNER);
			potentialOwner.addAttribute(BpmnXMLConstants.ATTRIBUTE_ID, UniqueIDUtil.getInstance().generateElementID(BpmnXMLConstants.ELEMENT_POTENTIALOWNER));
			BpmnXMLUtil.createConectorElement(potentialOwner.addElement(ELEMENT_NAME_BPMN2_EXTENSIONELEMENT), BpmnXMLConstants.TYPE_ACTORCONNECTOR, actorConnectors);
		}
		// 连接器
		List<Connector> connectors = userTask.getConnector();
		if (connectors != null && connectors.size() > 0) {
			BpmnXMLUtil.createConectorElement(extensionElement, BpmnXMLConstants.TYPE_FLOWCONNECTOR, connectors);
		}
		super.convertModelToXML(element, baseElement);
		
	}
	
	public Element cretateXMLElement() {
		return DocumentFactory.getInstance().createElement(BpmnXMLConstants.BPMN2_PREFIX + ':'
		        + BpmnXMLConstants.ELEMENT_TASK_USER, BpmnXMLConstants.BPMN2_NAMESPACE);
	}
	
}
