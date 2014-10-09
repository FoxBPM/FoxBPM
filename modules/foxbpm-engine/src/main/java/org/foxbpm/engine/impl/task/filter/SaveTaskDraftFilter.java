package org.foxbpm.engine.impl.task.filter;

import org.foxbpm.engine.task.Task;

public class SaveTaskDraftFilter extends AbstractCommandFilter {

	 
	public boolean accept(Task task) {
		if (task == null) {
			return true;
		}

		if (task.isSuspended()) {
			return false;
		}

		if (task.hasEnded()) {
			return false;
		}

		if (isProcessTracking()) {
			return false;
		}

		if (task.getDelegationState() != null) {
			return false;
		}

		if (task.getAssignee() != null) {

			return true;

		} else {
			if (task.isAutoClaim()) {
				return true;
			} else {
				return false;
			}
		}
	}

}
