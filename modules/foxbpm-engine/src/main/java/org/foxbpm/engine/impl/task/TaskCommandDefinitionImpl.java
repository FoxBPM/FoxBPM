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
package org.foxbpm.engine.impl.task;

import java.util.List;

import org.foxbpm.engine.task.TaskCommandDefinition;

public class TaskCommandDefinitionImpl implements TaskCommandDefinition {

	/**
	 * 命令定义ID
	 */
	private String id;
	
	/**
	 * 默认名称
	 */
	private String name;
	
	/**
	 * 参数实现类
	 */
	private String commandClass;
	
	/**
	 * 执行实现类
	 */
	private String cmdClass;
	
	/**
	 * 过滤器实现类
	 */
	private String filterClass;
	
	/**
	 * 命令描述
	 */
	private String description;
	
	/**
	 * 命令类型，待办任务或者系统命令
	 */
	private String type;
	
	/**
	 * 命令参数
	 */
	private List<CommandParam> commandParams;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommandClass() {
		return commandClass;
	}

	public void setCommandClass(String commandClass) {
		this.commandClass = commandClass;
	}

	public String getCmdClass() {
		return cmdClass;
	}

	public void setCmdClass(String cmdClass) {
		this.cmdClass = cmdClass;
	}

	public String getFilterClass() {
		return filterClass;
	}

	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<CommandParam> getCommandParam() {
		return commandParams;
	}

	public void setCommandParam(List<CommandParam> commandParams) {
		this.commandParams = commandParams;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	 
	public String getType() {
		return type;
	}

}
