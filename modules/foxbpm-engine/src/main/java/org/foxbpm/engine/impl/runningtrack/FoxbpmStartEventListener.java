package org.foxbpm.engine.impl.runningtrack;

import java.util.Date;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class FoxbpmStartEventListener extends AbstractEventListener {

	/**
	 * serialVersionUID:序列化ID
	 * 
	 */
	private static final long serialVersionUID = -4666204303153498711L;

	@Override
	protected RunningTrackEntity recordRunningTrack(ListenerExecutionContext executionContext) {
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

		KernelFlowNodeImpl startFlowNode = processInstanceEntity.getStartFlowNode();
		runningTrackEntity.setNodeId(startFlowNode.getId());
		runningTrackEntity.setNodeName(startFlowNode.getName());

		runningTrackEntity.setEventName(kernelTokenImpl.getEventName());
		runningTrackEntity.setTokenId(kernelTokenImpl.getId());
		runningTrackEntity.setArchiveTime(processInstanceEntity.getArchiveTime());
		return runningTrackEntity;
	}

}
