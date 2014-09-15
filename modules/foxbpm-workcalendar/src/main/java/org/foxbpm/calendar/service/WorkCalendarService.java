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

import java.util.Date;
import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.engine.impl.entity.GroupEntity;

public interface WorkCalendarService {

	void addCalendarType(CalendarTypeEntity calendarTypeEntity);
	
	void updateCalendarType(CalendarTypeEntity calendarTypeEntity);
	
	void deleteCalendarType(String id);
	
	List<CalendarTypeEntity> getCalendarType(int pageIndex, int pageSize, String idLike, String nameLike);
	
	CalendarTypeEntity getCalendarTypeById(String id);
	
	Long getCalendarTypeCount(String idLike, String nameLike);
	
	void addCalendarRule(CalendarRuleEntity calendarRuleEntity);
	
	void updateCalendarRule(CalendarRuleEntity calendarRuleEntity);
	
	void deleteCalendarRule(String id);
	
	List<CalendarRuleEntity> getCalendarRule(int pageIndex, int pageSize);
	
	CalendarRuleEntity getCalendarRuleById(String id);
	
	Long getCalendarRuleCount(String idLike, String nameLike);
	
	void addCalendarPart(CalendarPartEntity calendarPartEntity);
	
	void updateCalendarPart(CalendarPartEntity calendarPartEntity);

	void deleteCalendarPart(String id);
	
	List<CalendarPartEntity> getCalendarPart(int pageIndex, int pageSize);
	
	CalendarPartEntity getCalendarPartById(String id);
	
	Long getCalendarPartCount(String idLike);
	
	/**
	 * 查询截止时间
	 * @param userId 用户编号
	 * @param begin 开始时间
	 * @param hours 间隔时间
	 * @return
	 */
	Date getDueTime(String userId,Date begin,double hours);
	
	/**
	 * 查询截止时间
	 * @param group 组【id:组编号，type:组类型（部门，角色）】
	 * @param begin 开始时间
	 * @param hours 间隔时间
	 * @return
	 */
	Date getDueTime(GroupEntity group,Date begin,double hours);
}
