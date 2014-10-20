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
import java.util.Iterator;
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
	protected Map<String, Map<String, Bounds>> boundsLocationMap = new LinkedHashMap<String, Map<String, Bounds>>();
	protected Map<String, Map<String, List<WayPoint>>> waypointLocationMap = new LinkedHashMap<String, Map<String, List<WayPoint>>>();
	
	public List<Process> getProcesses() {
		return processes;
	}
	
	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}
	
	public Map<String, Map<String, Bounds>> getBoundsLocationMap() {
		return boundsLocationMap;
	}
	
	public void setBoundsLocationMap(Map<String, Map<String, Bounds>> boundsLocationMap) {
		this.boundsLocationMap = boundsLocationMap;
	}
	
	public Map<String, Map<String, List<WayPoint>>> getWaypointLocationMap() {
		return waypointLocationMap;
	}
	
	public void setWaypointLocationMap(Map<String, Map<String, List<WayPoint>>> waypointLocationMap) {
		this.waypointLocationMap = waypointLocationMap;
	}
	
	public void addBounds(String bpmnPlanId, String bpmnElement, Bounds bounds) {
		if (null == boundsLocationMap.get(bpmnPlanId)) {
			boundsLocationMap.put(bpmnPlanId, new LinkedHashMap<String, Bounds>());
		}
		boundsLocationMap.get(bpmnPlanId).put(bpmnElement, bounds);
	}
	public void addWaypoint(String bpmnPlanId, String bpmnElement, WayPoint wayPoint) {
		if (null == waypointLocationMap.get(bpmnPlanId)) {
			waypointLocationMap.put(bpmnPlanId, new LinkedHashMap<String, List<WayPoint>>());
		}
		if (null == waypointLocationMap.get(bpmnPlanId).get(bpmnElement)) {
			waypointLocationMap.get(bpmnPlanId).put(bpmnElement, new ArrayList<WayPoint>());
		}
		waypointLocationMap.get(bpmnPlanId).get(bpmnElement).add(wayPoint);
	}
	/**
	 * 获取流程线条集合
	 * 
	 * @param processId
	 *            流程ID
	 * @return 返回线条集合
	 */
	public Map<String, SequenceFlow> findSequenceFlow(String processId) {
		Process process = null;
		FlowElement flowElement = null;
		FlowContainer flowContainer = null;
		for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext();) {
			process = iterator.next();
			if (process.getId().equals(processId)) {
				return process.getSequenceFlows();
			} else {
				for (Iterator<FlowElement> iteratorFlowElement = process.getFlowElements().iterator(); iterator.hasNext();) {
					flowElement = iteratorFlowElement.next();
					if (flowElement instanceof FlowContainer) {
						flowContainer = (FlowContainer) flowElement;
						if (flowElement.getId().equals(processId)) {
							return flowContainer.getSequenceFlows();
						}
					}
				}
			}
		}
		return null;
	}
}
