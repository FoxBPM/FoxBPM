package org.foxbpm.engine.test.function;

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class ScriptTaskTest extends AbstractFoxBpmTestCase {
	/**
	 * 脚本任务会将其赋值
	 */
	public static String validateValue = "";
	@Test
	@Deployment(resources={"org/foxbpm/test/function/scriptTask_1.bpmn"})
	public void testMultiInstance(){
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setProcessDefinitionKey("scriptTask_1");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		//校验脚本执行情况
		assertEquals(validateValue, "true");
	}
}
