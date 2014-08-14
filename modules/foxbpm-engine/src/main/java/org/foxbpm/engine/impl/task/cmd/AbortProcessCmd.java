/**
 * 
 */
package org.foxbpm.engine.impl.task.cmd;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.AbortProcessCommand;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class AbortProcessCmd extends AbstractExpandTaskCmd<AbortProcessCommand, Void> {
	
	private static final long serialVersionUID = 1L;

	public AbortProcessCmd(AbortProcessCommand abstractCustomExpandTaskCommand) {
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
		/** 处理意见 */
		task.end(taskCommand, taskComment);
		
		
		/** 终止流程实例 */
		task.getProcessInstance().abort();
		
		return null;
	}

	
}
