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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.calendar.utils.DateCalUtils;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetWorkCalendarEndTimeCmd implements Command<Date> {
	private String userId;
	private Date begin;
	private double hours;
	private WorkCalendarService workCalendarService;
	private SimpleDateFormat simpleDateFormat;

	public GetWorkCalendarEndTimeCmd(String userId,Date begin,double hours) {
		this.userId = userId;
		this.begin = begin;
		this.hours = hours;
		this.workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		this.simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
	}
	
	@Override
	public Date execute(CommandContext commandContext) {
		//拿到参数中的时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int hour = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		
		//根据userId找到对应套用的日历类型
		CalendarTypeEntity calendarTypeEntity = getCalendarTypeById(userId);
		
		//初始化日历类型，找到里面所有的工作时间
		initCalendarType(calendarTypeEntity);
		
		CalendarRuleEntity calendarRuleEntity = null;
		//从日历类型拿到对应的工作时间
		for (CalendarRuleEntity calRuleEntity : calendarTypeEntity.getCalendarRuleEntities()) {
			//判断到年份和周相等   还有种特殊的时间 不按周 按时间点添在最后 或
			if(calRuleEntity.getYear()==year && calRuleEntity.getWeek()==DateCalUtils.dayForWeek(begin) || simpleDateFormat.format(calRuleEntity.getWorkdate()).equals(simpleDateFormat.format(begin))) {
				calendarRuleEntity = calRuleEntity;
			}
			
		}
		
		//如果不在工作时间内，找最近的工作时间
		if(calendarRuleEntity == null) {
			try {
				throw new Exception("所给时间不在工作时间内，计算出错");
			} catch (Exception e) {
				e.printStackTrace();
			}
//			calendarRuleEntity = findNearestWorkDate(year, begin, calendarTypeEntity);
		}
		//如果在的话开始算时间
		else {
			for (CalendarPartEntity calendarPartEntity : calendarRuleEntity.getCalendarPartEntities()) {
				calendarPartEntity.getAmorpm();
				calendarPartEntity.getStarttime();
				calendarPartEntity.getEndtime();
			}
			
			//计算当前所给时间到本天工作时间结束还有多长时间
			try {
				simpleDateFormat.parse(year+month +"");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			calendar.setTime(begin);
			begin.getTime();
			
			//如果工作时间够用则直接把时间相加 （不行，中间可能时间段是隔开的）
			
			//如果工作时间不够用，往后推最近的工作时间再次计算。
		}
		
		
		return null;
	}

//	private CalendarRuleEntity findNearestWorkDate(int year, Date date, CalendarTypeEntity calendarTypeEntity) { CalendarRuleEntity calendarRuleEntity = null;
//		for (CalendarRuleEntity calRuleEntity : calendarTypeEntity.getCalendarRuleEntities()) {
//			//判断到年份和周相等   还有种特殊的时间 不按周 按时间点添在最后 或
//			if(calRuleEntity.getYear()==year && calRuleEntity.getWeek()==DateCalUtils.dayForWeek(begin) || simpleDateFormat.format(calRuleEntity.getWorkdate()).equals(simpleDateFormat.format(begin))) {
//				calendarRuleEntity = calRuleEntity;
//			}
//		}
//		if(calendarRuleEntity == null) {
//			findNearestWorkDate(year, date, calendarTypeEntity);
//		}
//		return calendarRuleEntity;
//	}

	/**
	 * 初始化日历类型
	 * @param calendarTypeEntity
	 */
	private void initCalendarType(CalendarTypeEntity calendarTypeEntity) {
		//找到所有类型ID为当前日历类型的日历规则
		List<CalendarRuleEntity> calendarRuleEntities = workCalendarService.getCalendarRulesByTypeId(calendarTypeEntity.getId());
		
		for (CalendarRuleEntity calendarRuleEntity : calendarRuleEntities) {
			//找到所有规则ID为当前日历规则的日历时间
			List<CalendarPartEntity> calendarPartEntities = workCalendarService.getCalendarPartsByRuleId(calendarRuleEntity.getId());
			calendarRuleEntity.setCalendarPartEntities(calendarPartEntities);
		}
		
		calendarTypeEntity.setCalendarRuleEntities(calendarRuleEntities);
	}

	/**
	 * 根据userId找到对应的日历类型
	 * @param userId2
	 * @return
	 */
	private CalendarTypeEntity getCalendarTypeById(String userId2) {
		return workCalendarService.getCalendarTypeById(userId2);
	}

}
