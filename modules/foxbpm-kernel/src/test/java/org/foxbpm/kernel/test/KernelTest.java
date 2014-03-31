package org.foxbpm.kernel.test;

import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.test.behavior.CommonNodeBehavior;


public class KernelTest extends KernelTestCase {
	
	public void testProcessDefinitionBuilder(){
		
		KernelProcessDefinition processDefinition=new ProcessDefinitionBuilder()
		.createFlowNode("start")
        .initial()
        .behavior(new CommonNodeBehavior())
        .sequenceFlow("end")
        .endFlowNode()
        .createFlowNode("end")
        .behavior(new CommonNodeBehavior())
        .endFlowNode()
		.buildProcessDefinition();

		KernelProcessInstance processInstance=processDefinition.createProcessInstance();
		processInstance.start();
		
		
	}

}
