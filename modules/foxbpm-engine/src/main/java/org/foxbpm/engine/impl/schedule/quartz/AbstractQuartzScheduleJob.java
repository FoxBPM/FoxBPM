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

import org.foxbpm.engine.impl.schedule.FoxbpmJobExecutionContext;
import org.foxbpm.engine.impl.schedule.FoxbpmScheduleJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 初始化FoxbpmJobExecution上下文，初始化commandExecutor
 * 
 * @author MAENLIANG
 * @date 2014-06-25
 * 
 */
public abstract class AbstractQuartzScheduleJob extends FoxbpmScheduleJob {
	
	/**
	 * QUARTZ系统自动调度、 创建一个新的实例 AbstractQuartzScheduleJob.
	 * 
	 */
	public AbstractQuartzScheduleJob() {
	}
	
	/**
	 * 
	 * 创建一个新的实例 AbstractQuartzScheduleJob.
	 * 
	 * @param name
	 * @param groupName
	 * @param trigger
	 */
	public AbstractQuartzScheduleJob(String name, String groupName) {
		super(name, groupName);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 统一封装数据,统一设置执行命令
		FoxbpmJobExecutionContext foxpmJobExecutionContext = new FoxbpmJobExecutionContext(context);
		/** 扩展其他相关功能 */
		// 执行任务
		this.executeJob(foxpmJobExecutionContext);
	}
	
}
