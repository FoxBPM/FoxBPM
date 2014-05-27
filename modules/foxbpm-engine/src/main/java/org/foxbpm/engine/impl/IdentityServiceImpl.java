package org.foxbpm.engine.impl;

import org.foxbpm.engine.IdentityService;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.cmd.FindUser;

public class IdentityServiceImpl  extends ServiceImpl implements IdentityService {

	@Override
	public User getUser(String userId) {
		return  commandExecutor.execute(new FindUser(userId));
	}

}
