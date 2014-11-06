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
	KernelEvent BEFORE_PROCESS_SAVE = new KernelEventBeforeProcessSave();
	KernelEvent SEQUENCEFLOW_TAKE = new KernelEventSequenceFlowTake();

	boolean isAsync(InterpretableExecutionContext executionContext);

	public void execute(InterpretableExecutionContext executionContext);

}
