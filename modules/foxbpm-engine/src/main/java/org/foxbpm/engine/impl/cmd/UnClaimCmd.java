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
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class UnClaimCmd extends NeedsActiveTaskCmd<Void> {

	private static final long serialVersionUID = 1L;
	public UnClaimCmd(String taskId) {
		super(taskId);
	}
	
	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		if (task.getAssignee() != null) {
			if(task.getIdentityLinks().size()>0){
				task.setAssignee(null);
				task.setClaimTime(null);
			}else{
				throw new FoxBPMBizException("任务 " + taskId + " 没有候选处理者不能被释放!");
			}
		} else {
			throw new FoxBPMBizException("任务 " + taskId + " 没有被领取,所以不能做释放操作!");
		}
		return null;
	}
}
