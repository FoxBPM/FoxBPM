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
 * @author ych
 */
package org.foxbpm.engine.calendar;

import java.util.Date;
import java.util.Map;

/**
 * 工作日历,用户系统中超时任务的计算等
 * 该接口需由外部系统提供实现，如没有发现实现，则默认不计算超时时间，则超时任务等功能则无法实现。
 * 该功能一般在
 * @author ych
 *
 */
public interface WorkCalendar {

	/**
	 * 根据开始时间和期望执行时间计算超时时间
	 * <p>此方法由外部日历插件提供，根据日历规则的配置，计算出当前人员或者当前组的超时时间</p>
	 * @param begin 计算开始时间
	 * @param expectedHours 期望执行时间（小时）
	 * @param params 包含计算可能用到的参数，默认当前处理的人员编号或部门编号
	 * @return 任务截止时间（超时时间）
	 */
	public Date getDueTime(Date begin,double expectedHours,Map<String,Object> params);
	
}
