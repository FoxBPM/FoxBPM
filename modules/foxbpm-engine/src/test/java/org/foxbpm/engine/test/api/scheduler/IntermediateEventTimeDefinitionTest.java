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

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 
 * 
 * IntermediateEventTimeDefinitionTest 测试中间捕获事件时间定义
 * 
 * MAENLIANG 2014年7月29日 上午11:45:21
 * 
 * @version 1.0.0
 * 
 */
public class IntermediateEventTimeDefinitionTest extends BaseSchedulerTest {
	
	/**
	 * 测试场景：单个中间事件定义，日期时间定时执行
	 */
	@Test
	@Deployment(resources = {"org/foxbpm/engine/test/impl/scheduler/testIntermediateTimeDefinition_0.bpmn"})
	public void testAA() {
		cleanRunData();
		processKey = "testIntermediateTimeDefinition_0";
	}
	@Test
	public void testAB() {
		Authentication.setAuthenticatedUserId("admin");
		// 完成任务，启动流程实例并且提交第一个人工任务
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setInitiator("admin");
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setTaskComment("asfd");
		expandTaskCommand.setBusinessKey("bbb");
		expandTaskCommand.setProcessDefinitionKey(processKey);
		ProcessInstance processInstance = taskService.expandTaskComplete(expandTaskCommand, null);
		processInstanceID = processInstance.getId();
		
		// 校验令牌停在 IntermediateCatchEvent_2节点
		this.validateToken("IntermediateCatchEvent_2");
	}
	@Test
	public void testAC() {
		try {
			scheduler.start();
			waitQuartzScheduled(QUART_SCHEDULED_TIME);
			
			// 校验活动的节点
			this.validateActiveTask("UserTask_5");
			
			// 校验令牌
			this.validateToken("UserTask_5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
