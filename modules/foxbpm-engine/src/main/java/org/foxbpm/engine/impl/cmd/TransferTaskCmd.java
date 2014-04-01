/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
package org.foxbpm.engine.impl.cmd;

import java.util.Map;


import org.foxbpm.engine.exception.FixFlowException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.TaskCommandInst;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.command.TransferTaskCommand;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.ProcessDefinitionManager;
import org.foxbpm.engine.impl.task.TaskInstanceEntity;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;

public class TransferTaskCmd implements Command<Void> {

	/**
	 * 任务编号
	 */
	protected String taskId;

	/**
	 * 用户命令编号
	 */
	protected String userCommandId;

	/**
	 * 瞬态流程实例变量Map
	 */
	protected Map<String, Object> transientVariables = null;

	/**
	 * 持久化流程实例变量Map
	 */
	protected Map<String, Object> variables = null;

	/**
	 * 任务意见
	 */
	protected String taskComment;

	/**
	 * 转发的用户编号
	 */
	protected String transferUserId;

	protected TransferTaskCommand transferTaskCommand;

	public TransferTaskCmd(TransferTaskCommand transferTaskCommand) {
		String taskId = transferTaskCommand.getTaskId();
		String userCommandId = transferTaskCommand.getUserCommandId();
		Map<String, Object> transientVariables = transferTaskCommand.getTransientVariables();
		Map<String, Object> variables = transferTaskCommand.getVariables();
		String taskComment = transferTaskCommand.getTaskComment();
		String transferUserId = transferTaskCommand.getTransferUserId();

		this.taskId = taskId;
		this.userCommandId = userCommandId;
		this.transientVariables = transientVariables;
		this.variables = variables;
		this.taskComment = taskComment;
		this.transferUserId = transferUserId;
		this.transferTaskCommand = transferTaskCommand;
	}

	public Void execute(CommandContext commandContext) {

		if (taskId == null) {
			throw new FixFlowException("任务编号为空！");
		}

		TaskInstanceEntity taskInstance = Context.getCommandContext().getTaskManager().findTaskById(taskId);

		if (taskInstance == null) {
			throw new FixFlowException("无法找到编号为: " + taskId + " 的任务!");
		}
		if (transferUserId != null) {
			if (taskInstance.getAssignee() == null) {

				throw new FixFlowException("任务 " + taskId + " 无代理人!");

			} else {

				String nodeId = taskInstance.getNodeId();
				String processDefinitionId = taskInstance.getProcessDefinitionId();

				ProcessDefinitionManager processDefinitionManager = commandContext.getProcessDefinitionManager();

				ProcessDefinitionBehavior processDefinition = processDefinitionManager.findLatestProcessDefinitionById(processDefinitionId);

				UserTaskBehavior userTask = (UserTaskBehavior) processDefinition.getDefinitions().getElement(nodeId);

				TaskCommandInst taskCommand = userTask.getTaskCommandsMap().get(userCommandId);

				taskInstance.setEndTime(ClockUtil.getCurrentTime());
				taskInstance.setCommandId(taskCommand.getId());
				taskInstance.setCommandType(StringUtil.getString(taskCommand.getTaskCommandType()));
				taskInstance.setCommandMessage(taskCommand.getName());
				taskInstance.setTaskComment(taskComment);

				Context.getCommandContext().getTaskManager().saveTaskInstanceEntity(taskInstance);

				taskInstance.setId(GuidUtil.CreateGuid());
				taskInstance.setAssignee(transferUserId);
				taskInstance.setCreateTime(ClockUtil.getCurrentTime());
				taskInstance.setEndTime(null);
				taskInstance.setCommandType(null);
				taskInstance.setCommandMessage(null);
				taskInstance.setTaskComment(null);
				Context.getCommandContext().getTaskManager().saveTaskInstanceEntity(taskInstance);
			}
		} else {
			throw new FixFlowException("转发人不能为空!");
		}

		return null;

	}

}
