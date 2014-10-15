package org.foxbpm.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BpmnModel {
	protected List<Process> processes;
	protected Map<String, Graphic> boundsLocationMap = new LinkedHashMap<String, Graphic>();
	protected Map<String, List<Graphic>> waypointLocationMap = new LinkedHashMap<String, List<Graphic>>();
	
	public List<Process> getProcesses() {
		return processes;
	}
	
	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}
	
	public Map<String, Graphic> getBoundsLocationMap() {
		return boundsLocationMap;
	}
	
	public void setBoundsLocationMap(Map<String, Graphic> boundsLocationMap) {
		this.boundsLocationMap = boundsLocationMap;
	}
	
	public Map<String, List<Graphic>> getWaypointLocationMap() {
		return waypointLocationMap;
	}
	
	public void setWaypointLocationMap(Map<String, List<Graphic>> waypointLocationMap) {
		this.waypointLocationMap = waypointLocationMap;
	}
	
	public void addBoundsGraphic(String key, Graphic graphic) {
		boundsLocationMap.put(key, graphic);
	}
	public void addWaypointGraphic(String key, List<Graphic> graphicList) {
		waypointLocationMap.put(key, graphicList);
	}
}
