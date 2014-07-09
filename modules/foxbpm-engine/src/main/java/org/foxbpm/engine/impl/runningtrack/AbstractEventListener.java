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

import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelListener;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;

public abstract class AbstractEventListener implements KernelListener {

	/**
	 * serialVersionUID:序列化
	 */
	private static final long serialVersionUID = 6870039432248742401L;

	@Override
	public void notify(ListenerExecutionContext executionContext) throws Exception {
		// 记录流程实例的运行轨迹
		RunningTrackEntity runningTrackEntity = this.recordRunningTrack(executionContext);
		if (runningTrackEntity == null || StringUtil.isBlank(runningTrackEntity.getId())) {
			throw new FoxBPMException("分类构造的运行轨迹实体 不能为空，实体唯一标识不能为空 ");
		}
		this.saveRunningTrackEntity(runningTrackEntity);

		// TODO 用户定制其他的监听操作
	}
	protected abstract RunningTrackEntity recordRunningTrack(
			ListenerExecutionContext executionContext);
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
}
