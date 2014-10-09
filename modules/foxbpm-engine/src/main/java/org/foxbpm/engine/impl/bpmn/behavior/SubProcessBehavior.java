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
package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.bpmn.parser.model.BpmnParser;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class SubProcessBehavior extends ActivityBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 
	public void execute(FlowNodeExecutionContext executionContext) {
		
		TokenEntity token=(TokenEntity) executionContext;
		
		KernelFlowNodeImpl flowNode =executionContext.getFlowNode();
		
		KernelFlowNodeImpl initialFlowNode = (KernelFlowNodeImpl) flowNode.getProperty(BpmnParser.INITIAL);
	    
	    if (initialFlowNode == null) {
	      throw new FoxBPMException("内部子流程("+flowNode.getId()+")里没有找到启动节点");
	    }
	    
	    /** 令牌执行到内部子流程会先创建一个子令牌将这个子令牌进入子流程运行，自己则停在子流程上。 */
	    TokenEntity nodeToken = (TokenEntity)token.createForkedToken(token, flowNode.getId()).token;
	    
	    /** 设置当前令牌为子流程根令牌,子流程根令牌在结束的时候会去驱动父亲令牌向下。 */
	    nodeToken.setSubProcessRootToken(true);
	    
	    nodeToken.enter(initialFlowNode);

	}
	
	

}
