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
 * @author yangguangftlp
 */
package org.foxbpm.connector.test.actorconnector.SelectTaskByLeast;

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 将任务分配给最少资源的用户
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class SelectTaskByLeastTest extends AbstractFoxBpmTestCase {
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/SelectTaskByLeast/Test01_1.bpmn", "org/foxbpm/connector/test/actorconnector/SelectTaskByLeast/SelectTaskByLeastTest_1.bpmn" })
	public void testSelectTaskByLeast() {

		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('dd','管理员1')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('a','管理员2')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('b','管理员3')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('c','管理员4')");
		// a 用户当前任务3
		Authentication.setAuthenticatedUserId("a");
		ProcessInstance pi = null;
		Task task = null;
		ExpandTaskCommand expandTaskCommand = null;
		
		pi = runtimeService.startProcessInstanceByKey("Test01_1");
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("Test01_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("a");
		
		//执行进入任务节点
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//第二个
		pi = runtimeService.startProcessInstanceByKey("Test01_1");
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("Test01_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("a");
		//执行进入任务节点
		taskService.expandTaskComplete(expandTaskCommand, null);
		//第三个
		pi = runtimeService.startProcessInstanceByKey("Test01_1");
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("Test01_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("a");
		//执行进入任务节点
		taskService.expandTaskComplete(expandTaskCommand, null);
		// b 用户当前任务2
		Authentication.setAuthenticatedUserId("b");
		pi = runtimeService.startProcessInstanceByKey("Test01_1");
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("Test01_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("b");
		// c 用户当前任务0
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.taskAssignee("a");
		taskQuery.taskCandidateUser("a");
		taskQuery.taskNotEnd();
		// 如果数据量大 long 转换int 可能存在问题
	    StringUtil.getInt(taskQuery.count());
		Authentication.setAuthenticatedUserId("dd");
		runtimeService.startProcessInstanceByKey("SelectTaskByLeastTest_1");
		task = (Task) taskService.createTaskQuery().processDefinitionKey("SelectTaskByLeastTest_1").singleResult();
		assertEquals("c", task.getAssignee());
	}
}
