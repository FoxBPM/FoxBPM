package org.foxbpm.kernel.event;

import java.util.List;

import org.activiti.engine.impl.pvm.PvmException;
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
				throw new PvmException("不能执行事件监听 : " + e.getMessage(), e);
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
