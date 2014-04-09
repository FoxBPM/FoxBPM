package org.foxbpm.kernel.test.behavior;

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
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
		
		KernelFlowNode flowNode=executionContext.getFlowNode();
		
		if(flowNode.getOutgoingSequenceFlows().size()>0){
			executionContext.take(flowNode.getOutgoingSequenceFlows().get(0));
		}
		
		
	}

	public void cleanData(FlowNodeExecutionContext executionContext) {
		LOG.debug("清理节点: "+executionContext.getFlowNode().getId());
	}

}
