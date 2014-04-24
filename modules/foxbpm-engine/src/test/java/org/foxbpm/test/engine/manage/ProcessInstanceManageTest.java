
package org.foxbpm.test.engine.manage;

import java.util.Date;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.test.engine.AbstractFixFlowTestCase;

public class ProcessInstanceManageTest extends AbstractFixFlowTestCase {

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
		commandContext.getProcessInstanceManager().insert(processEntity);
		
		String processId = processEntity.getId();
		ProcessInstanceEntity process2 = commandContext.getProcessInstanceManager().selectById(ProcessInstanceEntity.class, processId);
		process2.setBizKey("bizKey2");
	}
	
	public void testSelectProcessById(){
//		System.out.println("ddddddddddddddddddddddddddddddddddddddddddddd");
//		ProcessInstanceEntity process = commandContext.getProcessInstanceManager().selectById(ProcessInstanceEntity.class, "c82d0948-3d1c-4d64-9927-3a12396ef04b");
//		assertNotNull(process);
//		process.setBizKey("bizkey_2");
//		commandContext.getProcessInstanceManager().update(process);
//		process = commandContext.getProcessInstanceManager().selectById(ProcessInstanceEntity.class, "c82d0948-3d1c-4d64-9927-3a12396ef04b");
//		System.out.println(process.getBizKey());
//		System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		
	}
}
