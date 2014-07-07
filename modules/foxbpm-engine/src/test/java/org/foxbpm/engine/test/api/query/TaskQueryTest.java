package org.foxbpm.engine.test.api.query;

import java.util.Calendar;
import java.util.List;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class TaskQueryTest extends AbstractFoxBpmTestCase {

	
	@Test
	@Deployment(resources = { "org/foxbpm/test/query/test_agentQuery_1.bpmn" })
	public void testAgentQuery(){
		
		String sqlInsertUser = "insert into au_userInfo(userId,USERNAME) VALUES ('admin2','管理员2')";
		String sqlInsertAgent = "insert into foxbpm_agent(id,agent_user,startTime,endTime,staus) values('_test_agent','admin',?,?,'1')";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -1);
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.YEAR, 1);
		String sqlInsertDetails = "insert into foxbpm_agent_details(id,agent_id,processDefinition_key,agent_touser) values('_test_agent_details','_test_agent','test_agentQuery_1','admin2')";
		
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
		taskQuery.taskAssignee("admin2");
		taskQuery.taskCandidateUser("admin2");
		taskQuery.isAgent(true);
		taskQuery.agentId("admin");
		taskQuery.processDefinitionKey("test_agentQuery_1");
		
		List<Task> tasks = taskQuery.list();
		System.out.println(tasks.size());
	}
}
