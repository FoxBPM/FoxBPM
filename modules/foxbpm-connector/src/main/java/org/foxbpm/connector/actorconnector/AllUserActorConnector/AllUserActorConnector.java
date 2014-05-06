package org.foxbpm.connector.actorconnector.AllUserActorConnector;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.impl.entity.UserEntity;


public class AllUserActorConnector extends ActorConnectorHandler {

	private static final long serialVersionUID = 1L;


	@Override
	public void execute(ConnectorExecutionContext executionContext) throws Exception {
		UserEntity user = new UserEntity();
		user.setUserId("fixflow_allusers");
		addUser(user);
	}

}