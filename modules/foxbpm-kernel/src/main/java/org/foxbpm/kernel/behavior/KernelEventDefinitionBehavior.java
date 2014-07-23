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
 * @author MAENLIANG
 */
package org.foxbpm.kernel.behavior;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * 
 * 
 * KernelEventDefinitionBehavior 事件定义的行为接口，例如时间定义、终止定义、、、
 * 
 * MAENLIANG 2014年7月23日 下午1:33:28
 * 
 * @version 1.0.0
 * 
 */
public interface KernelEventDefinitionBehavior {
	/**
	 * 
	 * execute(事件定义的执行)
	 * 
	 * @param executionContext
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	void execute(FlowNodeExecutionContext executionContext, String eventType, Object[] params);
}
