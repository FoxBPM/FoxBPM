package org.foxbpm.connector.actorconnector.LastStepActorConnector;


import java.util.*;

import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.ActorConnectorHandler;


public class LastStepActorConnector extends ActorConnectorHandler {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ConnectorExecutionContext executionContext) throws Exception {

		String userId=Authentication.getAuthenticatedUserId();
		UserTo userTo = new UserTo(userId);
		userTos.add(userTo);
		return userTos;
	}




}