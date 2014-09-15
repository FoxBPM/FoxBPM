package org.foxbpm.calendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateCalUtils {
	/**
	 * 判断当前日期是星期几<br>
	 * @param pTime 修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static int dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	/**
	 * 判断当前日期是星期几<br>
	 * @param pTime 修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static int dayForWeek(Date pTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(pTime);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	
	/**
	 * 返回0是上午 1是下午
	 * @param date
	 * @return
	 */
	public static int dayForAMorPM(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dateparam = null;
		try {
			dateparam = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateparam);
		
		return calendar.get(Calendar.AM_PM);
	}
	
	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay()
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month)
	{
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	public static void main(String args[]) {
//		int result = 0;
//		try {
//			result = dayForWeek(new Date());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("今天是周" + result);
		System.out.println("现在是0上午 1下午 :===" + dayForAMorPM("2014-02-25 11:41:40"));
	}
	
	
}
