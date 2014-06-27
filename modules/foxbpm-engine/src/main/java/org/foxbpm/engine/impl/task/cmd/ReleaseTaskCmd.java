package org.foxbpm.engine.impl.task.cmd;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.command.ReleaseTaskCommand;

public class ReleaseTaskCmd extends AbstractExpandTaskCmd<ReleaseTaskCommand, Void>{

	private static final long serialVersionUID = 1L;

	public ReleaseTaskCmd(ReleaseTaskCommand abstractCustomExpandTaskCommand) {
		super(abstractCustomExpandTaskCommand);
	}

	@Override
	protected Void execute(CommandContext commandContext, TaskEntity task) {
		
		if (task.getAssignee() != null) {
			if (!task.getAssignee().equals(Authentication.getAuthenticatedUserId())) {
				// 当任务已经被另一个不是自己的用户占有，则抛出异常。
				throw new FoxBPMException("任务 " + taskId + " 已经被另一个用户领取!您不能做还回操作!");
			}
			else{
				
				if(task.getIdentityLinks().size()>0){
					task.setAssignee(null);
					task.setClaimTime(null);
					
				}
				else{
					throw new FoxBPMException("任务 " + taskId + " 没有候选处理者不能被释放!");
				}
				
				
				
			}
		} else {
			throw new FoxBPMException("任务 " + taskId + " 没有被领取,所以不能做还回操作!");

		}
		
		return null;
	}

}
