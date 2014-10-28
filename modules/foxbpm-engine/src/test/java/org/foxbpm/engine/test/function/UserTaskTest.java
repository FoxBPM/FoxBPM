package org.foxbpm.engine.test.function;

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class UserTaskTest extends AbstractFoxBpmTestCase {
	@Test
	@Deployment(resources={"org/foxbpm/test/function/userTask_1.bpmn"})
	public void testMultiInstance(){
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setProcessDefinitionKey("userTask_1");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		TaskEntity task =(TaskEntity)taskService.createTaskQuery().processDefinitionKey("userTask_1").taskNotEnd().taskCandidateUser("admin").singleResult();

		assertEquals(task.getTaskType(), "foxbpmtask");
		assertEquals(task.getPriority(), Task.PRIORITY_HIGH);
		assertEquals(task.getSubject(), "任务主题");
		assertEquals(task.getDescription(), "任务描述");
		assertEquals(task.getCompleteDescription(), "完成后的描述");
		assertEquals(task.getExpectedExecutionTime()+"", 61.02+"");
		assertEquals(task.getFormUri(), "操作表单");
		assertEquals(task.getFormUriView(), "浏览表单");
		
	}
}
