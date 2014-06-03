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
import java.util.zip.ZipInputStream;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;

public class ModelServiceTest extends AbstractFoxBpmTestCase {
	
	public void testStartProcessById(){
//		ProcessInstance processInstance=runtimeService.startProcessInstanceById("process_test222:1:35b1da34-aaf2-4acf-ba1e-b27b20885124","bizkeyValue");
//		NativeTaskQuery nativeTaskQuery=processEngine.getTaskService().createNativeTaskQuery();
//		List<Task> tasks = nativeTaskQuery.sql("SELECT * FROM FOXBPM_RUN_TASK").list();
//		ProcessInstanceEntity processInstanceEntity=(ProcessInstanceEntity)processInstance;
//		runtimeService.signal(processInstanceEntity.getRootTokenId());
//		tasks = nativeTaskQuery.sql("SELECT * FROM FOXBPM_RUN_TASK").list();
//		assertNotNull(processInstance);
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
	
	
}
