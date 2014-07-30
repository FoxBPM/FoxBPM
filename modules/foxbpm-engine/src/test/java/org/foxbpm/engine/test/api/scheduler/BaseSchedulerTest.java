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
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 
 * BaseSchedulerTest quartz框架测试的父类，负责一些共通性工作。 quartz測試說明 1、导入
 * foxbpm_connector项目jar包 2、每一个测试用例包括两个方法 testXA，testXB.....
 * A方法用于清空数据，部署流程定义文件，B方法用户启动quartz引擎，校验结果......
 * 
 * MAENLIANG 2014年7月29日 上午10:20:39
 * 
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseSchedulerTest extends AbstractFoxBpmTestCase {
	protected static String processDefinitionID;
	protected static String processInstanceID;
	protected static String processKey;
	
	/** 列名称 */
	protected final static String NODE_ID = "NODE_ID";
	protected final static String PROCESS_ID = "PROCESS_ID";
	
	/** 每个测试用例 quartz完成调度所需要的基本时间：2分钟 */
	public final static int QUART_SCHEDULED_TIME = 2;
	
	/**
	 * 
	 * 校验调度器的数量
	 * 
	 * @param groupName
	 * @param count
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateQuartsCount(String groupName, int count) {
		List<Map<String, Object>> beforeResult = jdbcTemplate.queryForList("SELECT * FROM qrtz_job_details where job_group='"
		        + groupName + "'");
		assertEquals(beforeResult.size(), count);
	}
	/**
	 * 
	 * validateProcessInstanceCount(校验间隔性自动执行产生的流程实例个数)
	 * 
	 * @param count
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateProcessInstanceCount(int count) {
		List<Map<String, Object>> processInstanceResultList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_PROCESSINSTANCE where ID ='"
		        + processInstanceID + "' ");
		assertNotNull(processInstanceResultList);
		assertEquals(processInstanceResultList.size(), count);
	}
	
	/**
	 * 
	 * validateActiveTaskCount(校验间隔性自动执行产生的活动节点个数)
	 * 
	 * @param count
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateActiveTaskCount(int count) {
		List<Map<String, Object>> processResultList = jdbcTemplate.queryForList("SELECT PROCESS_ID FROM FOXBPM_DEF_PROCESSDEFINITION WHERE PROCESS_KEY='"
		        + processKey + "' ");
		processDefinitionID = (String) processResultList.get(0).get(PROCESS_ID);
		List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSDEFINITION_ID ='"
		        + processDefinitionID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
		assertNotNull(taskList);
		assertEquals(taskList.size(), count);
		
		processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
	}
	
	/**
	 * 
	 * validateActiveTokenCount(校验间隔性自动执行产生的活动令牌个数)
	 * 
	 * @param count
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateActiveTokenCount(int count) {
		List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where  END_TIME IS NULL ORDER BY START_TIME");
		assertNotNull(tokenList);
		assertEquals(tokenList.size(), count);
	}
	
	/**
	 * 
	 * validateToken(校验令牌)
	 * 
	 * @param nodeId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateToken(String nodeId) {
		List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
		        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
		assertNotNull(tokenList);
		assertEquals(tokenList.size(), 1);
		assertEquals((String) tokenList.get(0).get(NODE_ID), nodeId);
	}
	
	/**
	 * 
	 * validateToken(边界事件存在主令牌、子令牌的情况)
	 * 
	 * @param nodeIdA
	 * @param nodeIdB
	 * @param nodeIdC
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateToken(String nodeIdA, String nodeIdB, String nodeIdC) {
		List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
		        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
		assertNotNull(tokenList);
		assertEquals(tokenList.size(), 3);
		assertEquals((String) tokenList.get(0).get(NODE_ID), nodeIdA);
		assertEquals((String) tokenList.get(1).get(NODE_ID), nodeIdB);
		assertEquals((String) tokenList.get(2).get(NODE_ID), nodeIdC);
	}
	
	/**
	 * 
	 * 当多个非终止事件定时执行的时候，需要校验多个令牌
	 * 
	 * @param nodeIds
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateToken(String[] nodeIds) {
		List<Map<String, Object>> tokenList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TOKEN where PROCESSINSTANCE_ID ='"
		        + processInstanceID + "' AND END_TIME IS NULL ORDER BY START_TIME");
		assertNotNull(tokenList);
		assertEquals(tokenList.size(), nodeIds.length);
		for (int i = 0; i < nodeIds.length; i++) {
			assertEquals((String) tokenList.get(i).get(NODE_ID), nodeIds[i]);
		}
		
	}
	
	/**
	 * 
	 * validateActiveTask(校验单个的活动节点)
	 * 
	 * @param nodeId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateActiveTask(String nodeId) {
		List<Map<String, Object>> processResultList = jdbcTemplate.queryForList("SELECT PROCESS_ID FROM FOXBPM_DEF_PROCESSDEFINITION WHERE PROCESS_KEY='"
		        + processKey + "' ");
		processDefinitionID = (String) processResultList.get(0).get(PROCESS_ID);
		List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSDEFINITION_ID ='"
		        + processDefinitionID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
		processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
		assertNotNull(taskList);
		assertEquals(taskList.size(), 1);
		assertEquals(nodeId, (String) taskList.get(0).get("NODE_ID"));
	}
	
	/**
	 * 
	 * validateActiveTask(校验两个同时活动的节点)
	 * 
	 * @param nodeIdA
	 * @param nodeIdB
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateActiveTask(String nodeIdA, String nodeIdB) {
		List<Map<String, Object>> processResultList = jdbcTemplate.queryForList("SELECT PROCESS_ID FROM FOXBPM_DEF_PROCESSDEFINITION WHERE PROCESS_KEY='"
		        + processKey + "' ");
		processDefinitionID = (String) processResultList.get(0).get(PROCESS_ID);
		List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSDEFINITION_ID ='"
		        + processDefinitionID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
		assertNotNull(taskList);
		assertEquals(taskList.size(), 2);
		processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
		String nodeId = (String) taskList.get(0).get("NODE_ID");
		assertEquals(nodeId, nodeIdA);
		nodeId = (String) taskList.get(1).get(NODE_ID);
		assertEquals(nodeId, nodeIdB);
	}
	
	/**
	 * 
	 * validateActiveTask(校验多个活动任务的情况)
	 * 
	 * @param nodeIds
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void validateActiveTask(String[] nodeIds) {
		List<Map<String, Object>> processResultList = jdbcTemplate.queryForList("SELECT PROCESS_ID FROM FOXBPM_DEF_PROCESSDEFINITION WHERE PROCESS_KEY='"
		        + processKey + "' ");
		processDefinitionID = (String) processResultList.get(0).get(PROCESS_ID);
		List<Map<String, Object>> taskList = jdbcTemplate.queryForList("SELECT * FROM FOXBPM_RUN_TASK WHERE PROCESSDEFINITION_ID ='"
		        + processDefinitionID + "' AND END_TIME IS NULL ORDER BY CREATE_TIME");
		assertNotNull(taskList);
		assertEquals(taskList.size(), nodeIds.length);
		processInstanceID = (String) taskList.get(0).get("PROCESSINSTANCE_ID");
		for (int i = 0; i < nodeIds.length; i++) {
			assertEquals((String) taskList.get(i).get("NODE_ID"), nodeIds[i]);
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
	protected void waitQuartzScheduled(int minitues) {
		try {
			Thread.sleep(1000 * 60 * minitues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
