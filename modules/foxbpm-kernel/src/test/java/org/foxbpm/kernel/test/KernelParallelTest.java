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

import org.foxbpm.kernel.ProcessDefinitionBuilder;
import org.foxbpm.kernel.process.KernelProcessDefinition;
import org.foxbpm.kernel.runtime.KernelProcessInstance;
import org.foxbpm.kernel.test.behavior.AutomaticBehavior;
import org.foxbpm.kernel.test.behavior.EndBehavior;
import org.foxbpm.kernel.test.behavior.ParallelGatewayBehavior;

public class KernelParallelTest extends KernelTestCase {
	

	public void testParallel(){
	
			KernelProcessDefinition processDefinition = new ProcessDefinitionBuilder()
		    .createFlowNode("start")
		      .initial()
		      .behavior(new AutomaticBehavior())
		      .sequenceFlow("fork","sequenceFlow_1")
		    .endFlowNode()
		    .createFlowNode("fork")
		      .behavior(new ParallelGatewayBehavior("Diverging"))
		      .sequenceFlow("c1","sequenceFlow_2")
		      .sequenceFlow("c2","sequenceFlow_3")
		    .endFlowNode()
		    .createFlowNode("c1")
		      .behavior(new AutomaticBehavior())
		      .sequenceFlow("join","sequenceFlow_4")
		    .endFlowNode()
		    .createFlowNode("c2")
		      .behavior(new AutomaticBehavior())
		      .sequenceFlow("join","sequenceFlow_5")
		    .endFlowNode()
		    .createFlowNode("join")
		      .behavior(new ParallelGatewayBehavior("Converging"))
		      .sequenceFlow("end","sequenceFlow_6")
		    .endFlowNode()
		    .createFlowNode("end")
		      .behavior(new EndBehavior())
		    .endFlowNode()
		  .buildProcessDefinition();
		  
		  KernelProcessInstance processInstance = processDefinition.createProcessInstance(); 
		  processInstance.start();
		  
		  assertTrue(processInstance.isEnded());
		
	}

	/**
	 *                   +----+
	 *              +--->|end1|
	 *              |    +----+
	 *              |        
	 * +-----+   +----+      
	 * |start|-->|fork|      
	 * +-----+   +----+      
	 *              |        
	 *              |    +----+
	 *              +--->|end2|
	 *                   +----+
	 */
	public void testParallelEnd() {
		KernelProcessDefinition processDefinition = new ProcessDefinitionBuilder()
	    .createFlowNode("start")
	      .initial()
	      .behavior(new AutomaticBehavior())
	      .sequenceFlow("fork","sequenceFlow_1")
	    .endFlowNode()
	    .createFlowNode("fork")
	      .behavior(new ParallelGatewayBehavior("Diverging"))
	      .sequenceFlow("end1","sequenceFlow_2")
	      .sequenceFlow("end2","sequenceFlow_3")
	    .endFlowNode()
	    .createFlowNode("end1")
	      .behavior(new EndBehavior())
	    .endFlowNode()
	    .createFlowNode("end2")
	      .behavior(new EndBehavior())
	    .endFlowNode()
	  .buildProcessDefinition();
	  
	  KernelProcessInstance processInstance = processDefinition.createProcessInstance(); 
	  processInstance.start();
	  
	  assertTrue(processInstance.isEnded());
	}

}
