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

import java.io.InputStream;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMIllegalArgumentException;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.repository.DeploymentBuilder;

public class DeploymentByInputStreamCmd  implements Command<Void> {
	protected Map<String, InputStream>  inputStreamMap;
	protected DeploymentBuilder deploymentBuilder;
	public DeploymentByInputStreamCmd(DeploymentBuilder deploymentBuilder,Map<String, InputStream> inputStreamMap) {
		this.inputStreamMap = inputStreamMap;
		this.deploymentBuilder = deploymentBuilder;
	}

	public Void execute(CommandContext commandContext) {
		if(inputStreamMap == null || inputStreamMap.size() == 0){
			throw new FoxBPMIllegalArgumentException("发布 内容不能为空");
		}
		for(String resourceName : inputStreamMap.keySet()){
			deploymentBuilder.addInputStream(resourceName, inputStreamMap.get(resourceName));
		}
		deploymentBuilder.deploy();
		return null;
	}

}
