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
package org.foxbpm.kernel.process;

import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

/**
 * @author kenshin
 *
 */
public interface KernelSequenceFlow extends KernelFlowElement {
	
	
	KernelFlowNode getSourceRef();

	KernelFlowNode getTargetRef();
	
	
	/**
	 * 线条条件处理适配器执行方法
	 * @param executionContext 执行内容
	 */
	boolean isContinue(FlowNodeExecutionContext executionContext);
	
	
	
	/**
	 * 线条执行事件
	 * @param executionContext 执行内容
	 */
	public void take(InterpretableExecutionContext executionContext);

}
