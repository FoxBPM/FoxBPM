/**
 * 
 */
package org.foxbpm.kernel.event;

import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

/**
 * @author kenshin
 * 
 */
public interface KernelEvent {
	
	KernelEvent PROCESS_START = new KernelEventProcessStart();
	KernelEvent PROCESS_START_INITIAL = new KernelEventProcessStartInitial();
	KernelEvent PROCESS_END = new KernelEventProcessEnd();
	KernelEvent PROCESS_ABORT = new KernelEventProcessAbort();
	KernelEvent NODE_ENTER = new KernelEventNodeEnter();
	KernelEvent NODE_EXECUTE = new KernelEventNodeExecute();
	KernelEvent NODE_LEAVE = new KernelEventNodeLeave();
	
	KernelEvent SEQUENCEFLOW_TAKE= new KernelEventSequenceFlowTake();

	
	boolean isAsync(InterpretableExecutionContext executionContext);

	public void execute(InterpretableExecutionContext executionContext);

}
