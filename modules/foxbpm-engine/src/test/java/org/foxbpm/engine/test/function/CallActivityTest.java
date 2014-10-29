package org.foxbpm.engine.test.function;

import java.util.List;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;

public class CallActivityTest extends AbstractFoxBpmTestCase {
	@Test
	@Deployment(resources={"org/foxbpm/test/function/callActivity_1.bpmn","org/foxbpm/test/function/skipStrategy_1.bpmn"})
	public void subProcessTest(){
		//启动并且提交流程 
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setBusinessKey("callActivity_bizKey");
		expandTaskCommand.setProcessDefinitionKey("callActivity_1");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);

		Task task = taskService.createTaskQuery().processDefinitionKey("callActivity_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		Assert.assertEquals(task.getNodeName(),"人工任务_2");
		//执行任务
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		taskService.expandTaskComplete(expandTaskCommand, null);

		//校验两个流程实例关系
		ProcessInstance callActivityProcessInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("skipStrategy_1").notEnd().list().get(0);
		ProcessInstance parentProcessInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("callActivity_1").notEnd().list().get(0);
		Assert.assertEquals(((ProcessInstanceEntity)callActivityProcessInstance).getParentId(),parentProcessInstance.getId());
		//校验令牌
		String callActivityNodeId = runtimeService.createTokenQuery().processInstanceId(callActivityProcessInstance.getId()).list().get(0).getNodeId();
		String parentNodeId = runtimeService.createTokenQuery().processInstanceId(parentProcessInstance.getId()).list().get(0).getNodeId();
		Assert.assertEquals(callActivityNodeId,"UserTask_1");
		Assert.assertEquals(parentNodeId,"CallActivity_1");

		
		//============================执行子流程====================================//
		task = taskService.createTaskQuery().processDefinitionKey("skipStrategy_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		Assert.assertEquals(task.getNodeName(),"人工任务_1");
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setTaskCommandId("HandleCommand_4");  
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		task = taskService.createTaskQuery().processDefinitionKey("skipStrategy_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		Assert.assertEquals(task.getNodeName(),"人工任务_3");
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");  
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		
		List<Token> tokenList = runtimeService.createTokenQuery().tokenIsEnd().processInstanceId(callActivityProcessInstance.getId()).list();
		Assert.assertNotNull(((TokenEntity)tokenList.get(0)).getEndTime());
		Assert.assertEquals(((TokenEntity)tokenList.get(0)).isActive(),false);
		List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("skipStrategy_1").taskNotEnd().taskCandidateUser("admin").list();
		Assert.assertNotNull(taskList);
		Assert.assertEquals(taskList.size(),0);
	
		//====================================子流程执行校验完毕=================================//
		
		
		//跳出子流程
		//执行主流程最后一个任务
		task = taskService.createTaskQuery().processDefinitionKey("callActivity_1").taskNotEnd().taskCandidateUser("admin").singleResult();
		Assert.assertEquals(task.getNodeName(),"人工任务_3");
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");  
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		taskList = taskService.createTaskQuery().processDefinitionKey("callActivity_1").taskNotEnd().taskCandidateUser("admin").list();
		Assert.assertNotNull(taskList);
		Assert.assertEquals(taskList.size(),0);
		tokenList = runtimeService.createTokenQuery().tokenIsEnd().processInstanceId(parentProcessInstance.getId()).list();
		Assert.assertNotNull(((TokenEntity)tokenList.get(0)).getEndTime());
		Assert.assertEquals(((TokenEntity)tokenList.get(0)).isActive(),false);
 

	}
}
