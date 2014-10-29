package org.foxbpm.engine.impl.datavariable;

import org.foxbpm.engine.datavariable.DataObjectDefinition;

public class DataObjectDefinitionImpl implements DataObjectDefinition {
	
	protected String id;
	protected String name;
	protected String behavior;
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBehavior() {
		return behavior;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	
}
