package org.foxbpm.engine.test.api;

import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class TaskServiceTest extends AbstractFoxBpmTestCase {

	@Test
	@Deployment(resources = { "process_test_1.bpmn"})
	public void testTaskQuery(){
		
//		Authentication.setAuthenticatedUserId("2222");
//		Map<String, Object> transientVariables=new HashMap<String, Object>();
//		transientVariables.put("value", 10);
//		ProcessInstance processInstance=runtimeService.startProcessInstanceById
//		("process_foxbpm_1:1:ded8ceeb-6d8e-4cd9-b6e1-8e49a25beef7","bizkey",transientVariables, null);
//		
//		
//		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
//		
//		ExpandTaskCommand expandCommand = new ExpandTaskCommand();
//		expandCommand.setCommandType("general");
//		expandCommand.setTaskCommandId("HandleCommand_1");
//		expandCommand.setTaskId(task.getId());
//		taskService.expandTaskComplete(expandCommand, null);
		
//		taskService.createTaskQuery().taskAssignee("2222").taskCandidateUser("2222").taskNotEnd().listPagination(1, 10);
		
		ProcessDefinition processDefinition = modelService.getProcessDefinition("process_test_1", 1);
		System.out.println(processDefinition.getName());
		
//		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
//		ExpandTaskCommand expandCommand2 = new ExpandTaskCommand();
//		expandCommand2.setCommandType("general");
//		expandCommand2.setTaskCommandId("HandleCommand_1");
//		expandCommand2.setTaskId(task.getId());
//		taskService.expandTaskComplete(expandCommand2, null);
//		
//		processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
//		
//		assertTrue(processInstance.isEnd());
		
	}
	
//	public void testGetTaskCommand(){
//		Authentication.setAuthenticatedUserId("2222");
//		Map<String, Object> transientVariables=new HashMap<String, Object>();
//		transientVariables.put("value", 10);
//		ProcessInstance processInstance=runtimeService.startProcessInstanceById
//		("process_foxbpm_1:1:ded8ceeb-6d8e-4cd9-b6e1-8e49a25beef7","bizkey",transientVariables, null);
//		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
//		
//		List<TaskCommand> taskCommands = taskService.getTaskCommandByTaskId(task.getId());
//		System.out.println(taskCommands.size());
//		
//	}
//	
//	public void testGetTaskCommandByKey(){
//		Authentication.setAuthenticatedUserId("2222");
//		List<TaskCommand> taskCommands = taskService.getSubTaskCommandByKey("process_foxbpm_1");
//		
//		System.out.println(taskCommands);
//	}
	
	
}
