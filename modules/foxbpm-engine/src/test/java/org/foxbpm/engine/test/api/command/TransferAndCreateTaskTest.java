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
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class TransferAndCreateTaskTest extends AbstractFoxBpmTestCase {

	/**
	 * 转发命令测试类
	 * 此命令和转发逻辑基本一样，唯一不同的是会生成任务记录
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/TaskCommandTest_1.bpmn" })
	public void testTransfer(){
		Authentication.setAuthenticatedUserId("admin");
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("TaskCommandTest_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("TaskCommandTest_1");
		Task task = taskQuery.taskNotEnd().singleResult();
		
		//接收任务
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//转发任务
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("transferAndCreateTask");
		expandTaskCommand.setTaskId(task.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("transferUserId", "admin2");
		expandTaskCommand.setParamMap(map);
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		Task task2 = taskQuery.singleResult();
		
		assertNotEquals(task.getId(),task2.getId());
		assertEquals("admin2", task2.getAssignee());
		
	}
}
