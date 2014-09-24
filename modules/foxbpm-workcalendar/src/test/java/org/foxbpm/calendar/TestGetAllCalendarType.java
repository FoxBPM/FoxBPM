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
package org.foxbpm.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;

public class TestGetAllCalendarType extends AbstractFoxBpmTestCase{
	
	@Test
	public void testA(){
		WorkCalendarService workCalendarService = processEngine.getProcessEngineConfiguration().getService(WorkCalendarService.class);
		
		Calendar calendar = Calendar.getInstance();
		
		Date date = workCalendarService.getDueTime(calendar.getTime(), 45,null);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		System.out.println(df.format(date));
		
	}
}
