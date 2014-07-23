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

import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.quartz.TokenTimeoutAutoExecuteJob;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.QuartzUtil;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

public class TimerEventBehavior extends EventDefinition {

	private static final long serialVersionUID = 1L;

	/**
	 * 日期表达式
	 */
	private Expression timeDate;

	/**
	 * 时间间隔表达式
	 */
	private Expression timeDuration;

	/**
	 * Cycle表达式
	 */
	private Expression timeCycle;

	@Override
	public void execute(FlowNodeExecutionContext executionContext) {
		KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
		// TODO QuartzUtil类是否需要改方法参数，processDefinitionID改成 processInstanceID
		FoxbpmJobDetail<TokenTimeoutAutoExecuteJob> tokenTimeoutAutoExecuteJobDetail = new FoxbpmJobDetail<TokenTimeoutAutoExecuteJob>(
				new TokenTimeoutAutoExecuteJob(GuidUtil.CreateGuid(),
						kernelTokenImpl.getProcessInstanceId()));
		tokenTimeoutAutoExecuteJobDetail.createTriggerList(this.getTimeDate(), kernelTokenImpl);
		tokenTimeoutAutoExecuteJobDetail.putContextAttribute(
				FoxbpmJobExecutionContext.PROCESS_INSTANCE_ID,
				kernelTokenImpl.getProcessInstanceId());
		tokenTimeoutAutoExecuteJobDetail.putContextAttribute(FoxbpmJobExecutionContext.TOKEN_ID,
				kernelTokenImpl.getId());
		tokenTimeoutAutoExecuteJobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID,
				kernelTokenImpl.getFlowNode().getId());

		QuartzUtil.scheduleFoxbpmJob(tokenTimeoutAutoExecuteJobDetail);
	}

	public Expression getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = new ExpressionImpl(timeDate);
	}

	public Expression getTimeDuration() {
		return timeDuration;
	}

	public void setTimeDuration(String timeDuration) {
		this.timeDuration = new ExpressionImpl(timeDuration);
	}

	public Expression getTimeCycle() {
		return timeCycle;
	}

	public void setTimeCycle(String timeCycle) {
		this.timeCycle = new ExpressionImpl(timeCycle);
	}

}
