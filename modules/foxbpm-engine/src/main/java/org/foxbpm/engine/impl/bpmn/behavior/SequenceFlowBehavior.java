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
 * @author ych
 * @author kenshin
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.kernel.behavior.KernelSequenceFlowBehavior;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class SequenceFlowBehavior extends FlowElementBehavior implements KernelSequenceFlowBehavior {

	/**
	 * @author kenshin
	 */
	private static final long serialVersionUID = 1L;
	
	private String conditionExpression;
	
	public boolean isContinue(FlowNodeExecutionContext executionContext) {
		return false;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}
	
}
