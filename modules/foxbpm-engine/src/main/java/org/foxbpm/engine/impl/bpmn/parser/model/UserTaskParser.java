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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ResourceRole;
import org.eclipse.bpmn2.UserTask;
import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.task.CommandParamImpl;
import org.foxbpm.engine.impl.task.FormParam;
import org.foxbpm.engine.impl.task.TaskCommandImpl;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.util.BpmnModelUtil;
import org.foxbpm.engine.task.CommandParamType;
import org.foxbpm.model.bpmn.foxbpm.Expression;
import org.foxbpm.model.bpmn.foxbpm.FormParamContainer;
import org.foxbpm.model.bpmn.foxbpm.FoxBPMPackage;
import org.foxbpm.model.bpmn.foxbpm.Param;

/**
 * 人工任务节点转换 属性列表： 任务分配类型 任务分配表达式 任务处理者 任务命令 任务优先级 任务主题 任务类型 操作表单 浏览表单
 * 
 * @author ych
 * @author kenshin
 */
public class UserTaskParser extends TaskParser {

	 
	public BaseElementBehavior parser(BaseElement baseElement) {
		UserTaskBehavior userTaskBehavior = (UserTaskBehavior) baseElementBehavior;
		TaskDefinition taskDefinition = new TaskDefinition();

		UserTask userTask = (UserTask) baseElement;
		String taskID = userTask.getId();
		taskDefinition.setId(taskID);
		taskDefinition.setName(userTask.getName());
		List<org.foxbpm.model.bpmn.foxbpm.TaskCommand> taskCommandsObj = BpmnModelUtil
				.getExtensionElementList(org.foxbpm.model.bpmn.foxbpm.TaskCommand.class, userTask,
						FoxBPMPackage.Literals.DOCUMENT_ROOT__TASK_COMMAND);

		if (taskCommandsObj != null) {
			for (org.foxbpm.model.bpmn.foxbpm.TaskCommand taskCommand : taskCommandsObj) {
				TaskCommandImpl taskCommandNew = new TaskCommandImpl();
				taskCommandNew.setId(taskCommand.getId());
				taskCommandNew.setName(taskCommand.getName());
				taskCommandNew.setTaskCommandDefType(null);
				taskCommandNew.setTaskCommandType(taskCommand.getCommandType());
				taskCommandNew.setUserTask(userTaskBehavior);
				for (Param param : taskCommand.getParams()) {
					org.foxbpm.model.bpmn.foxbpm.CommandParam commandParamBpmn=(org.foxbpm.model.bpmn.foxbpm.CommandParam)param;
					CommandParamImpl commandParamEngine=new CommandParamImpl();
					commandParamEngine.setKey(commandParamBpmn.getKey());
					commandParamEngine.setName(commandParamBpmn.getName());
					commandParamEngine.setDescription(commandParamEngine.getDescription());
					commandParamEngine.setBizType(CommandParamType.valueOf(commandParamBpmn.getBizType()));
					commandParamEngine.setDataType(commandParamBpmn.getDataType());
					commandParamEngine.setExpression(commandParamBpmn.getExpression()!=null?commandParamBpmn.getExpression().getValue():null);
					taskCommandNew.getCommandParams().add(commandParamEngine);
				}
				Expression commandExpression = taskCommand.getExpression();
				if (commandExpression != null) {
					taskCommandNew.setExpression(new ExpressionImpl(commandExpression.getValue()));
				}
				taskDefinition.getTaskCommands().add(taskCommandNew);
			}
		}

		String claimType = BpmnModelUtil.claimType(userTask);
		taskDefinition.setClaimType(claimType);

		List<ResourceRole> resources = userTask.getResources();
		if (resources.size() > 0) {
			taskDefinition.getActorConnectors().addAll(
					parserConnector(resources.get(0), "actorConnector"));

		}
		userTaskBehavior.setTaskDefinition(taskDefinition);

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) getFlowElementsContainer();
		processDefinition.getTaskDefinitions().put(taskID, taskDefinition);

		FormParamContainer formParamContainer = BpmnModelUtil.getFormParamContainer(baseElement);

		if (formParamContainer != null) {
			List<FormParam> formParams = new ArrayList<FormParam>();
			for (org.foxbpm.model.bpmn.foxbpm.FormParam formParam : formParamContainer
					.getFormParam()) {

				FormParam formParamObj = new FormParam();
				formParamObj.setParamKey(formParam.getParamKey());
				Expression expression = formParam.getExpression();
				formParamObj.setExpression(expression != null ? expression.getValue() : null);
				formParamObj.setParamType(formParam.getParamType());
				formParams.add(formParamObj);
			}
			taskDefinition.setFormParams(formParams);
		}

		String subject = BpmnModelUtil.getUserTaskSubject(baseElement);
		String formUri = BpmnModelUtil.getFormUri(baseElement);
		String formUriView = BpmnModelUtil.getFormUriView(baseElement);
		String taskDescription = BpmnModelUtil.getUserTaskDescription(baseElement);
		String taskCompleteDescription = BpmnModelUtil.getUserTaskCompleteTaskDescription(baseElement);
		
		double expectedExecuteTime = BpmnModelUtil.getExpectedExecuteTime(baseElement);		
		
		String taskType = BpmnModelUtil.getUserTaskType(baseElement);
		taskDefinition.setTaskType(taskType);
		taskDefinition.setTaskSubject(subject);
		taskDefinition.setFormUri(formUri);
		taskDefinition.setFormUriView(formUriView);
		taskDefinition.setTaskDescription(taskDescription);
		taskDefinition.setCompleteTaskDescription(taskCompleteDescription);
		taskDefinition.setExpectExecuteTime(expectedExecuteTime);

		return super.parser(baseElement);
	}

	 
	public void init() {
		baseElementBehavior = new UserTaskBehavior();
	}

}
