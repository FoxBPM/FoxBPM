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

import org.foxbpm.engine.impl.expression.ExpressionMgmt;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.behavior.KernelSequenceFlowBehavior;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.model.SequenceFlow;

public class SequenceFlowBehavior extends FlowElementBehavior implements KernelSequenceFlowBehavior {

	/**
	 * @author kenshin
	 */
	private static final long serialVersionUID = 1L;

	public boolean isContinue(FlowNodeExecutionContext executionContext) {
		SequenceFlow sequenceFlow = (SequenceFlow)baseElement;
		if(StringUtil.isEmpty(sequenceFlow.getFlowCondition())){
			return true;
		}
		Object expressionValue = null;
		try{
			expressionValue = StringUtil.getBoolean(ExpressionMgmt.execute(sequenceFlow.getFlowCondition(), executionContext));
		}catch(Exception ex){
			throw ExceptionUtil.getException("10404020",ex,this.getId());
		}
		if (expressionValue == null) {
			return true;
		}
		if (expressionValue instanceof Boolean) {
			return (Boolean) expressionValue;
		}
		throw ExceptionUtil.getException("10404021",this.getId());
	}

}
