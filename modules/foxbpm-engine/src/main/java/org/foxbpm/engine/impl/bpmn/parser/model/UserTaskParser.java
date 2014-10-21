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

import org.foxbpm.engine.impl.bpmn.behavior.BaseElementBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.UserTaskBehavior;
import org.foxbpm.engine.impl.connector.ConnectorListener;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.task.CommandParamImpl;
import org.foxbpm.engine.impl.task.FormParam;
import org.foxbpm.engine.impl.task.TaskCommandImpl;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.task.CommandParamType;
import org.foxbpm.model.BaseElement;
import org.foxbpm.model.CommandParameter;
import org.foxbpm.model.Connector;
import org.foxbpm.model.TaskCommand;
import org.foxbpm.model.UserTask;

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
		List<TaskCommand> taskCommandsObj = userTask.getTaskCommands();

		if (taskCommandsObj != null) {
			for (TaskCommand taskCommand : taskCommandsObj) {
				TaskCommandImpl taskCommandNew = new TaskCommandImpl();
				taskCommandNew.setId(taskCommand.getId());
				taskCommandNew.setName(taskCommand.getName());
				taskCommandNew.setTaskCommandDefType(null);
				taskCommandNew.setTaskCommandType(taskCommand.getTaskCommandType());
				taskCommandNew.setUserTask(userTaskBehavior);
				for (CommandParameter param : taskCommand.getCommandParams()) {
					CommandParamImpl commandParamEngine=new CommandParamImpl();
					commandParamEngine.setKey(param.getId());
					commandParamEngine.setName(param.getName());
					commandParamEngine.setDescription(param.getDescription());
					commandParamEngine.setBizType(CommandParamType.valueOf(param.getBizType()));
					commandParamEngine.setDataType(param.getDataType());
					commandParamEngine.setExpression(param.getExpression());
					taskCommandNew.getCommandParams().add(commandParamEngine);
				}
				taskCommandNew.setExpression(new ExpressionImpl(taskCommand.getExpression()));
				taskDefinition.getTaskCommands().add(taskCommandNew);
			}
		}

		String claimType = userTask.getClaimType();
		taskDefinition.setClaimType(claimType);
		userTaskBehavior.setTaskDefinition(taskDefinition);
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) getFlowElementsContainer();
		processDefinition.getTaskDefinitions().put(taskID, taskDefinition);


		List<org.foxbpm.model.FormParam> formParamsModel = userTask.getFormParams();
		List<FormParam> formParams = new ArrayList<FormParam>();
		if(formParamsModel != null){
			for (org.foxbpm.model.FormParam formParam : formParamsModel) {

				FormParam formParamObj = new FormParam();
				formParamObj.setParamKey(formParam.getId());
				formParamObj.setExpression(formParam.getExpression());
				formParamObj.setParamType(formParam.getParamType());
				formParams.add(formParamObj);
			}
		}
		taskDefinition.setFormParams(formParams);

		taskDefinition.setTaskType(userTask.getTaskType());
		taskDefinition.setTaskSubject(userTask.getSubject());
		taskDefinition.setFormUri(userTask.getFormUri());
		taskDefinition.setFormUriView(userTask.getFormUriView());
		taskDefinition.setTaskDescription(userTask.getTaskDescription());
		taskDefinition.setCompleteTaskDescription(userTask.getCompleteDescription());
		taskDefinition.setExpectExecuteTime(0);
		
		List<Connector> actors = userTask.getActorConnectors();
		if(actors != null){
			for(Connector tmpConnector : actors){
				ConnectorListener cListener = new ConnectorListener();
				cListener.setConnector(tmpConnector);
				taskDefinition.getActorConnectors().add(cListener);
			}
		}
		return super.parser(baseElement);
	}

	 
	public void init() {
		baseElementBehavior = new UserTaskBehavior();
	}

}
