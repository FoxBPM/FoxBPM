package org.foxbpm.kernel.test;

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
		
		
	}
}
