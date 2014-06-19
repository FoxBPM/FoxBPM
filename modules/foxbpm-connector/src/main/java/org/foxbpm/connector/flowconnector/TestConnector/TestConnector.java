package org.foxbpm.connector.flowconnector.TestConnector;


import org.foxbpm.engine.execution.ConnectorExecutionContext;
import org.foxbpm.engine.impl.connector.FlowConnectorHandler;

public class TestConnector implements FlowConnectorHandler {

	private java.lang.String id;

	private java.lang.String name;

	@Override
	public void execute(ConnectorExecutionContext executionContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(id);
	}
	
	public void  setId(java.lang.String id){
		this.id = id;
	}

	public void  setName(java.lang.String name){
		this.name = name;
	}

}