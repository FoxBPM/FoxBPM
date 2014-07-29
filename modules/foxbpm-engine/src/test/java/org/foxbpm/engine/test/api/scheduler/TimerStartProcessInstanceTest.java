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

import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 
 * 
 * TimerStartProcessInstanceTest 测试定时启动流程实例
 * 
 * MAENLIANG 2014年7月25日 下午12:31:58
 * 
 * @version 1.0.0
 * 
 */
public class TimerStartProcessInstanceTest extends BaseSchedulerTest {
	
	private static String processDefinitionID;
	private static String processInstanceID;
	private static String processKey; 
	
	/** 列名称 */
	private final static String NODE_ID = "NODE_ID";
	private final static String PROCESS_ID = "PROCESS_ID";
	
	/**
	 * 测试场景：日期时间定时启动流程实例
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testTimeStart_0.bpmn"})
	public void testAA() {
		this.cleanRunData();
		processKey = "testTimeStart_0";
	}
	
	@Test
	public void testAB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME);
			// 校验
			List<Map<String, Object>> processResultList = jdbcTemplate.queryForList("SELECT PROCESS_ID FROM FOXBPM_DEF_PROCESSDEFINITION WHERE PROCESS_KEY='"
			        + processKey + "' ");
			processDefinitionID = (String) processResultList.get(0).get(PROCESS_ID);
			List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSDEFINITION_ID ='"
			        + processDefinitionID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
			assertNotNull(taskList);
			assertEquals(taskList.size(), 1);
			processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
			String nodeId = (String) taskList.get(0).get("NODE_ID");
			assertEquals(nodeId, "UserTask_1");
			
			List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
			assertNotNull(tokenList);
			assertEquals(tokenList.size(), 1);
			nodeId = (String) tokenList.get(0).get(NODE_ID);
			assertEquals(nodeId, "UserTask_1");
			
			List<Map<String, Object>> processInstanceResultList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_PROCESSINSTANCE where ID ='"
			        + processInstanceID + "' ");
			assertNotNull(processInstanceResultList);
			assertEquals(processInstanceResultList.size(), 1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试场景：间隔性启动流程实例
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testDurationStart_0.bpmn"})
	public void testBA() {
		this.cleanRunData();
		processKey = "testDurationStart_0";
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
			        + processDefinitionID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
			assertNotNull(taskList);
			assertEquals(taskList.size(), 13);
			
			processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
			
			List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
			        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
			assertNotNull(tokenList);
			assertEquals(tokenList.size(), 13);
			
			List<Map<String, Object>> processInstanceResultList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_PROCESSINSTANCE where ID ='"
			        + processInstanceID + "' ");
			assertNotNull(processInstanceResultList);
			assertEquals(processInstanceResultList.size(), 13);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
