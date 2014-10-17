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
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.BpmnModel;
import org.foxbpm.model.Connector;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.InputParam;
import org.foxbpm.model.OutputParam;
import org.foxbpm.model.TimerEventDefinition;
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
		userTask.setName(BpmnXMLConstants.ATTRIBUTE_NAME);
		userTask.setTaskType(BpmnXMLConstants.ATTRIBUTE_FOXBPM_TASKTYPE);
		
		Iterator<Element> elementIterator = element.elements().iterator();
		Element subElement = null;
		Element extentionElement = null;
		while(elementIterator.hasNext()){
			subElement = elementIterator.next();
			if(BpmnXMLConstants.ELEMENT_EXTENSIONS.equals(subElement.getName())){
				Iterator<Element> extentionIterator = subElement.elements().iterator();
				while(extentionIterator.hasNext()){
					extentionElement = extentionIterator.next();
					//转化连接器
					if(BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS.equals(extentionElement.getName())){
						List<Connector> listConnector = new ArrayList<Connector>();
						Connector connector = new Connector();
						connector.setClassName("");
						connector.setConnectorInstanceId("");
						connector.setDocumentation("");
						connector.setErrorCode("");
						connector.setErrorHandling("");
						connector.setEventType("");
						connector.setId("");
						List<InputParam> listInputParam = new ArrayList<InputParam>();
						InputParam inputParam = new InputParam();
						inputParam.setDataType("");
						inputParam.setDocumentation("");
						inputParam.setExecute(false);
						inputParam.setExpression("");
						inputParam.setId("");
						inputParam.setName("");
						
						connector.setInputsParam(listInputParam);
						List<OutputParam> listOutputParam = new ArrayList<OutputParam>();
						OutputParam outputParam = new OutputParam();
						outputParam.setVariableTarget("");
						outputParam.setId("");
						outputParam.setDocumentation("");
						
						connector.setOutputsParam(listOutputParam);
						connector.setPackageName("");
						connector.setSkipExpression("");
						TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
						timerEventDefinition.setDocumentation("");
						timerEventDefinition.setId("");
						timerEventDefinition.setTimeCycle("");
						timerEventDefinition.setTimeDate("");
						timerEventDefinition.setTimeDuration("");
						
						connector.setTimerEventDefinition(timerEventDefinition);
						userTask.setConnector(listConnector);
					}
				}
			}
		}
	}
	
	public void convertModelToXML(Element element, BpmnModel model) {
		// TODO Auto-generated method stub
		
	}
	
	public String getXMLElementName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
