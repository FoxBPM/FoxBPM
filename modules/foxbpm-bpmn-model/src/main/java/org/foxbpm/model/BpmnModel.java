package org.foxbpm.model;

import java.util.List;
import java.util.Map;

public class BpmnModel extends BaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<Process> processes;
	
	protected Map<String,Graphic> graphicMap;

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

	public Map<String, Graphic> getGraphicMap() {
		return graphicMap;
	}

	public void setGraphicMap(Map<String, Graphic> graphicMap) {
		this.graphicMap = graphicMap;
	}
	
}
