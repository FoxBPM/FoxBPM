package org.foxbpm.web.service.interfaces;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.task.Task;
import org.foxbpm.web.common.util.Pagination;

/**
 * 任务接口
 * 
 * @author MEL
 * @date 2014-06-04
 */
public interface IWebappTaskService {

	public void completeTask(String taskId);

	public List<Task> queryTask();

	/**
	 * 查询代办任务
	 * 
	 * @param pageInfor
	 *            分页对象
	 * @param params
	 *            查询条件
	 * @return 返回查询结果
	 */
	public Map<String, Object> queryToDoTask(Pagination<String> pageInfor,
			Map<String, Object> requestParams);
}
