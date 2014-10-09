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
import org.foxbpm.engine.impl.task.command.SuspendProcessInstanceCommand;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class SuspendProcessInstanceCmd extends AbstractExpandTaskCmd<SuspendProcessInstanceCommand, Void> {


	private static final long serialVersionUID = 1L;
	
	public SuspendProcessInstanceCmd(SuspendProcessInstanceCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}


	 
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		/** 获取任务命令 */
		TaskCommand taskCommand = getTaskCommand(task);
		/** 获取流程内容执行器 */
		FlowNodeExecutionContext executionContext = getExecutionContext(task);
		/** 任务命令的执行表达式变量 */
		taskCommand.getExpressionValue(executionContext);
		/** 设置任务处理者 */
		task.setAssignee(Authentication.getAuthenticatedUserId());
		/** 暂停流程实例 */
		task.getProcessInstance().suspendInstance();
		
		return null;
	}

}
