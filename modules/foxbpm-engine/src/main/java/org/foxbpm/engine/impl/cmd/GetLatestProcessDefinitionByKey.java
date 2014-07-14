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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.repository.ProcessDefinition;

/**
 * 获取最新流程定义通过定义key
 * 
 * @author yangguangftlp
 * @date 2014年7月14日
 */
public class GetLatestProcessDefinitionByKey implements Command<ProcessDefinition> {

	private String ProcessDefinitionKey;

	public GetLatestProcessDefinitionByKey(String ProcessDefinitionKey) {
		this.ProcessDefinitionKey = ProcessDefinitionKey;
	}

	@Override
	public ProcessDefinition execute(CommandContext commandContext) {
		if (StringUtil.isEmpty(ProcessDefinitionKey)) {
			throw new FoxBPMIllegalArgumentException("查询的流程定义key不能为null");
		}
		return Context.getProcessEngineConfiguration().getDeploymentManager().findDeployedLatestProcessDefinitionByKey(ProcessDefinitionKey);
	}
}
