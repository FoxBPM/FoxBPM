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

public interface WorkCalendarService{

	/**
	 * 添加日历类型
	 * @param calendarTypeEntity
	 */
	void addCalendarType(CalendarTypeEntity calendarTypeEntity);
	
	/**
	 * 更新日历类型
	 * @param calendarTypeEntity
	 */
	void updateCalendarType(CalendarTypeEntity calendarTypeEntity);
	
	/**
	 * 根据ID删除日历类型
	 * @param id
	 */
	void deleteCalendarType(String id);
	
	/**
	 * 查询所有日历类型 带分页
	 * @param pageIndex
	 * @param pageSize
	 * @param idLike
	 * @param nameLike
	 * @return
	 */
	List<CalendarTypeEntity> getCalendarType(int pageIndex, int pageSize, String idLike, String nameLike);
	
	/**
	 * 根据ID查询日历类型
	 * @param id
	 * @return
	 */
	CalendarTypeEntity getCalendarTypeById(String id);
	
	/**
	 * 查询日历类型的条数
	 * @param idLike
	 * @param nameLike
	 * @return
	 */
	Long getCalendarTypeCount(String idLike, String nameLike);
	
	/**
	 * 添加日历规则
	 * @param calendarRuleEntity
	 */
	void addCalendarRule(CalendarRuleEntity calendarRuleEntity);
	
	/**
	 * 更新日历规则
	 * @param calendarRuleEntity
	 */
	void updateCalendarRule(CalendarRuleEntity calendarRuleEntity);
	
	/**
	 * 根据ID删除日历规则
	 * @param id
	 */
	void deleteCalendarRule(String id);
	
	/**
	 * 查询所有日历规则 带分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<CalendarRuleEntity> getCalendarRule(int pageIndex, int pageSize, String idLike, String nameLike);
	
	/**
	 * 根据ID查询日历规则
	 * @param id
	 * @return
	 */
	CalendarRuleEntity getCalendarRuleById(String id);
	
	/**
	 * 查询日历规则的条数
	 * @param idLike
	 * @param nameLike
	 * @return
	 */
	Long getCalendarRuleCount(String idLike, String nameLike);
	
	/**
	 * 添加日历时间
	 * @param calendarPartEntity
	 */
	void addCalendarPart(CalendarPartEntity calendarPartEntity);
	
	/**
	 * 更新日历时间
	 * @param calendarPartEntity
	 */
	void updateCalendarPart(CalendarPartEntity calendarPartEntity);

	/**
	 * 根据ID删除日历时间
	 * @param id
	 */
	void deleteCalendarPart(String id);
	
	/**
	 * 查询所有日历时间 带分页
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<CalendarPartEntity> getCalendarPart(int pageIndex, int pageSize, String idLike);
	
	/**
	 * 根据ID查询日历时间
	 * @param id
	 * @return
	 */
	CalendarPartEntity getCalendarPartById(String id);
	
	/**
	 * 查询日历时间的条数
	 * @param idLike
	 * @return
	 */
	Long getCalendarPartCount(String idLike);
	
	/**
	 * 根据类型ID返回该类型下所有的规则
	 * @param typeId
	 * @return
	 */
	List<CalendarRuleEntity> getCalendarRulesByTypeId(String typeId);
	
	/**
	 * 根据规则ID返回该类型下所有的工作时间
	 * @param ruleId
	 * @return
	 */
	List<CalendarPartEntity> getCalendarPartsByRuleId(String ruleId);
	
	/**
	 * 获取过期时间
	 * @param ruleId 规则编号
	 * @param begin 开始计算时间
	 * @param hours 预计执行时间
	 * @return
	 */
	Date getDueTime(String ruleId,Date begin,double hours);
	
}
