package org.foxbpm.rest.service.api.model;

public class ProcessDefinitionResponse {

	private String id;
	private String name;
	private String catory;
	private String deploymentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCatory() {
		return catory;
	}
	public void setCatory(String catory) {
		this.catory = catory;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
}
