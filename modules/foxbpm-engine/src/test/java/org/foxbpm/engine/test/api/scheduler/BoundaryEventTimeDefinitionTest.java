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
package org.foxbpm.engine.test.api.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoundaryEventTimeDefinitionTest extends AbstractFoxBpmTestCase {
	
	private static String processDefinitionID;
	private static String processInstanceID;
	private static String processKey;
	/** 每个测试用例 quartz完成调度所需要的基本时间：2分钟 */
	private final static int QUART_SCHEDULED_TIME = 2;
	
	/** 列名称 */
	private final static String NODE_ID = "NODE_ID";
	private final static String PROCESS_ID = "PROCESS_ID";
	
	/**
	 * 测试场景：单个任务节点存在单个终止边界事件
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testCancelActivity_0.bpmn"})
	public void testBA() {
		this.cleanRunData();
		processKey = "testCancelActivity_0";
	}
	
	@Test
	public void testBB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME);
			// 校验
			List<Map<String, Object>> processResultList = jdbcTemplate.queryForList("SELECT PROCESS_ID FROM FOXBPM_DEF_PROCESSDEFINITION WHERE PROCESS_KEY='"
			        + processKey + "' ");
			processDefinitionID = (String) processResultList.get(0).get(PROCESS_ID);
			List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSDEFINITION_ID ='"
			        + processDefinitionID + "' ORDER BY CREATE_TIME");
			assertNotNull(taskList);
			assertEquals(taskList.size(), 3);
			processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
			String nodeId = (String) taskList.get(0).get("NODE_ID");
			assertEquals(nodeId, "StartEvent_2");
			nodeId = (String) taskList.get(1).get(NODE_ID);
			assertEquals(nodeId, "UserTask_1");
			nodeId = (String) taskList.get(2).get(NODE_ID);
			assertEquals(nodeId, "UserTask_2");
			
			List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' ORDER BY START_TIME");
			assertNotNull(tokenList);
			assertEquals(tokenList.size(), 2);
			nodeId = (String) taskList.get(0).get(NODE_ID);
			assertEquals(nodeId, "UserTask_1");
			nodeId = (String) taskList.get(1).get(NODE_ID);
			assertEquals(nodeId, "UserTask_2");
			scheduler.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * cleanRunData(清空流程运行过程中的数据) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	protected void cleanRunData() {
		jdbcTemplate.execute("delete from foxbpm_run_processinstance");
		jdbcTemplate.execute("delete from foxbpm_run_task");
		jdbcTemplate.execute("delete from foxbpm_run_taskidentitylink");
		jdbcTemplate.execute("delete from foxbpm_run_token");
		jdbcTemplate.execute("delete from foxbpm_run_variable");
	}
	
	/**
	 * 
	 * waitQuartzScheduled(设置调度等待的时间)
	 * 
	 * @param minitues
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void waitQuartzScheduled(int minitues) {
		try {
			Thread.sleep(1000 * 60 * minitues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
