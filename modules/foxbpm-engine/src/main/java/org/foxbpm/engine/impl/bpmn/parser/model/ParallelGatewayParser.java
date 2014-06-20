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
 * @author kenshin
 */
package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ParallelGateway;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.ParallelGatewayBehavior;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.engine.impl.util.StringUtil;

public class ParallelGatewayParser extends GatewayParser {

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		ParallelGatewayBehavior parallelGatewayBehavior=(ParallelGatewayBehavior)baseElementBehavior;
		

		ParallelGateway parallelGateway=(ParallelGateway)baseElement;
		String convergType=BpmnModelUtil.convergType(parallelGateway);
		if(StringUtil.isNotEmpty(convergType)){
			parallelGatewayBehavior.setConvergType(BpmnModelUtil.convergType(parallelGateway));
		}

		
		return super.parser(baseElement);
	}
	

	@Override
	public void init() {
		baseElementBehavior=new ParallelGatewayBehavior();
	}


}
