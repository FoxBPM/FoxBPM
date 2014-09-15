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
package org.foxbpm.engine.impl.diagramview.factory;

import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;

/**
 * 创建流程图形对象的工厂类
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractProcessDefinitionVOFactory implements
    FoxbpmProcessDefinitionVOFactory {
	/**
	 * 根据流程定义创建SVG字符串，返回的字符串可以直接在界面展示
	 * 
	 * @param deployedProcessDefinition
	 *            流程定义
	 * @return 流程定义对应的SVG字符串
	 */
	public abstract String createProcessDefinitionVOString(
	    ProcessDefinitionEntity deployedProcessDefinition);
	
}
