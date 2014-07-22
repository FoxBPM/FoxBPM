/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 * @author kenshin
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.ArrayList;
import java.util.List;

public abstract class CatchEventBehavior extends EventBehavior {
	private static final long serialVersionUID = 1L;

	private List<EventDefinition> eventDefinitions = new ArrayList<EventDefinition>();
	private boolean isParallelMultiple;
	public boolean isParallelMultiple() {
		return isParallelMultiple;
	}

	public void setParallelMultiple(boolean isParallelMultiple) {
		this.isParallelMultiple = isParallelMultiple;
	}

	public List<EventDefinition> getEventDefinitions() {
		return eventDefinitions;
	}

	public void setEventDefinitions(List<EventDefinition> eventDefinitions) {
		this.eventDefinitions = eventDefinitions;
	}

}
