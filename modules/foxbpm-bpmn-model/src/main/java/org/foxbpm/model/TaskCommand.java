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
 * @author ych
 */
package org.foxbpm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务命令
 * @author ych
 *
 */
public class TaskCommand extends BaseElement {
	
	private static final long serialVersionUID = -3240434310337515303L;

	/**
	 * 命令名称
	 */
	protected String name;

	/**
	 * 执行表达式
	 */
	protected String expression;

	/**
	 * 参数表达式
	 */
	protected String expressionParam;

	/**
	 * 命令类型
	 */
	protected String taskCommandType;

	/**
	 * 命令定义类型
	 */
	protected String taskCommandDefType;

	/**
	 * 命令参数
	 */
	protected List<CommandParameter> commandParams = new ArrayList<CommandParameter>();

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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpressionParam() {
		return expressionParam;
	}

	public void setExpressionParam(String expressionParam) {
		this.expressionParam = expressionParam;
	}

	public String getTaskCommandType() {
		return taskCommandType;
	}

	public void setTaskCommandType(String taskCommandType) {
		this.taskCommandType = taskCommandType;
	}

	public String getTaskCommandDefType() {
		return taskCommandDefType;
	}

	public void setTaskCommandDefType(String taskCommandDefType) {
		this.taskCommandDefType = taskCommandDefType;
	}

	public List<CommandParameter> getCommandParams() {
		return commandParams;
	}

	public void setCommandParams(List<CommandParameter> commandParams) {
		this.commandParams = commandParams;
	}

}
