package org.foxbpm.kernel.test.behavior;

import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.KernelExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonNodeBehavior implements KernelFlowNodeBehavior {

	protected static Logger LOG = LoggerFactory.getLogger(CommonNodeBehavior.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void enter(KernelExecutionContext executionContext) {
		LOG.debug("进入节点: "+executionContext.getToken().getFlowNode().getId());
		executionContext.getToken().execute(executionContext);
	}

	public void execute(KernelExecutionContext executionContext) {
		LOG.debug("执行节点: "+executionContext.getToken().getFlowNode().getId());
		executionContext.getToken().signal(executionContext);
	}

	public void leave(KernelExecutionContext executionContext) {
		LOG.debug("离开节点: "+executionContext.getToken().getFlowNode().getId());
		
		KernelFlowNodeImpl flowNode=executionContext.getToken().getFlowNode();
		
		if(flowNode.getOutgoingSequenceFlows().size()>0){
			executionContext.getToken().take(executionContext,flowNode.getOutgoingSequenceFlows().get(0));
		}
		
		
	}

	public void cleanData(KernelExecutionContext executionContext) {
		LOG.debug("清理节点: "+executionContext.getToken().getFlowNode().getId());
	}

}
