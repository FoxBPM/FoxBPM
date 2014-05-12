/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 * @author ych
 */
package org.foxbpm.engine.impl.persistence;

import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.task.Task;

/**
 * 任务数据管理器
 * @author kenshin
 */
public class TaskManager extends AbstractManager {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Task> findTasksByNativeQuery(Map<String, Object> parameterMap,int firstResult, int maxResults) {
		return (List)getSqlSession().selectListWithRawParameter("selectTaskByNativeQuery", parameterMap);
	}

	public long findTaskCountByNativeQuery(Map<String, Object> parameterMap) {
		return 0;
	}
	
	public TaskEntity findTaskById(String taskId){
		return selectById(TaskEntity.class, taskId);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TaskEntity> findTasksByProcessInstanceId(String processInstanceId){
		return (List)getSqlSession().selectListWithRawParameter("selectTasksByProcessInstanceId", processInstanceId);
	}
	
}
