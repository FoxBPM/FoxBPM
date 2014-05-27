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
 * @author ych
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.task.TaskType;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * 人工任务处理器
 * @author kenshin
 *
 */
public class UserTaskBehavior extends TaskBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作表单
	 */
	private String formUri;
	/**
	 * 浏览表单
	 */
	private String formUriView;
	/**
	 * 任务信息定义
	 */
	private TaskDefinition taskDefinition;
	/**
	 * 任务主题
	 */
	private String taskSubject;
	
	private String taskPriority;
	


	@Override
	public void execute(FlowNodeExecutionContext executionContext) {
		
		TaskEntity task = TaskEntity.createAndInsert(executionContext);
		
		task.setTaskDefinition(taskDefinition);
		
		((TokenEntity)executionContext).setAssignTask(task);
		
		ProcessInstanceEntity processInstance=(ProcessInstanceEntity) executionContext.getProcessInstance();
		
		task.setBizKey(processInstance.getBizKey());
		task.setDescription("任务主题");
		task.setToken((TokenEntity)executionContext);
		task.setTaskType(TaskType.FOXBPMTASK);
		
		for (Connector connector : taskDefinition.getActorConnectors()) {
			try {
				connector.notify((ListenerExecutionContext)executionContext);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		

	}
	
	public String getFormUri() {
		return formUri;
	}

	public void setFormUri(String formUri) {
		this.formUri = formUri;
	}

	public String getFormUriView() {
		return formUriView;
	}

	public void setFormUriView(String formUriView) {
		this.formUriView = formUriView;
	}

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}
	
}
