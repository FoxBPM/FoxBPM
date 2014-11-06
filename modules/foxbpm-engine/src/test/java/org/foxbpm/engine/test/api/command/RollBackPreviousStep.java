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

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 退回-上一步
 * 
 * @author yangguangftlp
 * @date 2014年10月29日
 */
public class RollBackPreviousStep extends AbstractFoxBpmTestCase {
	/**
	 * 自动给指定处理者
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/test/command/RollBack_previousStep_1.bpmn"})
	public void testlastStep() {
		// 预置角色
		jdbcTemplate.execute("insert into au_roleinfo(roleid,rolename) VALUES ('21100001','xxx经理')");
		// 预置用户用户
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test01','管理员Tst')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test02','管理员Tst')");
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('test03','管理员Tst')");
		// 设置用户和部门关系
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000001','test01','20000001','role')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000002','test02','20000001','role')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('11100000000000003','test03','20000001','role')");
		Authentication.setAuthenticatedUserId("admin");
		// 测试开始
		// 处理任务A
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("RollBack_previousStep_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setBusinessKey("bizKey");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		Task task = taskService.createTaskQuery().processDefinitionKey("RollBack_previousStep_1").taskNotEnd().singleResult();
		// 接收C
		Authentication.setAuthenticatedUserId("test01");
		task = taskService.createTaskQuery().processDefinitionKey("RollBack_previousStep_1").taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 处理C
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		// 接收任务B
		Authentication.setAuthenticatedUserId("test02");
		task = taskService.createTaskQuery().processDefinitionKey("RollBack_previousStep_1").taskNotEnd().singleResult();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("claim");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 处理任务B
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskCommandId("HandleCommand_5");
		expandTaskCommand.setCommandType("rollBack_previousStep");
		expandTaskCommand.setTaskId(task.getId());
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查询任务状态
		task = taskService.createTaskQuery().processDefinitionKey("RollBack_previousStep_1").taskNotEnd().singleResult();
		assertEquals("UserTask_3", task.getNodeId());
		assertEquals("test01", task.getAssignee());
		
	}
}
