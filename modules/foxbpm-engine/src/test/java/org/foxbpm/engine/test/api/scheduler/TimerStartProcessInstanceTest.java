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
			this.validateActiveTask("UserTask_1");
			
			this.validateToken("UserTask_1");
			
			this.validateProcessInstanceCount(1);
			
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
			
			// 校验间隔性启动产生的活动节点、活动令牌、流程实例
			// 可以只需要校验间隔性产生的个数，不用校验具体内容
			int resultCount = (QUART_SCHEDULED_TIME * 60 / 10) + 1;
			this.validateActiveTaskCount(resultCount);
			this.validateActiveTokenCount(resultCount);
			this.validateProcessInstanceCount(resultCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
