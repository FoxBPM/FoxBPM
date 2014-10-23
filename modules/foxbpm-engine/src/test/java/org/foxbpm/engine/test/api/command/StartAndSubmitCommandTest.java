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
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 任务命令测试类
 * @author ych
 *
 */
public class StartAndSubmitCommandTest extends AbstractFoxBpmTestCase {

	
	/**
	 * 启动并提交命令测试类
	 * <p>1.使用场景：流程定义第一个人工任务节点使用，用来启动并提交流程，必须和submit命令同时存在，已经启动流程上使用时，会抛出FoxbpmBizException异常</p>
	 * <p>2.处理过程：首先，根据processKey和bizKey启动流程，其次，执行第一个人工任务节点的submit类型的命令，将流程驱动到下个节点</p>
	 * <p>3.测试用例：</p>
	 * <p>		1.执行完成后，判断流程是否存在2个task</p>
	 * <p>		2.执行完成后，判断当前节点是否在userTask2节点</p>
	 * <p>		3.判断bizKey是否正确存储</p>
	 * <p>		4.判断流程变量是否正确存储</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/simpleTaskCommandTest_1.bpmn" })
	public void testStartAndSubmitCommand(){
		
		Authentication.setAuthenticatedUserId("admin");
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("simpleTaskCommandTest_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("simpleTaskCommandTest_1");
		long result = taskQuery.count();
		assertEquals(3, result);
		taskQuery.taskNotEnd();
		List<Task> tasks = taskQuery.list();
		Task task = tasks.get(0);
		assertEquals("UserTask_2", task.getNodeId());
		assertEquals("bizKey", task.getBizKey());
	}
	
	public void testSubmitCommand(){
		
	}
}
