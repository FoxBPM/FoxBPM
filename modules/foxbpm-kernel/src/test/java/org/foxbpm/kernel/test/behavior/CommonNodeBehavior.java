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
package org.foxbpm.kernel.test.behavior;

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonNodeBehavior implements KernelFlowNodeBehavior {

	protected static Logger LOG = LoggerFactory.getLogger(CommonNodeBehavior.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void enter(FlowNodeExecutionContext executionContext) {
		LOG.debug("进入节点: "+executionContext.getFlowNode().getId());
		executionContext.execute();
	}

	public void execute(FlowNodeExecutionContext executionContext) {
		
	}

	public void leave(FlowNodeExecutionContext executionContext) {
		LOG.debug("离开节点: "+executionContext.getFlowNode().getId());
		
		((KernelTokenImpl)executionContext).leave();
		
//		KernelFlowNode flowNode=executionContext.getFlowNode();
//		if(flowNode.getOutgoingSequenceFlows().size()>0){
//			executionContext.take(flowNode.getOutgoingSequenceFlows().get(0));
//		}
		
		
	}

	public void cleanData(FlowNodeExecutionContext executionContext) {
		LOG.debug("清理节点: "+executionContext.getFlowNode().getId());
	}

	@Override
	public void setKernelFlowNode(KernelFlowNodeImpl KernelFlowNode) {
		// TODO Auto-generated method stub
		
	}

}
