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
 * @author MAENLIANG
 */
package org.foxbpm.engine.impl.runningtrack;

import java.util.Date;

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public abstract class AbstractEventListener implements KernelListener {

	private static long tractRecord = 0000000000000;
	/**
	 * serialVersionUID:序列化
	 */
	private static final long serialVersionUID = 6870039432248742401L;

	@Override
	public void notify(ListenerExecutionContext executionContext) throws Exception {
		// 记录流程实例的运行轨迹
		this.recordOperate(executionContext);

		// TODO 用户定制其他的监听操作
		// otherOperate(executionContext);
	}

	/**
	 * recordOperate(记录流程实例的运行轨迹)
	 * 
	 * @param executionContext
	 *            void
	 */
	private void recordOperate(ListenerExecutionContext executionContext) {
		// 记录流程实例的运行轨迹
		RunningTrackEntity runningTrackEntity = this.recordRunningTrack(executionContext);
		if (runningTrackEntity == null) {
			throw new FoxBPMException("分类构造的运行轨迹实体 不能为空");
		}
		KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
		KernelProcessDefinitionImpl processDefinition = kernelTokenImpl.getProcessDefinition();
		runningTrackEntity.setId(GuidUtil.CreateGuid());
		runningTrackEntity.setProcessDefinitionId(processDefinition.getId());
		runningTrackEntity.setProcessDefinitionKey(processDefinition.getKey());
		runningTrackEntity.setTrackRecord(tractRecord);
		runningTrackEntity.setExecutionTime(new Date());
		runningTrackEntity.setEventName(kernelTokenImpl.getEventName());
		runningTrackEntity.setTokenId(kernelTokenImpl.getId());
		runningTrackEntity.setProcessInstanceId(kernelTokenImpl.getProcessInstanceId());
		// 增加运行轨迹、保存数据
		tractRecord = tractRecord + 1;
		this.saveRunningTrackEntity(runningTrackEntity);
	}
	/**
	 * 
	 * saveRunningTrackEntity 保存数据
	 * 
	 * @param runningTrackEntity
	 * @since 1.0.0
	 */
	private void saveRunningTrackEntity(RunningTrackEntity runningTrackEntity) {
		Context.getCommandContext().getRunningTrackManager().insert(runningTrackEntity);
	}

	/**
	 * 
	 * recordRunningTrack(记录操作人、节点信息等特定轨迹信息)
	 * 
	 * @param executionContext
	 * @return RunningTrackEntity
	 */
	protected abstract RunningTrackEntity recordRunningTrack(
			ListenerExecutionContext executionContext);
}
