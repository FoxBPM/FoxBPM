package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.List;

import org.foxbpm.kernel.process.KernelException;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class ParallelGatewayBehavior extends GatewayBehavior {

	protected String gatewayDirection;

	public String getGatewayDirection() {
		return gatewayDirection;
	}

	public void setGatewayDirection(String gatewayDirection) {
		this.gatewayDirection = gatewayDirection;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(FlowNodeExecutionContext executionContext) {
		if (this.gatewayDirection == null) {
			throw new KernelException("并行网关节点没有选择 GatewayDirection 类型,请检查流程定义文件!");
		}

		if (this.gatewayDirection.equals("Converging")) {
			parallelGatewayConverging(executionContext);
		} else {
			parallelGatewayDiverging(executionContext);

		}
	}

	private void parallelGatewayDiverging(FlowNodeExecutionContext executionContext) {
		executionContext.signal();
	}

	private void parallelGatewayConverging(FlowNodeExecutionContext executionContext) {

		KernelTokenImpl token = (KernelTokenImpl) executionContext;

		KernelTokenImpl parentToken = token.getParent();
		// 判断是否有父令牌
		if (parentToken != null) {

			// 判断令牌是否需要重新激活
			if (token.isActive()) {

				token.setActive(false);

				// 当子令牌都处于非激活状态才会驱动父令牌向下
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

	}

	public void leave(FlowNodeExecutionContext executionContext) {
		executionContext.leave();
	}

}
