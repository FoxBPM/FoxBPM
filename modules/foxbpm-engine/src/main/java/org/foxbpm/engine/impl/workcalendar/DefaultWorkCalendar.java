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
package org.foxbpm.engine.impl.workcalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.foxbpm.engine.calendar.WorkCalendar;

/**
 * 默认工作日历计算类
 * @author ych
 *
 */
public class DefaultWorkCalendar implements WorkCalendar {

	 
	public Date getDueTime(Date begin, double expectedHours, Map<String, Object> params) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		int minute = (int)(expectedHours*60);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

}
