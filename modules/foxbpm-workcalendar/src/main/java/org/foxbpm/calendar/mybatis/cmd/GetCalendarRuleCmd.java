package org.foxbpm.calendar.mybatis.cmd;

import java.util.List;

import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class GetCalendarRuleCmd implements Command<List<?>> {

	@Override
	public List<CalendarRuleEntity> execute(CommandContext commandContext) {
		return (List<CalendarRuleEntity>) commandContext.getSqlSession().selectList("selectAllCalendarRule");
	}

}
