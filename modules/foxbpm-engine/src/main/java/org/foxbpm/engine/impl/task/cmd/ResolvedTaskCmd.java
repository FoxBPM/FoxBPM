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

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.ResolvedTaskCommand;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.task.DelegationState;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class ResolvedTaskCmd  extends AbstractExpandTaskCmd<ResolvedTaskCommand, Void> {

	private static final long serialVersionUID = 1L;
	
	public ResolvedTaskCmd(ResolvedTaskCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		/** 获取任务命令 */
		TaskCommand taskCommand = getTaskCommand(task);
		/** 获取流程内容执行器 */
		FlowNodeExecutionContext executionContext = getExecutionContext(task);
		/** 任务命令的执行表达式变量 */
		taskCommand.getExpressionValue(executionContext);
		/** 设置任务处理者 */
		task.setAssignee(Authentication.getAuthenticatedUserId());
		/** 设置任务的处理命令 commandId commandName commandType */
		task.setTaskCommand(taskCommand);
		/** 处理意见 */
		task.setTaskComment(taskComment);
		/** 设置为还回状态 */
		task.setDelegationState(DelegationState.RESOLVED);
		/** 结束任务,但是并不驱动流程向下。 */
		task.end(taskCommand, taskComment);
		
		/** 创建新任务 */
		cloneAndInsertTask(task);
		
		return null;
	}
	
	private void cloneAndInsertTask(TaskEntity task) {

		/** 这里可以采用浅克隆的方式克隆出一个单简单字段的任务,也可以自己重新创建任务,但是需要赋值令牌、流程实例、定义等信息 */

		/** 克隆一个任务 */
		TaskEntity newTask = (TaskEntity) task.clone();
		/** 设置任务的原始拥有者,以便在还回的时候找到原始处理者 */
		
		String pendingUserId=task.getOwner();
		
		/** 重置任务非实例属性 */
		newTask.resetProperties();
		/** 重置任务字段 */
		newTask.setId(GuidUtil.CreateGuid());
		/** 设置新的处理者为被转发人 */
		newTask.setAssignee(pendingUserId);
		/** 重置任务创建时间 */
		newTask.setCreateTime(ClockUtil.getCurrentTime());
		/** 设置转办状态为null */
		newTask.setDelegationState(null);
		/** 插入任务 */
		Context.getCommandContext().getTaskManager().insert(newTask);
		
	}


}
