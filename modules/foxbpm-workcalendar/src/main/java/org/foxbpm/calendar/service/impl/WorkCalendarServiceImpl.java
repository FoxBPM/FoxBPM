package org.foxbpm.calendar.service.impl;

import java.util.List;

import org.foxbpm.calendar.mybatis.cmd.AddCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.AddCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.AddCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.DeleteCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.GetCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarPartCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarRuleCmd;
import org.foxbpm.calendar.mybatis.cmd.UpdateCalendarTypeCmd;
import org.foxbpm.calendar.mybatis.entity.CalendarPartEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarRuleEntity;
import org.foxbpm.calendar.mybatis.entity.CalendarTypeEntity;
import org.foxbpm.calendar.service.WorkCalendarService;
import org.foxbpm.engine.impl.ServiceImpl;
public class WorkCalendarServiceImpl  extends ServiceImpl implements WorkCalendarService {
	
	@Override
	public Class<?> getInterfaceClass() {
		return WorkCalendarService.class;
	}

	@Override
	public void addCalendarType() {
		commandExecutor.execute(new AddCalendarTypeCmd());
	}

	@Override
	public void updateCalendarType() {
		commandExecutor.execute(new UpdateCalendarTypeCmd());
	}

	@Override
	public void deleteCalendarType() {
		commandExecutor.execute(new DeleteCalendarTypeCmd());
	}

	@Override
	public List<CalendarTypeEntity> getCalendarType() {
		commandExecutor.execute(new GetCalendarTypeCmd());
		return null;
	}

	@Override
	public void addCalendarRule() {
		commandExecutor.execute(new AddCalendarRuleCmd());
	}

	@Override
	public void updateCalendarRule() {
		commandExecutor.execute(new UpdateCalendarRuleCmd());
	}

	@Override
	public void deleteCalendarRule() {
		commandExecutor.execute(new DeleteCalendarRuleCmd());
	}

	@Override
	public List<CalendarRuleEntity> getCalendarRule() {
		commandExecutor.execute(new GetCalendarRuleCmd());
		return null;
	}

	@Override
	public void addCalendarPart() {
		commandExecutor.execute(new AddCalendarPartCmd());
	}

	@Override
	public void updateCalendarPart() {
		commandExecutor.execute(new UpdateCalendarPartCmd());
	}

	@Override
	public void deleteCalendarPart() {
		commandExecutor.execute(new DeleteCalendarPartCmd());
	}

	@Override
	public List<CalendarPartEntity> getCalendarPart() {
		commandExecutor.execute(new GetCalendarPartCmd());
		return null;
	}
}
