package org.foxbpm.calendar.mybatis.cmd;

import java.util.List;

import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class DeleteCalendarTypeCmd implements Command<List<?>> {

	@Override
	public List<?> execute(CommandContext commandContext) {
		return commandContext.getSqlSession().selectList("deleteCalendarTypeById");
	}

}
