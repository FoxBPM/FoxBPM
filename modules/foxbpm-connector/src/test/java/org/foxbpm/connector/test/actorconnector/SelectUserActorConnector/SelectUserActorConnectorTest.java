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
package org.foxbpm.connector.test.actorconnector.SelectUserActorConnector;

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
 * 任务分配给指定用户
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class SelectUserActorConnectorTest extends AbstractFoxBpmTestCase {

	/**
	 * 任务分配给指定用户可以多个
	 * <p>1.使用场景：需要将任务分配指定用户</p>
	 * <p>2.预置条件：创建一个含有人工任务并且任务分配给指定用户的流程定义并发布<p>
	 * <p>3.处理过程：首先，启动任务使流程进入分配任务节点上</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看资源是否分配给指定用户</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/SelectUserActorConnector/SelectUserActorConnectorTest_1.bpmn" })
	public void testSelectUserActorConnector() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		runtimeService.startProcessInstanceByKey("SelectUserActorConnectorTest_1");
		Task task = taskService.createTaskQuery().processDefinitionKey("SelectUserActorConnectorTest_1").taskNotEnd().singleResult();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from foxbpm_run_taskidentitylink WHERE TASK_ID = '" + task.getId() + "'");
		List<String> userIds = new ArrayList<String>();
		while (sqlRowSet.next()) {
			userIds.add(sqlRowSet.getString("USER_ID"));
		}
		if (!userIds.contains("a") || !userIds.contains("b")) {
			throw new FoxBPMConnectorException("选择用户连接器出现异常");
		}
	}
}
