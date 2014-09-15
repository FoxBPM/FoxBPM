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
package org.foxbpm.engine.impl.persistence;

import org.foxbpm.engine.impl.util.QuartzUtil;

/**
 * SchedulerManager 调度器管理类 目前是采用 quartz框架提供的接口直接操作
 * 
 * MAENLIANG 2014年7月31日 上午11:23:58
 * 
 * @version 1.0.0
 * 
 */
public class SchedulerManager extends AbstractManager {
	/**
	 * 
	 * 删除流程实例下所有的调度器
	 * 
	 * @param processInstanceId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void deleteJobByInstanceId(String processInstanceId) {
		QuartzUtil.deleteJob(processInstanceId);
	}
	
	/**
	 * 
	 * 删除流程实例下所有的调度器
	 * 
	 * @param processInstanceId
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void deleteJobByProcessKey(String processKey) {
		QuartzUtil.deleteJob(processKey);
	}
}
