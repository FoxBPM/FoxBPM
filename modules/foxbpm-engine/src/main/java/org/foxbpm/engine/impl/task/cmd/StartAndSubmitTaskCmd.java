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

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.cmd.StartProcessInstanceCmd;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.task.command.StartAndSubmitTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class StartAndSubmitTaskCmd extends AbstractExpandTaskCmd<StartAndSubmitTaskCommand, ProcessInstance> {

	private static final long serialVersionUID = 1L;

	public StartAndSubmitTaskCmd(StartAndSubmitTaskCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}

	@Override
	public ProcessInstance execute(CommandContext commandContext) {
		TaskEntity task = Context.getCommandContext().getTaskManager().findTaskById(taskId);

		if (task != null && task.isSuspended()) {
			throw new FoxBPMException("task is suspended");
		}

		return execute(commandContext, task);
	}

	@Override
	protected ProcessInstance execute(CommandContext commandContext, TaskEntity task) {
		ProcessInstanceEntity processInstance = null;
		if (task == null) {

			/** 没有任务的时候需要新启动一个流程,然后完成他的第一个任务. */

			/** 发起流程,传入processKey,按照最新流程启动。 */
			processInstance = (ProcessInstanceEntity) getCommandExecutor().execute(
					new StartProcessInstanceCmd<ProcessInstance>(processDefinitionKey, null, businessKey, transientVariables,
							persistenceVariables));
			
			
			
			// 获取流程内容执行器
			FlowNodeExecutionContext executionContext = processInstance.getRootToken();
			// 获取任务命令
			TaskDefinition taskDefinition=((ProcessDefinitionEntity)processInstance.getProcessDefinition()).getSubTaskDefinition();
			if(taskDefinition!=null){
				TaskCommand taskCommand = taskDefinition.getTaskCommand(taskCommandId);
				// 任务命令的执行表达式变量
				taskCommand.getExpressionValue(executionContext);
			}else{
				throw new FoxBPMException("流程没有找到提交节点,请重新检查流程定义。");
			}

		

			List<TaskEntity> submitTasks = processInstance.getTasks();

			for (TaskEntity submitTask : submitTasks) {

				ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
				expandTaskCommand.setCommandType("submit");
				expandTaskCommand.setTaskComment(this.taskComment);
				expandTaskCommand.setTaskId(submitTask.getId());

				List<TaskCommand> taskCommands = submitTask.getTaskDefinition().getTaskCommands("submit");
				if (taskCommands.size() > 0) {
					expandTaskCommand.setTaskCommandId(taskCommands.get(0).getId());
				} else {
					throw new FoxBPMException("发起节点必须包含一个提交命令");
					// expandTaskCommand.setTaskCommandId(this.taskCommandId);
				}

				expandTaskCommand.setTransientVariables(transientVariables);
				expandTaskCommand.setBusinessKey(businessKey);
				expandTaskCommand.setInitiator(initiator);

				if (this.agent != null && !this.agent.equals("")) {
					expandTaskCommand.setAgent(this.agent);
				}

				getCommandExecutor().execute(new ExpandTaskCompleteCmd<ProcessInstance>(expandTaskCommand));

			}

		} else {

			/** 流程引擎启动了之后点击启动并提交按钮 */
			throw new FoxBPMBizException("启动并提交按钮不能在已经启动的流程上被执行");
		}

		return processInstance;
	}

}
