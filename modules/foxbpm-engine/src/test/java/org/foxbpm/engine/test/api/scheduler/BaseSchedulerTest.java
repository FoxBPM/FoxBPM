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
package org.foxbpm.engine.test.api.scheduler;

import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * 
 * 
 * BaseSchedulerTest quartz框架测试的父类
 * 
 * kin kin 2014年7月29日 上午10:20:39
 * 
 * @version 1.0.0
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseSchedulerTest extends AbstractFoxBpmTestCase {
	/** 每个测试用例 quartz完成调度所需要的基本时间：2分钟 */
	public final static int QUART_SCHEDULED_TIME = 2;
	
	/**
	 * 
	 * cleanRunData(清空流程运行过程中的数据) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	protected void cleanRunData() {
		jdbcTemplate.execute("delete from foxbpm_run_processinstance");
		jdbcTemplate.execute("delete from foxbpm_run_task");
		jdbcTemplate.execute("delete from foxbpm_run_taskidentitylink");
		jdbcTemplate.execute("delete from foxbpm_run_token");
		jdbcTemplate.execute("delete from foxbpm_run_variable");
	}
	
	/**
	 * 
	 * waitQuartzScheduled(设置调度等待的时间)
	 * 
	 * @param minitues
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	protected void waitQuartzScheduled(int minitues) {
		try {
			Thread.sleep(1000 * 60 * minitues);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
