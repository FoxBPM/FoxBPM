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
package org.foxbpm.engine.repository;

import java.util.Map;

/**
 * @author kenshin
 * 
 */
public interface ProcessDefinition {

	/**
	 * 获取流程定义编号
	 * @return
	 */
	String getId();

	/**
	 * 获取流程分类
	 * @return
	 */
	String getCategory();

	/**
	 * 获取流程定义名称
	 * @return
	 */
	String getName();

	/**
	 * 获取流程定义key
	 * @return
	 */
	String getKey();

	/**
	 * 获取流程定义描述
	 * @return
	 */
	String getDescription();

	/**
	 * 获取流程定义版本
	 * @return
	 */
	int getVersion();
	
	/**
	 * 获取流程多租户编号
	 * @return
	 */
	String getTenantId();

	/**
	 * 获取流程定义文件名称，结合deploymentId可以查出对应的Stream流文件
	 * @return
	 */
	String getResourceName();

	/**
	 * 获取流程发布号
	 * @return
	 */
	String getDeploymentId();

	/**
	 * 获取流程图名称，结合deploymentId可以查出对应的Stream流文件
	 * @return
	 */
	String getDiagramResourceName();
	
	/**
	 * 判断流程定义是否存在开始表单
	 * @return
	 */
	boolean hasStartFormKey();

	/**
	 * 判断流程定义是否暂停
	 * @return
	 */
	boolean isSuspended();
	
	/**
	 * 获取实体属性map
	 * @return
	 */
	Map<String, Object> getPersistentState();

}
