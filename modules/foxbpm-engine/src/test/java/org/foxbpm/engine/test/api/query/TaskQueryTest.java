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
package org.foxbpm.engine.test.api.query;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 任务查询接口测试类
 * @author ych
 *
 */
public class TaskQueryTest extends AbstractFoxBpmTestCase {

	
	/**
	 * 测试代理查询相关接口
	 * 将用户admin的test_agentQuery_1流程代理给test_admin2
	 * 启动test_agentQuery_1流程，查询admin代理给test_admin2的任务
	 * 验证结果是否正确
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/query/test_agentQuery_1.bpmn" })
	public void testAgentQuery(){
		
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME) VALUES ('test_admin2','管理员2')";
		String sqlInsertAgent = "insert into foxbpm_agent(id,agent_user,startTime,endTime,status) values('_test_agent','admin',?,?,'1')";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.YEAR, 1);
		String sqlInsertDetails = "insert into foxbpm_agent_details(id,agent_id,processDefinition_key,agent_touser) values('_test_agent_details','_test_agent','test_agentQuery_1','test_admin2')";
		
		jdbcTemplate.update(sqlInsertAgent, c.getTime(),c2.getTime());
		jdbcTemplate.execute(sqlInsertUser);
		jdbcTemplate.execute(sqlInsertDetails);
		
		//启动流程
		runtimeService.startProcessInstanceByKey("test_agentQuery_1");
		//完成第一步
		Task task = taskService.createTaskQuery().processDefinitionKey("test_agentQuery_1").taskNotEnd().singleResult();
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandCommand = new ExpandTaskCommand();
		
		expandCommand.setTaskId(task.getId());
		expandCommand.setTaskCommandId("HandleCommand_1");
		expandCommand.setCommandType("general");
		
		taskService.expandTaskComplete(expandCommand, null);
		
		//查询admin2代理任务 查询admin代理给admin2的代理任务
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.taskAssignee("test_admin2");
		taskQuery.taskCandidateUser("test_admin2");
		taskQuery.isAgent(true);
		taskQuery.agentId("admin");
		taskQuery.taskNotEnd();
		taskQuery.processDefinitionKey("test_agentQuery_1");
		
		List<Task> tasks = taskQuery.list();
		assertEquals(1, tasks.size());
	}
}
