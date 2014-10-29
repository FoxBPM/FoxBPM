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
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月27日
 */
public class RollBackTaskDesignationResetCommandTest extends AbstractFoxBpmTestCase {
	/**
	 * 退回-后台指定步骤-重新分配测试类 start->a->b->c->end
	 * <p>
	 * 1.使用场景：任务退回到指定节点后并且重新分配
	 * </p>
	 * <p>
	 * 2.处理过程：首先，A用户根据processKey和bizKey启动流程，其次，执行第一个人工任务节点的submit类型的命令，
	 * 并将流程驱动到c节点执行“退回-后台指定步骤-重新分配”命令,
	 * <p>
	 * 让c任务回退到b任务并且重新分配
	 * </p>
	 * <p>
	 * 3.测试用例：
	 * </p>
	 * <p>
	 * 1.执行完成后，判断以其他用户是否重新处理b任务
	 * </p>
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/test/command/Test_RollBackTaskDesignationResetCommand_1.bpmn"})
	public void testRollBackTask() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动任务
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("Test_RollBackTaskDesignationResetCommand_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查询未完成任务b
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("Test_RollBackTaskDesignationResetCommand_1");
		Task task = taskQuery.taskNotEnd().singleResult();
		// 接收任务b
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 驱动任务b下一步c
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查询未完成任务c
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("Test_RollBackTaskDesignationResetCommand_1");
		task = taskQuery.taskNotEnd().singleResult();
		// 接收任务c
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 执行回退
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("rollBack_designation_reset");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查看任务状态
		// 查询未完成任务c
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("Test_RollBackTaskDesignationResetCommand_1");
		task = taskQuery.taskNotEnd().singleResult();
		// 判断节点号是否为b任务的
		assertEquals(null, task.getAssignee());
		assertEquals("UserTask_2", task.getNodeId());
	}
	/**
	 * 分支退回
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/test/command/branchReturned_1.bpmn"})
	public void testBranchReturned() {
		Authentication.setAuthenticatedUserId("admin");
		
		// 预置角色
		jdbcTemplate.execute("insert into au_roleinfo(roleid,rolename) VALUES ('21100001','xxx经理')");
		// 预置用户用户
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test01','管理员Tst')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test02','管理员Tst')");
		// 设置用户和部门关系
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000001','test01','20000001','role')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000002','test02','20000001','role')");
		
		// 启动任务
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("branchReturned_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查询待办任务
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("branchReturned_1");
		Task task = taskQuery.taskNotEnd().singleResult();
		// 接收任务b
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 处理任务b
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查询待办任务
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("branchReturned_1");
		List<Task> tasks = taskQuery.taskNotEnd().list();
		assertEquals(true, (null != tasks));
		assertEquals(2, tasks.size());
		// 查询test01下任务
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("branchReturned_1");
		taskQuery.taskAssignee("test01");
		task = taskQuery.taskNotEnd().singleResult();
		// 执行退回命令
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("rollBack_designation_reset");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("test01");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查询任务是否在B节点上
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("branchReturned_1");
		for (Task task2 : taskQuery.taskNotEnd().list()) {
			System.out.println(task2.getNodeId() + " " + task2.getNodeName());
		}
		
		task = taskQuery.taskNotEnd().singleResult();
		assertEquals(null, task.getAssignee());
		assertEquals("UserTask_2", task.getNodeId());
		
	}
	/**
	 * 多实例退回
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/test/command/multiInstanceReturn_1.bpmn"})
	public void testMultiInstanceReturned() {
		Authentication.setAuthenticatedUserId("admin");
		// 预置角色
		jdbcTemplate.execute("insert into au_roleinfo(roleid,rolename) VALUES ('21100001','xxx经理')");
		// 预置用户用户
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test01','管理员Tst')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test02','管理员Tst')");
		// 设置用户和部门关系
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000001','test01','20000001','role')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000002','test02','20000001','role')");
		
		// 启动任务
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("multiInstanceReturn_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		// 查询待办任务
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("multiInstanceReturn_1");
		Task task = taskQuery.taskNotEnd().singleResult();
		// 接收任务b
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 处理任务b
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		// 查询待办任务
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("multiInstanceReturn_1");
		taskQuery.taskAssignee("test01");
		task = taskQuery.taskNotEnd().singleResult();
		// 执行退回命令
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("rollBack_designation_reset");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("test01");
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 获取任务是否在B上UserTask_3
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("multiInstanceReturn_1");
		task = taskQuery.taskNotEnd().singleResult();
		assertEquals(null, task.getAssignee());
		assertEquals("UserTask_3", task.getNodeId());
	}
}
