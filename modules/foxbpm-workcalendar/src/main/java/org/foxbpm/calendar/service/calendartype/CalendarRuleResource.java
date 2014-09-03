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
 * @author demornain
 */
package org.foxbpm.calendar.service.calendartype;

import java.util.Set;

import org.foxbpm.calendar.rest.common.AbstractRestResource;
import org.foxbpm.calendar.rest.common.DataResult;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.ProcessEngineManagement;
import org.restlet.data.Form;
import org.restlet.resource.Get;

public class CalendarRuleResource extends AbstractRestResource{
	@Get
	public DataResult getTasks(){
		
		Form queryForm = getQuery();
		Set<String> queryNames = queryForm.getNames();
		
		if(!validationUser())
			return null;
		
		WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		
		DataResult result = new DataResult();
		result.setData(workCalendarService.getCalendarRule());
		result.setPageIndex(pageIndex);
		result.setPageSize(pageSize);
		result.setRecordsTotal(workCalendarService.getCalendarRule().size());
		result.setRecordsFiltered(workCalendarService.getCalendarRule().size());
		return result;
	}
}
