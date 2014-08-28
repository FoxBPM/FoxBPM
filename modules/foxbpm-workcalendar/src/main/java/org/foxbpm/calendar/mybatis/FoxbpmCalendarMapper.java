package org.foxbpm.calendar.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.foxbpm.engine.impl.mybatis.FoxbpmMapperConfig;

public class FoxbpmCalendarMapper implements FoxbpmMapperConfig {

	@Override
	public List<String> getMapperConfig() {
		List<String> xmlList = new ArrayList<String>();
		xmlList.add("mybatis/mapping/CalendarType.xml");
		return xmlList;
	}

}
