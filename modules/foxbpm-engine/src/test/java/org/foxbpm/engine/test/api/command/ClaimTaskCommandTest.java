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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class ClaimTaskCommandTest extends AbstractFoxBpmTestCase{

	/**
	 * 接收任务命令测试类
	 * <p>1.使用场景：一般在共享任务中使用，“领取”后，该任务则会在其他共享人的待办中消失，只能由自己处理，在foxbpm.cfg.xml和节点上都可以配置任务领取方式，设为自动领取时，该按钮不会出现</p>
	 * <p>2.参数：基础参数（必填：taskId,taskCommandId,taskCommandType,task 可选：transVariable,variable）</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，任务还在原节点不变</p>
	 * <p>		2.assignee字段为线程副本中的用户（当前用户：Authentication.setAuthenticatedUserId("admin");）</p>
	 * <p>		3.claimTime字段不为空</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/TaskCommandTest_1.bpmn" })
	public void testClaimTaskCommand(){
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
		
		//验证结果
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("TaskCommandTest_1");
		task = taskQuery.taskNotEnd().singleResult();
		
		assertEquals("UserTask_2",task.getNodeId());
		assertEquals("admin", task.getAssignee());
		assertNotNull(task.getClaimTime());
		
	}
}
