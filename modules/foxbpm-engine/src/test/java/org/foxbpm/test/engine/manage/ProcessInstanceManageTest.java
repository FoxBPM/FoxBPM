
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
		commandContext.getProcessInstanceManager().save(processEntity);
	}
	
	public void testSelectProcessById(){
		ProcessInstanceEntity process = commandContext.getProcessInstanceManager().selectProcessById("c82d0948-3d1c-4d64-9927-3a12396ef04b");
		assertNotNull(process);
	}
}
