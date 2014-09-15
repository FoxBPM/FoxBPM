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
package org.foxbpm.connector.test.actorconnector.RandomAssign;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 资源随机分配
 * 
 * @author yangguangftlp
 * @date 2014年7月11日
 */
public class RandomAssignTest extends AbstractFoxBpmTestCase {

	/**
	 * 资源自动分配
	 * <p>
	 * 1.使用场景：在任务分配给a,b,c时,系统会自动随机分配给其中一个
	 * </p>
	 * <p>
	 * 2.预置条件：
	 * <p>
	 * 1.发布一条带有任务分配（资源随机分配选择器）的流程定义
	 * <p>
	 * 3.处理过程：首先，启动任务使流程进入分配任务节点上
	 * </p>
	 * <p>
	 * 4.测试用例：
	 * </p>
	 * <p>
	 * 1.执行完成后，相应查看资源分配是否是在a,b,c其中
	 * </p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/connector/test/actorconnector/RandomAssign/RandomAssignTest_1.bpmn" })
	public void testRandomAssign() {
		Authentication.setAuthenticatedUserId("admin");
		// 预置用户
		List<String> userIdList = new ArrayList<String>();
		userIdList.add("a");
		userIdList.add("b");
		userIdList.add("c");
		// 启动流程
		runtimeService.startProcessInstanceByKey("RandomAssignTest_1");
		Task task = taskService.createTaskQuery().processDefinitionKey("RandomAssignTest_1").taskNotEnd().singleResult();
		String userId = task.getAssignee();

		if (!userIdList.contains(userId)) {
			throw new RuntimeException("任务分配错误");
		}
	}
}
