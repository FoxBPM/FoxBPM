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

import org.foxbpm.engine.test.Deployment;
import org.foxbpm.engine.test.api.scheduler.BaseSchedulerTest;
import org.junit.Assert;
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
	/**
	 * 测试场景：日期时间定时启动流程实例
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testTimeStart_0.bpmn"})
	public void testAA() {
		processKey = "testTimeStart_0";
	}
	
	@Test
	public void testAB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME);
			// 校验
			this.validateActiveTask("UserTask_1");
			
			this.validateToken("UserTask_1");
			
			this.validateProcessInstanceCount(0);
			
		} catch (Exception e) {
			Assert.fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 测试场景：间隔性启动流程实例
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testDurationStart_0.bpmn"})
	public void testBA() {
		processKey = "testDurationStart_0";
	}
	
	@Test
	public void testBB() {
		try {
			scheduler.start();
			this.waitQuartzScheduled(QUART_SCHEDULED_TIME);
			this.scheduler.standby();
			// 校验间隔性启动产生的活动节点、活动令牌、流程实例
			// 可以只需要校验间隔性产生的个数，不用校验具体内容
			int resultCount = 1;
			this.validateActiveTaskCount(resultCount);
			this.validateActiveTokenCount(resultCount);
			this.validateProcessInstanceCount(resultCount);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
	/**
	 * 
	 * 测试场景 ：当删除一个存在自动启动流程实例调度器的流程定义时，同时也将持久化的调度器从数据库中删除
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testDeleteSchedulerSameTime_0.bpmn"})
	public void testCA() {
		processKey = "testDeleteSchedulerSameTime_0";
	}
	
	@Test
	public void testCB() {
		// 删除流程定义之前校验
		validateQuartsCount(processKey, 1);
		
		// 执行流程定义删除操作
		this.deleteProcessDefinition();
		
		// 校验quartz数据是否清空
		validateQuartsCount(processKey, 0);
	}
}
