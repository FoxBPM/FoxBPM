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

import java.util.List;

import org.foxbpm.calendar.mybatis.cmd.AddCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.AddCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.AddCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.impl.ServiceImpl;
public class WorkCalendarServiceImpl  extends ServiceImpl implements WorkCalendarService {
	
	@Override
	public Class<?> getInterfaceClass() {
		return WorkCalendarService.class;
	}

	@Override
	public void addCalendarType() {
		commandExecutor.execute(new AddCalendarTypeCmd());
	}

	@Override
	public void updateCalendarType() {
		commandExecutor.execute(new UpdateCalendarTypeCmd());
	}

	@Override
	public void deleteCalendarType() {
		commandExecutor.execute(new DeleteCalendarTypeCmd());
	}

	@Override
	public List<CalendarTypeEntity> getCalendarType() {
		return (List<CalendarTypeEntity>) commandExecutor.execute(new GetCalendarTypeCmd());
	}

	@Override
	public void addCalendarRule() {
		commandExecutor.execute(new AddCalendarRuleCmd());
	}

	@Override
	public void updateCalendarRule() {
		commandExecutor.execute(new UpdateCalendarRuleCmd());
	}

	@Override
	public void deleteCalendarRule() {
		commandExecutor.execute(new DeleteCalendarRuleCmd());
	}

	@Override
	public List<CalendarRuleEntity> getCalendarRule() {
		return (List<CalendarRuleEntity>) commandExecutor.execute(new GetCalendarRuleCmd());
	}

	@Override
	public void addCalendarPart() {
		commandExecutor.execute(new AddCalendarPartCmd());
	}

	@Override
	public void updateCalendarPart() {
		commandExecutor.execute(new UpdateCalendarPartCmd());
	}

	@Override
	public void deleteCalendarPart() {
		commandExecutor.execute(new DeleteCalendarPartCmd());
	}

	@Override
	public List<CalendarPartEntity> getCalendarPart() {
		return (List<CalendarPartEntity>) commandExecutor.execute(new GetCalendarPartCmd());
	}
}
