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
package org.foxbpm.engine.impl.task.filter;

import org.foxbpm.engine.task.DelegationState;
import org.foxbpm.engine.task.Task;

public class CompleteGeneralTaskFilter extends AbstractCommandFilter {

	 
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
//			return false;
			// 如果是转办状态的任务，则可以进行任务提交（ThinkGem 2015-6-12）
			return DelegationState.RESOLVED.equals(task.getDelegationState());
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
