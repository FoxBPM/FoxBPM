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
 * @author ych
 */
package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.persistence.deploy.DeploymentManager;
import org.foxbpm.engine.impl.util.CoreUtil;
import org.foxbpm.engine.task.TaskCommand;

/**
 * 获取提交节点的任务命令配置
 * 
 * @author ych
 * @author kenshin
 */
public class GetTaskCommandByKeyCmd implements Command<List<TaskCommand>> {

	private String processKey;

	public GetTaskCommandByKeyCmd(String processKey) {
		this.processKey = processKey;
	}

	@Override
	public List<TaskCommand> execute(CommandContext commandContext) {
		DeploymentManager deploymentManager = Context.getProcessEngineConfiguration().getDeploymentManager();
		ProcessDefinitionEntity processDefinition = deploymentManager.findDeployedLatestProcessDefinitionByKey(processKey);
		if (processDefinition == null) {
			throw new FoxBPMIllegalArgumentException("为找到key为：" + processKey + " 的流程定义");
		}

		List<TaskCommand> taskCommands = CoreUtil.getSubmitNodeTaskCommand(processDefinition.getSubTaskDefinition());

		return taskCommands;

	}
}
