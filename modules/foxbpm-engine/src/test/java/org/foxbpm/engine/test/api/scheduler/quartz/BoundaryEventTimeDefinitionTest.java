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
 * @author MAENLIANG
 */
package org.foxbpm.engine.test.api.scheduler.quartz;

import java.util.List;
import java.util.Map;
import static org.junit.Assert.fail;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.test.Deployment;
import org.foxbpm.engine.test.api.scheduler.BaseSchedulerTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * 
 * BoundaryEventTimeDefinitionTest 测试边界事件时间定义
 * 
 * MAENLIANG 2014年7月25日 下午12:31:35
 * 
 * @version 1.0.0
 * 
 */
public class BoundaryEventTimeDefinitionTest extends BaseSchedulerTest {
	
	@BeforeClass
	public static void initScheduler() {
	}
	
	/**
	 * 测试场景：单个任务节点存在单个终止边界事件
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testCancelActivity_0.bpmn"})
	public void testAA() {
		processKey = "testCancelActivity_0";
	}
	
	@Test
	public void testAB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME);
			// 校验活动节点
			validateActiveTask("UserTask_2");
			
			// 校验令牌
			this.validateToken("UserTask_2");
		} catch (Exception e) {
			fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 测试场景：单个任务节点 单个非终止边界事件
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testNotCancelActivity_0.bpmn"})
	public void testBA() {
		processKey = "testNotCancelActivity_0";
	}
	
	@Test
	public void testBB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME);
			// 校验活动节点，由于是非终止事件所以为产生两个同时活动的节点
			this.validateActiveTask(new String[]{"UserTask_1", "UserTask_2"});
			// 非终止边界事件会产生一个主令牌多个子令牌的情况
			this.validateToken(new String[]{"UserTask_1", "UserTask_1", "UserTask_2"});
			
		} catch (Exception e) {
			fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 
	 * 测试场景：同一个活动节点存在多个非终止边界事件定义
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testManyNoneCancelActivity_0.bpmn"})
	public void testCA() {
		processKey = "testManyNoneCancelActivity_0";
	}
	@Test
	public void testCB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME * 3);
			// 校验活动节点，由于是非终止事件所以为产生两个同时活动的节点
			this.validateActiveTask(new String[]{"UserTask_1", "UserTask_2", "UserTask_3"});
			
			// 非终止边界事件会产生一个主令牌多个子令牌的情况
			this.validateToken(new String[]{"UserTask_1", "UserTask_1", "UserTask_2", "UserTask_3"});
			
		} catch (Exception e) {
			fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 
	 * 测试场景：1、同时存在终止边界事件定义和非终止边界事件定义；2、先执行非终止时间定义
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testCancelNoneCancelActivity_0.bpmn"})
	public void testDA() {
		processKey = "testCancelNoneCancelActivity_0";
	}
	@Test
	public void testDB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME * 3);
			// 校验活动节点，由于是非终止事件所以为产生两个同时活动的节点
			this.validateActiveTask(new String[]{"UserTask_5"});
			
			// 非终止边界事件会产生一个主令牌多个子令牌的情况
			this.validateToken(new String[]{"UserTask_5"});
			
		} catch (Exception e) {
			fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 
	 * 测试场景：当活动节点离开时候，如果还有未触发的边界事件时间调度器，那么就清空该调度器
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testDeleteSchedulerAfterLeave_0.bpmn"})
	public void testEA() {
		processKey = "testDeleteSchedulerAfterLeave_0";
	}
	@Test
	public void testEB() {
		// 启动流程实例
		processInstanceID = runtimeService.startProcessInstanceByKey(processKey).getId();
	}
	
	@Test
	public void testEC() {
		try {
			List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT ID FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
			String groupName = tokenList.get(0).get("ID").toString() + "_UserTask_1";
			// 校验 quartz初始化
			this.validateQuartsCount(groupName, 1);
			
			List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
			// 执行任务
			Authentication.setAuthenticatedUserId("admin");
			ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
			expandTaskCommand.setCommandType("submit");
			expandTaskCommand.setInitiator("admin");
			expandTaskCommand.setTaskCommandId("HandleCommand_2");
			expandTaskCommand.setTaskComment("asfd");
			expandTaskCommand.setBusinessKey("bbb");
			expandTaskCommand.setTaskId(taskList.get(0).get("ID").toString());
			taskService.expandTaskComplete(expandTaskCommand, null);
			
			// 校验 quartz清空
			this.validateQuartsCount(groupName, 0);
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 
	 * 测试场景：当活动节点离开时候，如果还有未触发的调度器，那么就清空该调度器,所有附属在该活动节点的调度器都清空
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/deleteManySchedulerAfterNodeLeave_0.bpmn"})
	public void testFA() {
		processKey = "deleteManySchedulerAfterNodeLeave_0";
	}
	@Test
	public void testFB() {
		// 启动流程实例
		processInstanceID = runtimeService.startProcessInstanceByKey(processKey).getId();
	}
	
	@Test
	public void testFC() {
		try {
			List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT ID FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
			String groupName = tokenList.get(0).get("ID").toString() + "_UserTask_1";
			// 校验 quartz初始化
			this.validateQuartsCount(groupName, 2);
			
			List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
			// 执行任务
			Authentication.setAuthenticatedUserId("admin");
			ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
			expandTaskCommand.setCommandType("submit");
			expandTaskCommand.setInitiator("admin");
			expandTaskCommand.setTaskCommandId("HandleCommand_2");
			expandTaskCommand.setTaskComment("asfd");
			expandTaskCommand.setBusinessKey("bbb");
			expandTaskCommand.setTaskId(taskList.get(0).get("ID").toString());
			taskService.expandTaskComplete(expandTaskCommand, null);
			
			// 校验 quartz清空
			this.validateQuartsCount(groupName, 0);
		} finally {
			deleteProcessDefinition();
		}
	}
	
}
