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
package org.foxbpm.engine.impl.task.cmd;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.ClaimTaskCommand;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.impl.util.StringUtil;

public class ClaimTaskCmd extends AbstractExpandTaskCmd<ClaimTaskCommand, Void> {

	private static final long serialVersionUID = 1L;

	public ClaimTaskCmd(ClaimTaskCommand claimTaskCommand) {
		super(claimTaskCommand);
	}

	 
	protected Void execute(CommandContext commandContext, TaskEntity task) {

		if (StringUtil.isNotEmpty(agent)) {

			if (task.getAssignee() != null) {
				if (!task.getAssignee().equals(this.agent)) {
					// 当任务已经被另一个不是自己的用户占有，则抛出异常。
					throw ExceptionUtil.getException("10503003",taskId);
				}
			} else {

				task.setAssignee(this.agent);
				task.setClaimTime(ClockUtil.getCurrentTime());

			}

		} else {

			if (task.getAssignee() != null) {
				if (!task.getAssignee().equals(Authentication.getAuthenticatedUserId())) {
					// 当任务已经被另一个不是自己的用户占有，则抛出异常。
					throw ExceptionUtil.getException("10503003",taskId);
				}
			} else {

				task.setAssignee(Authentication.getAuthenticatedUserId());
				task.setClaimTime(ClockUtil.getCurrentTime());

			}

		}

		return null;
	}
}
