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
import static org.junit.Assert.assertNull;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class ReleaseTaskCommandTest extends AbstractFoxBpmTestCase {

	/**
	 * 释放任务命令测试类
	 * <p>1.使用场景：和“领取”命令成对出现，领取后，发现自己无法处理，释放掉让其他共享者进行处理</p>
	 * <p>2.参数：基础参数（必填：taskId,taskCommandId,taskCommandType,task 可选：transVariable,variable）</p>
	 * <p>2.注意事项：领取和释放均不会产生任务记录 2、释放前会判断存不存在共享用户和共享组，如果不存在，则会抛出FoxbpmBizException告诉用户，任务没有共享者，无法释放</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，任务还在原节点不变</p>
	 * <p>		2.assignee字段为 null</p>
	 * <p>		3.claimTime字段为null</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/TaskCommandTest_1.bpmn" })
	public void testReleaseTaskCommand(){
		
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
		assertNull(task.getAssignee());
		assertNull(task.getClaimTime());
		
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		
		//领取任务后，释放任务，则taskAssignee为空，claimTime为空
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setCommandType("releaseTask");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//验证结果
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("TaskCommandTest_1");
		task = taskQuery.taskNotEnd().singleResult();
		
		assertEquals("UserTask_2",task.getNodeId());
		assertNull(task.getAssignee());
		assertNull(task.getClaimTime());
	}
}
