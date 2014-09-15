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
package org.foxbpm.engine.impl.listener.runningtrack;

import org.foxbpm.engine.impl.bpmn.behavior.EndEventBehavior;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class FlowNodeTrackListener extends AbstractTrackListener {
	/**
	 * serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = -4927827964817111540L;
	@Override
	protected RunningTrackEntity recordRunningTrack(ListenerExecutionContext executionContext) {
		KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
		ProcessInstanceEntity processInstanceEntity = (ProcessInstanceEntity) kernelTokenImpl
				.getProcessInstance();
		// 创建运行轨迹
		RunningTrackEntity runningTrackEntity = new RunningTrackEntity();
		runningTrackEntity.setOperator(Authentication.getAuthenticatedUserId());
		KernelFlowNodeImpl flowNode = kernelTokenImpl.getFlowNode();
		runningTrackEntity.setNodeId(flowNode.getId());
		runningTrackEntity.setNodeName(flowNode.getName());
		// 结束事件记录归档时间
		if (flowNode.getKernelFlowNodeBehavior() instanceof EndEventBehavior) {
			runningTrackEntity.setArchiveTime(processInstanceEntity.getArchiveTime());
		}

		return runningTrackEntity;
	}
}
