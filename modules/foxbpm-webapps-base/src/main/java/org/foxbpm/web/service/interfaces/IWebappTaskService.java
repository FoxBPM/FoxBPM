package org.foxbpm.web.service.interfaces;

import java.util.List;

import org.foxbpm.engine.task.Task;

/**
 * 任务接口
 * 
 * @author MEL
 * @date 2014-06-04
 */
public interface IWebappTaskService {
	public void completeTask(String taskId);

	public List<Task> queryTask();
}
