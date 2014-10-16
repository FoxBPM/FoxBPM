/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author ych
 */
package org.foxbpm.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * BPMN模型
 * 
 * @author ych
 * 
 */
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
	public List<Graphic> getWaypointGraphic(String key) {
		return waypointLocationMap.get(key);
	}
}
