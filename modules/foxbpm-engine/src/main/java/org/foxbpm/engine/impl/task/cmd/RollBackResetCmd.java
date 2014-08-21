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

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.RollBackResetCommand;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * 退回到指定节点,任务重新分配。
 * @author kenshin
 */
public class RollBackResetCmd extends AbstractExpandTaskCmd<RollBackResetCommand, Void> {

	private static final long serialVersionUID = 1L;

	/** 退回的节点号 */
	protected String rollBackNodeId;

	public RollBackResetCmd(RollBackResetCommand rollBackResetCommand) {
		super(rollBackResetCommand);
		this.rollBackNodeId = rollBackResetCommand.getRollBackNodeId();
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
		/** 获取流程定义 */
		KernelProcessDefinition processDefinition=getProcessDefinition(task);
		/** 查找需要退回的节点 */
		KernelFlowNodeImpl flowNode=processDefinition.findFlowNode(rollBackNodeId);
		if(flowNode == null){
			throw new FoxBPMIllegalArgumentException("退回的目的节点："+rollBackNodeId+"不存在,请检查流程配置！");
		}
		/** 完成任务,并将流程推向指定的节点 */
		task.complete(flowNode);
		
		return null;
	}

}
