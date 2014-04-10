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
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public class KernelEventSequenceFlowTake implements KernelEvent {

	public boolean isAsync(InterpretableExecutionContext executionContext) {
		// TODO Auto-generated method stub
		return false;
	}

	public void execute(InterpretableExecutionContext executionContext) {
		KernelSequenceFlowImpl transition = executionContext.getSequenceFlow();
		
		List<KernelListener> kernelListeners = transition.getKernelListeners();
		int kernelListenerIndex = executionContext.getKernelListenerIndex();

		if (kernelListeners.size() > kernelListenerIndex) {
			executionContext.setEventName(KernelEventType.EVENTTYPE_SEQUENCEFLOW_TAKE);
			executionContext.setEventSource(transition);
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

		}
	}

}
