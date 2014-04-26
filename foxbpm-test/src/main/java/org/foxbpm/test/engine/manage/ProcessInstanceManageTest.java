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
package org.foxbpm.test.engine.manage;

import java.util.Date;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;

public class ProcessInstanceManageTest extends AbstractFoxBpmManageTestCase {

	public void testSave(){
		ProcessInstanceEntity processEntity = new ProcessInstanceEntity();
		Date date = new Date();
		processEntity.setBizKey("bizKey");
		processEntity.setDefinitionId("definitionId");
		processEntity.setProcessDefinitionId("processDefintionId");
		processEntity.setProcessDefinitionKey("processDefinitionKey");
		processEntity.setParentId("parentId");
		processEntity.setSubject("subject");
		processEntity.setRootTokenId("rootTokenId");
		processEntity.setStartAuthor("startAuthor");
		processEntity.setInitiator("initiator");
		processEntity.setProcessLocation("processLocation");
		processEntity.setStartTime(date);
		processEntity.setEndTime(date);
		processEntity.setArchiveTime(date);
		processEntity.setUpdateTime(date);
		processEntity.setParentTokenId("parentTokenId");
		processEntity.setInstanceStatus("instanceStatus");
		processEntity.setSuspended(true);
		commandContext.getProcessInstanceManager().insert(processEntity);
		
		String processId = processEntity.getId();
		ProcessInstanceEntity process2 = commandContext.getProcessInstanceManager().selectById(ProcessInstanceEntity.class, processId);
		process2.setBizKey("bizKey2");
	}
	
	public void testSelectProcessById(){
		System.out.println("ddddddddddddddddddddddddddddddddddddddddddddd");
		ProcessInstanceEntity process = commandContext.getProcessInstanceManager().selectById(ProcessInstanceEntity.class, "c82d0948-3d1c-4d64-9927-3a12396ef04b");
		assertNotNull(process);
		process.setBizKey("bizkey_2");
		process = commandContext.getProcessInstanceManager().selectById(ProcessInstanceEntity.class, "c82d0948-3d1c-4d64-9927-3a12396ef04b");
		System.out.println(process.getBizKey());
		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		
	}
}
