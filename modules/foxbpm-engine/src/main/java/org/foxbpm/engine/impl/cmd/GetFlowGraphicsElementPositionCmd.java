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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;

/**
 * 获取流程节点位置信息
 * @author ych
 *
 */
public class GetFlowGraphicsElementPositionCmd implements Command<Map<String, Map<String, Object>>> {

	/**
	 * 流程定义编号
	 */
	protected String processDefinitionId;
	protected String processDefinitionKey;
	
//	protected DefinitionsBehavior definitions;
	public GetFlowGraphicsElementPositionCmd(String processDefinitionId,String processDefinitionKey) {
		this.processDefinitionId = processDefinitionId;
		this.processDefinitionKey= processDefinitionKey;
	}
	
	/**
	 * 目前子流程有bug，
	 */
	 
	public Map<String, Map<String, Object>> execute(CommandContext commandContext) {
		
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String,Object>>();
		DeploymentManager deploymentCache = Context.getProcessEngineConfiguration().getDeploymentManager();
		ProcessDefinitionEntity processDefinition = null;
		if(processDefinitionId!=null){
			processDefinition = deploymentCache.findDeployedProcessDefinitionById(processDefinitionId);
		}else if(processDefinitionKey != null){
			processDefinition = deploymentCache.findDeployedLatestProcessDefinitionByKey(processDefinitionKey);
		}else{
			throw ExceptionUtil.getException("10601103");
		}
		
		List<KernelFlowNodeImpl> flowNodes = processDefinition.getFlowNodes();
		
		for(KernelFlowNodeImpl flowNode :flowNodes){
			Map<String,Object> nodePosition = new HashMap<String, Object>();
			nodePosition.put("height", flowNode.getHeight());
			nodePosition.put("width", flowNode.getWidth());
			nodePosition.put("x", flowNode.getX());
			nodePosition.put("y", flowNode.getY());
			result.put(flowNode.getId(),nodePosition);
		}
		return result;
	}
}
