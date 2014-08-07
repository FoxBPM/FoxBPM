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
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.List;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class EndEventBehavior extends EventBehavior {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void enter(FlowNodeExecutionContext executionContext) {
		executionContext.execute();
	}
	
	public void execute(FlowNodeExecutionContext executionContext) {
		// 执行边界事件定义,例如 时间定义
		List<EventDefinition> eventDefinitions = this.getEventDefinitions();
		for (EventDefinition eventDefinition : eventDefinitions) {
			eventDefinition.execute(executionContext, null, null);
		}
		executionContext.end();
	}
	
}
