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
 * @author kenshin
 */
package org.foxbpm.engine.impl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.foxbpm.engine.exception.FoxBPMException;

/**
 * @author kenshin
 * 
 */
public class ClockUtil {

	private volatile static Date CURRENT_TIME = null;

	private final static String NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static Date parseStringToDate(String dateTime){
		Date date = null;
		try {
			date = new SimpleDateFormat(NORMAL_FORMAT)
					.parse((String) dateTime);
		} catch (ParseException e) {
			throw new FoxBPMException("日期时间转换错误 parseStringToDate！");
		}
		return date;
	}
	public static void setCurrentTime(Date currentTime) {
		ClockUtil.CURRENT_TIME = currentTime;
	}

	public static void reset() {
		ClockUtil.CURRENT_TIME = null;
	}

	public static Date getCurrentTime() {
		if (CURRENT_TIME != null) {
			return CURRENT_TIME;
		}
		return new Date();
	}

}
