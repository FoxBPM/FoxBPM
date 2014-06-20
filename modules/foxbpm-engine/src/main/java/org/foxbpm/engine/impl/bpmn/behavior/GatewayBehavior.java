package org.foxbpm.engine.impl.bpmn.behavior;

public abstract class GatewayBehavior extends FlowNodeBehavior {

	private static final long serialVersionUID = 1L;
	
	protected GatewayDirection gatewayDirection=GatewayDirection.MIXED;

	public GatewayDirection getGatewayDirection() {
		return gatewayDirection;
	}

	public void setGatewayDirection(GatewayDirection gatewayDirection) {
		this.gatewayDirection = gatewayDirection;
	}

}
