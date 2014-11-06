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
package org.foxbpm.connector.test.actorconnector.SelectRole;

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 选择角色
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class SelectRoleTest extends AbstractFoxBpmTestCase {

	/**
	 * 任务分配给一个角色或多个角色
	 * <p>1.使用场景：需要将任务分配给一个角色或多个角色</p>
	 * <p>2.预置条件：<p>
	 *          1.存在角色
	 * <p>3.处理过程：首先，启动任务使流程进入分配任务节点上</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看资源是否分配给相应角色</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/SelectRole/SelectRoleTest_1.bpmn" })
	public void testSelectDepartment0() {
		Authentication.setAuthenticatedUserId("admin");
		// 预置角色
		jdbcTemplate.execute("insert into au_roleinfo(roleid,rolename) VALUES ('2001','2001')");
		// 预置用户用户
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('a','管理员Tst')");
		// 设置用户和部门关系
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('10000000000000001','a','2001','role')");
		// 启动流程触发任务分配
		runtimeService.startProcessInstanceByKey("SelectRoleTest_1");
		Task task = (Task) taskService.createTaskQuery().processDefinitionKey("SelectRoleTest_1").taskNotEnd().singleResult();
		// 同时分配给两个人
		String taskId = task.getId();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from foxbpm_run_taskidentitylink WHERE TASK_ID = '" + taskId + "'");
		String groupId = null;
		while (sqlRowSet.next()) {
			groupId = sqlRowSet.getString("GROUP_ID");
		}
		assertEquals("2001", groupId);
	}
}
