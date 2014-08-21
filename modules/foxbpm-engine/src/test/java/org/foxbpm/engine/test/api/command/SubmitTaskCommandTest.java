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
 * @author ych
 */
package org.foxbpm.engine.test.api.command;

import static org.junit.Assert.assertEquals;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class SubmitTaskCommandTest extends AbstractFoxBpmTestCase {

	/**
	 * 启动并提交命令测试类
	 * <p>1.使用场景：流程启动后，第一个节点使用，如：系统月底启动月底填报流程，需要各单位填写完，提交上报！</p>
	 * <p>2.处理过程：首先，根据processKey启动流程，然后执行提交按钮，并且传bizKey参数</p>
	 * <p>3.测试用例：</p>
	 * <p>		1.执行完成后，判断流程是否存在2个task</p>
	 * <p>		2.执行完成后，判断当前节点是否在userTask2节点</p>
	 * <p>		3.判断bizKey是否正确存储</p>
	 * <p>		4.判断流程变量是否正确存储</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/simpleTaskCommandTest_1.bpmn" })
	public void TestSubmitTaskCommand(){
		Authentication.setAuthenticatedUserId("admin");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleTaskCommandTest_1");
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
		
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setTaskCommandId("HandleCommand_4");
		expandTaskCommand.setCommandType("submit");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("simpleTaskCommandTest_1");
		long result = taskQuery.count();
		assertEquals(3, result);
		taskQuery.taskNotEnd();
		task = taskQuery.singleResult();
		assertEquals("UserTask_2", task.getNodeId());
		assertEquals("bizKey", task.getBizKey());
	}
}
