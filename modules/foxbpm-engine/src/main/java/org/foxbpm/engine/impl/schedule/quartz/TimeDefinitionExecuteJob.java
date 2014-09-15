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
package org.foxbpm.engine.impl.schedule.quartz;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.ModelService;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.RuntimeServiceImpl;
import org.foxbpm.engine.impl.bpmn.behavior.BoundaryEventBehavior;
import org.foxbpm.engine.impl.bpmn.behavior.IntermediateCatchEventBehavior;
import org.foxbpm.engine.impl.entity.ProcessDefinitionEntity;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.behavior.KernelFlowNodeBehavior;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * 事件时间定义的自动执行JOB、包括边界事件时间定义和中间事件时间定义
 * 
 * MAENLIANG 2014年7月8日 下午1:59:37
 * 
 * @version 1.0.0
 * 
 */
public class TimeDefinitionExecuteJob extends AbstractQuartzScheduleJob {
	private static Logger LOG = LoggerFactory.getLogger(TimeDefinitionExecuteJob.class);
	
	/**
	 * quartz系统创建
	 */
	public TimeDefinitionExecuteJob() {
	}
	
	/**
	 * 本地自动调度创建
	 * 
	 * @param name
	 * @param groupName
	 * @param trigger
	 */
	public TimeDefinitionExecuteJob(String name, String groupName) {
		super(name, groupName);
	}
	
	@Override
	public void executeJob(FoxbpmJobExecutionContext foxpmJobExecutionContext)
	    throws JobExecutionException {
		String processId = foxpmJobExecutionContext.getProcessId();
		String tokenId = foxpmJobExecutionContext.getTokenId();
		String nodeId = foxpmJobExecutionContext.getNodeId();
		ModelService modelService = ProcessEngineManagement.getDefaultProcessEngine().getModelService();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) modelService.getProcessDefinition(processId);
		// 获取BoundaryEventBehavior 和 IntermediateCatchEventBehavior
		KernelFlowNodeBehavior kernelFlowNodeBehavior = null;
	
		for (KernelFlowNodeImpl kernelFlowNodeImpl : processDefinitionEntity.getFlowNodes()) {
			kernelFlowNodeBehavior = kernelFlowNodeImpl.getKernelFlowNodeBehavior();
			if (StringUtil.equals(kernelFlowNodeImpl.getId(), nodeId)
			        && (kernelFlowNodeBehavior instanceof BoundaryEventBehavior || kernelFlowNodeBehavior instanceof IntermediateCatchEventBehavior)) {
				break;
			}
		}
		
		Map<String, Object> transientVariables = new HashMap<String, Object>();
		Map<String, Object> persistenceVariables = new HashMap<String, Object>();
		
		// 驱动令牌
		RuntimeServiceImpl runtimeService = (RuntimeServiceImpl) ProcessEngineManagement.getDefaultProcessEngine().getRuntimeService();
		if (kernelFlowNodeBehavior != null) {
			if (kernelFlowNodeBehavior instanceof IntermediateCatchEventBehavior) {
				LOG.debug("TimeDefinitionExecuteJob执行,执行中间事件时间定义,执行参数为=实例ID:{} 令牌ID:{} 节点ID:{}", processId, tokenId, nodeId);
				
				// 中间事件时间定义
				runtimeService.signal(tokenId, transientVariables, persistenceVariables);
			} else if (kernelFlowNodeBehavior instanceof BoundaryEventBehavior) {
				LOG.debug("TimeDefinitionExecuteJob执行,执行边间事件时间定义,执行参数为=实例ID:{} 令牌ID:{} 节点ID:{}", processId, tokenId, nodeId);
				
				// 边界时间定义
				runtimeService.boundaryTimeSignal(tokenId, nodeId, ((BoundaryEventBehavior) kernelFlowNodeBehavior).isCancelActivity(), transientVariables, persistenceVariables);
			}
		}
		
	}
}
