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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.impl.diagramview.factory.ConcreteProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.diagramview.factory.FoxbpmProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.task.TaskQueryImpl;
import org.foxbpm.engine.task.Task;
import org.foxbpm.engine.task.TaskQuery;

/**
 * 获取具有状态标记的流程实例SVG图像
 * 
 * @author MAENLIANG
 * @date 2014-06-16
 * 
 */
public class GetProcessInstanceSVGImageCmd implements Command<String> {
	private String processInstanceID;

	public GetProcessInstanceSVGImageCmd(String processInstanceID) {
		this.processInstanceID = processInstanceID;
	}

	@Override
	public String execute(CommandContext commandContext) {
		ProcessInstanceEntity processEntity = commandContext
				.getProcessInstanceManager().findProcessInstanceById(
						processInstanceID); 
		String processDefinitionID = processEntity.getProcessDefinitionId();
		ProcessDefinitionEntity deployedProcessDefinition = commandContext
				.getProcessEngineConfigurationImpl().getDeploymentManager()
				.findDeployedProcessDefinitionById(processDefinitionID);
		TaskQuery taskQuery = new TaskQueryImpl(commandContext);
		List<Task> listTask = taskQuery
				.processInstanceId(processInstanceID).list();
		// SVG上一层接口，独立于SVG，后期支持动态切换到微软SVG实现
		FoxbpmProcessDefinitionVOFactory svgFactory = new ConcreteProcessDefinitionVOFactory();
		return svgFactory.createProcessInstanceSVGImageString(listTask,
				deployedProcessDefinition);

	}
}
