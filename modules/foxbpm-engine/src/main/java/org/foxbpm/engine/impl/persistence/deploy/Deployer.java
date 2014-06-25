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
 */
package org.foxbpm.engine.impl.persistence.deploy;

import org.foxbpm.engine.impl.entity.DeploymentEntity;

/**
 * @author kenshin
 * 
 */
public interface Deployer {
	/**
	 * 部署文件名称后缀名
	 */
	public static final String BPMN_RESOURCE_SUFFIX = ".bpmn";
	public static final String DIAGRAM_SUFFIXES = ".png";

	/**
	 * 添加结构返回值；修改人：马恩亮;修改时间：2014-06-25
	 * 
	 * @param deployment
	 * @return ProcessDefinitionID
	 */
	String deploy(DeploymentEntity deployment);

}
