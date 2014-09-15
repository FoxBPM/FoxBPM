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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.rest.common.AbstractRestResource;
import org.foxbpm.calendar.rest.common.DataResult;
import org.foxbpm.calendar.rest.common.RestConstants;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.util.StringUtil;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class CalendarRuleCollectionResource extends AbstractRestResource{
	@Get
	public DataResult getCalendarRules(){
		
		Form queryForm = getQuery();
		Set<String> queryNames = queryForm.getNames();
		
		String id = StringUtil.getString(getQueryParameter("id", queryForm));
		String name = StringUtil.getString(getQueryParameter("name", queryForm));
		
		String idLike = null;
		String nameLike = null;
		if (StringUtil.isNotEmpty(id)) {
			idLike = "%" + id + "%";
		}
		if (StringUtil.isNotEmpty(name)) {
			nameLike = "%" + name + "%";
		}
		
		if (queryNames.contains(RestConstants.PAGE_START)) {
			if (queryNames.contains(RestConstants.PAGE_LENGTH)) {
				pageSize = StringUtil.getInt(getQueryParameter(RestConstants.PAGE_LENGTH, queryForm));
			}
			pageIndex = StringUtil.getInt(getQueryParameter(RestConstants.PAGE_START, queryForm)) / pageSize + 1;
		}
		
		if (queryNames.contains(RestConstants.PAGE_INDEX)) {
			pageIndex = StringUtil.getInt(getQueryParameter(RestConstants.PAGE_INDEX, queryForm));
		}
		if (queryNames.contains(RestConstants.PAGE_SIZE)) {
			pageSize = StringUtil.getInt(getQueryParameter(RestConstants.PAGE_SIZE, queryForm));
		}
		
		if(!validationUser())
			return null;
		
		WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		
		DataResult result = new DataResult();
		result.setData(workCalendarService.getCalendarRule(pageIndex, pageSize));
		result.setPageIndex(pageIndex);
		result.setPageSize(pageSize);
		result.setRecordsTotal(workCalendarService.getCalendarRuleCount(idLike, nameLike));
		result.setRecordsFiltered(workCalendarService.getCalendarRuleCount(idLike, nameLike));
		return result;
	}
	
	@Post
	public void addCalendarRule(Representation entity) {
		// 获取参数
//		String id = getAttribute("id");
		Map<String, String> paramsMap = getRequestParams(entity);
		String id =  URLDecoder.decode(paramsMap.get("id"));
		String year =  URLDecoder.decode(paramsMap.get("year"));
		String week =  URLDecoder.decode(paramsMap.get("week"));
		String name = URLDecoder.decode(paramsMap.get("name"));
		String workdate = URLDecoder.decode(paramsMap.get("workdate"));
		String status =  URLDecoder.decode(paramsMap.get("status"));
		String typeid =  URLDecoder.decode(paramsMap.get("typeid"));
		if (StringUtil.isNotEmpty(id)) {
			CalendarRuleEntity calendarRuleEntity = new CalendarRuleEntity(id);
			if (StringUtil.isNotEmpty(year)) {
				calendarRuleEntity.setYear(Integer.valueOf(year));
			}
			if (StringUtil.isNotEmpty(week)) {
				calendarRuleEntity.setWeek(Integer.valueOf(week));
			}
			if (StringUtil.isNotEmpty(name)) {
				calendarRuleEntity.setName(name);
			}
			if (StringUtil.isNotEmpty(workdate)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				try {
					calendarRuleEntity.setWorkdate(dateFormat.parse(workdate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (StringUtil.isNotEmpty(status)) {
				calendarRuleEntity.setStatus(Integer.valueOf(status));
			}
			if (StringUtil.isNotEmpty(typeid)) {
				calendarRuleEntity.setTypeid(typeid);
			}
			// 获取服务
			WorkCalendarService workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
			// 构造用户信息
			workCalendarService.addCalendarRule(calendarRuleEntity);
		}
	}
}
