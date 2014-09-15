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

import java.util.Map;

import org.foxbpm.engine.impl.diagramview.factory.FoxbpmProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.diagramview.factory.ConcreteProcessDefinitionVOFactory;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.StringUtil;

/**
 * 获取流程定义SVG图像
 * 
 * @author MAENLIANG
 * @date 2014-06-16
 * 
 */
public class GetProcessDefinitionSVGCmd implements Command<String> {
	private static final String SVG_PROPERTIES_NAME = "svgDocument";
	private String processDefinitionId;

	public GetProcessDefinitionSVGCmd(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	@Override
	public String execute(CommandContext commandContext) {
		ProcessDefinitionEntity deployedProcessDefinition = commandContext
				.getProcessEngineConfigurationImpl().getDeploymentManager()
				.findDeployedProcessDefinitionById(processDefinitionId);
		Map<String, Object> properties = deployedProcessDefinition.getProperties();
		String svgDocument = (String) properties.get(SVG_PROPERTIES_NAME);
		if (StringUtil.isNotBlank(svgDocument)) {
			return svgDocument;
		}
		// SVG上一层接口，独立于SVG，后期支持动态切换到微软SVG实现
		FoxbpmProcessDefinitionVOFactory svgFactory = new ConcreteProcessDefinitionVOFactory();
		String tempSVGDocument = svgFactory
				.createProcessDefinitionVOString(deployedProcessDefinition);
		deployedProcessDefinition.getProperties().put(SVG_PROPERTIES_NAME, tempSVGDocument);
		return tempSVGDocument;

	}

}
