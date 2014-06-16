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
import java.util.Map;

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
	@Deployment(resources = { "process_ma_1.bpmn" })
	public void testGetStartProcessByUserId(){
//		List<Map<String,Object>> list = modelService.getStartProcessByUserId("admin");
//		System.out.println(list.size());
	}
	
	/**
	 * 判断用户是否有权限发起流程
	 */
	@Test
	@Deployment(resources = { "process_test_1.bpmn" })
	public void testVerifyStartProcessByUserId(){
		String userId = "admin";
		boolean result = modelService.verifyStartProcessByUserId(userId, null);
		Assert.isTrue(result);
	}
	
	@Test
	public void testDeploy(){
//		ZipInputStream zipInput = new ZipInputStream(this.getClass().getClassLoader().getResourceAsStream("process_test222.zip"));
//		modelService.deployByZip(zipInput);
		Map<String,Map<String,Object>> result = modelService.getFlowGraphicsElementPositionByKey("process_test_fang_1");
		System.out.println(result);
//		modelService.GetFlowGraphicsImgStreamByDefId("process_test_fang_1:1:3fc97b4d-1fb8-476e-8e05-317fb327d92d");
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
	
	@Test
	public void testDeleteDeploy(){
		String deploymentId = "3e280222-7f82-4182-a7c7-2756a4bc0548";
		modelService.deleteDeployment(deploymentId);
	}
	
	@Test
	public void testSVG(){
		
		String processDefinitionId = "process_ma_1:1:caffbffe-b7fe-466d-955f-8e7db3252389";
		modelService.getProcessDefinitionSVG(processDefinitionId);
	} 
}
