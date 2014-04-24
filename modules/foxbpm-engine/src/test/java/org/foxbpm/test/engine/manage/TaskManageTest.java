package org.foxbpm.test.engine.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.task.DelegationState;
import org.foxbpm.engine.task.TaskType;

public class TaskManageTest extends AbstractFoxBpmManageTestCase {

	
	public void testSave() throws ParseException{
		String taskId = GuidUtil.CreateGuid();
		//创建任务实例
		TaskEntity taskInstanceEntity = new TaskEntity();
		taskInstanceEntity.setId(taskId);
		taskInstanceEntity.setName("name");
		taskInstanceEntity.setDescription("description");
		taskInstanceEntity.setProcessInstanceId("processInstanceId");
		taskInstanceEntity.setProcessDefinitionId("processDefinitionId");
		taskInstanceEntity.setProcessDefinitionKey("processDefinitionKey");
		taskInstanceEntity.setVersion(0);
		taskInstanceEntity.setTokenId("tokenId");
		taskInstanceEntity.setNodeId("nodeId");
		taskInstanceEntity.setNodeName("nodeName");
		taskInstanceEntity.setParentId("parentTaskInstanceId");
		taskInstanceEntity.setAssignee("assignee");
		taskInstanceEntity.setClaimTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25"));
		taskInstanceEntity.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25"));
		taskInstanceEntity.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25"));
		taskInstanceEntity.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25"));
		taskInstanceEntity.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25"));
		taskInstanceEntity.setArchiveTime(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-25"));
		taskInstanceEntity.setBlocking(true);
		taskInstanceEntity.setPriority(2);
		taskInstanceEntity.setCategory("category");
		taskInstanceEntity.setOwner("owner");
		taskInstanceEntity.setDelegationState(DelegationState.PENDING);
		taskInstanceEntity.setBizKey("bizKey");
		taskInstanceEntity.setTaskComment("taskComment");
		taskInstanceEntity.setFormUri("formUri");
		taskInstanceEntity.setFormUriView("formUriView");
		taskInstanceEntity.setTaskGroup("taskGroup");
		taskInstanceEntity.setTaskType("foxbpmtask");
		taskInstanceEntity.setCancelled(true);
		taskInstanceEntity.setSuspended(true);
		taskInstanceEntity.setOpen(false);
		taskInstanceEntity.setDraft(true);
		taskInstanceEntity.setExpectedExecutionTime(3);
		taskInstanceEntity.setAgent("agent");
		taskInstanceEntity.setAdmin("admin");
		taskInstanceEntity.setCallActivityInstanceId("callActivitiProcessInstanceId");
		taskInstanceEntity.setPendingTaskId("pendingTaskId");
		taskInstanceEntity.setCommandId("commandId");
		taskInstanceEntity.setCommandType("commandType");
		taskInstanceEntity.setCommandMessage("commandMessage");
		////////测试保存方法
		//保存任务实例
		commandContext.getTaskManager().insert(taskInstanceEntity);
		commandContext.flushSession();
		//查询任务实例
		TaskEntity taskInstanceEntity2 = commandContext.getTaskManager().selectById(TaskEntity.class, taskId);
		//验证查询结果正确
		assertEquals(taskId, taskInstanceEntity2.getId());
		assertEquals("name", taskInstanceEntity2.getName());
		assertEquals("description", taskInstanceEntity2.getDescription());
		assertEquals("processInstanceId", taskInstanceEntity2.getProcessInstanceId());
		assertEquals("processDefinitionId", taskInstanceEntity2.getProcessDefinitionId());
		assertEquals("processDefinitionKey", taskInstanceEntity2.getProcessDefinitionKey());
		assertEquals(0, taskInstanceEntity2.getVersion());
		assertEquals("tokenId", taskInstanceEntity2.getTokenId());
		assertEquals("nodeId",taskInstanceEntity2.getNodeId());
		assertEquals("nodeName", taskInstanceEntity2.getNodeName());
		
	}
}
