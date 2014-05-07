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
package org.foxbpm.engine;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.foxbpm.engine.impl.model.DeploymentBuilderImpl;
import org.foxbpm.engine.model.DeploymentBuilder;

/**
 * 获取所有对流程定义级别操作
 * @author kenshin
 *
 */
public interface ModelService {
	
	/**
	 * 获取用户可以发起的流程集合
	 * @param userId 用户编号
	 * @return
	 * "processDefinitionId" 流程唯一号;<br>
	 * "processDefinitionName" 流程名称;<br>
	 * "processDefinitionKey" 流程定义号;<br>
	 * "category" 分类;<br>
	 * "version" 版本号;<br>
	 * "resourceName", 流程定义资源名称;<br>
	 * "resourceId" 流程定义资源编号;<br>
	 * "deploymentId" 资源定义发布号;<br>
	 * "diagramResourceName" 流程图名称;<br>
	 * "startFormKey" 启动表单;<br>
	 */
	List<Map<String, String>> getStartProcessByUserId(String userId);
	
	/**
	 * 通过zip文件发布流程
	 * @param path zip文件路径
	 * @return 发布号
	 */
	void deployByZip(ZipInputStream zipInputStream);

	void deploy(DeploymentBuilderImpl deploymentBuilderImpl);
	
	void updateByZip(String deploymentId,ZipInputStream zipInputStream);
	
	DeploymentBuilder createDeployment();
	
	void testCmd(String params);
	
	
}
