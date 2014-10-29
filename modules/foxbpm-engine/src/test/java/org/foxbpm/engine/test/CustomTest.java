package org.foxbpm.engine.test;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.junit.Test;

public class CustomTest extends AbstractFoxBpmTestCase {

	@Test
	public void testAddTask(){
		
		TaskEntity task = (TaskEntity)taskService.newTask();
		
		task.setAssignee("admin");
		task.setTaskType("chedan");
		task.setFormUri("cccc");
		
		taskService.saveTask(task);
	}
}
