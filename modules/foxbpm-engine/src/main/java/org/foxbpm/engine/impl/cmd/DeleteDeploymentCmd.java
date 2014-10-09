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

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

/**
 * 根据发布号删除发布信息
 * 级联删除大字段表、流程定义、流程实例、任务实例、等所有相关表
 * @author ych
 *
 */
public class DeleteDeploymentCmd implements Command<Void>{

	private String deploymentId;
	public DeleteDeploymentCmd(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	 
	public Void execute(CommandContext commandContext) {
		if(deploymentId == null){
			throw new FoxBPMIllegalArgumentException("删除的发布号为null");
		}
		Context.getProcessEngineConfiguration().getDeploymentManager().removeDeployment(deploymentId, true);
		return null;
	}
}
