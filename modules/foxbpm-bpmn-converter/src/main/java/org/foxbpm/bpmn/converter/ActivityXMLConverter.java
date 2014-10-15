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

import org.foxbpm.model.BaseElement;
import org.foxbpm.model.BpmnModel;
import org.w3c.dom.Element;

/**
 * 活动元素处理
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class ActivityXMLConverter extends BaseElementXMLConverter {
	
	@Override
	protected Class<? extends BaseElement> getBpmnElementType() {
		return null;
	}
	
	@Override
	protected BaseElement convertXMLToElement(Element element, BpmnModel model) throws Exception {
		return null;
	}
	
	@Override
	protected String getXMLElementName() {
		return ELEMENT_TASK_USER;
	}
	
}
