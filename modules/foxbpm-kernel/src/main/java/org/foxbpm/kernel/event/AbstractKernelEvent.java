package org.foxbpm.kernel.event;

import java.util.List;

import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.impl.KernelFlowElementsContainerImpl;
import org.foxbpm.kernel.runtime.KernelExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public abstract class AbstractKernelEvent implements KernelEvent {

	public boolean isAsync(KernelExecutionContext executionContext) {
		// TODO Auto-generated method stub
		return false;
	}

	public void execute(KernelExecutionContext executionContext) {

		KernelTokenImpl token = executionContext.getToken();		
		KernelFlowElementsContainerImpl container = getContainer(executionContext);		
		List<KernelListener> kernelListeners = container.getKernelListeners(getEventName());
		
		int kernelListenerIndex = token.getKernelListenerIndex();

		if (kernelListeners.size() > kernelListenerIndex) {
			token.setEventName(getEventName());
			token.setEventSource(container);
			KernelListener listener = kernelListeners.get(kernelListenerIndex);
			try {
				listener.notify(executionContext);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new KernelException("不能执行事件监听 : " + e.getMessage(), e);
			}
			token.setKernelListenerIndex(kernelListenerIndex + 1);
			token.fireEvent(this);

		} else {
			token.setKernelListenerIndex(0);
			token.setEventName(null);
			token.setEventSource(null);

			eventNotificationsCompleted(executionContext);
		}

	}

	protected abstract KernelFlowElementsContainerImpl getContainer(KernelExecutionContext executionContext);

	protected abstract String getEventName();

	protected abstract void eventNotificationsCompleted(KernelExecutionContext executionContext);

}
