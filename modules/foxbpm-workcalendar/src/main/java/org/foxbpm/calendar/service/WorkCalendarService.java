package org.foxbpm.calendar.service;

import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;

public interface WorkCalendarService {

	void addCalendarType();
	
	void updateCalendarType();
	
	void deleteCalendarType();
	
	List<CalendarTypeEntity> getCalendarType();
	
	void addCalendarRule();
	
	void updateCalendarRule();
	
	void deleteCalendarRule();
	
	List<CalendarRuleEntity> getCalendarRule();
	
	void addCalendarPart();
	
	void updateCalendarPart();

	void deleteCalendarPart();
	
	List<CalendarPartEntity> getCalendarPart();
}
