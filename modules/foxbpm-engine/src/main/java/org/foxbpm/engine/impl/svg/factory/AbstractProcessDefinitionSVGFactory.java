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
package org.foxbpm.engine.impl.svg.factory;

import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;

/**
 * 创建流程SVG对象的抽象工厂
 * 
 * @author MAENLIANG
 * @date 2014-06-10
 * 
 */
public abstract class AbstractProcessDefinitionSVGFactory {
	/**
	 * 流程定义的SVG画布坐标MINX
	 */
	protected static final String SVG_MINX = "canvas_minX";
	/**
	 * 流程定义的SVG画布坐标MINY
	 */
	protected static final String SVG_MINY = "canvas_minY";
	/**
	 * 流程定义的SVG画布坐标MAXX
	 */
	protected static final String SVG_MAXX = "canvas_maxX";
	/**
	 * 流程定义的SVG画布坐标MAXY
	 */
	protected static final String SVG_MAXY = "canvas_maxY";

	/**
	 * 
	 * 根据流程定义创建SVG文档字符串
	 * 
	 * @param deployedProcessDefinition
	 *            流程定义实体对象
	 * @return
	 */
	public abstract String createProcessDefinitionSVGString(
			ProcessDefinitionEntity deployedProcessDefinition);

}
