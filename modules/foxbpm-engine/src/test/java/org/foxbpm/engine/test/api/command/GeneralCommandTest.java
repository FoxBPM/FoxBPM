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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.task.command.ExpandTaskCommand;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

public class GeneralCommandTest extends AbstractFoxBpmTestCase {
	
	/**
	 * 启动并提交命令测试类
	 * <p>1.使用场景：一般做“同意”按钮用，直接结束当前任务，驱动当前令牌向下流转，后面如果多线条，则会令牌分散，生成多个任务</p>
	 * <p>2.参数：基础参数（必填：taskId,taskCommandId,taskCommandType,task 可选：transVariable,variable）</p>
	 * <p>3.处理过程：首先，根据processKey启动流程，然后执行提交按钮，并且传bizKey参数,</p>
	 * <p>4.测试用例：</p>
	 * <p>		1.执行完成后，判断流程是否存在2个task</p>
	 * <p>		2.执行完成后，判断当前节点是否在userTask2节点</p>
	 * <p>		3.判断bizKey是否正确存储</p>
	 * <p>		4.判断流程变量是否正确存储</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/command/simpleTaskCommandTest_1.bpmn" })
	public void testGeneralCommand(){
		
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
		
		
		//启动流程结束，下面开始执行通用按钮，驱动流程向下走
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("simpleTaskCommandTest_1");
		task = taskQuery.taskNotEnd().singleResult();
		
		expandTaskCommand = new ExpandTaskCommand();
		expandTaskCommand.setTaskId(task.getId());
		expandTaskCommand.setTaskCommandId("HandleCommand_1");
		expandTaskCommand.setCommandType("general");
		Map<String,Object> variableMap = new HashMap<String, Object>();
		variableMap.put("变量", "修改后的变量");
		expandTaskCommand.setPersistenceVariables(variableMap);
		taskService.expandTaskComplete(expandTaskCommand, null);
		
		taskQuery = taskService.createTaskQuery();
		processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		assertTrue(processInstance.isEnd());
		VariableInstance variable = runtimeService.createVariableQuery().processInstanceId(processInstance.getId()).addVariableKey("变量").singleResult();
		assertEquals("修改后的变量", variable.getValueObject());
	}
}
