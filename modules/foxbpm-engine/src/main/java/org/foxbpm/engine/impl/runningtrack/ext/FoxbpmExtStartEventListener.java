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
package org.foxbpm.engine.impl.runningtrack.ext;

import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.task.TaskType;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * 流程启动事件监听
 * 
 * @author yangguangftlp
 * @date 2014年7月16日
 */
public class FoxbpmExtStartEventListener extends AbstractExtEventListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8093133229946107408L;

	private static final String START_TYPE = "start";

	@Override
	protected Object handleOperate(ListenerExecutionContext executionContext) {
		TokenEntity tokenEntity = (TokenEntity) executionContext;
		KernelFlowNodeImpl kernelFlowNode = tokenEntity.getProcessInstance().getStartFlowNode();
		// 创建流程启动
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setNodeId(kernelFlowNode.getId());
		taskEntity.setNodeName(kernelFlowNode.getName());
		taskEntity.setCommandMessage(kernelFlowNode.getName());
		taskEntity.setCommandType(START_TYPE);
		taskEntity.setTaskType(TaskType.STARTEVENTTASK);
		return taskEntity;
	}

}
