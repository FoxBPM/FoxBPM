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
package org.foxbpm.kernel.runtime;

import java.util.List;

import org.foxbpm.kernel.event.KernelEvent;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.impl.KernelProcessInstanceImpl;

/**
 * @author kenshin
 * 
 */
public interface FlowNodeExecutionContext extends DelegateExecutionContext {

	KernelProcessInstanceImpl getProcessInstance();

	KernelFlowNodeImpl getFlowNode();

	void ensureEnterInitialized(KernelFlowNodeImpl flowNode);

	void enter(KernelFlowNodeImpl flowNode);

	void execute();

	void signal();
	
	void signal(KernelFlowNodeImpl flowNode);

	void fireEvent(KernelEvent kernelEvent);

	public void take(KernelSequenceFlow sequenceFlow);

	public void take(KernelFlowNodeImpl flowNode);

	void end();

	List<KernelToken> findInactiveToken(KernelFlowNode flowNode);

	FlowNodeExecutionContext createChildrenToken();

	KernelProcessInstance createSubProcessInstance(KernelProcessDefinition processDefinition);

	FlowNodeExecutionContext getParent();

	List<? extends FlowNodeExecutionContext> getChildren();

	KernelProcessDefinitionImpl getProcessDefinition();

	void terminationChildToken();

}
