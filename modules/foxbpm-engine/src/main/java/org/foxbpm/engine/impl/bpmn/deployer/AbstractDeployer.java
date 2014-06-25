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
package org.foxbpm.engine.impl.bpmn.deployer;

import org.foxbpm.engine.impl.entity.DeploymentEntity;
import org.foxbpm.engine.impl.persistence.deploy.Deployer;
import org.foxbpm.engine.modelparse.ProcessModelParseHandler;

/**
 * 抽象类，提取了流程模型转换类 ProcessModelParseHandler
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 */
public abstract class AbstractDeployer implements Deployer {
	/**
	 * 部署时候 流程模型转换
	 */
	protected ProcessModelParseHandler processModelParseHandler;

	public ProcessModelParseHandler getProcessModelParseHandler() {
		return processModelParseHandler;
	}

	public void setProcessModelParseHandler(
			ProcessModelParseHandler processModelParseHandler) {
		this.processModelParseHandler = processModelParseHandler;
	}

	public abstract String deploy(DeploymentEntity deployment);

}
