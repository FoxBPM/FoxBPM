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
package org.foxbpm.calendar.service.calendar;

import java.net.URLDecoder;
import java.util.Map;

import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.calendar.rest.common.AbstractRestResource;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.util.StringUtil;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public class CalendarTypeResource extends AbstractRestResource {
	@Get
	public Object getCalendarTypeById() {
		// 获取参数
		String id = getAttribute("calendartypeId");
		if (StringUtil.isNotEmpty(id)) {
			// 获取服务
			WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
			CalendarTypeEntity calendarTypeEntity = workCalendarService.getCalendarTypeById(id);
			if (null != calendarTypeEntity) {
				return calendarTypeEntity;
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@Put
	public String updateCalendarType(Representation entity) {
		// 获取参数
		String id = getAttribute("calendartypeId");
		Map<String, String> paramsMap = getRequestParams(entity);
		String name = URLDecoder.decode(paramsMap.get("name"));
		if (StringUtil.isNotEmpty(id)) {
			CalendarTypeEntity calendarTypeEntity = new CalendarTypeEntity(id);
			if (StringUtil.isNotEmpty(name)) {
				calendarTypeEntity.setName(name);
			}
			// 获取服务
			WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
			// 构造用户信息
			workCalendarService.updateCalendarType(calendarTypeEntity);
		}
		return "{}";
	}
	
	@Delete
	public String deleteCalendarType() {
		// 获取参数
		String id = getAttribute("calendartypeId");
		WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		workCalendarService.deleteCalendarType(id);
		return "SUCCESS";
	}
}
