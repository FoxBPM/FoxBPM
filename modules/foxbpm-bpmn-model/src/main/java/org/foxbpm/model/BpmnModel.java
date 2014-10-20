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

import java.util.ArrayList;
import java.util.HashMap;
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
	protected List<Process> processes = new ArrayList<Process>();
	protected Map<String, Bounds> boundsLocationMap = new LinkedHashMap<String, Bounds>();
	protected Map<String, List<WayPoint>> waypointLocationMap = new LinkedHashMap<String, List<WayPoint>>();
	
	public List<Process> getProcesses() {
		return processes;
	}
	
	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}
	
	public Map<String, Bounds> getBoundsLocationMap() {
		return boundsLocationMap;
	}
	
	public void setBoundsLocationMap(Map<String, Bounds> boundsLocationMap) {
		this.boundsLocationMap = boundsLocationMap;
	}
	
	public Map<String, List<WayPoint>> getWaypointLocationMap() {
		return waypointLocationMap;
	}
	
	public void setWaypointLocationMap(Map<String, List<WayPoint>> waypointLocationMap) {
		this.waypointLocationMap = waypointLocationMap;
	}
	
	public void addBounds(String bpmnElement, Bounds bounds) {
		boundsLocationMap.put(bpmnElement, bounds);
	}
	public void addWaypoint(String bpmnElement, WayPoint wayPoint) {
		if (null == waypointLocationMap) {
			waypointLocationMap = new HashMap<String, List<WayPoint>>();
		}
		if (null == waypointLocationMap.get(bpmnElement)) {
			waypointLocationMap.put(bpmnElement, new ArrayList<WayPoint>());
		}
		waypointLocationMap.get(bpmnElement).add(wayPoint);
	}
}
