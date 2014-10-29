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

import java.util.List;

/**
 * bpmn事件
 * @author ych
 *
 */
public abstract class Event extends FlowNode {

	private static final long serialVersionUID = 1L;

	/**
	 * 事件定义集合，如 空事件，事件事件，信号等
	 */
	protected List<EventDefinition> eventDefinitions;

	public List<EventDefinition> getEventDefinitions() {
		return eventDefinitions;
	}

	public void setEventDefinitions(List<EventDefinition> eventDefinitions) {
		this.eventDefinitions = eventDefinitions;
	}

}
