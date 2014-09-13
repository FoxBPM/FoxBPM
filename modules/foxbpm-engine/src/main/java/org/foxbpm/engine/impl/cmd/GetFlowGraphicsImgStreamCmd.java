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
package org.foxbpm.engine.impl.cmd;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.repository.ProcessDefinition;

/**
 * 
 * 获取流程图流文件 参数不可同时为null，如果同时存在，优先处理processDefinitionId
 * 
 * @author kenshin
 * 
 */
public class GetFlowGraphicsImgStreamCmd implements Command<InputStream> {
	
	protected String processDefinitionId;
	protected String processDefinitionKey;
	
	public GetFlowGraphicsImgStreamCmd(String processDefinitionId, String processDefinitionKey) {
		this.processDefinitionId = processDefinitionId;
		this.processDefinitionKey = processDefinitionKey;
	}
	
	public InputStream execute(CommandContext commandContext) {
		ProcessDefinition processDefinition = null;
		DeploymentManager deploymentCache = Context.getProcessEngineConfiguration().getDeploymentManager();
		if (this.processDefinitionId != null && !this.processDefinitionId.equals("")) {
			processDefinition = deploymentCache.findDeployedProcessDefinitionById(processDefinitionId);
		} else {
			if (this.processDefinitionKey != null && !this.processDefinitionKey.equals("")) {
				processDefinition = deploymentCache.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
			} else {
				throw new FoxBPMIllegalArgumentException("查询流程图的processDefinitionId、processDefinitionKey不能都为空!");
			}
		}
		String deploymentId = processDefinition.getDeploymentId();
		String diagramResourceName = processDefinition.getDiagramResourceName();
		ResourceEntity resourceEntity = commandContext.getResourceManager().selectResourceByDeployIdAndName(deploymentId, diagramResourceName);
		if (null == resourceEntity) {
			throw new FoxBPMIllegalArgumentException("查询流程图为空,对应processDefinitionId=" + processDefinition.getId());
		}
		InputStream inputStream = new ByteArrayInputStream(resourceEntity.getBytes());
		return inputStream;
	}
	
}
