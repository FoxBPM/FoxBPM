/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
import org.eclipse.bpmn2.Gateway;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.GatewayBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.GatewayDirection;

/**
 * @author kenshin
 * 
 */
public class GatewayParser extends FlowNodeParser {

	 
	public BaseElementBehavior parser(BaseElement baseElement) {
		
		GatewayBehavior gatewayBehavior = (GatewayBehavior) baseElementBehavior;
		
		Gateway gateway = (Gateway) baseElement;

		if (gateway.getGatewayDirection() != null) {
			
			gatewayBehavior.setGatewayDirection(GatewayDirection.valueOf(gateway.getGatewayDirection().name().toUpperCase()));
			
		}

		return super.parser(baseElement);

	}

}
