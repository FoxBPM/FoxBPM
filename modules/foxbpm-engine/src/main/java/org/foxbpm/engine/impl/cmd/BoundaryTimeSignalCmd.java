package org.foxbpm.engine.impl.cmd;

import java.util.Map;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

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
		
		FlowNodeExecutionContext executionContext = commandContext.getTokenManager().findTokenById(tokenId);
		
		KernelProcessDefinitionImpl processDefinition = executionContext.getProcessDefinition();
		
		KernelFlowNodeImpl eventNode = processDefinition.findFlowNode(eventNodeId);
		
		
		if(isCancelActivity){
			/** 中断边界事件 */
			
			/** 终止所有的子令牌，并执行节点清理事件 */
			executionContext.terminationChildToken();
			/** 将主令牌从指定节点驱动向下 */
			executionContext.signal(eventNode);
			
		}else{
			/** 非中断边界事件 */
			
			/** 创建一个子令牌 */
			FlowNodeExecutionContext childrenExecutionContext=executionContext.createChildrenToken();
			/** 驱动子令牌从事件节点向下 */
			childrenExecutionContext.signal(eventNode);
			
		}
		
		
		
		return null;
	}

}
