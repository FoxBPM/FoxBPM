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

		log.debug("搜集 event: {} on {}", executionContext.getEventName(), executionContext.getEventSource());
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
