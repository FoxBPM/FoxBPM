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
 * 活动
 * @author ych
 *
 */
public abstract class Activity extends FlowNode {

	private static final long serialVersionUID = 1L;

	/**
	 * 跳过策略
	 */
	protected SkipStrategy skipStrategy;
	
	/**
	 * 循环对象，如多实例
	 */
	protected LoopCharacteristics loopCharacteristics;

	/**
	 * 边界事件
	 */
	protected List<BoundaryEvent> boundaryEvents;

	public SkipStrategy getSkipStrategy() {
		return skipStrategy;
	}

	public void setSkipStrategy(SkipStrategy skipStrategy) {
		this.skipStrategy = skipStrategy;
	}

	public LoopCharacteristics getLoopCharacteristics() {
		return loopCharacteristics;
	}

	public void setLoopCharacteristics(LoopCharacteristics loopCharacteristics) {
		this.loopCharacteristics = loopCharacteristics;
	}

	public List<BoundaryEvent> getBoundaryEvents() {
		return boundaryEvents;
	}

	public void setBoundaryEvents(List<BoundaryEvent> boundaryEvents) {
		this.boundaryEvents = boundaryEvents;
	}

}
