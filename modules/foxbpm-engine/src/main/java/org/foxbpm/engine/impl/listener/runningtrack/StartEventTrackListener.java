package org.foxbpm.engine.impl.listener.runningtrack;

import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.RunningTrackEntity;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.runtime.ListenerExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class StartEventTrackListener extends AbstractTrackListener {

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

		// 创建运行轨迹
		RunningTrackEntity runningTrackEntity = new RunningTrackEntity();
		runningTrackEntity.setOperator(Authentication.getAuthenticatedUserId());
		KernelFlowNodeImpl startFlowNode = processInstanceEntity.getStartFlowNode();
		runningTrackEntity.setNodeId(startFlowNode.getId());
		runningTrackEntity.setNodeName(startFlowNode.getName());
		return runningTrackEntity;
	}

}
