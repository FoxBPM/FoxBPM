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
package org.foxbpm.engine.test.api.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 暂停和恢复 命令测试类
 * @author ych
 *
 */
public class SuspendAndContinueCommandTest extends AbstractFoxBpmTestCase {

	/**
	 * 暂停和恢复命令测试类
	 * 暂停和恢复是针对流程实例的暂停和恢复
	 * 流程实例被暂停之后：
	 * 1.本任务会被暂停，任务命令只剩下“流程状态”和“恢复实例”
	 * 2.流程和令牌也会被暂停
	 * 3.如果有人试图对已经暂停的流程实例进行操作（通过命令或API），会得到foxbpmException的异常
	 * 4.任务被暂停后， 只能由有恢复按钮的任务进行恢复操作
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/SuspendAndContiueTest_1.bpmn" })
	public void testSuspendAndContinue(){
		
		Authentication.setAuthenticatedUserId("admin");
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("SuspendAndContiueTest_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processDefinitionKey("SuspendAndContiueTest_1").nodeId("UserTask_2").taskNotEnd().singleResult();
		
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("suspendProcessInstance");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processDefinitionKey("SuspendAndContiueTest_1").nodeId("UserTask_3").taskNotEnd().singleResult();
		
		List<TaskCommand> taskCommands = taskService.getTaskCommandByTaskId(task.getId());
		//验证其他按钮被屏蔽
		assertEquals(1, taskCommands.size());
		//验证剩余按钮只有流程状态
		assertEquals(true, taskCommands.get(0).getTaskCommandType().equals("processStatus"));
		//验证任务实例被暂停
		assertEquals(true, task.isSuspended());
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		//验证流程实例被暂停
		assertEquals(true, processInstance.isSuspended());

		List<Token> tokens = runtimeService.createTokenQuery().processInstanceId(task.getProcessInstanceId()).list();
		//验证所有令牌被暂停
		for(Token token : tokens){
			assertEquals(true, token.isSuspended());
		}
		
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskId(task.getId());
		try{
			//验证非法调用API，抛出bizException
			taskService.expandTaskComplete(expandTaskCommand, null);
			fail();
		}catch(FoxBPMException ex){
			
		}
		
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processDefinitionKey("SuspendAndContiueTest_1").nodeId("UserTask_2").taskNotEnd().singleResult();
		
		//构造恢复命令
		expandTaskCommand.setTaskCommandId("HandleCommand_6");
		expandTaskCommand.setCommandType("continueProcessInstance");
		expandTaskCommand.setTaskId(task.getId());
		//执行恢复命令
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		
		//验证流程实例被恢复
		assertEquals(false, processInstance.isSuspended());

		//验证令牌被恢复
		tokens = runtimeService.createTokenQuery().processInstanceId(task.getProcessInstanceId()).list();
		for(Token token : tokens){
			assertEquals(false, token.isSuspended());
		}
		
		//执行通用命令，正常流转流程
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
	}
}
