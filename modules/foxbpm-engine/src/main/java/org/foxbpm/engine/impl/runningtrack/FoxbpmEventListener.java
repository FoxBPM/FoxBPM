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

import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelEventType;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.process.impl.KernelSequenceFlowImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class FoxbpmEventListener extends AbstractEventListener {

	/**
	 * serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = -4927827964817111540L;
	@Override
	protected void recordRunningTrack(ListenerExecutionContext executionContext) {
		KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
		ProcessInstanceEntity processInstanceEntity = (ProcessInstanceEntity) kernelTokenImpl
				.getProcessInstance();
		KernelProcessDefinitionImpl processDefinition = kernelTokenImpl.getProcessDefinition();

		// 创建运行轨迹
		RunningTrackEntity runningTrackEntity = new RunningTrackEntity();
		runningTrackEntity.setId(GuidUtil.CreateGuid());
		runningTrackEntity.setProcessInstanceId(kernelTokenImpl.getProcessInstanceId());
		runningTrackEntity.setProcessDefinitionId(processDefinition.getId());
		runningTrackEntity.setProcessDefinitionKey(processDefinition.getKey());
		runningTrackEntity.setExecutionTime(new Date());
		runningTrackEntity.setOperator(Authentication.getAuthenticatedUserId());

		String nodeId = null;
		String nodeName = null;
		// 区分处理节点和线条
		if (StringUtil.equals(kernelTokenImpl.getEventName(),
				KernelEventType.EVENTTYPE_PROCESS_START)) {
			nodeId = processInstanceEntity.getStartFlowNode().getId();
			nodeName = processInstanceEntity.getStartFlowNode().getName();
		} else {
			KernelFlowNodeImpl flowNode = kernelTokenImpl.getFlowNode();
			if (flowNode != null) {
				nodeId = flowNode.getId();
				nodeName = flowNode.getName();
			} else {
				KernelSequenceFlowImpl sequenceFlow = kernelTokenImpl.getSequenceFlow();
				nodeId = sequenceFlow.getId();
				nodeName = sequenceFlow.getName();
			}
		}

		runningTrackEntity.setNodeId(nodeId);
		runningTrackEntity.setNodeName(nodeName);

		runningTrackEntity.setEventName(kernelTokenImpl.getEventName());
		runningTrackEntity.setTokenId(kernelTokenImpl.getId());
		runningTrackEntity.setArchiveTime(processInstanceEntity.getArchiveTime());
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

}
