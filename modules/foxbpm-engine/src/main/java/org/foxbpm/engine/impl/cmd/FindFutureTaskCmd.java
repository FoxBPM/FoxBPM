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
package org.foxbpm.engine.impl.cmd;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.bpmn.behavior.TaskBehavior;
import org.foxbpm.engine.impl.entity.ProcessInstanceEntity;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.engine.task.Task;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.KernelFlowNode;
import org.foxbpm.kernel.process.KernelSequenceFlow;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.foxbpm.kernel.process.impl.KernelProcessDefinitionImpl;

/**
 * 根据待办任务Id查询分支前的未办任务
 * @author MAENLIANG
 *
 */
public class FindFutureTaskCmd implements Command<List<Task>> {

	protected String todoTaskId;
	
	public FindFutureTaskCmd(String todoTaskId){
		this.todoTaskId=todoTaskId;
	}
	
	public List<Task> execute(CommandContext commandContext) {
		List<Task> listTaskEntity = new ArrayList<Task>();
		TaskEntity todoTaskEntity=commandContext.getTaskManager().findTaskById(todoTaskId);
		ProcessInstanceEntity processInstance = commandContext.getProcessInstanceManager().findProcessInstanceById(todoTaskEntity.getProcessInstanceId());
		KernelProcessDefinitionImpl processDefinition = processInstance.getProcessDefinition();
		List<KernelFlowNodeImpl> flowNodes = processDefinition.getFlowNodes();
		for(KernelFlowNodeImpl kernelFlowNodeImpl: flowNodes){
			//当前待办的任务节点
			if(StringUtil.equals(kernelFlowNodeImpl.getId(), todoTaskEntity.getNodeId())){
				this.createFutureTask(listTaskEntity, kernelFlowNodeImpl);
			}
		}
		return listTaskEntity;
	}
	
	private void createFutureTask(List<Task> listTaskEntity,KernelFlowNodeImpl kernelFlowNodeImpl){
		List<KernelSequenceFlow> outgoingSequenceFlows = kernelFlowNodeImpl.getOutgoingSequenceFlows();
		if(outgoingSequenceFlows.size() ==1){
			KernelSequenceFlow kernelSequenceFlow = outgoingSequenceFlows.get(0);
			KernelFlowNode targetRef = kernelSequenceFlow.getTargetRef();
			//必须是任务活动节点
			if(targetRef instanceof KernelFlowNodeImpl){
				KernelFlowNodeImpl futureFlowNode = (KernelFlowNodeImpl)targetRef;
				KernelFlowNodeBehavior kernelFlowNodeBehavior = futureFlowNode.getKernelFlowNodeBehavior();
				if(kernelFlowNodeBehavior instanceof TaskBehavior && futureFlowNode.getOutgoingSequenceFlows().size() ==1) {
					TaskEntity taskEntity = new TaskEntity();
					taskEntity.setName(futureFlowNode.getName());
					taskEntity.setId(futureFlowNode.getId());
					taskEntity.setNodeId(futureFlowNode.getId());
					taskEntity.setNodeName(futureFlowNode.getName());
					listTaskEntity.add(taskEntity);
					this.createFutureTask(listTaskEntity, futureFlowNode);
				} else {
					return;
				}
			}
		}
	}
	

}
