package org.foxbpm.engine.test.function;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;

public class SkipStrategyTest extends AbstractFoxBpmTestCase {

	/**
	 * 人工任务_2 节点启动跳过策略
	 */
	@Test
	@Deployment(resources={"org/foxbpm/test/function/skipStrategy_1.bpmn"})
	public void testSkipStrategy(){
		
		//启动并且提交流程 
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setProcessDefinitionKey("skipStrategy_1");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		Task task = taskService.createTaskQuery().processDefinitionKey("skipStrategy_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		String nodeName = task.getNodeName();
		//跳过人工任务_2 ，进入人工任务_3
		Assert.assertEquals(nodeName,"人工任务_3");
	}
}
