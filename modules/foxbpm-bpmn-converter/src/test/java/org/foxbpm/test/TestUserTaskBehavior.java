package org.foxbpm.test;
import java.io.InputStream;

import org.foxbpm.bpmn.converter.BpmnParseHandlerImpl;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;
import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.test.KernelTestCase;
import org.foxbpm.kernel.test.behavior.AutomaticBehavior;
import org.foxbpm.kernel.test.behavior.EndBehavior;


public class TestUserTaskBehavior extends KernelTestCase{

public void testProcessDefinitionBuilder(){
	
	ProcessModelParseHandler parse = new BpmnParseHandlerImpl();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("process_test222.bpmn");
	KernelProcessDefinition processDefinition = parse.createProcessDefinition("process_test222",is);
	KernelProcessInstance processInstance=processDefinition.createProcessInstance();
	processInstance.start();
	
	assertTrue(processInstance.getRootToken().isEnded());
	assertTrue(processInstance.isEnded());
	
	
		
//		KernelProcessDefinition processDefinition=new ProcessDefinitionBuilder("kernelTest")
//		.createFlowNode("start")
//        .initial()
//        .behavior(new AutomaticBehavior())
//        .sequenceFlow("task")
//        .endFlowNode()
//        .createFlowNode("task")
//        .behavior(new AutomaticBehavior())
//        .sequenceFlow("end")
//        .endFlowNode()
//        .createFlowNode("end")
//        .behavior(new EndBehavior())
//        .endFlowNode()
//		.buildProcessDefinition();

	
		
		
	}
}
