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

import java.io.InputStream;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.engine.runtime.ProcessInstanceQuery;
import org.foxbpm.rest.common.api.AbstractRestResource;
import org.foxbpm.rest.common.api.FoxBpmUtil;
import org.restlet.data.Form;
import org.restlet.resource.Get;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年8月12日
 */
public class FlowGraphicImgResource extends AbstractRestResource {
	@Get
	public InputStream getFlowGraphicImg() {
		Form query = getQuery();
		String processInstanceId = getQueryParameter("processInstanceId", query);
		String processDefinitionKey = getQueryParameter("processDefinitionKey", query);
		// 获取引擎
		ProcessEngine processEngine = FoxBpmUtil.getProcessEngine();
		ModelService modelService = processEngine.getModelService();
		// 获取图片
		InputStream in = null;
		// 流程定义id
		String processDefinitionId = null;
		// 流程定义Id
		if (StringUtil.isEmpty(processInstanceId)) {
			// 流程定义Key
			if (StringUtil.isEmpty(processDefinitionKey)) {
				throw new FoxBPMBizException("processDefinitionKey is null !");
			}
			ProcessDefinition processDefinition = modelService.getLatestProcessDefinition(processDefinitionKey);
			if (null == processDefinition) {
				throw new FoxBPMBizException("无效的流程定义key:" + processDefinitionKey);
			}
			processDefinitionId = processDefinition.getId();
		} else {
			ProcessInstanceQuery processInstanceQuery = processEngine.getRuntimeService().createProcessInstanceQuery();
			ProcessInstance processInstance = processInstanceQuery.processInstanceId(processInstanceId).singleResult();
			if (null == processInstance) {
				throw new FoxBPMBizException("无效的流程实例id:" + processInstanceId);
			}
			processDefinitionId = processInstance.getProcessDefinitionId();
		}
		 
		in = modelService.GetFlowGraphicsImgStreamByDefId(processDefinitionId);
		return in;
	}
 
}
