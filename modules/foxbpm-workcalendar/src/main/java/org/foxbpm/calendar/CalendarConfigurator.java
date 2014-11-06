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
package org.foxbpm.calendar;

import org.foxbpm.calendar.mybatis.FoxbpmCalendarMapper;
import org.foxbpm.calendar.service.impl.WorkCalendarImpl;
import org.foxbpm.calendar.service.impl.WorkCalendarServiceImpl;
import org.foxbpm.engine.config.ProcessEngineConfigurator;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;

public class CalendarConfigurator implements ProcessEngineConfigurator {

	public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
		//暴露workCalendarService接口
		processEngineConfiguration.getProcessServices().add(new WorkCalendarServiceImpl());
		//注册calendar插件用到的mybatis相关的mapper文件
		processEngineConfiguration.getCustomMapperConfig().add(new FoxbpmCalendarMapper());
	}

	public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
		//替换掉引擎自带的日历计算插件
		processEngineConfiguration.setWorkCalendar(new WorkCalendarImpl());
	}

	public int getPriority() {
		return 0;
	}

}
