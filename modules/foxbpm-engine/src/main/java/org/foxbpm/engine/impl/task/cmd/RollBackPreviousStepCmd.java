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

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.RollBackPreviousStepCommand;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * @author kenshin
 * 
 */
public class RollBackPreviousStepCmd extends AbstractExpandTaskCmd<RollBackPreviousStepCommand, Void> {

	private static final long serialVersionUID = 1L;

	public RollBackPreviousStepCmd(RollBackPreviousStepCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {

		/** 放置流程实例级别的瞬态变量 */
		task.setProcessInstanceTransientVariables(this.transientVariables);
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
		
		/** 获取当前令牌所有父亲令牌 */
		List<KernelTokenImpl> allParent = executionContext.getAllParent();
		List<String> tokenIds=new ArrayList<String>();
		/** 创建出父令牌ID集合 */
		for (KernelTokenImpl tokenParent : allParent) {
			tokenIds.add(tokenParent.getId());
		}
		/** 将自己本身也添加进去 */
		tokenIds.add(executionContext.getId());
		
		/** 查询相关联的完成的任务 */
		List<TaskEntity> tasks=commandContext.getTaskManager().findEndTasksByTokenIds(tokenIds);
		
		
		if(tasks!=null&&tasks.size()>0){
			//未完成
		}
		
		/** 获取流程定义 */
		KernelProcessDefinition processDefinition=getProcessDefinition(task);
		/** 查找需要退回的节点 */
		KernelFlowNodeImpl flowNode=processDefinition.findFlowNode(null);
		/** 完成任务,并将流程推向指定的节点 */
		task.complete(flowNode);
		

		return null;
	}

}
