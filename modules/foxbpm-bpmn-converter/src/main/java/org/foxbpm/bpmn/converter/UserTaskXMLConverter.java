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
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.FlowElement;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public Class<? extends BaseElement> getBpmnElementType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		UserTask userTask =(UserTask)baseElement;
		userTask.setId(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID)); 
		userTask.setClaimType(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_FOXBPM_CLAIMTYPE));
		userTask.setTaskType(BpmnXMLConstants.ATTRIBUTE_FOXBPM_TASKTYPE);
		
		Iterator<Element> elementIterator = element.elements().iterator();
		Element subElement = null;
		Element extentionElement = null;
		while(elementIterator.hasNext()){
			subElement = elementIterator.next();
			if(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equals(subElement.getName())){
				Iterator<Element> extentionIterator = subElement.elements().iterator();
				while(extentionIterator.hasNext()){
					extentionElement = extentionIterator.next();
					 
					if(BpmnXMLConstants.ELEMENT_TASKCOMMAND.equals(extentionElement.getName())){
						List<TaskCommand> listTaskCommand = userTask.getTaskCommands();
						if(listTaskCommand == null){
							listTaskCommand = new ArrayList<TaskCommand>();
							userTask.setTaskCommands(listTaskCommand);
						}
						TaskCommand taskCommand = new TaskCommand();
						taskCommand.setName(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_NAME));
						taskCommand.setId(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_ID));
						taskCommand.setTaskCommandType(extentionElement.attributeValue(BpmnXMLConstants.ATTRIBUTE_COMMANDTYPE));
						
						listTaskCommand.add(taskCommand);
					}else if(BpmnXMLConstants.ELEMENT_POTENTIALOWNER.equals(extentionElement.getName())){
						userTask.setActorConnectors(BpmnXMLUtil.parserConnectorElement(extentionElement.element(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS).element(BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS)));
					} 
					

				}
			}
		}
		
		super.convertXMLToModel(element, baseElement);
	}
	 
	public void convertModelToXML(Element element, BpmnModel model) {
 
	}
	
	public String getXMLElementName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void convertModelToXML(Element element, BaseElement baseElement) {
		// TODO Auto-generated method stub
		
	}
	
}
