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
 * @author MAENLIANG
 */
package org.foxbpm.kernel.process.impl;

import java.util.ArrayList;
import java.util.List;

public class KernelAssociationImpl extends KernelArtifactImpl {

	public KernelAssociationImpl(String id, KernelProcessDefinitionImpl processDefinition) {
		super(id, processDefinition);
	}
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5119294789844995924L;

	/** Graphical information: a list of waypoints: x1, y1, x2, y2, x3, y3, .. */
	protected List<Integer> waypoints = new ArrayList<Integer>();
	public List<Integer> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Integer> waypoints) {
		this.waypoints = waypoints;
	}

}
