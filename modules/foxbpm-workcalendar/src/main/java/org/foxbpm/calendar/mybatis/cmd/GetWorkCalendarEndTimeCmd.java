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
import java.util.Collections;
import java.util.Comparator;
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
	public static final long HOURTIME = 1000L * 60 * 60;
	private String userId;
	private Date begin;
	private double hours;
	private WorkCalendarService workCalendarService;
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int hour = 0;
	private int minutes = 0;
	private int seconds = 0;
	private int k=0;
	private CalendarTypeEntity calendarTypeEntity;
	private boolean isAddDay = false;

	public GetWorkCalendarEndTimeCmd(String userId,Date begin,double hours) {
		this.userId = userId;
		this.begin = begin;
		this.hours = hours;
		this.workCalendarService = ProcessEngineManagement.getDefaultProcessEngine().getService(WorkCalendarService.class);
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.timeFormat = new SimpleDateFormat("hh:mm");
	}
	
	@Override
	public Date execute(CommandContext commandContext) {
		//拿到参数中的时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(begin);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);
		hour = calendar.get(Calendar.HOUR);
		minutes = calendar.get(Calendar.MINUTE);
		seconds = calendar.get(Calendar.SECOND);
		
		//根据userId找到对应套用的日历类型
		calendarTypeEntity = getCalendarTypeById(userId);
		
		//初始化日历类型，找到里面所有的工作时间
		initCalendarType(calendarTypeEntity);
		
		CalendarRuleEntity calendarRuleEntity = null;
		CalendarRuleEntity spCalendarRuleEntity = null;
		//从日历类型拿到对应的工作时间
		for (k=0; k<calendarTypeEntity.getCalendarRuleEntities().size();k++) {
			CalendarRuleEntity calRuleEntity = calendarTypeEntity.getCalendarRuleEntities().get(k);
			//判断到年份和周相等   还有种特殊的时间 不按周 按时间点添在最后 或
			if(calRuleEntity.getYear()==year && calRuleEntity.getWeek()==DateCalUtils.dayForWeek(begin)) {
				calendarRuleEntity = calRuleEntity;
			}
			else if((calRuleEntity.getWorkdate() != null && dateFormat.format(calRuleEntity.getWorkdate()).equals(dateFormat.format(begin)))) {
				spCalendarRuleEntity = calRuleEntity;
			}
			
			//如果不在工作时间内 则报错
			if(calendarRuleEntity == null) {
				continue;
			}
			//如果在的话开始算时间
			else {
				Calendar endCalendar = Calendar.getInstance();
				Date endDate = CalculateEndTime(calendarRuleEntity);
				endCalendar.setTime(endDate);
				System.out.println("最终的计算结果为：" + endCalendar.getTime());
				return endDate;
			}
		}
		
//		try {
//		throw new Exception("所给时间不在工作时间内，计算出错");
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
		System.out.println("所给时间不在工作时间内，计算出错");
		return null;
	}

	private Date CalculateEndTime(CalendarRuleEntity calendarRuleEntity) {
		Date endDate = null;

		for (int j=0;j<calendarRuleEntity.getCalendarPartEntities().size();j++) {
			CalendarPartEntity calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(j);
			//先匹配上午下午 先默认里面只有一个上午一个下午  //TODO 暂时还没试，应该是支持的
			if(calendarPartEntity.getAmorpm() == DateCalUtils.dayForAMorPM(begin)) {
				try {
					Calendar formatCalendar = Calendar.getInstance();

					//时间段开始的毫秒数
					long startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
					formatCalendar.setTimeInMillis(startTime);
					System.out.println("开始时间段为" + formatCalendar.getTime());

					//时间段结束的毫秒数
					long endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
					formatCalendar.setTimeInMillis(endTime);
					System.out.println("结束时间段为" + formatCalendar.getTime());
					
					//传过来时间的毫秒数
					long beginTime = begin.getTime();
					formatCalendar.setTime(begin);
					System.out.println("参数开始时间段为" + formatCalendar.getTime());
					
					//传过来的时间加上小时数的毫秒数
					long beginEndTime = (long) (hours * this.HOURTIME + beginTime);
					formatCalendar.setTimeInMillis(beginEndTime);
					System.out.println("预计结束时间段为" + formatCalendar.getTime());
					
					endDate = CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, j);
					if(endDate==null) {
						System.out.println("计算出错");
						break;
					}
					
					return endDate;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			//计算当前所给时间到本天工作时间结束还有多长时间
			
			//如果工作时间够用则直接把时间相加 （不行，中间可能时间段是隔开的）
			
			//如果工作时间不够用，往后推最近的工作时间再次计算。
			}
		return endDate;
	}
	
	private Date CalculateTime(long startTime, long endTime, long beginTime, long beginEndTime, CalendarRuleEntity calendarRuleEntity, int i) {
		Calendar endCalendar = Calendar.getInstance();
		//1、不在时间段内
		if((beginTime<startTime && beginEndTime<startTime)) {
			//还没到工作开始时间，找本天工作时间开始算
			CalendarPartEntity calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(i);
			try {
				startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
				endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
			
				beginTime = startTime;
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(beginTime);
				begin = calendar.getTime();
				beginEndTime = (long) (beginTime + hours * HOURTIME);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//再次计算
			return CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, i + 1);
		}
		//如果是已经是超过时间段结束时间的，找下一个时间段
		else if((beginTime>endTime && beginEndTime>endTime)) {
			//找下一个时间段
			if(i+1<calendarRuleEntity.getCalendarPartEntities().size()) {
				CalendarPartEntity calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(i+1);
				try {
					startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
					endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
				
					beginTime = startTime;
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(beginTime);
					begin = calendar.getTime();
					beginEndTime = (long) (beginTime + hours * HOURTIME);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//再次计算
				return CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, i + 1);
			}
			//如果已经加过天数，则开始相减
			if(isAddDay) {
				CalendarPartEntity calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(0);
				try {
					startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
					endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
					beginTime = startTime;
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(beginTime);
					begin = calendar.getTime();
					beginEndTime = (long) (beginTime + hours * HOURTIME);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//再次计算
				isAddDay = false;
				return CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, i);
			}
			
			//如果这天全部没有 需要往后推工作时间
			else {
				if(k+1 < calendarTypeEntity.getCalendarRuleEntities().size()) {
					CalendarRuleEntity calendarRuleEntity2 = calendarTypeEntity.getCalendarRuleEntities().get(k+1);
					day +=1;
					isAddDay = true;
					try {
						startTime = getCalculateTime(calendarRuleEntity2.getCalendarPartEntities().get(0).getStarttime(), calendarRuleEntity2.getCalendarPartEntities().get(0).getAmorpm());
						endTime = getCalculateTime(calendarRuleEntity2.getCalendarPartEntities().get(0).getEndtime(), calendarRuleEntity2.getCalendarPartEntities().get(0).getAmorpm());
						beginTime = startTime;
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(beginTime);
						begin = calendar.getTime();
						beginEndTime = (long) (beginTime + hours * HOURTIME);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return CalculateEndTime(calendarRuleEntity2);
				}
			}
		}
		
		//2、左边超出开始时间段 ，结束时间在时间段内
		else if(beginTime<startTime && beginEndTime>startTime && beginEndTime<=endTime) {
			//用开始的时间段开始计算，
			//如果右边没超出，则是整段时间加上
			if((endTime-startTime)-hours * HOURTIME>0) {
				endCalendar.setTimeInMillis((long) (startTime + hours * HOURTIME));
				return endCalendar.getTime();
			}
			//如果右边超出了，先减去这个时间段的工作时间，再找下一个时间段
			else {
				if(i+1<calendarRuleEntity.getCalendarPartEntities().size()) {
					CalendarPartEntity calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(i+1);
					try {
						beginEndTime = (long) (hours * HOURTIME - (endTime - startTime));
						hours = hours - ((double)(endTime-startTime)/(HOURTIME));
						startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
						endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
						beginTime = startTime;
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(beginTime);
						begin = calendar.getTime();
						beginEndTime += startTime;
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					//再次计算
					return CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, i + 1);
				}
			}
		}
		
		//3、正好两边都在时间段内
		else if(startTime<=beginTime && beginEndTime>startTime && beginEndTime<=endTime) {
			//直接开始加时间
			endCalendar.setTimeInMillis(beginEndTime);
		}
		
		//4、右边超出时间段
		else if(startTime<=beginTime && beginEndTime>endTime) {
				CalendarPartEntity calendarPartEntity = null;
				if(i+1<calendarRuleEntity.getCalendarPartEntities().size()) {
					calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(i+1);
					try {
						//如果开始时间刚好，则就这个时间段开始算 可以整块减掉工作时间
						if(startTime == beginTime) {
							beginEndTime = (long) (hours * HOURTIME - (endTime - startTime));
							hours = hours - ((double)(endTime-startTime)/(HOURTIME));
						}
						//否则只能拿工作结束时间减去开始时间
						else {
							beginEndTime = (long) (hours * HOURTIME - (endTime - beginTime));
							hours = hours - ((double)(endTime - beginTime)/(HOURTIME));
						}
						
						startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
						endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
						beginTime = startTime;
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(beginTime);
						begin = calendar.getTime();
						beginEndTime += startTime;
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					//再次计算
					return CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, i + 1);
				}
				
			//如果本天的时间段都不够用了，则加一天再算
			else{
				if(k+1 < calendarTypeEntity.getCalendarRuleEntities().size()) {
					CalendarRuleEntity calendarRuleEntity2 = calendarTypeEntity.getCalendarRuleEntities().get(k+1);
					day +=1;
					try {
						hours = hours - ((double)(endTime-startTime)/(HOURTIME));
						startTime = getCalculateTime(calendarRuleEntity2.getCalendarPartEntities().get(0).getStarttime(), calendarRuleEntity2.getCalendarPartEntities().get(0).getAmorpm());
						endTime = getCalculateTime(calendarRuleEntity2.getCalendarPartEntities().get(0).getEndtime(), calendarRuleEntity2.getCalendarPartEntities().get(0).getAmorpm());
						beginTime = startTime;
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(beginTime);
						begin = calendar.getTime();
						beginEndTime = (long) (beginTime + hours * HOURTIME);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return CalculateEndTime(calendarRuleEntity2);
				}
			}
		}
		
		//5、时间覆盖了整段的工作时间（两头都超出）
		else if(beginTime<startTime && beginEndTime>endTime) {
			//还没到工作开始时间，找本天工作时间段开始算
			CalendarPartEntity calendarPartEntity = calendarRuleEntity.getCalendarPartEntities().get(i);
			try {
				startTime = getCalculateTime(calendarPartEntity.getStarttime(), calendarPartEntity.getAmorpm());
				endTime = getCalculateTime(calendarPartEntity.getEndtime(), calendarPartEntity.getAmorpm());
				beginTime = startTime;
				beginEndTime = (long) (beginTime + (hours * HOURTIME));
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(beginTime);
				begin = calendar.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//再次计算
			return CalculateTime(startTime, endTime, beginTime, beginEndTime, calendarRuleEntity, i);
		}
		
		return endCalendar.getTime();
	}

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
			
			//给时间段排序
			Collections.sort(calendarPartEntities, new Comparator<CalendarPartEntity>() {

				@Override
				public int compare(CalendarPartEntity o1, CalendarPartEntity o2) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
					long o1s = 0;
					long o2s = 0;
					try {
						Calendar calendar = Calendar.getInstance();
						Date o1d = simpleDateFormat.parse(o1.getStarttime());
						calendar.setTime(o1d);
						//"12点和0点特殊对待上午下午"
						if(o1.getStarttime().equals("12:00")) {
							calendar.set(Calendar.AM_PM, 1);
						}
						else if(o1.getStarttime().equals("00:00")) {
							calendar.set(Calendar.AM_PM, 0);
						}else {
							calendar.set(Calendar.AM_PM, o1.getAmorpm());
						}
						
						o1s = calendar.getTimeInMillis();
//						System.out.println("===========日期1:" + calendar.getTime());
						
						Date o2d = simpleDateFormat.parse(o2.getStarttime());
						calendar.setTime(o2d);
						//"12点和0点特殊对待上午下午"
						if(o2.getStarttime().equals("12:00")) {
							calendar.set(Calendar.AM_PM, 1);
						}
						else if(o2.getStarttime().equals("00:00")) {
							calendar.set(Calendar.AM_PM, 0);
						}else {
							calendar.set(Calendar.AM_PM, o2.getAmorpm());
						}
						
						o2s = calendar.getTimeInMillis();
						calendar.getTime();
//						System.out.println("============日期2:" + calendar.getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return o1s<o2s==true?0:1;
				}
			});
			
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

	/**
	 * 获取毫秒时间用于计算
	 * @param date
	 * @param amorpm
	 * @return
	 * @throws ParseException
	 */
	private long getCalculateTime(String date, int amorpm) throws ParseException {
		Date startDate = timeFormat.parse(date);
		Calendar formatCalendar = Calendar.getInstance();
		formatCalendar.setTime(startDate);
		formatCalendar.set(Calendar.YEAR, year);
		formatCalendar.set(Calendar.MONTH, month-1);
		formatCalendar.set(Calendar.DATE, day);
		//"12点和0点特殊对待上午下午"
		if(date.equals("12:00")) {
			formatCalendar.set(Calendar.AM_PM, 1);
		}
		else if(date.equals("00:00")) {
			formatCalendar.set(Calendar.AM_PM, 0);
		}else {
			formatCalendar.set(Calendar.AM_PM, amorpm);
		}
		
		//时间段开始的毫秒数
		long startTime = formatCalendar.getTimeInMillis();
		
		return startTime;
	}
	
}
