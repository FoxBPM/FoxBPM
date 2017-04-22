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

import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

/**
 * 可扩展事件监听器
 * 
 * @author yangguangftlp
 * @date 2014年7月16日
 */
public abstract class AbstractTaskEventListener implements KernelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3511158448950973072L;

	 
	public void notify(ListenerExecutionContext executionContext) throws Exception {
		recordTask(executionContext);
	}

	/**
	 * 记录处理信息
	 * 
	 * @param executionContext
	 *            上下文
	 */
	private void recordTask(ListenerExecutionContext executionContext) {
		// 记录流程实例的运行轨迹
		TaskEntity taskEntity = handleTaskEntity(executionContext);
		TokenEntity tokenEntity = (TokenEntity) executionContext;
		handleCommonTask(tokenEntity, taskEntity);
	}

	/**
	 * 额外的任务实例处理,子类可以重载定制
	 * 
	 * @param tokenEntity
	 *            流程token
	 * @param taskEntity
	 *            任务实例
	 */
	protected void handleCommonTask(TokenEntity tokenEntity, TaskEntity taskEntity) {
		ProcessInstanceEntity kernelProcessInstance = tokenEntity.getProcessInstance();
		ProcessDefinitionEntity kernelProcessDefinition = (ProcessDefinitionEntity) tokenEntity.getProcessDefinition();
		taskEntity.setOpen(false);
		taskEntity.setEndTime(ClockUtil.getCurrentTime());
		taskEntity.setProcessInitiator(Authentication.getAuthenticatedUserId());
		taskEntity.setProcessInstanceId(kernelProcessInstance.getId());
		taskEntity.setAssignee(kernelProcessInstance.getStartAuthor());
		taskEntity.setProcessDefinitionId(kernelProcessDefinition.getId());
		taskEntity.setProcessDefinitionKey(kernelProcessDefinition.getKey());
		taskEntity.setProcessDefinitionName(kernelProcessDefinition.getName());
		taskEntity.setTokenId(tokenEntity.getId());
	}

	/**
	 * 
	 * 事件处理操作(开始启动、结束事件等节点信息处理) 该处理有子类处理
	 * 
	 * @param executionContext
	 *            上下文
	 * @return 返回处理结果
	 */
	protected abstract TaskEntity handleTaskEntity(ListenerExecutionContext executionContext);
}
