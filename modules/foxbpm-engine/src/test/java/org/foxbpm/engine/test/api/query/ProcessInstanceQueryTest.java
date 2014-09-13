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
package org.foxbpm.engine.test.api.query;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.foxbpm.kernel.runtime.ProcessInstanceStatus;
import org.junit.Test;

public class ProcessInstanceQueryTest extends AbstractFoxBpmTestCase {

	
	/**
	 * 测试processInstanceQuery各接口的正确性
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/api/Test_RuntimeService_1.bpmn" })
	public void testProcessInstanceQuery(){
		
		Authentication.setAuthenticatedUserId("test_admin3");
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("Test_RuntimeService_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("runtimeServiceBizKey");
		expandTaskCommand.setInitiator("test_admin2");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("Test_RuntimeService_1").singleResult();	
		String processInstanceId = processInstance.getId();
		String processDefinitionId = processInstance.getProcessDefinitionId();
		
		 ProcessInstanceQuery processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processInstanceId(processInstanceId);
		 List<ProcessInstance> process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processDefinitionId(processDefinitionId);
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processDefinitionKey("Test_RuntimeService_1");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processDefinitionName("runTimeService测试流程");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processDefinitionNameLike("runTimeService测试流%");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.initiator("test_admin2");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.initiatorLike("%est_admin2%");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.subject("runtimeService测试流程的主题");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.subjectLike("%untimeService测试流程的主%");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processInstanceBusinessKey("runtimeServiceBizKey");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processInstanceBusinessKeyLike("%untimeServiceBizKe%");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.taskParticipants("test_admin3");
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.processInstanceStatus(ProcessInstanceStatus.RUNNING).processDefinitionId(processDefinitionId);
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.isEnd().processDefinitionId(processDefinitionId);
		 process = processQuery.list();
		 assertEquals(0, process.size());
		
		 processQuery = runtimeService.createProcessInstanceQuery();
		 processQuery.notEnd().processDefinitionId(processDefinitionId);
		 process = processQuery.list();
		 assertEquals(1, process.size());
		
	}
}
