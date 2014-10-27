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
package org.foxbpm.engine.config;

import java.util.List;

import org.foxbpm.engine.task.TaskCommandDefinition;

/**
 * 常量类
 * 
 * @author yangguangftlp
 * @date 2014年10月27日
 */
public class TaskCommandDefinitionImpl implements TaskCommandDefinition {
	/**
	 * 是否可用
	 */
	protected boolean isEnabled;
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
	private String command;
	
	/**
	 * 执行实现类
	 */
	private String cmd;
	
	/**
	 * 过滤器实现类
	 */
	private String filter;
	
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
	private CommandParam[] commandParam;
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public void setCommandParam(CommandParam[] commandParam) {
		this.commandParam = commandParam;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public String getFilter() {
		return filter;
	}
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCommandClass() {
		return command;
	}
	
	public String getCmdClass() {
		return cmd;
	}
	
	public String getFilterClass() {
		return filter;
	}
	
	public List<org.foxbpm.engine.task.CommandParam> getCommandParam() {
		return null;
	}
	
	/*
	 * public CommandParam[] getCommandParam() { return commandParam; }
	 */
	
}
