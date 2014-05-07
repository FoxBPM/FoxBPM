package org.foxbpm.engine.impl.cmd;

import java.util.List;

import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.impl.identity.Authentication;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class TestCmd implements Command<String>{

	private String params;
	public TestCmd(String params) {
		this.params = params;
	}
	public String execute(CommandContext commandContext) {
		
//		Context.getCommandContext().
		List<Group> list = Authentication.selectGroupByUserId(params);
		for(Group group :list){
			System.out.println(group.getGroupId()+":"+group.getGroupName()+":"+group.getGroupType());
		}
		return "aa";
	}
}
