package org.foxbpm.engine.test.function;

import java.util.List;

import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;

public class SubProcessTest extends AbstractFoxBpmTestCase {

	@Test
	@Deployment(resources={"org/foxbpm/test/function/subProcess_1.bpmn"})
	public void subProcessTest(){
		//启动并且提交流程 
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setBusinessKey("subProcess_bizKey");
		expandTaskCommand.setProcessDefinitionKey("subProcess_1");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		Task sub_task_1 = taskService.createTaskQuery().processDefinitionKey("subProcess_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		String processInstanceId = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey("subProcess_bizKey").list().get(0).getId();
		List<Token> tokenList = runtimeService.createTokenQuery().processInstanceId(processInstanceId).list();
		//进入子流程
		Assert.assertEquals(sub_task_1.getNodeName(),"内部子流程人工任务_1");
		//存在主令牌子令牌
		Assert.assertEquals(tokenList.size(),2);
		//校验令牌关系
		Assert.assertEquals(tokenList.get(0).getId(),tokenList.get(1).getParentId());
		
		//执行任务
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(sub_task_1.getId());
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		taskService.expandTaskComplete(expandTaskCommand, null);

		Task sub_task_2 = taskService.createTaskQuery().processDefinitionKey("subProcess_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		Assert.assertEquals(sub_task_2.getNodeName(),"内部子流程人工任务_2");
		
		//执行任务
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(sub_task_2.getId());
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		taskService.expandTaskComplete(expandTaskCommand, null);

		List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("subProcess_1").taskNotEnd().taskCandidateUser("admin").list();
		Assert.assertNotNull(taskList);
		Assert.assertEquals(taskList.size(),0);
		tokenList = runtimeService.createTokenQuery().tokenIsEnd().processInstanceId(processInstanceId).list();
		Assert.assertNotNull(((TokenEntity)tokenList.get(0)).getEndTime());
		Assert.assertNotNull(((TokenEntity)tokenList.get(1)).getEndTime());
		Assert.assertEquals(((TokenEntity)tokenList.get(0)).isActive(),false);
		Assert.assertEquals(((TokenEntity)tokenList.get(1)).isActive(),false);
	}
}
