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

import java.util.Date;
import org.foxbpm.engine.exception.FoxBPMBizException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.bpmn.deployer.BpmnDeployer;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.entity.ResourceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.model.DeploymentBuilder;

public class DeployCmd implements Command<Void>{

	protected DeploymentBuilder deploymentBuilder;
	public DeployCmd(DeploymentBuilder deploymentBuilder) {
		this.deploymentBuilder = deploymentBuilder;
	}
	public Void execute(CommandContext commandContext) {
		DeploymentEntity deployment = deploymentBuilder.getDeployment();
		if(deployment.getUpdateDeploymentId()!=null&&!deployment.getUpdateDeploymentId().equals("")){
			DeploymentEntity deploymentOld=Context.getCommandContext().getDeploymentEntityManager().findDeploymentById(deployment.getUpdateDeploymentId());
			
			if(deploymentOld.getResources().keySet().size()!=2){
				throw new FoxBPMBizException("资源发布号,中不存在流程定义和流程图两个文件！");
			}
			ResourceEntity resourceEntityNewBpmn = null;
			ResourceEntity resourceEntityNewPng = null;
			
			for ( ResourceEntity resourceEntityNew : deployment.getResources().values()) {
				if (resourceEntityNew.getName().toLowerCase().endsWith(BpmnDeployer.BPMN_RESOURCE_SUFFIX)) {
					resourceEntityNewBpmn = resourceEntityNew;
				} else {
					if (resourceEntityNew.getName().toLowerCase().endsWith(BpmnDeployer.DIAGRAM_SUFFIXES)) {
						resourceEntityNewPng = resourceEntityNew;
					}
				}
			}
			for ( ResourceEntity resourceEntityOld : deploymentOld.getResources().values()) {
				if (resourceEntityOld.getName().toLowerCase().endsWith(BpmnDeployer.BPMN_RESOURCE_SUFFIX)) {
					resourceEntityOld.setBytes(resourceEntityNewBpmn.getBytes());
				} else {
					if (resourceEntityOld.getName().toLowerCase().endsWith(BpmnDeployer.DIAGRAM_SUFFIXES)) {
						resourceEntityOld.setBytes(resourceEntityNewPng.getBytes());
					}
				}
			}
		}else{
			deployment.setDeploymentTime(new Date());
			deployment.setNew(true);
			Context.getCommandContext().getDeploymentEntityManager().insertDeployment(deployment);
			Context.getProcessEngineConfiguration().getDeploymentManager().deploy(deployment);
		}
		return null;
	}
}
