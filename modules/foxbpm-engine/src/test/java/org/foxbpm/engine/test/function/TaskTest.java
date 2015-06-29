package org.foxbpm.engine.test.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;

public class TaskTest extends AbstractFoxBpmTestCase {
	
	@Test
	public void testOptimisticLockingThrownOnMultipleUpdates() {
		Task task = taskService.newTask();
		taskService.saveTask(task);
		String taskId = task.getId();
		
		// first modification
		TaskEntity task1 = (TaskEntity)taskService.createTaskQuery().taskId(taskId).singleResult();
		TaskEntity task2 = (TaskEntity)taskService.createTaskQuery().taskId(taskId).singleResult();
		
		task1.setDescription("first modification");
		taskService.saveTask(task1);
		
		// second modification on the initial instance
		task2.setDescription("second modification");
		try {
			taskService.saveTask(task2);
			fail("should get an exception here as the task was modified by someone else.");
		} catch (FoxBPMException e) {
			// exception was thrown as expected
		
		}
		
		taskService.deleteTask(taskId, true);
	}
	@Test
	public void testRevisionUpdatedOnSave() {
		TaskEntity task = taskService.newTask();
		taskService.saveTask(task);
		assertEquals(1, ((TaskEntity) task).getRevision());
		
		task.setDescription("first modification");
		taskService.saveTask(task);
		assertEquals(2, ((TaskEntity) task).getRevision());
		
		task.setDescription("second modification");
		taskService.saveTask(task);
		assertEquals(3, ((TaskEntity) task).getRevision());
		
		taskService.deleteTask(task.getId(), true);
	}
	@Test
	public void testRevisionUpdatedOnSaveWhenFetchedUsingQuery() {
		TaskEntity task = taskService.newTask();
		taskService.saveTask(task);
		assertEquals(1, ((TaskEntity) task).getRevision());
		
		task.setAssignee("kermit");
		taskService.saveTask(task);
		assertEquals(2, ((TaskEntity) task).getRevision());
		
		// Now fetch the task through the query api
		task = (TaskEntity)taskService.createTaskQuery().taskId(task.getId()).singleResult();
		assertEquals(2, ((TaskEntity) task).getRevision());
		task.setDescription("ych");
		taskService.saveTask(task);
		
		assertEquals(3, ((TaskEntity) task).getRevision());
		
		taskService.deleteTask(task.getId(), true);
	}
}
