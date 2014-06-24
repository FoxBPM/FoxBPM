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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.connector.Connector;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.event.AbstractTaskEvent;
import org.foxbpm.engine.impl.task.FormParam;
import org.foxbpm.engine.impl.task.TaskDefinition;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * 人工任务处理器
 * 
 * @author kenshin
 * 
 */
public class UserTaskBehavior extends TaskBehavior {

	private static final long serialVersionUID = 1L;


	/** 任务信息定义 */
	private TaskDefinition taskDefinition;


	@Override
	public void execute(FlowNodeExecutionContext executionContext) {

		TaskEntity task = TaskEntity.createAndInsert(executionContext);

		task.setTaskDefinition(taskDefinition);

		task.setAutoClaim(taskDefinition.isAutoClaim());

		task.setNodeId(getId());
		
		

		task.setNodeName(getName());

		((TokenEntity) executionContext).setAssignTask(task);

		ProcessInstanceEntity processInstance = (ProcessInstanceEntity) executionContext.getProcessInstance();
		
		task.setBizKey(processInstance.getBizKey());
		
		ProcessDefinitionEntity processDefinition=(ProcessDefinitionEntity)processInstance.getProcessDefinition();
		
		if(taskDefinition.getTaskSubject()==null||StringUtil.isEmpty(taskDefinition.getTaskSubject().getExpressionText())){
			
			task.setSubject(StringUtil.getString(processDefinition.getSubject().getValue(executionContext)));
		}else{
			task.setSubject(StringUtil.getString(taskDefinition.getTaskSubject().getValue(executionContext)));
		}
		
		if(StringUtil.isEmpty(task.getSubject())){
			task.setSubject(getName());
		}
		
		
		
		
		task.setDescription(StringUtil.getString(taskDefinition.getTaskDescription().getValue(executionContext)));
		task.setCompleteDescription(StringUtil.getString(taskDefinition.getCompleteTaskDescription().getValue(executionContext)));
		task.setToken((TokenEntity) executionContext);
		task.setTaskType(taskDefinition.getTaskType());
		
		task.setFormUri(StringUtil.getString(taskDefinition.getFormUri().getValue(executionContext)));
		task.setFormUriView(StringUtil.getString(taskDefinition.getFormUriView().getValue(executionContext)));
		
		if(taskDefinition.getTaskPriority() !=null){
			task.setPriority(StringUtil.getInt(taskDefinition.getTaskPriority().getValue(executionContext)));
		}
		List<FormParam> formParams = taskDefinition.getFormParams();
		if(formParams!=null&&formParams.size()>0){
			Map<String, Object> paramMap=new HashMap<String, Object>();
			for (FormParam formParam : formParams) {
				
				paramMap.put(formParam.getParamKey(), formParam.getExpression().getValue(executionContext));
			}
			task.setParamMap(paramMap);
		}
		
		

		for (Connector connector : taskDefinition.getActorConnectors()) {
			try {
				connector.notify((ListenerExecutionContext) executionContext);
			} catch (Exception e) {
				if(e instanceof FoxBPMException)
					throw (FoxBPMException)e;
				else{
					throw new FoxBPMException("执行选择人处理器失败！节点"+this.getId()+",处理器："+connector.getConnectorId() , e);
				}
			}
		}
		
		TokenEntity tokenEntity=(TokenEntity)executionContext;
		tokenEntity.setAssignTask(task);
		/** 触发分配事件(后事件) */
		executionContext.fireEvent(AbstractTaskEvent.TASK_ASSIGN);
		tokenEntity.setAssignTask(null);

	}

	

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition taskDefinition) {
		this.taskDefinition = taskDefinition;
	}



	@Override
	public void cleanData(FlowNodeExecutionContext executionContext) {
		super.cleanData(executionContext);
	}

	


}
