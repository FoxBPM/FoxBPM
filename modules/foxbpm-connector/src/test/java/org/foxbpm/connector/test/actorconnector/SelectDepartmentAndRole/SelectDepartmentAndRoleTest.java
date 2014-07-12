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
package org.foxbpm.connector.test.actorconnector.SelectDepartmentAndRole;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.cache.CacheUtil;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 将任务分配给既是属于部门和角色的人
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class SelectDepartmentAndRoleTest extends AbstractFoxBpmTestCase {

	/**
	 * 任务分配给同时属于部门和角色下的用户
	 * <p>1.使用场景：需要将任务分配给部门和角色</p>
	 * <p>2.预置条件：<p>
	 *          1.同时存在用户是属于部门和角色
	 * <p>3.处理过程：首先，启动任务使流程进入分配任务节点上</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看资源是否分配给相应用户</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/SelectDepartmentAndRoleTest/SelectDepartmentAndRoleTest_1.bpmn" })
	public void testSelectDepartment() {
		Authentication.setAuthenticatedUserId("admin");

		// 1、当只存在一个用户时，需要将该任务分配给该用户独占
		// 预置部门用户
		jdbcTemplate.execute("insert into au_orginfo(orgid,suporgid) VALUES ('2001','2001')");
		// 预置角色
		jdbcTemplate.execute("insert into au_roleinfo(roleid,rolename) VALUES ('3001','3001')");
		// 预置用户用户
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('a','管理员Tst')");
		// 设置用户和部门关系
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('10000000000000001','a','2001','dept')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('10000000000000002','a','3001','role')");

		// 启动流程触发任务分配
		runtimeService.startProcessInstanceByKey("SelectDepartmentAndRoleTest_1");
		Task task = (Task) taskService.createTaskQuery().processDefinitionKey("SelectDepartmentAndRoleTest_1").singleResult();
		assertEquals("a", task.getAssignee());

		// 存在多个用户时需要共享
		jdbcTemplate.execute("insert into au_userInfo(userId,USERNAME) VALUES ('b','管理员Tst')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('10000000000000003','b','2001','dept')");
		jdbcTemplate.execute("insert into au_group_relation(guid,userid,groupid,grouptype) VALUES ('10000000000000004','b','3001','role')");
		CacheUtil.clearCache();
		// 启动流程触发任务分配
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("SelectDepartmentAndRoleTest_1");

		List<String> userIds = new ArrayList<String>();
		List<Task> taskList = taskService.createTaskQuery().processDefinitionKey("SelectDepartmentAndRoleTest_1").taskNotEnd().list();
		if (null != taskList) {
			for (Task task2 : taskList) {
				if (task2.getProcessInstanceId().equals(processInstance.getId())) {
					task = task2;
				}
			}
		}
		// 同时分配给两个人
		String taskId = task.getId();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from foxbpm_run_taskidentitylink WHERE TASK_ID = '" + taskId + "'");
		while (sqlRowSet.next()) {
			userIds.add(sqlRowSet.getString("USER_ID"));
		}
		if (!userIds.contains("a") && !userIds.contains("b")) {
			throw new RuntimeException("任务分配失败!");
		}
	}
}
