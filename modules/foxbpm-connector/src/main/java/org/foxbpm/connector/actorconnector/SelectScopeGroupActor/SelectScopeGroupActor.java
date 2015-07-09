package org.foxbpm.connector.actorconnector.SelectScopeGroupActor;


import org.foxbpm.engine.impl.connector.ActorConnectorHandler;
import org.foxbpm.engine.task.DelegateTask;

public class SelectScopeGroupActor extends ActorConnectorHandler {

	private java.lang.String scopeId;

	private java.lang.String scopeType;

	private java.lang.String groupId;

	public void assign(DelegateTask task) throws Exception {

	}

	public void  setScopeId(java.lang.String scopeId){
		this.scopeId = scopeId;
	}

	public void  setScopeType(java.lang.String scopeType){
		this.scopeType = scopeType;
	}

	public void  setGroupId(java.lang.String groupId){
		this.groupId = groupId;
	}

}