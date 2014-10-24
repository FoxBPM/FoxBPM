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
import java.util.List;

public class LaneSet extends BaseElement {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private List<Lane> lanes = new ArrayList<Lane>();
	
	public void setLanes(List<Lane> lanes) {
		this.lanes = lanes;
	}
	
	public List<Lane> getLanes() {
		return lanes;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "LaneSet [name=" + name + ", id=" + id + ']';
	}
	
}
