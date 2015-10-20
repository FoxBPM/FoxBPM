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
import org.foxbpm.engine.impl.task.command.RollBackAssigneeCommand;
import org.foxbpm.engine.impl.task.command.RollBackAutoAssigneeCommand;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * 
 * @author kenshin
 */
public class RollBackAutoAssigneeCmd extends AbstractExpandTaskCmd<RollBackAutoAssigneeCommand, Void> {

	private static final long serialVersionUID = 1L;

	/** 退回的节点号 */
	protected String rollBackNodeId;
	
	/** 退回节点的指定处理者 */
	protected String rollBackAssignee;

	public RollBackAutoAssigneeCmd(RollBackAutoAssigneeCommand rollBackAutoAssigneeCommand) {
		super(rollBackAutoAssigneeCommand);
		this.rollBackNodeId = rollBackAutoAssigneeCommand.getRollBackNodeId();
	}

	 
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		/** 获取任务命令 */
		TaskCommand taskCommand = getTaskCommand(task);
		if(taskCommand != null) {
			/** 获取流程内容执行器 */
			FlowNodeExecutionContext executionContext = getExecutionContext(task);
			/** 任务命令的执行表达式变量 */
			taskCommand.getExpressionValue(executionContext);
			/** 设置任务的处理命令 commandId commandName commandType */
			task.setTaskCommand(taskCommand);		
		}
		
		
		/** 设置任务处理者 */
		task.setAssignee(Authentication.getAuthenticatedUserId());
		/** 处理意见 */
		task.setTaskComment(taskComment);
		/** 获取流程定义 */
		KernelProcessDefinition processDefinition=getProcessDefinition(task);
		/** 查找需要退回的节点 */
		KernelFlowNodeImpl flowNode=processDefinition.findFlowNode(rollBackNodeId);
		
		if(flowNode == null){
			throw ExceptionUtil.getException("10502004",rollBackNodeId);
		}
		if(isMutilInstance(flowNode)){
			throw ExceptionUtil.getException("10501004",rollBackNodeId);
		}
		task.setCommandType("rollback");
		/** 完成任务,并将流程推向指定的节点,并指定处理者 */
		
		TaskEntity lastEndTask=commandContext.getTaskManager().findLastEndTaskByProcessInstanceIdAndNodeId(task.getProcessInstanceId(), rollBackNodeId);
		task.complete(flowNode,lastEndTask.getAssignee());
		
		return null;

		
	}

}
