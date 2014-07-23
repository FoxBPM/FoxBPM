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

import org.apache.commons.lang3.StringUtils;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduleJob;
import org.foxbpm.engine.impl.schedule.quartz.ConnectorAutoExecuteJob;
import org.foxbpm.engine.impl.schedule.quartz.ProcessIntanceAutoStartJob;
import org.foxbpm.engine.impl.schedule.quartz.TimeDefinitionExecuteJob;
import org.foxbpm.engine.impl.util.GuidUtil;
import org.foxbpm.engine.impl.util.QuartzUtil;
import org.foxbpm.engine.impl.util.StringUtil;
import org.foxbpm.kernel.event.KernelEventType;
import org.foxbpm.kernel.runtime.FlowNodeExecutionContext;
import org.foxbpm.kernel.runtime.impl.KernelTokenImpl;

/**
 * 
 * 
 * TimerEventBehavior 定时任务调度，包括 定时启动流程实例、边界定时任务、连接器定时执行
 * 
 * MAENLIANG 2014年7月23日 下午4:43:55
 * 
 * @version 1.0.0
 * 
 */
public class TimerEventBehavior extends EventDefinition {

	private static final long serialVersionUID = 1L;

	/**
	 * 时间启动事件
	 */
	public final static String EVENT_TYPE_START = "startTimeEvent";
	/**
	 * 边界时间事件
	 */
	public final static String EVENT_TYPE_BOUNDARY = "boundaryTimeEvent";

	/**
	 * 连接器自动执行
	 */
	public final static String EVENT_TYPE_CONNECTOR = "connectorTimeEvent";

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
	public void execute(FlowNodeExecutionContext executionContext, String eventType, Object[] params) {
		// 创建TRIGGER JOB JOBDETAIL
		FoxbpmJobDetail<FoxbpmScheduleJob> jobDetail = null;
		if (StringUtil.equals(eventType, EVENT_TYPE_START)) {
			/** 定时启动流程实例 */
			jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(new ProcessIntanceAutoStartJob(
					GuidUtil.CreateGuid(), (String) params[1]));
			// 根据三种表达式创建TRIGGER
			this.createTriggerList(jobDetail, null);
			// 设置调度变量
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_ID,
					(String) params[0]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_KEY,
					(String) params[1]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_NAME,
					(String) params[2]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID, (String) params[3]);

		} else if (StringUtil.equals(eventType, EVENT_TYPE_BOUNDARY)) {
			KernelTokenImpl kernelTokenImpl = (KernelTokenImpl) executionContext;
			/** 边界事件时间定义执行 */
			jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(new TimeDefinitionExecuteJob(
					GuidUtil.CreateGuid(), kernelTokenImpl.getProcessInstanceId()));

			// 根据三种表达式创建TRIGGER
			this.createTriggerList(jobDetail, kernelTokenImpl);

			// 设置调度变量
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_INSTANCE_ID,
					kernelTokenImpl.getProcessInstanceId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.TOKEN_ID,
					kernelTokenImpl.getId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID, kernelTokenImpl
					.getFlowNode().getId());

		} else if (StringUtil.equals(eventType, EVENT_TYPE_CONNECTOR)) {
			/** 连接器定时执行 */
			jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(new ConnectorAutoExecuteJob(
					GuidUtil.CreateGuid(), executionContext.getProcessInstanceId()));
			this.createTriggerList(jobDetail, (KernelTokenImpl) executionContext);

			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.CONNECTOR_ID, params[0]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_INSTANCE_ID,
					executionContext.getProcessInstanceId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.EVENT_NAME, (String) params[1]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.TOKEN_ID,
					executionContext.getId());
			if (StringUtils.equalsIgnoreCase((String) params[1],
					KernelEventType.EVENTTYPE_TASK_ASSIGN)) {
				TaskEntity assignTask = ((TokenEntity) executionContext).getAssignTask();
				if (assignTask != null) {
					jobDetail.putContextAttribute(FoxbpmJobExecutionContext.TASK_ID,
							assignTask.getId());
				}
			}

			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID, params[2]);
		}

		// 保存更新调度信息
		QuartzUtil.scheduleFoxbpmJob(jobDetail);
	}
	/**
	 * 
	 * createTriggerList(创建TriggerList)
	 * 
	 * @param jobDetail
	 * @param kernelTokenImpl
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void createTriggerList(FoxbpmJobDetail<FoxbpmScheduleJob> jobDetail,
			KernelTokenImpl kernelTokenImpl) {
		jobDetail.createTriggerList(this.timeDate, kernelTokenImpl);
		jobDetail.createTriggerList(this.timeDuration, kernelTokenImpl);
		jobDetail.createTriggerList(this.timeCycle, kernelTokenImpl);
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
