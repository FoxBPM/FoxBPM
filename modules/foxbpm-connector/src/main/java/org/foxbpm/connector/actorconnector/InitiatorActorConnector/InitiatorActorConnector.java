package org.foxbpm.connector.actorconnector.InitiatorActorConnector;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.entity.UserEntity;


public class InitiatorActorConnector extends ActorConnectorHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ConnectorExecutionContext executionContext) throws Exception {

		//获取发起人
		String initiator=executionContext.getInitiator();
		UserEntity user= new UserEntity(initiator);
		addUser(user);

	}

	

}