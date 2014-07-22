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
 * @author yangguangftlp
 */
package org.foxbpm.engine.impl.listener.task;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.task.TaskCommandSystemType;
import org.foxbpm.engine.task.TaskType;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.model.config.foxbpmconfig.TaskCommandDefinition;

/**
 * 流程结束事件监听
 * 
 * @author yangguangftlp
 * @author kenshin 7.22号修改,创建的任务处理命令从系统变量获取
 * @date 2014年7月16日
 */
public class EndEventTaskListener extends AbstractTaskEventListener {

	private static final long serialVersionUID = -261843320544102107L;

	@Override
	protected TaskEntity handleTaskEntity(ListenerExecutionContext executionContext) {
		TokenEntity tokenEntity = (TokenEntity) executionContext;
		KernelFlowNodeImpl kernelFlowNode = tokenEntity.getFlowNode();
		// 创建流程启动
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setNodeId(kernelFlowNode.getId());
		taskEntity.setNodeName(kernelFlowNode.getName());

		taskEntity.setTaskType(TaskType.ENDEVENTTASK);
		
		taskEntity.setCommandId(TaskCommandSystemType.ENDEVENT);
		taskEntity.setCommandType(TaskCommandSystemType.ENDEVENT);
		//taskInstance.setCommandMessage("流程启动");
		TaskCommandDefinition taskCommandDef=Context.getProcessEngineConfiguration().getTaskCommandDefinitionMap().get(TaskCommandSystemType.ENDEVENT);
		if(taskCommandDef!=null){
			taskEntity.setCommandMessage(taskCommandDef.getName());
		}

		return taskEntity;
	}
}
