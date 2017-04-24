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
 * 流程结束事件监听
 * 
 * @author yangguangftlp
 * @author kenshin 7.22号修改,创建的任务处理命令从系统变量获取
 * @date 2014年7月16日
 */
public class EndEventTaskListener extends AbstractTaskEventListener {
	
	private static final long serialVersionUID = -261843320544102107L;
	
	protected TaskEntity handleTaskEntity(ListenerExecutionContext executionContext) {
		TokenEntity tokenEntity = (TokenEntity) executionContext;
		KernelFlowNodeImpl kernelFlowNode = tokenEntity.getFlowNode();
		// 创建流程结束
		TaskEntity taskEntity = TaskEntity.createAndInsert(tokenEntity);
		taskEntity.setNodeId(kernelFlowNode.getId());
		taskEntity.setNodeName(kernelFlowNode.getName());
		taskEntity.setBizKey(tokenEntity.getBizKey());
		taskEntity.setTaskType(TaskType.ENDEVENTTASK);
		taskEntity.setSubject("结束流程");
		taskEntity.setCommandId(TaskCommandSystemType.ENDEVENT);
		taskEntity.setCommandType(TaskCommandSystemType.ENDEVENT);
		taskEntity.setCommandMessage("结束流程");
		taskEntity.setAssignee(tokenEntity.getProcessInstance().getStartAuthor());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 1);
		taskEntity.setCreateTime(calendar.getTime());
		taskEntity.setEndTime(calendar.getTime());
		TaskCommandDefinition taskCommandDef = Context.getProcessEngineConfiguration().getTaskCommandDefinitionMap().get(TaskCommandSystemType.ENDEVENT);
		if (taskCommandDef != null) {
			taskEntity.setCommandMessage(taskCommandDef.getName());
		}
		
		return taskEntity;
	}
}
