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
package org.foxbpm.calendar.mybatis.cmd;

import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class UpdateCalendarTypeCmd implements Command<Void> {
	private CalendarTypeEntity calendarTypeEntity;
	
	public UpdateCalendarTypeCmd(CalendarTypeEntity calendarTypeEntity) {
		this.calendarTypeEntity = calendarTypeEntity;
	}

	 
	public Void execute(CommandContext commandContext) {
		commandContext.getSqlSession().update("updateCalendarType", calendarTypeEntity);
		return null;
	}

}
