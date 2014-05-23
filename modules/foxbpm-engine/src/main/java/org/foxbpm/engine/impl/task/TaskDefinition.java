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
import java.util.List;

public class TaskDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1357653109003002722L;

	/**
	 * 任务分配策略
	 */
	private String assignPolicyType;

	private String assigneeExpression;
	/**
	 * 处理人集合
	 */
	private List<TaskAssigneeDefinition> taskAssignees;

	/**
	 * 处理命令集合
	 */
	private List<TaskCommand> taskCommands = new ArrayList<TaskCommand>();

	public List<TaskAssigneeDefinition> getTaskAssignees() {
		return taskAssignees;
	}

	public void setTaskAssignees(List<TaskAssigneeDefinition> taskAssignees) {
		this.taskAssignees = taskAssignees;
	}

	public List<TaskCommand> getTaskCommands() {
		return taskCommands;
	}

	public TaskCommand getTaskCommand(String taskCommandId) {

		for (TaskCommand taskCommand : taskCommands) {
			if (taskCommand.getId().equals(taskCommandId)) {
				return taskCommand;
			}
		}

		return null;
	}

	public void setTaskCommands(List<TaskCommand> taskCommands) {
		this.taskCommands = taskCommands;
	}

	public String getAssignPolicyType() {
		return assignPolicyType;
	}

	public void setAssignPolicyType(String assignPolicyType) {
		this.assignPolicyType = assignPolicyType;
	}

	public String getAssigneeExpression() {
		return assigneeExpression;
	}

	public void setAssigneeExpression(String assigneeExpression) {
		this.assigneeExpression = assigneeExpression;
	}

}
