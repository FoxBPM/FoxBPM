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
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.Token;
import org.foxbpm.engine.runtime.TokenQuery;
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
	 * <p>测试通过流程定义Key启动流程</p>
	 * <p>通过相应接口测试相应的数据正确性</p>
	 * <p>   1.验证流程是否正常启动</p>
	 * <p>   2.验证bizKey是否正确存储</p>
	 * <p>   3.验证持久化变量是否正确存储</p>
	 * <p>   4.验证有瞬态变量的表达式 计算结果是否正确(流程开始线条上写有表达式)</p>
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
		 * 测试带参数的流程启动
		 */
		
		//持久化变量
		Map<String,Object> variable = new HashMap<String, Object>();
		variable.put("variable", "variableValue");
		
		//瞬态变量
		Map<String,Object> transVariable = new HashMap<String, Object>();
		transVariable.put("瞬态变量", "瞬态");
		
		/**
		 * 带参数的流程启动
		 */
		processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1","bizKey",transVariable,variable);
		processInstanceId = processInstance.getId();
		assertEquals("bizKey",processInstance.getBizKey());
		
		VariableQuery variableQuery = runtimeService.createVariableQuery();
		VariableInstance variableInstance = variableQuery.processInstanceId(processInstanceId).addVariableKey("持久变量").singleResult();
		//验证变量是否存储，（节点上通过瞬态变量进行了计算）
		assertEquals("瞬态变化", variableInstance.getValueObject());
		
		variableQuery = runtimeService.createVariableQuery();
		variableInstance = variableQuery.processInstanceId(processInstanceId).addVariableKey("variable").singleResult();
		//验证持久化变量是否存储
		assertEquals("variableValue", variableInstance.getValueObject());
		
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证节点是否正确
		assertEquals("UserTask_1", task.getNodeId());
	}
	
	/**
	 *<p>和上面byKey用的同一个重载方法，所以测试代码基本相同</p>
	 * <p>测试通过流程定义id启动流程</p>
	 * <p>通过相应接口测试相应的数据正确性</p>
	 * <p>   1.验证流程是否正常启动</p>
	 * <p>   2.验证bizKey是否正确存储</p>
	 * <p>   3.验证持久化变量是否正确存储</p>
	 * <p>   4.验证有瞬态变量的表达式 计算结果是否正确(流程开始线条上写有表达式)</p>
	 */
	@Test
	@Deployment(resources = { "org/foxbpm/test/api/Test_RuntimeService_1.bpmn"})
	public void testStartProcessInstanceById(){
		ProcessDefinitionQuery processDefinitionQuery = modelService.createProcessDefinitionQuery();
		
		ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionKey("Test_RuntimeService_1").singleResult();
		String processDefinitionId = processDefinition.getId();
		
		/**
		 * 测试不带参数的启动流程
		 */
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
		String processInstanceId = processInstance.getId();
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证当前节点是否在UserTask_1
		assertEquals("UserTask_1", task.getNodeId());
		
		/**
		 * 测试带bizKey的参数的启动流程
		 */
		processInstance = runtimeService.startProcessInstanceById(processDefinitionId,"bizKey");
		//验证bizKey是否正确存储
		assertEquals("bizKey", processInstance.getBizKey());
		processInstanceId = processInstance.getId();
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证节点是否正确
		assertEquals("UserTask_1", task.getNodeId());
		
		
		/**
		 * 测试带参数的流程启动
		 */
		
		//持久化变量
		Map<String,Object> variable = new HashMap<String, Object>();
		variable.put("variable", "variableValue");
		
		//瞬态变量
		Map<String,Object> transVariable = new HashMap<String, Object>();
		transVariable.put("瞬态变量", "瞬态");
		
		/**
		 * 带参数的流程启动
		 */
		processInstance = runtimeService.startProcessInstanceById(processDefinitionId,"bizKey",transVariable,variable);
		processInstanceId = processInstance.getId();
		assertEquals("bizKey",processInstance.getBizKey());
		
		VariableQuery variableQuery = runtimeService.createVariableQuery();
		VariableInstance variableInstance = variableQuery.processInstanceId(processInstanceId).addVariableKey("持久变量").singleResult();
		//验证变量是否存储，（节点上通过瞬态变量进行了计算）
		assertEquals("瞬态变化", variableInstance.getValueObject());
		
		variableQuery = runtimeService.createVariableQuery();
		variableInstance = variableQuery.processInstanceId(processInstanceId).addVariableKey("variable").singleResult();
		//验证持久化变量是否存储
		assertEquals("variableValue", variableInstance.getValueObject());
		
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证节点是否正确
		assertEquals("UserTask_1", task.getNodeId());
		
	}
	
	/**
	 *<p>测试流程手工驱动方法</p>
	 * <p>启动流程->验证当前节点在UserTask_1->驱动->验证当前节点再userTask_2</p>
	 * <p>通过相应接口测试相应的数据正确性</p>
	*/
	@Test
	@Deployment(resources = { "org/foxbpm/test/api/Test_RuntimeService_1.bpmn"})
	public void testSignal(){
		/**
		 * 测试不带参数的启动流程
		 */
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Test_RuntimeService_1");
		String processInstanceId = processInstance.getId();
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证当前节点是否在UserTask_1
		assertEquals("UserTask_1", task.getNodeId());
		
		TokenQuery tokenQuery = runtimeService.createTokenQuery();
		String tokenId = tokenQuery.processInstanceId(processInstanceId).singleResult().getId();

		runtimeService.signal(tokenId);
		
		Token token = runtimeService.createTokenQuery().processInstanceId(processInstanceId).singleResult();
		assertEquals("UserTask_2",token.getNodeId());
		
		/**
		 * 目前没有写节点的清理事件 ，所以此处不通过
		 */
		taskQuery = taskService.createTaskQuery();
		task = taskQuery.processInstanceId(processInstanceId).taskNotEnd().singleResult();
		//验证当前节点是否在UserTask_1
		assertEquals("UserTask_2", task.getNodeId());
	}

}
