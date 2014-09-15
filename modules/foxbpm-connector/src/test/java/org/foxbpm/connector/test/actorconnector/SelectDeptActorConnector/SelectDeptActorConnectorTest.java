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
package org.foxbpm.connector.test.actorconnector.SelectDeptActorConnector;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.exception.FoxBPMConnectorException;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 选择部门
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class SelectDeptActorConnectorTest extends AbstractFoxBpmTestCase {

	/**
	 * 资源共享
	 * <p>1.使用场景：将任务分配给某部门(部门可以多个)</p>
	 * <p>2.预置条件：<p>
	 *          1.发布一条带有任务分配（分配部门）的流程定义
	 * <p>3.处理过程：首先，启动任务使流程进入分配任务节点上</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看资源分配是否是所以人都能看得到</p>
	 * <p>		2.执行完成后，相应查看foxbpm_run_taskidentitylink任务是否分配给部门</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/SelectDeptActorConnector/SelectDeptActorConnectorTest_1.bpmn" })
	public void testSelectDeptActorConnector() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		runtimeService.startProcessInstanceByKey("SelectDeptActorConnectorTest_1");
		Task task = taskService.createTaskQuery().processDefinitionKey("SelectDeptActorConnectorTest_1").taskNotEnd().singleResult();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from foxbpm_run_taskidentitylink WHERE TASK_ID = '" + task.getId() + "'");
		List<String> userIds = new ArrayList<String>();
		while (sqlRowSet.next()) {
			userIds.add(sqlRowSet.getString("GROUP_ID"));
		}
		if (!userIds.contains("a") || !userIds.contains("b")) {
			throw new FoxBPMConnectorException("选择用户连接器出现异常");
		}
	}
}
