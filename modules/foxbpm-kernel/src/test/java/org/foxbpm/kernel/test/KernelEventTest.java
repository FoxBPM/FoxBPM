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
package org.foxbpm.kernel.test;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.event.KernelEventType;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.test.behavior.AutomaticBehavior;
import org.foxbpm.kernel.test.behavior.EndBehavior;
import org.foxbpm.kernel.test.listener.EventCollector;

public class KernelEventTest extends KernelTestCase  {
	
	public void testProcessStartEndEvent(){
		
		EventCollector eventCollector = new EventCollector();
	    
	    KernelProcessDefinition processDefinition = new ProcessDefinitionBuilder("events")
	      .executionListener(KernelEventType.EVENTTYPE_PROCESS_START, eventCollector)
	      .executionListener(KernelEventType.EVENTTYPE_PROCESS_END, eventCollector)
	      .createFlowNode("start")
	        .initial()
	        .behavior(new AutomaticBehavior())
	        .executionListener(KernelEventType.EVENTTYPE_NODE_ENTER, eventCollector)
	        .executionListener(KernelEventType.EVENTTYPE_NODE_EXECUTE, eventCollector)
	        .executionListener(KernelEventType.EVENTTYPE_NODE_LEAVE, eventCollector)
	        .startSequenceFlow("end")
	          .executionListener(eventCollector)
	        .endSequenceFlow()
	      .endFlowNode()
	      .createFlowNode("end")
	        .behavior(new EndBehavior())
	        .executionListener(KernelEventType.EVENTTYPE_NODE_ENTER, eventCollector)
	        .executionListener(KernelEventType.EVENTTYPE_NODE_EXECUTE, eventCollector)
	      .endFlowNode()
	    .buildProcessDefinition();
	    
	    KernelProcessInstance processInstance = processDefinition.createProcessInstance();
	    processInstance.start();
	    
	    
		assertTrue(processInstance.getRootToken().isEnded());
		assertTrue(processInstance.isEnded());
		
		List<String> expectedEvents = new ArrayList<String>();
	    expectedEvents.add("process-start on ProcessDefinition(events)");
	    expectedEvents.add("node-enter on FlowNode(start)");
	    expectedEvents.add("node-execute on FlowNode(start)");
	    expectedEvents.add("node-leave on FlowNode(start)");
	    expectedEvents.add("sequenceflow-take on (start)-->(end)");
	    expectedEvents.add("node-enter on FlowNode(end)");
	    expectedEvents.add("node-execute on FlowNode(end)");
	    expectedEvents.add("process-end on ProcessDefinition(events)");
	    
	    

	    assertEquals("预期的事件顺序为: "+expectedEvents+", 但实际的执行顺序为: \n"+eventCollector+"\n", expectedEvents, eventCollector.events);
		
	
		
	}
}
