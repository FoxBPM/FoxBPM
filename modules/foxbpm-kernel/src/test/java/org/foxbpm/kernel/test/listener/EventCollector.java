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
 * @author kenshin
 */
package org.foxbpm.kernel.test.listener;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventCollector implements KernelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(EventCollector.class);

	public List<String> events = new ArrayList<String>();

	public void notify(ListenerExecutionContext executionContext) {

		log.debug("触发事件 event: {} on {}", executionContext.getEventName(), executionContext.getEventSource());
		events.add(executionContext.getEventName() + " on " + executionContext.getEventSource());
	}

	public String toString() {
		StringBuilder text = new StringBuilder();
		for (String event : events) {
			text.append(event);
			text.append("\n");
		}
		return text.toString();
	}

}
