/**
 * 
 */
package org.foxbpm.engine.impl.task.filter;

import org.foxbpm.engine.task.Task;

/**
 * @author kenshin
 *
 */
public class PendingTaskFilter extends AbstractCommandFilter {

	@Override
	public boolean accept(Task task) {

		if (task == null) {
			return false;
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
		}

		if (isAutoClaim(task)) {
			return true;
		} else {
			return false;
		}

	}

}
