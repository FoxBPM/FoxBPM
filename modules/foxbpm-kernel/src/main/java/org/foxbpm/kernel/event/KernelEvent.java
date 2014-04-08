/**
 * 
 */
package org.foxbpm.kernel.event;

import org.foxbpm.kernel.runtime.KernelExecutionContext;

/**
 * @author kenshin
 * 
 */
public interface KernelEvent {
	
	KernelEvent PROCESS_START = new KernelEventProcessStart();
	KernelEvent PROCESS_START_INITIAL = new KernelEventProcessStartInitial();
	KernelEvent PROCESS_END = new KernelEventProcessEnd();
	KernelEvent NODE_START = new KernelEventNodeStart();
	KernelEvent NODE_EXECUTE = new KernelEventNodeExecute();
	KernelEvent NODE_END = new KernelEventNodeEnd();


	boolean isAsync(KernelExecutionContext executionContext);

	public void execute(KernelExecutionContext executionContext);

}
