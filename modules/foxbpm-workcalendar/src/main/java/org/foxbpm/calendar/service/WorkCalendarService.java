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
package org.foxbpm.calendar.service;

import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;

public interface WorkCalendarService {

	void addCalendarType(CalendarTypeEntity calendarTypeEntity);
	
	void updateCalendarType(CalendarTypeEntity calendarTypeEntity);
	
	void deleteCalendarType(String id);
	
	List<CalendarTypeEntity> getCalendarType(int pageIndex, int pageSize, String idLike, String nameLike);
	
	CalendarTypeEntity getCalendarTypeById(String id);
	
	Long getCalendarTypeCount(String idLike, String nameLike);
	
	void addCalendarRule();
	
	void updateCalendarRule();
	
	void deleteCalendarRule();
	
	List<CalendarRuleEntity> getCalendarRule(int pageIndex, int pageSize);
	
	Long getCalendarRuleCount(String idLike, String nameLike);
	
	void addCalendarPart();
	
	void updateCalendarPart();

	void deleteCalendarPart();
	
	List<CalendarPartEntity> getCalendarPart(int pageIndex, int pageSize);
	
	Long getCalendarPartCount(String idLike);
}
