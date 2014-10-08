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
 * @author ych
 */
package org.foxbpm.rest.service.api.engine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.Task;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 获取可退回任务
 * @author ych
 *
 */
public class RollbackTaskCollectionResource extends AbstractRestResource{

	@Get
	public DataResult getRollbackTask(){
		
		String taskId = getAttribute(RestConstants.TASK_ID);
		if(StringUtil.isEmpty(taskId)){
			throw new FoxBPMIllegalArgumentException("taskId is null");
		}
		List<Map<String,Object>> resultList= new ArrayList<Map<String,Object>>();
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		List<Task> tasks = taskService.getRollbackTasks(taskId);
		if(tasks != null && !tasks.isEmpty()){
			for(Task task : tasks){
				Map<String,Object> map = task.getPersistentState();
				String userId = task.getAssignee();
				map.put("assgneeUserName", getUserName(userId));
				Date endDate = task.getEndTime();
				String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endDate);
				map.put("endTime", endTime);
				resultList.add(map);
			}
		}
		DataResult result = new DataResult();
		result.setData(resultList);
		result.setTotal(resultList.size());
		return result;
	}
}
