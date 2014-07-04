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
package org.foxbpm.kernel.event;

import java.util.List;

import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public abstract class AbstractKernelEvent implements KernelEvent {

	public boolean isAsync(InterpretableExecutionContext executionContext) {
		// TODO Auto-generated method stub
		return false;
	}

	public void execute(InterpretableExecutionContext executionContext) {
	
		KernelFlowElementsContainerImpl container = getContainer(executionContext);		
		String eventName = getEventName();
		List<KernelListener> kernelListeners = container.getKernelListeners(eventName);
		
		int kernelListenerIndex = executionContext.getKernelListenerIndex();

		if (kernelListeners.size() > kernelListenerIndex) {
			executionContext.setEventName(eventName);
			executionContext.setEventSource(container);
			KernelListener listener = kernelListeners.get(kernelListenerIndex);
			try {
				listener.notify(executionContext);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new KernelException("不能执行事件监听 : " + e.getMessage(), e);
			}
			executionContext.setKernelListenerIndex(kernelListenerIndex + 1);
			executionContext.fireEvent(this);

		} else {
			executionContext.setKernelListenerIndex(0);
			executionContext.setEventName(null);
			executionContext.setEventSource(null);

			eventNotificationsCompleted(executionContext);
		}

	}

	protected abstract KernelFlowElementsContainerImpl getContainer(InterpretableExecutionContext executionContext);

	protected abstract String getEventName();

	protected abstract void eventNotificationsCompleted(InterpretableExecutionContext executionContext);

}
