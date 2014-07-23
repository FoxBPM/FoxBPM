package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

public class BoundaryTimeSignalCmd implements Command<Void> {

	protected String tokenId;
	protected String eventNodeId;
	protected boolean isCancelActivity=true;
	protected Map<String, Object> transientVariables;
	protected Map<String, Object> persistenceVariables;
	

	public BoundaryTimeSignalCmd(String tokenId, String eventNodeId,boolean isCancelActivity, Map<String, Object> transientVariables,
			Map<String, Object> persistenceVariables) {
		
		this.tokenId=tokenId;
		this.eventNodeId=eventNodeId;
		this.isCancelActivity=isCancelActivity;
		this.transientVariables=transientVariables;
		this.persistenceVariables=persistenceVariables;

	}

	@Override
	public Void execute(CommandContext commandContext) {
		
		TokenEntity mToken = commandContext.getTokenManager().findTokenById(tokenId);
		
		KernelFlowNodeImpl flowNode=mToken.getFlowNode();
		
		if(isCancelActivity){
			/** 中断边界事件 */
		}else{
			/** 非中断边界事件 */
		}
		
		
		
		return null;
	}

}
