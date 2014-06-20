/**
 * Copyright 1996-2014 FoxBPM ORG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author kenshin
 */
package org.foxbpm.engine.impl.identity;

import java.util.List;

import org.foxbpm.engine.ProcessEngine;
import org.foxbpm.engine.ProcessEngineManagement;
import org.foxbpm.engine.cache.Cache;
import org.foxbpm.engine.exception.FoxBPMObjectNotFoundException;
import org.foxbpm.engine.identity.Group;
import org.foxbpm.engine.identity.User;
import org.foxbpm.engine.impl.cmd.FindUserByIdNoCacheCmd;
import org.foxbpm.engine.impl.interceptor.CommandExecutor;

public abstract class Authentication {

	static ThreadLocal<String> authenticatedUserIdThreadLocal = new ThreadLocal<String>();

	public static void setAuthenticatedUserId(String authenticatedUserId) {
		authenticatedUserIdThreadLocal.set(authenticatedUserId);
	}

	public static String getAuthenticatedUserId() {
		return authenticatedUserIdThreadLocal.get();
	}
	
	public static List<Group> selectGroupByUserId(String userId) {
		User user = selectUserByUserId(userId);
		if(user == null){
			throw new FoxBPMObjectNotFoundException("为找到ID:"+userId+"的用户！");
		}
		return user.getGroups();
	}
	
	public static User selectUserByUserId(String userId){
		ProcessEngine processEngine = ProcessEngineManagement.getDefaultProcessEngine();
		
		Cache<User> userCache = processEngine.getProcessEngineConfiguration().getUserCache();
		User result = userCache.get(userId);
		if(result != null){
			return result;
		}
		CommandExecutor commandExecutor = processEngine.getProcessEngineConfiguration().getCommandExecutor();
		User user = commandExecutor.execute(new FindUserByIdNoCacheCmd(userId));
		userCache.add(userId, user);
		return user;
	}

}
