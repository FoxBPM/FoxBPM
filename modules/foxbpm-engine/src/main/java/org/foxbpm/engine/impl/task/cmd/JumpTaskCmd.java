/**
 * 
 */
package org.foxbpm.engine.impl.task.cmd;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.JumpTaskCommand;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

/**
 * @author kenshin
 *
 */
public class JumpTaskCmd extends AbstractExpandTaskCmd<JumpTaskCommand, Void> {

	
	private static final long serialVersionUID = 1L;

	/** 跳转的节点号 */
	protected String jumpNodeId;

	public JumpTaskCmd(JumpTaskCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
		this.jumpNodeId=abstractCustomExpandTaskCommand.getJumpNodeId();
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
		KernelFlowNodeImpl flowNode=processDefinition.findFlowNode(jumpNodeId);
		/** 完成任务,并将流程推向指定的节点 */
		task.complete(flowNode);
		
		return null;
	}


}
