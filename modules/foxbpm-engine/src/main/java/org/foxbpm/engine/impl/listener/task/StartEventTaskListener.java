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

import java.util.Calendar;

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.task.TaskCommandSystemType;
import org.foxbpm.engine.task.TaskCommandDefinition;
import org.foxbpm.engine.task.TaskType;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * 流程启动事件监听
 * 
 * @author yangguangftlp
 * @date 2014年7月16日
 */
public class StartEventTaskListener extends AbstractTaskEventListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8093133229946107408L;
	
	protected TaskEntity handleTaskEntity(ListenerExecutionContext executionContext) {
		TokenEntity tokenEntity = (TokenEntity) executionContext;
		KernelFlowNodeImpl kernelFlowNode = tokenEntity.getProcessInstance().getStartFlowNode();
		// 创建流程启动
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setNodeId(kernelFlowNode.getId());
		taskEntity.setBizKey(tokenEntity.getBizKey());
		taskEntity.setNodeName(kernelFlowNode.getName());
		taskEntity.setTaskType(TaskType.STARTEVENTTASK);
		taskEntity.setSubject("启动流程");
		taskEntity.setCommandId(TaskCommandSystemType.STARTEVENT);
		taskEntity.setCommandType(TaskCommandSystemType.STARTEVENT);
		taskEntity.setCommandMessage("启动流程");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, -1);
		taskEntity.setCreateTime(calendar.getTime());
		taskEntity.setEndTime(calendar.getTime());
		taskEntity.setAssignee(tokenEntity.getProcessInstance().getStartAuthor());
		TaskCommandDefinition taskCommandDef = Context.getProcessEngineConfiguration().getTaskCommandDefinitionMap().get(TaskCommandSystemType.STARTEVENT);
		if (taskCommandDef != null) {
			taskEntity.setCommandMessage(taskCommandDef.getName());
		}
		
		return taskEntity;
	}
}
