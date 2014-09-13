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
package org.foxbpm.connector.test.actorconnector.LastStepActorConnector;

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 返回上一步处理者
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class LastStepActorConnectorTest extends AbstractFoxBpmTestCase {
	/**
	 * 任务分配给上一步骤处理人
	 * <p>1.使用场景：将任务分配给上一步骤处理人</p>
	 * <p>2.预置条件：<p>
	 *          1.发布一条带有任务分配（分配上一步骤处理人）的流程定义
	 * <p>3.处理过程：首先，启动任务使流程进入分配任务节点上</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，相应查看资源分配是否是上一步骤处理人</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/LastStepActorConnector/LastStepActorConnectorTest_1.bpmn" })
	public void testLastStepActorConnector() {
		Authentication.setAuthenticatedUserId("admin");
		// 启动流程
		runtimeService.startProcessInstanceByKey("LastStepActorConnectorTest_1");
		Task task = taskService.createTaskQuery().processDefinitionKey("LastStepActorConnectorTest_1").taskNotEnd().singleResult();
		assertEquals("admin", task.getAssignee());
	}
}
