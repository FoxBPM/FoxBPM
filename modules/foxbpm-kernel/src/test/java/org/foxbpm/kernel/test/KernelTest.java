package org.foxbpm.kernel.test;

import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.test.behavior.AutomaticBehavior;



public class KernelTest extends KernelTestCase {
	
	public void testProcessDefinitionBuilder(){
		
		KernelProcessDefinition processDefinition=new ProcessDefinitionBuilder("kernelTest")
		.createFlowNode("start")
        .initial()
        .behavior(new AutomaticBehavior())
        .sequenceFlow("task")
        .endFlowNode()
        .createFlowNode("task")
        .behavior(new AutomaticBehavior())
        .sequenceFlow("end")
        .endFlowNode()
        .createFlowNode("end")
        .behavior(new AutomaticBehavior())
        .endFlowNode()
		.buildProcessDefinition();

		KernelProcessInstance processInstance=processDefinition.createProcessInstance();
		processInstance.start();
		
		
	}

}
