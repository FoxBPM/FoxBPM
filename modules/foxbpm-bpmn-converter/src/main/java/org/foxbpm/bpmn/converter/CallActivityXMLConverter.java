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

import org.dom4j.Element;
import org.foxbpm.bpmn.constants.BpmnXMLConstants;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.CallActivity;
import org.foxbpm.model.FlowElement;
import org.springframework.util.StringUtils;

/**
 * 公有子流程节点转换处理类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class CallActivityXMLConverter extends ActivityXMLConverter {
	
	public FlowElement cretateFlowElement() {
		return new CallActivity();
	}
	
	@Override
	public Class<? extends BaseElement> getBpmnElementType() {
		return CallActivity.class;
	}
	
	@Override
	public void convertXMLToModel(Element element, BaseElement baseElement) {
		CallActivity activity = (CallActivity)baseElement;
		String callElementId = element.attributeValue("foxbpm:callableElementId");
		String callElementVersion = element.attributeValue("foxbpm:callableElementVersion");
		String bizKey = element.attributeValue("foxbpm:callableElementBizKey");
		String isAsync = element.attributeValue("foxbpm:isAsync");
		if(!StringUtils.isEmpty(callElementId)){
			activity.setCallableElementId(callElementId);
		}
		
		if(!StringUtils.isEmpty(callElementVersion)){
			activity.setCallableElementVersion(callElementVersion);
		}
		
		if(!StringUtils.isEmpty(bizKey)){
			activity.setBizKey(bizKey);
		}
		
		if(!StringUtils.isEmpty(isAsync)){
			activity.setAsync(Boolean.parseBoolean(isAsync));
		}
	}
	
	@Override
	public void convertModelToXML(Element element, BaseElement baseElement) {
		
	}
	
	@Override
	public String getXMLElementName() {
		return BpmnXMLConstants.ELEMENT_CALLACTIVITY;
	}
	
}
