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

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.task.TaskCommand;

public class TaskDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1357653109003002722L;
	
	private String id;
	
	private String name;
	
	
	private String taskType;
	
	


	private Expression completeTaskDescription;
	

	private Expression taskDescription;
	
	private boolean isAutoClaim;
	
	private Expression taskSubject;
	
	private Expression taskPriority;
	
	private Expression formUri;
	
	private Expression formUriView;
	
	private List<FormParam> formParams;
	
	
	public Expression getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = new ExpressionImpl(taskSubject);
	}

	public Expression getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = new ExpressionImpl(taskPriority);
	}

	public Expression getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = new ExpressionImpl(formUri);
	}

	public Expression getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = new ExpressionImpl(formUriView);
	}

	public List<FormParam> getFormParams() {
		return formParams;
	}

	public void setFormParams(List<FormParam> formParams) {
		this.formParams = formParams;
	}

	/**
	 * 处理命令集合
	 */
	private List<TaskCommand> taskCommands = new ArrayList<TaskCommand>();

	
	
	public boolean isAutoClaim() {
		return isAutoClaim;
	}

	public void setAutoClaim(boolean isAutoClaim) {
		this.isAutoClaim = isAutoClaim;
	}

	protected List<Connector> actorConnectors=new ArrayList<Connector>();

	
	public List<Connector> getActorConnectors() {
		return actorConnectors;
	}

	public void setActorConnectors(List<Connector> actorConnectors) {
		this.actorConnectors = actorConnectors;
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Expression getCompleteTaskDescription() {
		return completeTaskDescription;
	}

	public void setCompleteTaskDescription(String completeTaskDescription) {
		this.completeTaskDescription =  new ExpressionImpl(completeTaskDescription);
	}

	public Expression getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription =  new ExpressionImpl(taskDescription);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

}
