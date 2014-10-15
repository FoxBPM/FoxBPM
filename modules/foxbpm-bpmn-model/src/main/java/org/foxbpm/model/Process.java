package org.foxbpm.model;

import java.util.List;

public class Process extends FlowElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String key;
	protected String subject;
	protected String formUri;
	protected String formUriView;
	protected String category;
	protected List<PotentialStarter> potentialStarters;

	protected List<DataVariable> dataVariables;

	protected List<LaneSet> laneSets;

	protected List<FlowElement> flowNodes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = formUri;
	}

	public String getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = formUriView;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<PotentialStarter> getPotentialStarters() {
		return potentialStarters;
	}

	public void setPotentialStarters(List<PotentialStarter> potentialStarters) {
		this.potentialStarters = potentialStarters;
	}

	public List<DataVariable> getDataVariables() {
		return dataVariables;
	}

	public void setDataVariables(List<DataVariable> dataVariables) {
		this.dataVariables = dataVariables;
	}

	public List<LaneSet> getLaneSets() {
		return laneSets;
	}

	public void setLaneSets(List<LaneSet> laneSets) {
		this.laneSets = laneSets;
	}

	public List<FlowElement> getFlowNodes() {
		return flowNodes;
	}

	public void setFlowNodes(List<FlowElement> flowNodes) {
		this.flowNodes = flowNodes;
	}

}
