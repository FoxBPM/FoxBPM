package org.foxbpm.engine.test.api;

import java.util.List;

import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;

public class TaskServiceTest extends AbstractFoxBpmTestCase {

	public void testTaskQuery(){
		
		//api
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.taskIsEnd();
		List<Task> tasks = taskQuery.list();
		System.out.println("taskIsEnd****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskNotEnd();
		tasks = taskQuery.list();
		System.out.println("taskNotEnd****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.isSuspended(true);
		tasks = taskQuery.list();
		System.out.println("isSuspended****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.tokenId("tokenId");
		tasks = taskQuery.list();
		System.out.println("tokenId****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.initiator("initiator");
		tasks = taskQuery.list();
		System.out.println("initiator****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskId("taskId");
		tasks = taskQuery.list();
		System.out.println("taskId****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskName("taskName");
		tasks = taskQuery.list();
		System.out.println("taskName****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskNameLike("taskNameLike");
		tasks = taskQuery.list();
		System.out.println("taskNameLike****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.businessKey("businessKey");
		tasks = taskQuery.list();
		System.out.println("businessKey****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.businessKeyLike("businessKeyLike");
		tasks = taskQuery.list();
		System.out.println("businessKeyLike****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.addTaskType("addTaskType");
		tasks = taskQuery.list();
		System.out.println("addTaskType****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskDescription("taskDescription");
		tasks = taskQuery.list();
		System.out.println("taskDescription****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskDescriptionLike("taskDescriptionLike");
		tasks = taskQuery.list();
		System.out.println("taskDescriptionLike****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskAssignee("taskAssignee");
		tasks = taskQuery.list();
		System.out.println("taskAssignee****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskOwner("taskOwner");
		tasks = taskQuery.list();
		System.out.println("taskOwner****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskUnnassigned();
		tasks = taskQuery.list();
		System.out.println("taskUnnassigned****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.taskCandidateUser("admin");
		tasks = taskQuery.list();
		System.out.println("taskCandidateUser****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.processInstanceId("processInstanceId");
		tasks = taskQuery.list();
		System.out.println("processInstanceId****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionKey("processDefinitionKey");
		tasks = taskQuery.list();
		System.out.println("processDefinitionKey****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionId("processDefinitionId");
		tasks = taskQuery.list();
		System.out.println("processDefinitionId****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionName("processDefinitionName");
		tasks = taskQuery.list();
		System.out.println("processDefinitionName****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.processDefinitionNameLike("processDefinitionNameLike");
		tasks = taskQuery.list();
		System.out.println("processDefinitionNameLike****************"+tasks.size());
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.nodeId("nodeId");
		tasks = taskQuery.list();
		System.out.println("nodeId****************"+tasks.size());
		
		//order by 
		
		taskQuery = taskService.createTaskQuery();
		taskQuery.nodeId("nodeId").orderByEndTime().asc().orderByProcessInstanceId().desc().orderByTaskAssignee().asc();
		taskQuery.orderByTaskCreateTime().asc().orderByTaskDescription().desc().orderByTaskId().asc().orderByTaskName().desc();
		tasks = taskQuery.list();
		System.out.println("nodeId****************"+tasks.size());
		
		//page测试
		taskQuery = taskService.createTaskQuery();
		taskQuery.nodeId("nodeId");
		tasks = taskQuery.listPage(0, 15);
		System.out.println("nodeId****************"+tasks.size());
		
	}
}
