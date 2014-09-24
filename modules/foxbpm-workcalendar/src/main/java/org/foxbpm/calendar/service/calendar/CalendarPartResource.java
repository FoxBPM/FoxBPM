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

import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.rest.common.AbstractRestResource;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.util.StringUtil;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public class CalendarPartResource extends AbstractRestResource{
	@Get
	public Object getCalendarRuleById() {
		// 获取参数
		String id = getAttribute("calendarpartId");
		if (StringUtil.isNotEmpty(id)) {
			// 获取服务
			WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
			CalendarPartEntity calendarPartEntity = workCalendarService.getCalendarPartById(id);
			if (null != calendarPartEntity) {
				return calendarPartEntity;
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@Put
	public String updateCalendarRule(Representation entity) {
		// 获取参数
		String id = getAttribute("calendarpartId");
		Map<String, String> paramsMap = getRequestParams(entity);
		String amorpm = URLDecoder.decode(paramsMap.get("amorpm"));
		String starttime = URLDecoder.decode(paramsMap.get("starttime"));
		String endtime = URLDecoder.decode(paramsMap.get("endtime"));
		String ruleid = URLDecoder.decode(paramsMap.get("ruleid"));
		if (StringUtil.isNotEmpty(id)) {
			CalendarPartEntity calendarPartEntity = new CalendarPartEntity(id);
			if (StringUtil.isNotEmpty(amorpm)) {
				calendarPartEntity.setAmorpm(Integer.valueOf(amorpm));
			}
			if (StringUtil.isNotEmpty(starttime)) {
				calendarPartEntity.setStarttime(starttime);
			}
			if (StringUtil.isNotEmpty(endtime)) {
				calendarPartEntity.setEndtime(endtime);
			}
			if (StringUtil.isNotEmpty(ruleid)) {
				calendarPartEntity.setRuleid(ruleid);
			}
			// 获取服务
			WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
			// 构造用户信息
			workCalendarService.updateCalendarPart(calendarPartEntity);
		}
		return "{}";
	}
	
	@Delete
	public String deleteCalendarPart() {
		// 获取参数
		String id = getAttribute("calendarpartId");
		WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		workCalendarService.deleteCalendarPart(id);
		return "SUCCESS";
	}
}
