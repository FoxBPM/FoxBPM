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
import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;

public class TaskCommand implements Serializable {

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
		return expression.getValue(executionContext);
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

	public Map<String, Object> getPersistentState() {
		Map<String, Object> persistentState = new HashMap<String, Object>();
		persistentState.put("id", this.id);
		persistentState.put("name", getName());
		persistentState.put("type", this.taskCommandType);

		if (this.userTask != null) {
			persistentState.put("nodeId", this.userTask.getId());
			persistentState.put("nodeName", this.userTask.getName());
		}

		return persistentState;
	}

}
