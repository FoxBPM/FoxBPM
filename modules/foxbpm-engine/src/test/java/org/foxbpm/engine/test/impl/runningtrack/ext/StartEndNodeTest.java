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
package org.foxbpm.engine.test.impl.runningtrack.ext;

import static org.junit.Assert.assertTrue;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * 流程启动和结束事件处理
 * 
 * @author yangguangftlp
 * @date 2014年7月16日
 */
public class StartEndNodeTest extends AbstractFoxBpmTestCase {

	/**
	 * 任务启动和结束时记录任务信息
	 * <p>1.使用场景：任务启动和结束需要记录信息 </p>
	 * <p>2.预置条件：<p>
	 *          1.发布流程定义
	 * <p>3.处理过程：首先，启动任务使流程结束</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看流程启动和结束任务信息</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/engine/test/impl/runningtrack/ext/StartEndNode_1.bpmn" })
	public void testFlowStartEnd() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey("StartEndNode_1");
		// 查看是否插入启动任务信息
		String sql = "select DISTINCT RES.* from FOXBPM_RUN_TASK RES WHERE   PROCESSINSTANCE_ID = '" + pi.getId() + "' and TASKTYPE = 'starteventtask'";
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
		boolean flag = false;
		if (rowSet.next()) {
			System.out.println(rowSet.getString("NODE_ID"));
			flag = true;
		}
		assertTrue("任务开始失败", flag);
		// 查询流程并驱动流程
		Task task = this.taskService.createTaskQuery().processDefinitionKey("StartEndNode_1").taskNotEnd().singleResult();

		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setProcessDefinitionKey("StartEndNode_1");
		expandTaskCommand.setTaskCommandId("HandleCommand_2");
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("a");
		// 执行进入任务节点
		taskService.expandTaskComplete(expandTaskCommand, null);
		// 查看是否插入启动任务信息
		sql = "select DISTINCT RES.* from FOXBPM_RUN_TASK RES WHERE   PROCESSINSTANCE_ID = '" + pi.getId() + "' and TASKTYPE = 'endeventtask'";
		rowSet = jdbcTemplate.queryForRowSet(sql);
		flag = false;
		if (rowSet.next()) {
			System.out.println(rowSet.getString("NODE_ID"));
			flag = true;
		}
		assertTrue("任务结束失败", flag);
	}
}
