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
package org.foxbpm.calendar.service.impl;

import java.util.Date;
import java.util.List;

import org.foxbpm.calendar.mybatis.cmd.AddCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.AddCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.AddCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarPartByIdCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarPartCountCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarPartsByRuleIdCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarRuleByIdCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarRuleCountCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarRulesByTypeIdCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarTypeByIdCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarTypeCountCmd;
import org.foxbpm.calendar.mybatis.cmd.GetWorkCalendarEndTimeCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.impl.ServiceImpl;
public class WorkCalendarServiceImpl  extends ServiceImpl implements WorkCalendarService {
	
	 
	public Class<?> getInterfaceClass() {
		return WorkCalendarService.class;
	}

	 
	public void addCalendarType(CalendarTypeEntity calendarTypeEntity) {
		commandExecutor.execute(new AddCalendarTypeCmd(calendarTypeEntity));
	}

	 
	public void updateCalendarType(CalendarTypeEntity calendarTypeEntity) {
		commandExecutor.execute(new UpdateCalendarTypeCmd(calendarTypeEntity));
	}

	 
	public void deleteCalendarType(String id) {
		commandExecutor.execute(new DeleteCalendarTypeCmd(id));
	}

	 
	public void addCalendarRule(CalendarRuleEntity calendarRuleEntity) {
		commandExecutor.execute(new AddCalendarRuleCmd(calendarRuleEntity));
	}

	 
	public void updateCalendarRule(CalendarRuleEntity calendarRuleEntity) {
		commandExecutor.execute(new UpdateCalendarRuleCmd(calendarRuleEntity));
	}

	 
	public void deleteCalendarRule(String id) {
		commandExecutor.execute(new DeleteCalendarRuleCmd(id));
	}

	@SuppressWarnings("unchecked")
	 
	public List<CalendarRuleEntity> getCalendarRule(int pageIndex, int pageSize, String idLike, String nameLike) {
		return (List<CalendarRuleEntity>) commandExecutor.execute(new GetCalendarRuleCmd(pageIndex, pageSize, idLike, nameLike));
	}

	 
	public void addCalendarPart(CalendarPartEntity calendarPartEntity) {
		commandExecutor.execute(new AddCalendarPartCmd(calendarPartEntity));
	}

	 
	public void updateCalendarPart(CalendarPartEntity calendarPartEntity) {
		commandExecutor.execute(new UpdateCalendarPartCmd(calendarPartEntity));
	}

	 
	public void deleteCalendarPart(String id) {
		commandExecutor.execute(new DeleteCalendarPartCmd(id));
	}

	@SuppressWarnings("unchecked")
	 
	public List<CalendarPartEntity> getCalendarPart(int pageIndex, int pageSize, String idLike) {
		return (List<CalendarPartEntity>) commandExecutor.execute(new GetCalendarPartCmd(pageIndex, pageSize, idLike));
	}

	 
	public Long getCalendarTypeCount(String idLike, String nameLike) {
		return commandExecutor.execute(new GetCalendarTypeCountCmd(idLike, nameLike));
	}

	 
	public Long getCalendarRuleCount(String idLike, String nameLike) {
		return commandExecutor.execute(new GetCalendarRuleCountCmd(idLike, nameLike));
	}

	 
	public Long getCalendarPartCount(String idLike) {
		return commandExecutor.execute(new GetCalendarPartCountCmd(idLike));
	}

	 
	public CalendarTypeEntity getCalendarTypeById(String id) {
		return commandExecutor.execute(new GetCalendarTypeByIdCmd(id));
	}

	@SuppressWarnings("unchecked")
	 
	public List<CalendarTypeEntity> getCalendarType(int pageIndex, int pageSize, String idLike, String nameLike) {
		return (List<CalendarTypeEntity>) commandExecutor.execute(new GetCalendarTypeCmd(pageIndex, pageSize, idLike, nameLike));
	}

	 
	public CalendarRuleEntity getCalendarRuleById(String id) {
		return commandExecutor.execute(new GetCalendarRuleByIdCmd(id));
	}

	 
	public CalendarPartEntity getCalendarPartById(String id) {
		return commandExecutor.execute(new GetCalendarPartByIdCmd(id));
	}
	
	public Date getDueTime(String ruleId,Date begin, double hours) {
		return commandExecutor.execute(new GetWorkCalendarEndTimeCmd( begin, hours,ruleId));
	}
	
	 
	public List<CalendarRuleEntity> getCalendarRulesByTypeId(String typeId) {
		return commandExecutor.execute(new GetCalendarRulesByTypeIdCmd(typeId));
	}

	 
	public List<CalendarPartEntity> getCalendarPartsByRuleId(String ruleId) {
		return commandExecutor.execute(new GetCalendarPartsByRuleIdCmd(ruleId));
	}
	
	
}
