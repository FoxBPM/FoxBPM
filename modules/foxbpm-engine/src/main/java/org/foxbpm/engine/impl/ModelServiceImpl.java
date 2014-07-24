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

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.impl.cmd.DeleteDeploymentCmd;
import org.foxbpm.engine.impl.cmd.DeployCmd;
import org.foxbpm.engine.impl.cmd.GetBizSystemDataObjectCmd;
import org.foxbpm.engine.impl.cmd.GetFlowGraphicsElementPositionCmd;
import org.foxbpm.engine.impl.cmd.GetFlowGraphicsImgStreamCmd;
import org.foxbpm.engine.impl.cmd.GetLatestProcessDefinitionByKey;
import org.foxbpm.engine.impl.cmd.GetProcessDefinitionByKeyAndVersionCmd;
import org.foxbpm.engine.impl.cmd.GetProcessDefinitionCmd;
import org.foxbpm.engine.impl.cmd.GetProcessDefinitionSVGCmd;
import org.foxbpm.engine.impl.cmd.GetResouceByDeployIdAndNameCmd;
import org.foxbpm.engine.impl.cmd.GetStartProcessByUserIdCmd;
import org.foxbpm.engine.impl.cmd.VerificationStartUserCmd;
import org.foxbpm.engine.impl.datavariable.DataObject;
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

	public List<ProcessDefinition> getStartProcessByUserId(String userId) {
		return commandExecutor.execute(new GetStartProcessByUserIdCmd(userId));
		
	}

	public Deployment deploy(DeploymentBuilderImpl deploymentBuilder) {
		return commandExecutor.execute(new DeployCmd(deploymentBuilder));
	}

	public void deleteDeployment(String deploymentId) {
		commandExecutor.execute(new DeleteDeploymentCmd(deploymentId));
	}

	public ProcessDefinitionQuery createProcessDefinitionQuery() {
		return new ProcessDefinitionQueryImpl(commandExecutor);
	}

	@Override
	public InputStream GetFlowGraphicsImgStreamByDefId(String processDefinitionId) {
		return commandExecutor.execute(new GetFlowGraphicsImgStreamCmd(processDefinitionId, null));
	}

	@Override
	public InputStream GetFlowGraphicsImgStreamByDefKey(String processDefinitionKey) {
		return commandExecutor.execute(new GetFlowGraphicsImgStreamCmd(null, processDefinitionKey));
	}

	@Override
	public ProcessDefinition getProcessDefinition(String processDefinitionId) {
		return commandExecutor.execute(new GetProcessDefinitionCmd(processDefinitionId));
	}

	@Override
	public ProcessDefinition getProcessDefinition(String processKey, int version) {
		return commandExecutor.execute(new GetProcessDefinitionByKeyAndVersionCmd(processKey, version));
	}

	@Override
	public Map<String, Map<String, Object>> getFlowGraphicsElementPositionById(String processDefinitionId) {
		return commandExecutor.execute(new GetFlowGraphicsElementPositionCmd(processDefinitionId, null));
	}
	
	@Override
	public Map<String, Map<String, Object>> getFlowGraphicsElementPositionByKey(String processDefinitionKey) {
		return commandExecutor.execute(new GetFlowGraphicsElementPositionCmd(null, processDefinitionKey));
	}

	@Override
	public String getProcessDefinitionSVG(String processDefinitionId) {
		return commandExecutor.execute(new GetProcessDefinitionSVGCmd(processDefinitionId));
	}
	
	@Override
	public boolean verifyStartProcessByUserId(String userId,String processDefinitionId) {
		return commandExecutor.execute(new VerificationStartUserCmd(userId, null, processDefinitionId));
	}
	
	@Override
	public InputStream getResourceByDeployIdAndName(String deployId, String resourceName) {
		return commandExecutor.execute(new GetResouceByDeployIdAndNameCmd(deployId,resourceName));
	}

	@Override
	public ProcessDefinition getLatestProcessDefinition(String processDefinitionKey) {
		return commandExecutor.execute(new GetLatestProcessDefinitionByKey(processDefinitionKey));
	}

	@Override
	public List<DataObject> getBizSystemDataObject() {
		return commandExecutor.execute(new GetBizSystemDataObjectCmd());
	}

}
