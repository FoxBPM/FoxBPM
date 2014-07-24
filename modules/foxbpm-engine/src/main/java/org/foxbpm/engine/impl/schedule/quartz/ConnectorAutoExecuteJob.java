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

import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.RuntimeService;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * 连接器自动执行JOB、ConnectorAutoExecuteJob
 * 
 * MAENLIANG 2014年6月27日 下午2:17:22
 * 
 * @version 1.0.0
 * 
 */
public class ConnectorAutoExecuteJob extends AbstractQuartzScheduleJob {
	
	/**
	 * quartz系统创建
	 */
	public ConnectorAutoExecuteJob() {
	}
	
	/**
	 * 本地自动调度创建
	 * 
	 * @param name
	 * @param groupName
	 * @param trigger
	 */
	public ConnectorAutoExecuteJob(String name, String groupName) {
		super(name, groupName);
	}
	
	@Override
	public void executeJob(FoxbpmJobExecutionContext foxpmJobExecutionContext)
	    throws JobExecutionException {
		String processInstanceID = foxpmJobExecutionContext.getProcessInstanceId();
		String connectorID = foxpmJobExecutionContext.getConnectorId();
		String tokenID = foxpmJobExecutionContext.getTokenId();
		String eventName = foxpmJobExecutionContext.getEventName();
		String nodeID = foxpmJobExecutionContext.getNodeId();
		RuntimeService runtimeService = ProcessEngineManagement.getDefaultProcessEngine().getRuntimeService();
		runtimeService.autoExecuteConnector(processInstanceID, connectorID, eventName, tokenID, nodeID);
	}
	
}
