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

import java.util.List;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.runtime.TokenQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class AbortProcessCommandTest extends AbstractFoxBpmTestCase {

	
	/**
	 * 终止命令测试类
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/TerminationCommandTest_1.bpmn" })
	public void testTermination(){
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("TerminationCommandTest_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		
		Task task = taskQuery.processDefinitionKey("TerminationCommandTest_1").nodeId("UserTask_2").taskNotEnd().singleResult();
		
		//执行终止命令
		expandTaskCommand.setCommandType("abortProcess");
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//验证流程实例已经被终止，终止状态正确
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey("TerminationCommandTest_1").singleResult();
		assertEquals("abort", processInstance.getInstanceStatus());
		assertEquals(true,processInstance.isEnd());
		
		//验证所有令牌被结束
		TokenQuery tokenQuery = runtimeService.createTokenQuery();
		List<Token> tokens = tokenQuery.processInstanceId(processInstance.getId()).list();
		for(Token token : tokens){
			assertEquals(true, token.isEnded());
		}
		
		//验证所有任务被结束
		taskQuery = taskService.createTaskQuery();
		long taskCount = taskQuery.processInstanceId(processInstance.getId()).taskNotEnd().count();
		assertEquals(0, taskCount);
	}
}
