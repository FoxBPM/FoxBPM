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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.TaskService;
import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.rest.common.RestConstants;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 获取可退回节点
 * @author ych
 *
 */
public class RollbackNodeCollectionResource extends AbstractRestResource{

	@Get
	public DataResult getRollbackNode(){
		
		Form query = getQuery();
		String taskId = getQueryParameter(RestConstants.TASK_ID, query);
		if(StringUtil.isEmpty(taskId)){
			throw new FoxBPMIllegalArgumentException("taskId is null");
		}
		List<Map<String,String>> resultList= new ArrayList<Map<String,String>>();
		TaskService taskService = FoxBpmUtil.getProcessEngine().getTaskService();
		List<KernelFlowNode> flowNodes = taskService.getRollbackFlowNode(taskId);
		Map<String, String> tmpResult = null;
		if(flowNodes != null && !flowNodes.isEmpty()){
			for(KernelFlowNode tmpFlowNode : flowNodes){
				tmpResult = new HashMap<String, String>();
				tmpResult.put("nodeId", tmpFlowNode.getId());
				tmpResult.put("nodeName", tmpFlowNode.getName());
				resultList.add(tmpResult);
			}
		}
		DataResult result = new DataResult();
		result.setData(resultList);
		result.setTotal(resultList.size());
		return result;
	}
}
