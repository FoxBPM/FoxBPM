package org.foxbpm.engine.impl;

import java.util.List;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.cmd.FindUserByIdCmd;
import org.foxbpm.engine.impl.cmd.FindUsersCmd;

public class IdentityServiceImpl  extends ServiceImpl implements IdentityService {

	@Override
	public User getUser(String userId) {
		return  commandExecutor.execute(new FindUserByIdCmd(userId));
	}
	
	@Override
	public List<User> getUsers(String idLike, String nameLike) {
		return commandExecutor.execute(new FindUsersCmd(idLike,nameLike));
	}

}
