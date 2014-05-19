package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.connector.Connector;

public class BaseElementBehavior {
	
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	protected List<Connector> connectors=new ArrayList<Connector>();
	

	public List<Connector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<Connector> connectors) {
		this.connectors = connectors;
	}
	

	

}
