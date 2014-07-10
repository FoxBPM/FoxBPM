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

import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class FoxbpmSequenceEventListener extends AbstractEventListener {
	/**
	 * serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = -4927827964817111540L;
	@Override
	protected RunningTrackEntity recordRunningTrack(ListenerExecutionContext executionContext) {
		KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
		// 创建运行轨迹
		RunningTrackEntity runningTrackEntity = new RunningTrackEntity();
		runningTrackEntity.setOperator(Authentication.getAuthenticatedUserId());
		KernelSequenceFlowImpl sequenceFlow = kernelTokenImpl.getSequenceFlow();
		runningTrackEntity.setNodeId(sequenceFlow.getId());
		runningTrackEntity.setNodeName(sequenceFlow.getName());
		return runningTrackEntity;
	}

}
