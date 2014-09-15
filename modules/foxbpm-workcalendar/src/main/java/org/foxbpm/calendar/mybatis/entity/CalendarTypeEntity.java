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
package org.foxbpm.calendar.mybatis.entity;

import java.util.ArrayList;
import java.util.List;


public class CalendarTypeEntity{
	private String id;
	private String name;
	private List<CalendarRuleEntity> calendarRuleEntities;
	
	public CalendarTypeEntity() {
	}
	
	public CalendarTypeEntity(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<CalendarRuleEntity> getCalendarRuleEntities() {
		if(calendarRuleEntities == null) {
			calendarRuleEntities = new ArrayList<CalendarRuleEntity>();
		}
		return calendarRuleEntities;
	}

	public void setCalendarRuleEntities(List<CalendarRuleEntity> calendarRuleEntities) {
		this.calendarRuleEntities = calendarRuleEntities;
	}

}
