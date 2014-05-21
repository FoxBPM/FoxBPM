package org.foxbpm.engine.test.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;

public class TaskServiceTest extends AbstractFoxBpmTestCase {

	public void testTaskQuery(){
		
		
		Map<String, Object> transientVariables=new HashMap<String, Object>();
		transientVariables.put("value", 10);
		ProcessInstance processInstance=runtimeService.startProcessInstanceById
		("FirstFoxbpm:1:916131d2-4598-4e4c-952f-c13c76c77f71","bizkey",transientVariables, null);
		
		
		Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
		taskService.complete(task.getId());
		
		task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskNotEnd().singleResult();
		taskService.complete(task.getId());
		
		processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		
		assertTrue(processInstance.isEnd());
		
	}
}
