package org.foxbpm.engine.test.connector;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class TestTaskConnector extends AbstractFoxBpmTestCase {

	@Test
	@Deployment(resources = { "org/foxbpm/test/connector/test_connector_1.bpmn" })
	public void testProcessEnter(){
		runtimeService.startProcessInstanceByKey("test_connector_1");
		
		Task task = taskService.createTaskQuery().processDefinitionKey("test_connector_1").taskNotEnd().singleResult();
		
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandCommand = new ExpandTaskCommand();
		
		expandCommand.setTaskId(task.getId());
		expandCommand.setTaskCommandId("HandleCommand_1");
		expandCommand.setCommandType("general");
		
		taskService.expandTaskComplete(expandCommand, null);
		
		task = taskService.createTaskQuery().processDefinitionKey("test_connector_1").taskNotEnd().singleResult();
		
		expandCommand = new ExpandTaskCommand();
		
		expandCommand.setTaskId(task.getId());
		expandCommand.setTaskCommandId("HandleCommand_1");
		expandCommand.setCommandType("general");
		taskService.expandTaskComplete(expandCommand, null);
	}
}
