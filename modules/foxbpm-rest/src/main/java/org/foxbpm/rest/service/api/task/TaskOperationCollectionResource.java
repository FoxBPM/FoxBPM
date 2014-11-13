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
package org.foxbpm.rest.service.api.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxbpmPluginException;
import org.foxbpm.engine.impl.entity.ProcessOperatingEntity;
import org.foxbpm.engine.impl.entity.UserEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.resource.Get;

/**
 * 获取所有对任务的操作
 * @author ych
 *
 */
public class TaskOperationCollectionResource extends AbstractRestResource {

	@Get
	public DataResult getOperation(){
		
		String taskId = getAttribute("taskId");
		if(StringUtil.isEmpty(taskId)){
			throw new FoxbpmPluginException("任务编号为空", "Rest服务");
		}
		ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		List<ProcessOperatingEntity> operations = taskService.getTaskOperations(taskId);
		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
		if(operations != null && operations.size() >0){
			for(ProcessOperatingEntity tmp : operations){
				Map<String,Object> tmpMap = tmp.getPersistentState();
				String operator = StringUtil.getString(tmpMap.get("operator"));
				String operatorName = "空用户名";
				if(StringUtil.isNotEmpty(operator)){
					UserEntity user = Authentication.selectUserByUserId(operator);
					if(user != null){
						operatorName = user.getUserName();
					}
				}
				tmpMap.put("operatorName", operatorName);
				resultMap.add(tmpMap);
			}
		}
		DataResult dataResult = new DataResult();
		dataResult.setData(resultMap);
		dataResult.setTotal(resultMap.size());
		return dataResult;
	}
}
