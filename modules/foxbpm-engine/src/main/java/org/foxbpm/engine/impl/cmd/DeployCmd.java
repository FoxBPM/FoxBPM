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
import org.foxbpm.engine.repository.Deployment;
import org.foxbpm.engine.repository.DeploymentBuilder;

public class DeployCmd implements Command<Deployment>{

	protected DeploymentBuilder deploymentBuilder;
	public DeployCmd(DeploymentBuilder deploymentBuilder) {
		this.deploymentBuilder = deploymentBuilder;
	}
	public Deployment execute(CommandContext commandContext) {
		DeploymentEntity deployment = deploymentBuilder.getDeployment();
		if(deployment.getUpdateDeploymentId()!=null&&!deployment.getUpdateDeploymentId().equals("")){
			DeploymentEntity deploymentOld=Context.getCommandContext().getDeploymentEntityManager().findDeploymentById(deployment.getUpdateDeploymentId());
			
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
			
			if(resourceEntityNewBpmn == null){
				throw new FoxBPMBizException("发布包中必须存在bpmn文件");
			}
			if(resourceEntityNewPng == null){
				throw new FoxBPMBizException("发布包中必须存在png文件");
			}
			ResourceEntity resourceEntityOldBpmn = null;
			ResourceEntity resourceEntityOldPng = null;
			
			for ( ResourceEntity resourceEntityOld : deploymentOld.getResources().values()) {
				if (resourceEntityOld.getName().toLowerCase().endsWith(BpmnDeployer.BPMN_RESOURCE_SUFFIX)) {
					resourceEntityOldBpmn = resourceEntityOld;
				} else {
					if (resourceEntityOld.getName().toLowerCase().endsWith(BpmnDeployer.DIAGRAM_SUFFIXES)) {
						resourceEntityOldPng = resourceEntityOld;
					}
				}
			}
			if(resourceEntityOldBpmn == null){
				Context.getCommandContext().getResourceManager().insert(resourceEntityNewBpmn);
			}else{
				resourceEntityOldBpmn.setBytes(resourceEntityNewBpmn.getBytes());
			}
			if(resourceEntityOldPng == null){
				Context.getCommandContext().getResourceManager().insert(resourceEntityNewPng);
			}else{
				resourceEntityOldPng.setBytes(resourceEntityOldPng.getBytes());
			}
		}else{
			deployment.setDeploymentTime(new Date());
			deployment.setNew(true);
			Context.getCommandContext().getDeploymentEntityManager().insertDeployment(deployment);
			Context.getProcessEngineConfiguration().getDeploymentManager().deploy(deployment);
		}
		return deployment;
	}
}
