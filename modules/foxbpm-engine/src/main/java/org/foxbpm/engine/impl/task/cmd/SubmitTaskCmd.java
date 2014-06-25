/**
 * 
 */
package org.foxbpm.engine.impl.task.cmd;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.SubmitTaskCommand;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class SubmitTaskCmd extends AbstractExpandTaskCmd<SubmitTaskCommand, Void>  {

	private static final long serialVersionUID = 1L;

	public SubmitTaskCmd(SubmitTaskCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		

		

		// 放置流程实例级别的瞬态变量
		task.setProcessInstanceTransientVariables(this.transientVariables);
		// 获取任务命令
		TaskCommand taskCommand = getTaskCommand(task);
		// 获取流程内容执行器
		FlowNodeExecutionContext executionContext = getExecutionContext(task);
		// 任务命令的执行表达式变量
		taskCommand.getExpressionValue(executionContext);
		// 设置任务处理者
		task.setAssignee(Authentication.getAuthenticatedUserId());
		// 设置任务上点击的处理命令
		task.setCommandId(taskCommand.getId());
		// 设置任务上点击的处理命令类型
		task.setCommandType(taskCommand.getTaskCommandType());
		// 设置任务上点击的处理命令文本
		task.setCommandMessage(taskCommand.getName());
		// 处理意见
		task.setTaskComment(taskComment);
		// 设置流程提交人
		task.setProcessInitiator(this.initiator);
		
		ProcessInstanceEntity processInstance = commandContext.getProcessInstanceManager().findProcessInstanceById(task.getProcessInstanceId());

		if (businessKey != null) {
			processInstance.setBizKey(this.businessKey);
		}

		if (initiator != null) {
			processInstance.setInitiator(this.initiator);
		}

		task.complete();

		
		return null;
	}

}
