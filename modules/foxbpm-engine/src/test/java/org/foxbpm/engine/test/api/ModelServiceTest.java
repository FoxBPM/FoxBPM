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

import java.util.List;

import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.foxbpm.engine.test.Clear;
import org.foxbpm.engine.test.Deployment;
import org.junit.Test;
import org.springframework.util.Assert;

public class ModelServiceTest extends AbstractFoxBpmTestCase {
	
	
	/**
	 * 功能：获取用户有权限发起的流程
	 * 逻辑：根据流程定义开始节点后面第一个人工任务的任务分配属性获得
	 */
	@Test
	@Clear
	@Deployment(resources = { "process_test_1.bpmn" })
	public void testGetStartProcessByUserId(){
		modelService.getStartProcessByUserId("admin");
	}
	
	/**
	 * 判断用户是否有权限发起流程
	 */
	@Test
	@Deployment(resources = { "process_test_1.bpmn" })
	public void testVerifyStartProcessByUserId(){
		String userId = "admin";
		String processDefinitionKey = "process_test_1";
		boolean result = modelService.verifyStartProcessByUserId(userId, processDefinitionKey, null);
		Assert.isTrue(result);
	}
	
	
	public void testDeploy(){
//		ZipInputStream zipInput = new ZipInputStream(this.getClass().getClassLoader().getResourceAsStream("process_test222.zip"));
//		modelService.deployByZip(zipInput);
	}
	
	public void testUpdateDeploy(){
//		ZipInputStream zipInput = new ZipInputStream(this.getClass().getClassLoader().getResourceAsStream("process_2222.zip"));
//		modelService.updateByZip("e3aa6ab3-3c2b-4e38-8567-97223e3a346b", zipInput);
	}
	
	public void testCmd(){
//		modelService.testCmd("admin");
	}
	
	public void testProcessDefinitionQuery(){
		ProcessDefinitionQuery processQuery = modelService.createProcessDefinitionQuery();
		processQuery.processDefinitionKey("process_test222").orderByProcessDefinitionKey().asc();
		List<ProcessDefinition> process = processQuery.listPage(0, 10);
		System.out.println(process.size());
	}
	
	public void testDeleteDeploy(){
		String deploymentId = "1e496bcd-708e-45a1-8f01-9d5b02b2aa21";
		modelService.deleteDeployment(deploymentId);
	}
	
	@Test
	public void testSVG(){
		String processDefinitionId = "process_test_1:1:377503a3-3e6d-46bc-a801-bdb11e094205";
		modelService.getProcessDefinitionSVG(processDefinitionId);
	}
	
	
}
