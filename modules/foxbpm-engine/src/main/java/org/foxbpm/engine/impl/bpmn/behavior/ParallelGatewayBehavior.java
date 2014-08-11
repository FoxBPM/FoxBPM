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

import java.util.List;

import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.KernelToken;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * ParallelGatewayBehavior
 * 
 * MAENLIANG 2014年8月1日 下午3:56:56 重构
 * kenshin 2014年8月11日 下午2:13:56 重构
 * 
 * @version 1.0.0
 * 
 */
public class ParallelGatewayBehavior extends GatewayBehavior {
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(ParallelGatewayBehavior.class);
	/**
	 * 令牌数量 合并方式
	 */
	private static final String CONVERGTYPE_TOKENNUM = "tokenNum";
	/**
	 * 线条数量 合并方式
	 */
	protected String convergType = "flowNum";// tokenNum

	public void execute(FlowNodeExecutionContext executionContext) {
		/** 获取当前的pvm节点 */
		KernelFlowNode flowNode = executionContext.getFlowNode();
		/** 获取pvm节点的进入线条 */
		List<KernelSequenceFlow> incomingSequenceFlows = flowNode.getIncomingSequenceFlows();
		/** 获取pvm节点的输出线条 */

		KernelTokenImpl token = (KernelTokenImpl) executionContext;
		KernelTokenImpl parentToken = token.getParent();
		// 判断是否有父令牌
		if (parentToken != null) {
			// 判断令牌是否需要重新激活
			if (token.isActive()) {
				token.setActive(false);
				if (this.convergType.equals(CONVERGTYPE_TOKENNUM)) {
					/** 按照令牌数量合并方式 */
					// 当子令牌都处于非激活状态才会驱动父令牌向下 ！！这里可能会出现一种情况有问题,
					boolean reactivateParent = !parentToken.hasActiveChildren();
					// 判断是否需要把父令牌移动到下一个节点
					if (reactivateParent) {
						this.signalParentToken(parentToken, token.getFlowNode());
					}
				} else {
					/** 按照进入线的数量合并方式 */
					List<KernelTokenImpl> joinedExecutions = executionContext.findInactiveToken(flowNode);
					int nbrOfExecutionsToJoin = incomingSequenceFlows.size();
					int nbrOfExecutionsJoined = joinedExecutions.size();
					if (nbrOfExecutionsJoined == nbrOfExecutionsToJoin) {
						// Fork
						if (LOG.isDebugEnabled()) {
							LOG.debug("并行网关 '{}' activates: {} of {} joined", flowNode.getId(), nbrOfExecutionsJoined,
									nbrOfExecutionsToJoin);
						}

						this.signalParentToken(parentToken, token.getFlowNode());
					} else if (LOG.isDebugEnabled()) {
						LOG.debug("并行网关 '{}' does not activate: {} of {} joined", flowNode.getId(), nbrOfExecutionsJoined,
								nbrOfExecutionsToJoin);
					}
				}

			}

		} else {
			// 没有父令牌则直接离开
			executionContext.signal();
		}

	}

	public void leave(FlowNodeExecutionContext executionContext) {
		List<KernelSequenceFlow> outgoingSequenceFlows = executionContext.getFlowNode().getOutgoingSequenceFlows();
		// 并行网关 直接忽略所有线条条件产生并发
		((KernelTokenImpl) executionContext).leave(outgoingSequenceFlows);
	}

	/**
	 * 
	 * 驱动令牌
	 * 
	 * @param token
	 *            父令牌
	 * @param flowNode
	 *            推向的节点 void
	 * @exception
	 * @since 1.0.0
	 */
	private void signalParentToken(KernelTokenImpl parentToken, KernelFlowNodeImpl flowNode) {
		for (KernelToken cToken : parentToken.getChildren()) {
			((KernelTokenImpl) cToken).end();
		}
		parentToken.setFlowNode(flowNode);
		parentToken.signal();
	}

	public String getConvergType() {
		return convergType;
	}

	public void setConvergType(String convergType) {
		this.convergType = convergType;
	}

}
