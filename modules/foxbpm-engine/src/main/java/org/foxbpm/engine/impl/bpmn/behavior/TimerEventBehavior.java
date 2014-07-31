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
import org.foxbpm.engine.exception.FoxBPMException;
import org.foxbpm.engine.expression.Expression;
import org.foxbpm.engine.impl.entity.TaskEntity;
import org.foxbpm.engine.impl.entity.TokenEntity;
import org.foxbpm.engine.impl.expression.ExpressionImpl;
import org.foxbpm.engine.impl.schedule.FoxbpmJobDetail;
import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduleJob;
import org.foxbpm.engine.impl.schedule.FoxbpmSchedulerGroupnameGernerater;
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
	 * 中间事件时间定义
	 */
	public final static String EVENT_TYPE_INTERMIDATE = "intermidateTimeEvent";
	
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
	public void execute(FlowNodeExecutionContext executionContext, String eventType,
	    Object[] objParams) {
		/** 创建JOB DETAIL */
		KernelTokenImpl kernelTokenImpl = executionContext == null ? null : (KernelTokenImpl) executionContext;
		FoxbpmJobDetail<FoxbpmScheduleJob> jobDetail = null;
		String[] params = (String[]) objParams;
		String groupName = null;
		if (StringUtil.equals(eventType, EVENT_TYPE_START)) {
			/** 定时启动流程实例、流程删除时，需要删除该JOB DETAIL */
			/** GroupName 命名采用流程定义KEY */
			groupName = params[1];
			jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(new ProcessIntanceAutoStartJob(GuidUtil.CreateGuid(), groupName));
			
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_ID, params[0]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_KEY, params[1]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_NAME, params[2]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID, params[3]);
			
		} else if (StringUtil.equals(eventType, EVENT_TYPE_BOUNDARY)
		        || StringUtil.equals(eventType, EVENT_TYPE_INTERMIDATE)) {
			/** 边界中间事件时间定义执行、令牌离开时，需要删除该JOB DETAIL */
			/** GroupName 命名采用拼接的形式 事件节点ID+流程实例ID */
			groupName = new FoxbpmSchedulerGroupnameGernerater(kernelTokenImpl).gernerateDefinitionGroupName();
			jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(new TimeDefinitionExecuteJob(GuidUtil.CreateGuid(), groupName));
			
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_INSTANCE_ID, kernelTokenImpl.getProcessInstanceId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.TOKEN_ID, kernelTokenImpl.getId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID, params[0]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_DEFINITION_ID, params[1]);
			
		} else if (StringUtil.equals(eventType, EVENT_TYPE_CONNECTOR)) {
			/** 连接器定时执行、流程结束时，需要删除该JOB DETAIL */
			/** GroupName 命名采用节点ID+流程实例ID */
			groupName = new FoxbpmSchedulerGroupnameGernerater(kernelTokenImpl).gernerateDefinitionGroupName();
			jobDetail = new FoxbpmJobDetail<FoxbpmScheduleJob>(new ConnectorAutoExecuteJob(GuidUtil.CreateGuid(), groupName));
			
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.CONNECTOR_ID, params[0]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.PROCESS_INSTANCE_ID, executionContext.getProcessInstanceId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.EVENT_NAME, params[1]);
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.TOKEN_ID, executionContext.getId());
			jobDetail.putContextAttribute(FoxbpmJobExecutionContext.NODE_ID, params[2]);
			if (StringUtils.equalsIgnoreCase(params[1], KernelEventType.EVENTTYPE_TASK_ASSIGN)) {
				TaskEntity assignTask = ((TokenEntity) executionContext).getAssignTask();
				if (assignTask != null) {
					jobDetail.putContextAttribute(FoxbpmJobExecutionContext.TASK_ID, assignTask.getId());
				}
			}
			
		}
		
		if (jobDetail == null) {
			throw new FoxBPMException("TimerEventBehavior 执行的时候 jobDetail未创建成功!");
		} else {
			// 创建Trigger List
			this.createTriggerList(jobDetail, kernelTokenImpl, groupName);
			// 保存更新调度信息
			QuartzUtil.scheduleFoxbpmJob(jobDetail);
		}
		
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
	    KernelTokenImpl kernelTokenImpl, String groupName) {
		jobDetail.createTriggerList(this.timeDate, kernelTokenImpl, groupName);
		jobDetail.createTriggerListByDuration(this.timeDuration, kernelTokenImpl, groupName);
		jobDetail.createTriggerList(this.timeCycle, kernelTokenImpl, groupName);
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
