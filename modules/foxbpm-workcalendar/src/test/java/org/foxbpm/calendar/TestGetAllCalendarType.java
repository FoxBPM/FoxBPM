package org.foxbpm.calendar;

import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.test.AbstractFoxBpmTestCase;
import org.junit.Test;

public class TestGetAllCalendarType extends AbstractFoxBpmTestCase{
	
	@Test
	public void testA(){
		WorkCalendarService workCalendarService = processEngine.getProcessEngineConfiguration().getService(WorkCalendarService.class);
		workCalendarService.getCalendarRule();
	}
}
