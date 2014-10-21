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
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.FlowElement;
import org.foxbpm.model.ScriptTask;

/**
 * 脚本任务节点转换处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class ScriptTaskXMLConverter extends TaskXMLConverter {
	
	public FlowElement cretateFlowElement() {
		return new ScriptTask();
	}
	
	@Override
	public Class<? extends BaseElement> getBpmnElementType() {
		return ScriptTask.class;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		ScriptTask scriptTask = (ScriptTask) baseElement;
		scriptTask.setScript(element.attributeValue(BpmnXMLConstants.FOXBPM_PREFIX + ':'
		        + BpmnXMLConstants.ATTRIBUTE_SCRIPTNAME));
		scriptTask.setScriptFormat(element.attributeValue(BpmnXMLConstants.ATTRIBUTE_SCRIPTFORMAT));
		
		Element elem = null;
		String nodeName = null;
		for (Iterator iterator = element.elements().iterator(); iterator.hasNext();) {
			elem = (Element) iterator.next();
			nodeName = elem.getName();
			if (BpmnXMLConstants.ELEMENT_SCRIPT.equalsIgnoreCase(nodeName)) {
				scriptTask.setScript(elem.getText().replace(BpmnXMLConstants.XML_QUOT, BpmnXMLConstants.EMPTY_STRING));
			}
		}
		super.convertXMLToModel(element, baseElement);
	}
	
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		
	}
	
	@Override
	public String getXMLElementName() {
		return BpmnXMLConstants.ELEMENT_SCRIPTTASK;
	}

	public Element cretateXMLElement() {
	    // TODO Auto-generated method stub
	    return null;
    }
	
}
