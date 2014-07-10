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
package org.foxbpm.engine;

import java.util.List;

import org.foxbpm.engine.impl.schedule.FoxbpmScheduler;
import org.quartz.Trigger;

/**
 * 调度器服务类
 * 
 * @author MAENLIANG
 * @date 2014-06-24
 * 
 */
public interface ScheduleService {
	/**
	 * 设置调度器
	 * 
	 * @param foxbpmScheduler
	 */
	public void setFoxbpmScheduler(FoxbpmScheduler foxbpmScheduler);

	/**
	 * 暂停任务
	 * 
	 * @param name
	 * @param group
	 */
	public void suspendJob(String name, String group);

	/**
	 * 继续任务
	 * 
	 * @param name
	 * @param group
	 */
	public void continueJob(String name, String group);

	/**
	 * 获取所有的触发器
	 * 
	 * @param name
	 * @param group
	 */
	public List<Trigger> getTriggerList(String jobName, String jobGroup);
}
