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
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.DelegationState;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 转办，还回按钮测试类
 * @author ych
 *
 */
public class PendingTaskCommandTest extends AbstractFoxBpmTestCase {

	/**
	 * 转办按钮测试
	 * 转办按钮会将待办转给其他人处理，被转办人处理后只能还给原处理者进行操作，并且转办会创建新的任务，留下转办记录
	 * 应用场景：转办按钮一般用在此任务自己单人无法完成，需要其他人协助填写表单变量，填写完之后还给自己的场景，也可叫“协办”
	 * 测试用例：转办后，当前人没有任务，而转办人有这条待办，并且任务转办状态为true
	 * 
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/pendingTaskCommandTest_1.bpmn" })
	public void testPendingAndResolved(){
		
		Authentication.setAuthenticatedUserId("admin");
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("pendingTaskCommandTest_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processDefinitionKey("pendingTaskCommandTest_1").taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("pending");
		Map<String,Object> taskParams = new HashMap<String, Object>();
		taskParams.put("pendingUserId", "adminYch");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setParamMap(taskParams);
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processDefinitionKey("pendingTaskCommandTest_1").taskNotEnd().singleResult();
		assertEquals("adminYch", task.getAssignee());
		//判断任务未“需还回”状态
		assertEquals(DelegationState.RESOLVED,task.getDelegationState());
		
		List<TaskCommand> taskCommands = taskService.getTaskCommandByTaskId(task.getId());
		assertEquals(2, taskCommands.size());
		
		
		//判断按钮中只有“流程状态”和“还回”按钮
		boolean flag = false;
		boolean flag2 = true;
		for(TaskCommand tmp : taskCommands){
			if(tmp.getTaskCommandType().equals("resolved")){
				flag = true;
			}else{
				if(!tmp.getTaskCommandType().equals("processStatus")){
					flag2 = false;
				}
			}
		}
		assertEquals(true, flag&&flag2);
		
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_6");
		expandTaskCommand.setCommandType("resolved");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processDefinitionKey("pendingTaskCommandTest_1").taskNotEnd().singleResult();
		
		assertEquals("admin", task.getAssignee());
		
		taskCommands = taskService.getTaskCommandByTaskId(task.getId());
		assertEquals(true, taskCommands.size() > 2);
	}
}
