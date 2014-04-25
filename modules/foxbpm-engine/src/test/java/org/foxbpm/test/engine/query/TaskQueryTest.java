package org.foxbpm.test.engine.query;

import java.util.List;

import org.foxbpm.engine.query.NativeTaskQuery;
import org.foxbpm.engine.task.Task;
import org.foxbpm.test.engine.AbstractFoxBpmTestCase;

public class TaskQueryTest extends AbstractFoxBpmTestCase {

	public void testTaskQuery(){
		NativeTaskQuery nativeTaskQuery = taskService.createNativeTaskQuery();
		nativeTaskQuery.sql("select * from foxbpm_run_task");
		
		List<Task> list = nativeTaskQuery.list();
		
		assertNotNull(list);
	}
}
