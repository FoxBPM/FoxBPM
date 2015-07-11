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
package org.foxbpm.engine.impl.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.util.ExceptionUtil;
import org.foxbpm.engine.task.CommandParamType;
import org.foxbpm.engine.task.TaskCommand;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class TaskCommandImpl implements Serializable, TaskCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3240434310337515303L;

	protected String id;

	protected String name;

	protected Expression expression;

	protected Expression expressionParam;

	protected String taskCommandType;

	protected UserTaskBehavior userTask;

	protected String taskCommandDefType;

	protected List<CommandParam> commandParams = new ArrayList<CommandParam>();

	public List<CommandParam> getCommandParamsByType(CommandParamType commandParamType) {
		List<CommandParam> commandParamsType = new ArrayList<CommandParam>();
		for (CommandParam commandParam : commandParams) {
			if (commandParam.getBizType() == commandParamType) {
				commandParamsType.add(commandParam);
			}
		}
		return commandParamsType;
	}
	
	public CommandParam getCommandParam(String paramKey) {
		for (CommandParam commandParam : commandParams) {
			if (commandParam.getKey().equals(paramKey)) {
				return commandParam;
			}
		}
		return null;
	}


	public List<CommandParam> getCommandParams() {
		return commandParams;
	}

	public void setCommandParams(List<CommandParam> commandParams) {
		this.commandParams = commandParams;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	public Object getExpressionValue(FlowNodeExecutionContext executionContext) {

		if (expression != null) {
			try{
				return expression.getValue(executionContext);
			}catch(Exception ex){
				throw ExceptionUtil.getException("10304003",ex);
			}
			
		}
		return null;
	}

	public Expression getExpressionParam() {
		return expressionParam;
	}

	public String getTaskCommandType() {
		return taskCommandType;
	}

	public UserTaskBehavior getUserTask() {
		return userTask;
	}

	public String getTaskCommandDefType() {
		return taskCommandDefType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public void setExpressionParam(Expression expressionParam) {
		this.expressionParam = expressionParam;
	}

	public void setTaskCommandType(String taskCommandType) {
		this.taskCommandType = taskCommandType;
	}

	public void setUserTask(UserTaskBehavior userTask) {
		this.userTask = userTask;
	}

	public void setTaskCommandDefType(String taskCommandDefType) {
		this.taskCommandDefType = taskCommandDefType;
	}

	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("name", getName());
		persistentState.put("type", this.taskCommandType);

		if (this.userTask != null) {
			persistentState.put("nodeId", this.userTask.getId());
			persistentState.put("nodeName", this.userTask.getName());
		}
		
		// 增加命令参数 ThinkGem 2015-7-10
		if (this.getCommandParams() != null){
			Map<String, Object> commandParamMap = new HashMap<String, Object>();
			for (CommandParam commandParam : this.getCommandParams()) {
				Map<String, Object> commandParamObj = new HashMap<String, Object>();
				commandParamObj.put("name", commandParam.getName());
				if (commandParam.getValue() != null){
					commandParamObj.put("value", commandParam.getValue());
				}else if (commandParam.getExpression() != null){
					commandParamObj.put("value", commandParam.getExpression().getExpressionText());
				}
				commandParamObj.put("description", commandParam.getDescription());
				commandParamObj.put("dataType", commandParam.getDataType());
				commandParamMap.put(commandParam.getKey(), commandParamObj);
			}
			persistentState.put("commandParam", commandParamMap);
		}

		return persistentState;
	}

}
