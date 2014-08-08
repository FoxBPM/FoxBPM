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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 任务命令的rest服务
 * <p>接收query参数ProcessKey,taskId</p>
 * <p>如：taskCommands?processKey=Expense_1&taskId=ych</p>
 * <p>优先处理taskId</p>
 * @author ych
 *
 */
public class TaskCommandCollectionResource extends AbstractRestResource {

	@Get
	public DataResult getTaskCommands(){
		Form query = getQuery();
		String processKey = getQueryParameter(RestConstants.PROCESS_KEY, query);
		String taskId = getQueryParameter(RestConstants.TASK_ID, query); 
		List<TaskCommand> taskCommands = null;
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		if(StringUtil.isNotEmpty(taskId)){
			taskCommands = taskService.getTaskCommandByTaskId(taskId);
			
		}else{
			if(StringUtil.isEmpty(processKey)){
				throw new FoxBPMException("查询任务命令时参数不足");
			}
			taskCommands = taskService.getSubTaskCommandByKey(processKey);
		}
		
		if(!taskCommands.isEmpty()){
			for(TaskCommand taskCommand : taskCommands){
				resultList.add(taskCommand.getPersistentState());
			}
		}
		
		DataResult result = new DataResult();
		result.setData(resultList);
		result.setStart(0);
		result.setTotal(resultList.size());
		return result;
		
	}
}
