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
package org.foxbpm.engine.impl.bpmn.parser.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.UserTask;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.task.TaskDefinition;


/**
 * 人工任务节点转换
 * 属性列表：
 * 任务分配类型
 * 任务分配表达式
 * 任务处理者
 * 任务命令
 * 任务优先级
 * 任务主题
 * 任务类型
 * 操作表单
 * 浏览表单
 * @author ych
 * 
 */
public class UserTaskParser extends TaskParser {
	
	

	@Override
	public BaseElementBehavior parser(BaseElement baseElement) {
		UserTaskBehavior userTaskBehavior=(UserTaskBehavior)baseElementBehavior;
		
		TaskDefinition taskDefinition=new TaskDefinition();
		
		
		UserTask userTask=(UserTask)baseElement;
		if(userTask.getResources().size()>0){
			taskDefinition.getActorConnectors().addAll(parserConnector(userTask.getResources().get(0), "actorConnector"));
			
		}
		userTaskBehavior.setTaskDefinition(taskDefinition);
		/*String subject = BpmnModelUtil.getUserTaskSubject(baseElement);
		String formUri = BpmnModelUtil.getFormUri(baseElement);
		String formUriView = BpmnModelUtil.getFormUriView(baseElement);
		String assigneePolicy = BpmnModelUtil.getUserTaskAssigneePolicyType(baseElement);
		String assigneeExpression = BpmnModelUtil.getUserTaskAssigneeExpression(baseElement);
		String taskPriority = BpmnModelUtil.getUserTaskPriority(baseElement);
		
		//任务分配
		List<TaskAssigneeDefinition> assignees = BpmnModelUtil.getUserTaskAssignees(baseElement);
		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setTaskAssignees(assignees);
		taskDefinition.setAssignPolicyType(assigneePolicy);
		taskDefinition.setAssigneeExpression(assigneeExpression);
		//任务命令
		List<TaskCommandDefinition> taskCommands = BpmnModelUtil.getUserTaskCommands(baseElement);
		taskDefinition.setTaskCommands(taskCommands);
		
		userTaskBehavior.setFormUri(formUri);
		userTaskBehavior.setFormUriView(formUriView);
		userTaskBehavior.setTaskSubject(subject);
		userTaskBehavior.setTaskPriority(taskPriority);
		userTaskBehavior.setTaskDefinition(taskDefinition);*/
		return super.parser(baseElement);
	}

	@Override
	public void init() {
		baseElementBehavior=new UserTaskBehavior();
	}

}
