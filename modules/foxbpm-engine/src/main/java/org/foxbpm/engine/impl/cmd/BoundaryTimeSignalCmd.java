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
 * @author MAENLIANG
 */
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
