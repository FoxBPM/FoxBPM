/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 */
package org.foxbpm.engine.impl.cmd;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.foxbpm.engine.exception.FixFlowBizException;
import org.foxbpm.engine.impl.bpmn.behavior.ProcessDefinitionBehavior;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.definition.ResourceEntity;

public class GetFlowGraphicsImgStreamCmd  implements Command<InputStream> {

	protected String processDefinitionId;
	protected String processDefinitionKey;
	
	public GetFlowGraphicsImgStreamCmd(String processDefinitionId,String processDefinitionKey){
		this.processDefinitionId=processDefinitionId;
		this.processDefinitionKey=processDefinitionKey;
	}
	
	public InputStream execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		ProcessDefinitionBehavior processDefinitionBehavior=null;
		
		if(this.processDefinitionId!=null&&!this.processDefinitionId.equals("")){
			
			processDefinitionBehavior=commandContext.getProcessDefinitionManager().findLatestProcessDefinitionById(this.processDefinitionId);
			
			
		}else{
			if(this.processDefinitionKey!=null&&!this.processDefinitionKey.equals("")){
				processDefinitionBehavior=commandContext.getProcessDefinitionManager().findLatestProcessDefinitionByKey(processDefinitionKey);
			}
			else{
				throw new FixFlowBizException("查询流程图的processDefinitionId、processDefinitionKey不能都为空!");
			}
		}
		
		String deploymentId=processDefinitionBehavior.getDeploymentId();
		String diagramResourceName=processDefinitionBehavior.getDiagramResourceName();
		ResourceEntity resourceEntity=commandContext.getResourceManager().findResourceByDeploymentIdAndResourceName(deploymentId, diagramResourceName);
		InputStream inputStream = new ByteArrayInputStream(resourceEntity.getBytes()); 
		return inputStream;
		
	
	}

}
