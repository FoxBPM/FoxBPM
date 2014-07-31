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

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.test.Deployment;
import org.foxbpm.engine.test.api.scheduler.BaseSchedulerTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * 
 * ConnectorExecuteTest 连接器定时执行测试
 * 
 * MAENLIANG 2014年7月29日 上午10:07:40
 * 
 * @version 1.0.0
 * 
 */
public class ConnectorTimeExecuteTest extends BaseSchedulerTest {
	public static String validateValue = "";
	
	/**
	 * 测试场景：日期时间定时执行连接器
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testTimeConnector_0.bpmn"})
	public void testAA() {
	}
	
	@Test
	public void testAB() {
		try {
			scheduler.start();
			waitQuartzScheduled(QUART_SCHEDULED_TIME);
			// 校验连接器是否执行成功
			assertEquals(validateValue, "true");
		} catch (Exception e) {
			Assert.fail();
		} finally {
			deleteProcessDefinition();
		}
	}
	
}
