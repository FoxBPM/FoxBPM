/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 */
package org.foxbpm.engine.impl.cmd;

import java.io.InputStream;
import java.util.Map;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.model.DeploymentBuilder;

public class UpdateDeploymentByStreamCmd implements Command<String> {

	protected Map<String,InputStream> inputStreamMap = null; 
	protected DeploymentBuilder deploymentBuilder = null;
	protected String deploymentId = null;
	public UpdateDeploymentByStreamCmd(DeploymentBuilder deploymentBuilder,Map<String,InputStream> inputStreamMap,String deploymentId) {
		this.deploymentBuilder = deploymentBuilder;
		this.inputStreamMap = inputStreamMap;
		this.deploymentId = deploymentId;
	}
	
	public String execute(CommandContext commandContext) {
		if(inputStreamMap != null){
			for(String key : inputStreamMap.keySet()){
				InputStream tmpInputStream = inputStreamMap.get(key);
				deploymentBuilder.addInputStream(key, tmpInputStream);
			}
		}
		deploymentBuilder.updateDeploymentId(deploymentId);
		return deploymentBuilder.deploy().getId();
	}
}
