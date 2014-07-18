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
package org.foxbpm.engine.test.api;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.datavariable.VariableInstance;
import org.foxbpm.engine.datavariable.VariableQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;

/**
 * runTimeService 测试类
 * @author ych
 *
 */
public class RuntimeServiceTest extends AbstractFoxBpmTestCase {

	/**
	 * 测试通过流程定义Key启动流程
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/api/Test_RuntimeService_1.bpmn"})
	public void testStartProcessInstanceByKey(){
		
		/**
		 * 测试不带参数的启动流程
		 */
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1");
		String processInstanceId = processInstance.getId();
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证当前节点是否在UserTask_1
		assertEquals("UserTask_1", task.getNodeId());
		
		/**
		 * 测试带bizKey的参数的启动流程
		 */
		processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1","bizKey");
		//验证bizKey是否正确存储
		assertEquals("bizKey", processInstance.getBizKey());
		processInstanceId = processInstance.getId();
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证节点是否正确
		assertEquals("UserTask_1", task.getNodeId());
		
		/**
		 * 测试持久化变量的启动流程
		 */
		Map<String,Object> variable = new HashMap<String, Object>();
		variable.put("variable", "variableValue");
		processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1",null,variable);
		processInstanceId = processInstance.getId();
		
		VariableQuery variableQuery = runtimeService.createVariableQuery();
		VariableInstance variableInstance = variableQuery.processInstanceId(processInstanceId).addVariableKey("variable").singleResult();
		//验证变量是否存储
		assertEquals("variableValue", variableInstance.getValueObject());
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证节点是否正确
		assertEquals("UserTask_1", task.getNodeId());
		
		/**
		 * 测试瞬态变量的启动流程
		 */
		Map<String,Object> transVariable = new HashMap<String, Object>();
		transVariable.put("瞬态变量", "瞬态");
		processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1",transVariable,null);
		processInstanceId = processInstance.getId();
		
		variableQuery = runtimeService.createVariableQuery();
		variableInstance = variableQuery.processInstanceId(processInstanceId).addVariableKey("持久变量").singleResult();
		//验证变量是否存储，（节点上通过瞬态变量进行了计算）
		assertEquals("瞬态变化", variableInstance.getValueObject());
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证节点是否正确
		assertEquals("UserTask_1", task.getNodeId());
	}
	
	
	
	
	
	@Test
	public void testTokenQuery() {
		// TokenQuery tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.tokenId("2222");
		// List<Token> tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());
		//
		// tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.processInstanceId("18aebfe6-c30a-40a8-ba6d-ce6c19114ba8");
		// tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());
		//
		// tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.tokenIsEnd();
		// tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());
		//
		// tokenQuery = runtimeService.createTokenQuery();
		// tokenQuery.tokenNotEnd();
		// tokens = tokenQuery.list();
		// System.out.println("********************"+tokens.size());

	}

	public void testProcessInstanceQuery() {
		// ProcessInstanceQuery processQuery =
		// runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceId("222");
		// List<ProcessInstance> process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionId("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionKey("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionName("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionNameLike("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionNameLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.initiator("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.initiatorLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.subject("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.subjectLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceBusinessKey("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceBusinessKeyLike("%222%");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.taskParticipants("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processInstanceStatus(ProcessInstanceStatus.RUNNING);
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.isEnd();
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.notEnd();
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
		//
		// processQuery = runtimeService.createProcessInstanceQuery();
		// processQuery.processDefinitionId("222");
		// process = processQuery.list();
		// System.out.println("********************"+process.size());
	}
}
