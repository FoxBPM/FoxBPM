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
package org.foxbpm.calendar.service.impl;

import java.util.Date;
import java.util.Map;

import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.calendar.WorkCalendar;
import org.foxbpm.engine.impl.Context;
import org.foxbpm.engine.impl.ProcessEngineConfigurationImpl;

public class WorkCalendarImpl implements WorkCalendar {

	@Override
	public Date getDueTime(Date begin, double expectedHours, Map<String, Object> params) {
		WorkCalendarService workCalendarService = null;
		ProcessEngineConfigurationImpl processEngineConfigurationImpl = Context.getProcessEngineConfiguration();
		if(processEngineConfigurationImpl != null){
			workCalendarService = processEngineConfigurationImpl.getService(WorkCalendarService.class);
		}else{
			workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		}
		Date result = workCalendarService.getDueTime("AAA", begin, expectedHours);
		return result;
	}

}
