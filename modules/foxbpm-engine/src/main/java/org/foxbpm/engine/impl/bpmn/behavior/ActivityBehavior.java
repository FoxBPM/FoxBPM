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

public class ActivityBehavior extends FlowNodeBehavior {

	private static final long serialVersionUID = 1L;
	
	/** 跳过策略 */
	protected SkipStrategy skipStrategy;

	

	public SkipStrategy getSkipStrategy() {
		return skipStrategy;
	}

	public void setSkipStrategy(SkipStrategy skipStrategy) {
		this.skipStrategy = skipStrategy;
	}

	/** 边界事件集合 */
	protected List<BoundaryEventBehavior> boundaryEvents = new ArrayList<BoundaryEventBehavior>();

	/** 循环对象 */
	protected LoopCharacteristics loopCharacteristics;

	public List<BoundaryEventBehavior> getBoundaryEvents() {
		return boundaryEvents;
	}

	public void setBoundaryEvents(List<BoundaryEventBehavior> boundaryEvents) {
		this.boundaryEvents = boundaryEvents;
	}

	public LoopCharacteristics getLoopCharacteristics() {
		return loopCharacteristics;
	}

	public void setLoopCharacteristics(LoopCharacteristics loopCharacteristics) {
		this.loopCharacteristics = loopCharacteristics;
	}

}
