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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.cmd;

import java.util.Date;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.DeploymentEntityManager;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.Deployment;
import org.foxbpm.engine.repository.DeploymentBuilder;

public class DeployCmd implements Command<Deployment> {
	/** 日志处理 */
	protected DeploymentBuilder deploymentBuilder;

	public DeployCmd(DeploymentBuilder deploymentBuilder) {
		this.deploymentBuilder = deploymentBuilder;
	}

	public Deployment execute(CommandContext commandContext) {
		// 获取发布实例
		DeploymentEntity deployment = deploymentBuilder.getDeployment();
		// 获取更新发布Id
		String updateDeploymentId = deployment.getUpdateDeploymentId();
		// 获取发布实例管理器
		DeploymentEntityManager deploymentEntityManager = Context.getCommandContext().getDeploymentEntityManager();
		// 判断是否新增发布
		if (StringUtil.isEmpty(updateDeploymentId)) {
			deployment.setDeploymentTime(new Date());
			deploymentEntityManager.insertDeployment(deployment);
		}
		Context.getProcessEngineConfiguration().getDeploymentManager().deploy(deployment);
		return deployment;
	}
}
