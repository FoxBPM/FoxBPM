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

import org.foxbpm.engine.impl.cmd.TimeStartProcessInstanceCmd;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.foxbpm.kernel.process.impl.KernelFlowNodeImpl;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

/**
 * 流程实例自动启动JOB
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public class ProcessIntanceAutoStartJob extends AbstractQuartzScheduleJob {
	public ProcessIntanceAutoStartJob(String name, String groupName,
			Trigger trigger) {
		super(name, groupName, trigger);
	}

	@Override
	public void executeJob(FoxbpmJobExecutionContext foxpmJobExecutionContext)
			throws JobExecutionException {
		String processDefinitionId = foxpmJobExecutionContext.getProcessId();
		String nodeId = foxpmJobExecutionContext.getNodeId();
		String processDefinitionKey = foxpmJobExecutionContext.getProcessKey();
		String businessKey = foxpmJobExecutionContext.getBizKey();
		Map<String, Object> transientVariableMap = new HashMap<String, Object>();
		KernelFlowNodeImpl startFlowNode = foxpmJobExecutionContext
				.getKernelFlowNodeImpl();
		TimeStartProcessInstanceCmd<ProcessInstance> timeStartProcessInstanceCmd = new TimeStartProcessInstanceCmd<ProcessInstance>();
		timeStartProcessInstanceCmd.setNodeId(nodeId);
		timeStartProcessInstanceCmd.setProcessDefinitionId(processDefinitionId);
		timeStartProcessInstanceCmd.setTransientVariables(transientVariableMap);
		timeStartProcessInstanceCmd
				.setProcessDefinitionKey(processDefinitionKey);
		timeStartProcessInstanceCmd.setStartFlowNode(startFlowNode);

		timeStartProcessInstanceCmd.setBusinessKey(businessKey);
		// 执行流程启动命令
		this.commandExecutor.execute(timeStartProcessInstanceCmd);
	}

}
