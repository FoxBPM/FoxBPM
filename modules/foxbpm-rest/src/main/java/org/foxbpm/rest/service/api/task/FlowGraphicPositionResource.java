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
 * @author yangguangftlp
 */
package org.foxbpm.rest.service.api.task;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.exception.FoxbpmPluginException;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.DataResult;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 流程位置信息
 * 
 * @author yangguangftlp
 * @date 2014年9月24日
 */
public class FlowGraphicPositionResource extends AbstractRestResource {
	
	@Get
	public DataResult getPositionInfor() {
		Form query = getQuery();
		String processDefinitionId = getQueryParameter("processDefinitionId", query);
		if (StringUtil.isEmpty(processDefinitionId)) {
			throw new FoxbpmPluginException("流程定义唯一编号为空", "Rest服务");
		}
		ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
		ModelService modelService = processEngine.getModelService();
		Map<String, Map<String, Object>> positionInfor = modelService.getFlowGraphicsElementPositionById(processDefinitionId);
		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("positionInfor", positionInfor);
		DataResult result = new DataResult();
		result.setData(resultData);
		return result;
		
	}
}
