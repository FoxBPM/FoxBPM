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

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.bpmn.converter.util.BpmnXMLUtil;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.CommandParameter;
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
	
	public FlowElement cretateFlowElement() {
		return new UserTask();
	}
	
	public Class<? extends BaseElement> getBpmnElementType() {
		return UserTask.class;
	}
	
	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		UserTask userTask = (UserTask) baseElement;
		//领取方式
		userTask.setClaimType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_FOXBPM_CLAIMTYPE));
		//任务类型
		userTask.setTaskType(BpmnXMLConstants.ATTRIBUTE_FOXBPM_TASKTYPE);
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
							
							commandParams.add(commandParameter);
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
						if(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DAY) != null && extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DAY).trim() !=""){
							userTask.setExpectedExecuteDay(Integer.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_DAY)));
						}
						if(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_HOUR) != null && extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_HOUR).trim() !=""){
							userTask.setExpectedExecuteHour(Integer.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_HOUR)));

						}
						if(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_MINUTE) != null && extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_MINUTE).trim() !=""){
							userTask.setExpectedExecuteMinute(Integer.valueOf(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_MINUTE)));

						}
					} else if (BpmnXMLConstants.ELEMENT_FORMPARAMCONTAINER.equals(extentionElement.getName())) {
						// 任务表单参数
						List<FormParam> listFormParam = userTask.getFormParams();
						if(listFormParam == null){
							listFormParam = new ArrayList<FormParam>();
							userTask.setFormParams(listFormParam);
						}
						Iterator<Element> formParamIter = extentionElement.elementIterator(BpmnXMLConstants.ELEMENT_FORMPARAM);
						Element formParamElement = null;
						while(formParamIter.hasNext()){
							formParamElement = formParamIter.next();
							FormParam formParam = new FormParam(); 
							formParam.setParamType(formParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_PARAMTYPE));
							formParam.setParamKey(formParamElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_PARAMKEY));
							formParam.setExpression(BpmnXMLUtil.parseExpression(formParamElement.element(BpmnXMLConstants.ELEMENT_EXPRESSION)));
							listFormParam.add(formParam);
						}
					}
					
				} 
			} else if (BpmnXMLConstants.ELEMENT_POTENTIALOWNER.equals(extentionElement.getName())) {
				// 任务分配连接器
				userTask.setActorConnectors(BpmnXMLUtil.parserConnectorElement(extentionElement.element(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS).element(BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS)));
			}
		}
		
		super.convertXMLToModel(element, baseElement);
	}
	
	public String getXMLElementName() {
		return BpmnXMLConstants.ELEMENT_TASK_USER;
	}
	
	public void convertModelToXML(Element element, BaseElement baseElement) {
		// TODO Auto-generated method stub
		
	}
 
}
