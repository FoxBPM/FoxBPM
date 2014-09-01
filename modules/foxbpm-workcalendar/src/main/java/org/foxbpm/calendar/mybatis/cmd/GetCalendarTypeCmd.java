package org.foxbpm.calendar.mybatis.cmd;

import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetCalendarTypeCmd implements Command<List<?>> {

	@Override
	public List<CalendarTypeEntity> execute(CommandContext commandContext) {
		return (List<CalendarTypeEntity>) commandContext.getSqlSession().selectList("selectAllCalendarType");
	}

}
