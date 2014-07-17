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

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.ClockUtil;
import org.foxbpm.engine.impl.util.GuidUtil;
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

	@Override
	public void notify(ListenerExecutionContext executionContext) throws Exception {
		recordOperate(executionContext);
	}

	/**
	 * 记录处理信息
	 * 
	 * @param executionContext
	 *            上下文
	 */
	private void recordOperate(ListenerExecutionContext executionContext) {
		// 记录流程实例的运行轨迹
		Object object = handleOperate(executionContext);
		if (object == null) {
			throw new FoxBPMException("分类构造的运行轨迹实体 不能为空");
		}
		TokenEntity tokenEntity = (TokenEntity) executionContext;
		if (object instanceof TaskEntity) {
			TaskEntity taskEntity = (TaskEntity) object;
			handleTaskEntitly(tokenEntity, taskEntity);
			saveTaskEntity(taskEntity);
		}
	}

	/**
	 * 额外的任务实例处理,子类可以重载定制
	 * 
	 * @param tokenEntity
	 *            流程token
	 * @param taskEntity
	 *            任务实例
	 */
	protected void handleTaskEntitly(TokenEntity tokenEntity, TaskEntity taskEntity) {
		ProcessInstanceEntity kernelProcessInstance = tokenEntity.getProcessInstance();
		ProcessDefinitionEntity kernelProcessDefinition = (ProcessDefinitionEntity) tokenEntity.getProcessDefinition();
		taskEntity.setOpen(false);
		taskEntity.setId(GuidUtil.CreateGuid());
		taskEntity.setCreateTime(ClockUtil.getCurrentTime());
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
	 * 保存实例数据
	 * 
	 * @param taskEntity
	 *            任务实例
	 * @since 1.0.0
	 */
	private void saveTaskEntity(TaskEntity taskEntity) {
		Context.getCommandContext().getTaskManager().insert(taskEntity);
	}

	/**
	 * 
	 * 事件处理操作(开始启动、结束事件等节点信息处理) 该处理有子类处理
	 * 
	 * @param executionContext
	 *            上下文
	 * @return 返回处理结果
	 */
	protected abstract Object handleOperate(ListenerExecutionContext executionContext);
}
