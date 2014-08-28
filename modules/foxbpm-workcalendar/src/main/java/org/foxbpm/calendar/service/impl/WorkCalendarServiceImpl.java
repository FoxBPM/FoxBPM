package org.foxbpm.calendar.service.impl;

import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.impl.ServiceImpl;
public class WorkCalendarServiceImpl  extends ServiceImpl implements WorkCalendarService {

	
	@Override
	public void testAA() {
		System.out.println("service执行");
	}
	
	@Override
	public Class<?> getInterfaceClass() {
		return WorkCalendarService.class;
	}
}
