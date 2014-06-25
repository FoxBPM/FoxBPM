package org.foxbpm.engine.impl.schedule.quartz;

import java.util.HashMap;
import java.util.Map;

import org.foxbpm.engine.impl.cmd.TimeStartProcessInstanceCmd;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.runtime.ProcessInstance;
import org.quartz.JobExecutionException;

public class ProcessIntanceAutoStartJob extends AbstractQuartzScheduleJob {

	@Override
	public void executeJob(FoxbpmJobExecutionContext foxpmJobExecutionContext)
			throws JobExecutionException {
		String processDefinitionId = foxpmJobExecutionContext.getProcessId();
		String nodeId = foxpmJobExecutionContext.getNodeId();
		String processDefinitionKey = foxpmJobExecutionContext.getProcessKey();
		String businessKey = foxpmJobExecutionContext.getBizKey();
		Map<String, Object> transientVariableMap = new HashMap<String, Object>();

		TimeStartProcessInstanceCmd<ProcessInstance> timeStartProcessInstanceCmd = new TimeStartProcessInstanceCmd<ProcessInstance>();
		timeStartProcessInstanceCmd.setNodeId(nodeId);
		timeStartProcessInstanceCmd.setProcessDefinitionId(processDefinitionId);
		timeStartProcessInstanceCmd.setTransientVariables(transientVariableMap);
		timeStartProcessInstanceCmd
				.setProcessDefinitionKey(processDefinitionKey);
		timeStartProcessInstanceCmd.setBusinessKey(businessKey);
		// 执行流程启动命令
		this.commandExecutor.execute(timeStartProcessInstanceCmd);
	}

}
