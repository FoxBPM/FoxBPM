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
import org.foxbpm.model.Connector;
import org.foxbpm.model.FlowElement;


/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public abstract class FlowElementXMLConverter extends BaseElementXMLConverter {

	public FlowElement cretateFlowElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends BaseElement> getBpmnElementType() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		FlowElement flowElement =(FlowElement)baseElement;
		flowElement.setName(BpmnXMLConstants.ATTRIBUTE_NAME);
		
		Iterator<Element> elementIterator = element.elements().iterator();
		Element subElement = null;
		Element extentionElement = null;
		while(elementIterator.hasNext()){
			subElement = elementIterator.next();
			if(BpmnXMLConstants.ELEMENT_EXTENSION_ELEMENTS.equals(subElement.getName())){
				Iterator<Element> extentionIterator = subElement.elements().iterator();
				while(extentionIterator.hasNext()){
					extentionElement = extentionIterator.next();
					//转化连接器
					if(BpmnXMLConstants.ELEMENT_CONNECTORINSTANCEELEMENTS.equals(extentionElement.getName())){
						List<Connector> listConnector = new ArrayList<Connector>();
						 BpmnXMLUtil.parserConnectorElement(extentionElement);
						 flowElement.setConnector(listConnector);
					} 
					

				}
			}
		}
		
	}

	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getXMLElementName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
