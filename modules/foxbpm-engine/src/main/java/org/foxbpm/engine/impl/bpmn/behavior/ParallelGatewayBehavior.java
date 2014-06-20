package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.List;

import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.KernelToken;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParallelGatewayBehavior extends GatewayBehavior {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LoggerFactory.getLogger(ParallelGatewayBehavior.class);
	
	protected String convergType="flowNum";//tokenNum

	public String getConvergType() {
		return convergType;
	}

	public void setConvergType(String convergType) {
		this.convergType = convergType;
	}



	public void execute(FlowNodeExecutionContext executionContext) {
		
		/** 获取当前的pvm节点 */
		KernelFlowNode flowNode=executionContext.getFlowNode();
		/** 获取pvm节点的进入线条 */
		List<KernelSequenceFlow> incomingSequenceFlows = flowNode.getIncomingSequenceFlows();
		/** 获取pvm节点的输出线条 */
		//List<KernelSequenceFlow> outgoingSequenceFlows = flowNode.getOutgoingSequenceFlows();
		

		if (this.convergType.equals("tokenNum")) {
			
			
			KernelTokenImpl token = (KernelTokenImpl) executionContext;

			KernelTokenImpl parentToken = token.getParent();
			// 判断是否有父令牌
			if (parentToken != null) {

				// 判断令牌是否需要重新激活
				if (token.isActive()) {

					token.setActive(false);

					// 当子令牌都处于非激活状态才会驱动父令牌向下  ！！这里可能会出现一种情况有问题,
					boolean reactivateParent = !parentToken.hasActiveChildren();

					// 判断是否需要把父令牌移动到下一个节点
					if (reactivateParent) {

						List<KernelTokenImpl> cTokens = parentToken.getChildren();
						for (KernelTokenImpl cToken : cTokens) {
							cToken.end(false);
						}

						parentToken.setFlowNode((KernelFlowNodeImpl) executionContext.getFlowNode());
						parentToken.leave();
					}

				}

			} else {
				// 没有父令牌则直接离开
				executionContext.signal();
			}
			
			
			
		} else {
			
			KernelTokenImpl token = (KernelTokenImpl) executionContext;

			KernelTokenImpl parentToken = token.getParent();
			// 判断是否有父令牌
			if (parentToken != null) {

				// 判断令牌是否需要重新激活
				if (token.isActive()) {

					token.setActive(false);

					  List<KernelToken> joinedExecutions = executionContext.findInactiveToken(flowNode);
					    int nbrOfExecutionsToJoin = incomingSequenceFlows.size();
					    int nbrOfExecutionsJoined = joinedExecutions.size();

					    if (nbrOfExecutionsJoined==nbrOfExecutionsToJoin) {
					        
					        // Fork
					        if(LOG.isDebugEnabled()) {
					        	LOG.debug("parallel gateway '{}' activates: {} of {} joined", flowNode.getId(), nbrOfExecutionsJoined, nbrOfExecutionsToJoin);
					        }
					        
					    	List<KernelTokenImpl> cTokens = parentToken.getChildren();
							for (KernelTokenImpl cToken : cTokens) {
								cToken.end(false);
							}

							parentToken.setFlowNode((KernelFlowNodeImpl) executionContext.getFlowNode());
							parentToken.signal();
					        //execution.takeAll(outgoingTransitions, joinedExecutions);
					        
					      } else if (LOG.isDebugEnabled()){
					    	  LOG.debug("parallel gateway '{}' does not activate: {} of {} joined", flowNode.getId(), nbrOfExecutionsJoined, nbrOfExecutionsToJoin);
					      }

				}

			} else {
				// 没有父令牌则直接离开
				executionContext.signal();
			}
		}
	}
	

	public void leave(FlowNodeExecutionContext executionContext) {
		((KernelTokenImpl)executionContext).leave(false);
	}

}
