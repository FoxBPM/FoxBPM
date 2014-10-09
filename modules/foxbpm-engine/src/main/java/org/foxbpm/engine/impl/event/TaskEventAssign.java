package org.foxbpm.engine.impl.event;

import org.foxbpm.kernel.event.KernelEventType;
import org.foxbpm.kernel.runtime.InterpretableExecutionContext;

public class TaskEventAssign extends AbstractTaskEvent {


	 
	protected String getEventName() {
		return KernelEventType.EVENTTYPE_TASK_ASSIGN;
	}

	 
	protected void eventNotificationsCompleted(InterpretableExecutionContext executionContext) {
		// TODO Auto-generated method stub

	}

}
