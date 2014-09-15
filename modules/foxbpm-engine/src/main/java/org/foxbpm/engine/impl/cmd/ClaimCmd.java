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
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.ClockUtil;

public class ClaimCmd extends NeedsActiveTaskCmd<Void> {

	private static final long serialVersionUID = 1L;
	private String userId;
	public ClaimCmd(String taskId,String userId) {
		super(taskId);
		this.userId = userId;
	}
	
	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		if(userId == null){
			throw new FoxBPMIllegalArgumentException("userId is null");
		}
		if (task.getAssignee() != null) {
			throw new FoxBPMBizException("任务 " + taskId + " 已经被领取，不能重复领取");
		} else {
			task.setAssignee(userId);
			task.setClaimTime(ClockUtil.getCurrentTime());
		}
		return null;
	}
}
