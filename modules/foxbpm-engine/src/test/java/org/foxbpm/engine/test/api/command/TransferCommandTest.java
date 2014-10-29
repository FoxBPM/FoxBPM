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

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class TransferCommandTest extends AbstractFoxBpmTestCase {

	/**
	 * 转发命令测试类
	 * <p>1.使用场景：领取任务后，发现任务自己无法完成，转发给相应人进行处理，转发后，对方可直接办理，和本人权限和操作相同</p>
	 * <p>2.参数：基础参数（必填：taskId,taskCommandId,taskCommandType,task 可选：transVariable,variable） </p>
	 * <p>3.命令所需参数：transferUserId 既：转发目的人编号</p>
	 * <p>4.注意事项：</p>
	 * <p>			1.只有任务当前处理者（Assignee）有权转发，否则抛出异常</p>
	 * <p>			2.直接转发当前任务，不留下转发记录</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.转发后，流程当前任务节点不变，新任务assignee字段为转发目的人</p>
	 * <p>		2.当前待办任务和转办之前的任务编号相同</p>
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
		expandTaskCommand.setCommandType("transfer");
		expandTaskCommand.setTaskId(task.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("transferUserId", "admin2");
		expandTaskCommand.setParamMap(map);
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		Task task2 = taskQuery.singleResult();
		
		assertEquals(task.getId(),task2.getId());
		assertEquals("admin2", task2.getAssignee());
		
	}
}
