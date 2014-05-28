package org.foxbpm.engine.impl.cmd;

import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.identity.UserEntityManager;
import org.foxbpm.engine.impl.interceptor.Command;
import org.foxbpm.engine.impl.interceptor.CommandContext;

public class FindUserByIdCmd implements Command<User> {

	
	private String userId;
	public FindUserByIdCmd(String userId) {
		this.userId = userId;
	}
	
	@Override
	public User execute(CommandContext commandContext) {
		UserEntityManager userEntityManager = commandContext.getUserEntityManager();
		return userEntityManager.findUserById(userId);
	}
}
