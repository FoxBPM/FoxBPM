package org.foxbpm.calendar.mybatis.cmd;

import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetCalendarPartCmd implements Command<List<?>> {

	@Override
	public List<CalendarPartEntity> execute(CommandContext commandContext) {
		return (List<CalendarPartEntity>) commandContext.getSqlSession().selectList("selectAllCalendarPart");
	}

}
