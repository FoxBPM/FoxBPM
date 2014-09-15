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
package org.foxbpm.engine.test.function;

import static org.junit.Assert.assertNotNull;

import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * 多实例测试类
 * @author ych
 *
 */
public class MultiInstanceTest extends AbstractFoxBpmTestCase{

	/**
	 * <p>并行多实例测试类</p>
	 * <p>测试用例：发起流程-admin.admin2,admin3 三人会签，其中：admin同意，admin2拒绝，admin3同意，结果：会签通过，流程结束</p>
	 */
	@Test
	@Deployment(resources={"org/foxbpm/test/function/Test_MultiInstance_1.bpmn"})
	public void testMultiInstance(){
		
		/***1.测试处理过程，同意-拒绝-同意,结果应为：会签处理通过，流程实例结束**/
		Authentication.setAuthenticatedUserId("admin");
		ExpandTaskCommand expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setCommandType("startandsubmit");
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setBusinessKey("bizKey");
		expandTaskCommand.setProcessDefinitionKey("Test_MultiInstance_1");
		expandTaskCommand.setInitiator("admin");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//admin同意
		String taskId = taskService.createTaskQuery().processDefinitionKey("Test_MultiInstance_1").taskNotEnd().taskAssignee("admin").singleResult().getId();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(taskId);
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("general");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//admin2拒绝
		Authentication.setAuthenticatedUserId("admin2");
		Task task = taskService.createTaskQuery().processDefinitionKey("Test_MultiInstance_1").taskNotEnd().taskAssignee("admin2").singleResult();
		taskId =task.getId();
		String processInstanceId = task.getProcessInstanceId();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(taskId);
		expandTaskCommand.setTaskCommandId("HandleCommand_3");
		expandTaskCommand.setCommandType("general");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		//admin3同意
		Authentication.setAuthenticatedUserId("admin3");
		taskId = taskService.createTaskQuery().processDefinitionKey("Test_MultiInstance_1").taskNotEnd().taskAssignee("admin3").singleResult().getId();
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(taskId);
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("general");
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		assertNotNull(processInstance.getEndTime());
		
	}
}
