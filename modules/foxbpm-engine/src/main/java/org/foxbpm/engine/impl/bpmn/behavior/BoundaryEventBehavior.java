/**
 * Copyright 1996-2014 FoxBPM Co.,Ltd.
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
 * @author kenshin
 */
package org.foxbpm.engine.impl.bpmn.behavior;

import java.util.Iterator;
import java.util.List;

import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.quartz.TokenTimeoutAutoExecuteJob;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.QuartzUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class BoundaryEventBehavior extends CatchEventBehavior {
	private static final long serialVersionUID = 1L;
	@Override
	public void execute(FlowNodeExecutionContext executionContext) {
		List<EventDefinition> eventDefinitions = super.getEventDefinitions();
		Iterator<EventDefinition> iterator = eventDefinitions.iterator();
		EventDefinition eventDefinition = null;
		while (iterator.hasNext()) {
			eventDefinition = iterator.next();
			if (eventDefinition instanceof TimerEventDefinition) {
				// 边界事件的定时任务
				TimerEventDefinition timerEventDefinition = (TimerEventDefinition) eventDefinition;
				// 时间定义，设置自动调度工作
				this.scheduleAutoExecuteJob(timerEventDefinition,
						(KernelTokenImpl) executionContext);
			}

		}
		// super.execute(executionContext);
	}

	/**
	 * 
	 * scheduleAutoExecuteJob(保存调度信息)
	 * 
	 * @param executionContext
	 * @throws Exception
	 * @since 1.0.0
	 */
	private void scheduleAutoExecuteJob(TimerEventDefinition timerEventDefinition,
			KernelTokenImpl kernelTokenImpl) {
		// TODO QuartzUtil类是否需要改方法参数，processDefinitionID改成 processInstanceID
		FoxbpmJobDetail<TokenTimeoutAutoExecuteJob> tokenTimeoutAutoExecuteJobDetail = new FoxbpmJobDetail<TokenTimeoutAutoExecuteJob>(
				new TokenTimeoutAutoExecuteJob(GuidUtil.CreateGuid(),
						kernelTokenImpl.getProcessInstanceId()));
		tokenTimeoutAutoExecuteJobDetail.createTriggerList(timerEventDefinition.getTimeDate(),
				kernelTokenImpl);
		tokenTimeoutAutoExecuteJobDetail.putContextAttribute(
				FoxbpmJobExecutionContext.PROCESS_INSTANCE_ID,
				kernelTokenImpl.getProcessInstanceId());
		tokenTimeoutAutoExecuteJobDetail.putContextAttribute(FoxbpmJobExecutionContext.TOKEN_ID,
				kernelTokenImpl.getId());
		tokenTimeoutAutoExecuteJobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID,
				kernelTokenImpl.getFlowNode().getId());

		QuartzUtil.scheduleFoxbpmJob(tokenTimeoutAutoExecuteJobDetail);
	}
}
