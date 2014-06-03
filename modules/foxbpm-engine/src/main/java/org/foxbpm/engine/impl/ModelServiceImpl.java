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
package org.foxbpm.engine.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.impl.cmd.DeleteDeploymentCmd;
import org.foxbpm.engine.impl.cmd.DeployCmd;
import org.foxbpm.engine.impl.cmd.DeploymentByInputStreamCmd;
import org.foxbpm.engine.impl.cmd.DeploymentByZipCmd;
import org.foxbpm.engine.impl.cmd.GetFlowGraphicsImgStreamCmd;
import org.foxbpm.engine.impl.cmd.GetProcessDefinition;
import org.foxbpm.engine.impl.cmd.GetProcessDefinitionByKeyAndVersion;
import org.foxbpm.engine.impl.cmd.UpdateDeploymentByStreamMapCmd;
import org.foxbpm.engine.impl.cmd.UpdateDeploymentByZipCmd;
import org.foxbpm.engine.impl.model.DeploymentBuilderImpl;
import org.foxbpm.engine.impl.model.ProcessDefinitionQueryImpl;
import org.foxbpm.engine.repository.Deployment;
import org.foxbpm.engine.repository.DeploymentBuilder;
import org.foxbpm.engine.repository.ProcessDefinition;
import org.foxbpm.engine.repository.ProcessDefinitionQuery;

public class ModelServiceImpl extends ServiceImpl implements ModelService {
	
	public DeploymentBuilder createDeployment() {
		return new DeploymentBuilderImpl(this);
	}

	public List<Map<String, String>> getStartProcessByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String deployByInputStream(Map<String, InputStream> inputStreamMap) {
		return commandExecutor.execute(new DeploymentByInputStreamCmd(createDeployment(),inputStreamMap));
	}
	
	public String deployByZip(ZipInputStream zipInputStream) {
		return commandExecutor.execute(new DeploymentByZipCmd(createDeployment(),zipInputStream));
	}

	public Deployment deploy(DeploymentBuilderImpl deploymentBuilder) {
		return commandExecutor.execute(new DeployCmd(deploymentBuilder));
	}
	
	public void updateByZip(String deploymentId, ZipInputStream zipInputStream) {
		commandExecutor.execute(new UpdateDeploymentByZipCmd(createDeployment() , deploymentId , zipInputStream));
	}
	
	public void updateByStreamMap(String deploymentId, Map<String, InputStream> inputStreamMap) {
		commandExecutor.execute(new UpdateDeploymentByStreamMapCmd(createDeployment() , deploymentId , inputStreamMap));
	}
	
	public void deleteDeployment(String deploymentId){
		commandExecutor.execute(new DeleteDeploymentCmd(deploymentId));
	}
	
	public ProcessDefinitionQuery createProcessDefinitionQuery() {
	    return new ProcessDefinitionQueryImpl(commandExecutor);
	}
	
	@Override
	public InputStream GetFlowGraphicsImgStreamByDefId(String processDefinitionId) {
		return commandExecutor.execute(new GetFlowGraphicsImgStreamCmd(processDefinitionId,null));
	}
	
	@Override
	public InputStream GetFlowGraphicsImgStreamByDefKey(String processDefinitionKey) {
		return commandExecutor.execute(new GetFlowGraphicsImgStreamCmd(null,processDefinitionKey));
	}
	
	@Override
	public ProcessDefinition getProcessDefinition(String processDefinitionId) {
		return commandExecutor.execute(new GetProcessDefinition(processDefinitionId));
	}
	
	@Override
	public ProcessDefinition getProcessDefinition(String processKey, int version) {
		return commandExecutor.execute(new GetProcessDefinitionByKeyAndVersion(processKey,version));
	}
	
	@Override
	public Map<String, Map<String, Object>> GetFlowGraphicsElementPosition(String processDefinitionId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
