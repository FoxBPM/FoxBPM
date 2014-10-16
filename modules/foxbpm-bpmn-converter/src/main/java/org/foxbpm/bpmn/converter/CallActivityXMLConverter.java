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
import org.dom4j.Element;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月15日
 */
public class CallActivityXMLConverter extends ActivityXMLConverter {

	@Override
	public Class<? extends BaseElement> getBpmnElementType() {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
	public String getXMLElementName() {
	    return null;
    }

	@Override
	public BaseElement convertXMLToMode(Element element, BpmnModel model) throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
	public Element convertModeToXML(Element element, BpmnModel model) throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }
	
}
