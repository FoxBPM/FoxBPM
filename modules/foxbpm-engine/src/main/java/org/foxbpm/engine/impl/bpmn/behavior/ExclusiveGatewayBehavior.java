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

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排他网关
 * @author kenshin
 *
 */
public class ExclusiveGatewayBehavior extends GatewayBehavior {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LoggerFactory.getLogger(ExclusiveGatewayBehavior.class);
	
	public void execute(FlowNodeExecutionContext executionContext) {
		executionContext.signal();
	}
	
	public void leave(FlowNodeExecutionContext executionContext) {
		
		
		List<KernelSequenceFlow> outgoingSequenceFlows = executionContext.getFlowNode().getOutgoingSequenceFlows();
		
		
		List<KernelSequenceFlow> sequenceFlowList = new ArrayList<KernelSequenceFlow>();

		for (KernelSequenceFlow sequenceFlow : outgoingSequenceFlows) {
			
			if(sequenceFlow.isContinue(executionContext))
			{
				sequenceFlowList.add(sequenceFlow);
			}

		}
		// 节点后面没有线的处理
		if (sequenceFlowList.size()==0) {
			if(outgoingSequenceFlows.size()==0){
				throw new FoxBPMException(this.getName()+"("+this.getId()+") 节点后面没有处理线条！");
			}
			else{
				throw new FoxBPMException(this.getName()+"("+this.getId()+") 节点后面的条件都不满足导致节点后面没有处理线条,请检查后续线条条件！");
			}
			
			
		}

		// 节点后面就一条线的处理
		if (sequenceFlowList.size() == 1) {
			((KernelTokenImpl) executionContext).leave(sequenceFlowList.get(0));
			return;
		}
		// 节点后面大于一条线的处理
		if (sequenceFlowList.size() > 1) {
			
			LOG.info("唯一网关后面的链接线条满足条件的大于一条,默认选择第一条满足的线条. 默认离开线条为: 编号:"+sequenceFlowList.get(0).getId()+"  名称:"+sequenceFlowList.get(0).getName());
			((KernelTokenImpl) executionContext).leave(sequenceFlowList.get(0));
		}

	}

}
